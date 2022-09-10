package Commands;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import ServerCode.CollectionManager;


public class ClearCommand extends AbstractCommand {

    public ClearCommand(CollectionManager manager) {
        super(manager);
        setDescription("Removes all elements of the collection.");
    }

    @Override
    public synchronized String execute() {
        getManager().clear();
        try {
            getManager().save();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return "Collection is cleaned.";
    }
}
