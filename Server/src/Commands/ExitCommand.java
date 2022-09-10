package Commands;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import ServerCode.CollectionManager;

public class ExitCommand extends AbstractCommand {

    public ExitCommand(CollectionManager manager) {
        super(manager);
        setDescription("Switches off a program.");
    }

    @Override
    public synchronized String execute() {
        try {
            getManager().save();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return "Finishing a program.";
    }
}
