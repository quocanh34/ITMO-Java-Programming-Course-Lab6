package Commands;

import Data.Flat;
import ServerCode.CollectionManager;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.TreeSet;

public class RemoveByIdCommand extends AbstractCommand {

    public RemoveByIdCommand(CollectionManager manager) {
        super(manager);
        setDescription("Removes an element by its ID.");
    }

    public String execute (String id) {
        TreeSet<Flat> flats = getManager().getFlats();
        for (Flat flat : flats) {
            Long longId = flat.getId();
            String strId = String.valueOf(longId);
            if (strId.equals(id)) {
                flats.remove(flat);
                try {
                    getManager().save();
                } catch (ParserConfigurationException | TransformerException e) {
                    e.printStackTrace();
                }
                return "Element was removed successfully.";
            }
        }
        try {
            getManager().save();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return "Element with this ID is not exist.";
    }
}

