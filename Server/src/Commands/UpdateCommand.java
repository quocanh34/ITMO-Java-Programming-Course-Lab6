package Commands;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import Data.Flat;
import ServerCode.CollectionManager;
import java.io.IOException;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class UpdateCommand extends AbstractCommand {

    public UpdateCommand(CollectionManager manager) {
        super(manager);
        setDescription("Updates an element finding it by it`s ID.");
    }

    public String execute (String arg) {
        try {
            Flat newFlat = new XmlMapper().readValue(arg, Flat.class);
            TreeSet<Flat> flats = getManager().getFlats();
            String id = String.valueOf(newFlat.getId());
            for (Flat flat : flats) {
                Long intId = flat.getId();
                String strId = String.valueOf(intId);
                if (strId.equals(id)) {
                    flats.remove(flat);
                    getManager().getFlats().add(newFlat);
                    try {
                        getManager().save();
                    } catch (ParserConfigurationException | TransformerException e) {
                        e.printStackTrace();
                    }
                    return "Element was added successfully.";
                }
            }
            return "Element was not added, because it`s id is not defined in the collection";
        } catch (IOException ioException) {
            try {
                getManager().save();
            } catch (ParserConfigurationException | TransformerException e) {
                e.printStackTrace();
            }
            return "File updating error.";
        }
    }
}
