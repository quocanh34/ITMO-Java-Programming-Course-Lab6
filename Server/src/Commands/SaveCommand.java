package Commands;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import ServerCode.CollectionManager;

public class SaveCommand extends AbstractCommand {

    public SaveCommand(CollectionManager manager) {
        super(manager);
        setDescription("Does nothing. Saving is an automatic process");
    }

    @Override
    public synchronized String execute() {
        try {
            getManager().save();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return "Don`t worry, saving is an automatic process starting after some changes in collection";
    }
}
