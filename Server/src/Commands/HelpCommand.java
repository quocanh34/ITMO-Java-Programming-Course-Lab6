package Commands;

import ServerCode.CollectionManager;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class HelpCommand extends AbstractCommand {


    public HelpCommand(CollectionManager manager) {
        super(manager);
        setDescription("Displays help for available commands.");
    }


    @Override
    public synchronized String execute() {
        StringBuilder result = new StringBuilder();
        HashMap<String, String> commandsInfo = getManager().getInfoCommands();
        for (Map.Entry<String, String> entry : commandsInfo.entrySet()) {
            result.append(entry.getKey() + entry.getValue() + "\n");
        }
        try {
            getManager().save();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
