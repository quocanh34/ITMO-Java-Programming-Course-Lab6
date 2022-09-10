package Commands;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import ServerCode.CollectionManager;

public class GroupCountingByIdCommand extends AbstractCommand {

    public GroupCountingByIdCommand(CollectionManager manager) {
        super(manager);
        setDescription("Groups elements by id and returns amount of element in every group.");
    }


    public synchronized String execute() {
        StringBuilder result = new StringBuilder();
        result.append(getManager().group_counting_by_id());
        try {
            getManager().save();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
