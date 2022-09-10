package Commands;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import Data.Flat;
import ServerCode.CollectionManager;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class AddCommand extends AbstractCommand {

    public AddCommand(CollectionManager manager) {
        super(manager);
        setDescription("Add new element to the collection.");
    }

    public synchronized String execute(String arg) {
        try {
            Flat flat = new XmlMapper().readValue(arg, Flat.class);
            getManager().getFlats().add(flat);
            getManager().save();
            return "Element was added successfully.";
        } catch (IOException | ParserConfigurationException | TransformerException ioException) {
            return "Invalid argument";
        }
    }
}
