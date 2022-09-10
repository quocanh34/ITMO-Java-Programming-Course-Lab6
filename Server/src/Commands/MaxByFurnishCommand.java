package Commands;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import ServerCode.CollectionManager;


public class MaxByFurnishCommand extends AbstractCommand {


    public MaxByFurnishCommand(CollectionManager manager) {
        super(manager);
        setDescription("Return the max option of furnish.");
    }


    @Override
    public synchronized String execute() {
        StringBuilder result = new StringBuilder();
        result.append("Max option of furnish is designer");
        try {
            getManager().save();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
