package Commands;

import Data.Flat;
import ServerCode.CollectionManager;
import java.util.TreeSet;

public class ShowCommand extends AbstractCommand {

    public ShowCommand(CollectionManager manager) {
        super(manager);
        setDescription("Prints all elements in string representation to standard output.");
    }

    @Override
    public synchronized String execute() {
        TreeSet<Flat> flats = getManager().getFlats();
        StringBuilder result = new StringBuilder();
        for (Flat flat : flats) {
            result.append(flat.toString() + "\n");
        }
        if (flats.size() != 0) {
            return result.toString();
        } else return "Collection is empty.";
    }
}
