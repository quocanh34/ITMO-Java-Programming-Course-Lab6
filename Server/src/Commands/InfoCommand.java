package Commands;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import ServerCode.CollectionManager;


public class InfoCommand extends AbstractCommand {


    public InfoCommand(CollectionManager manager) {
        super(manager);
        setDescription("Prints information about the collection.");
    }


    @Override
    public synchronized String execute() {
        StringBuilder result = new StringBuilder();
        result.append(getManager().info());
        try {
            getManager().save();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}