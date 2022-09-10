package Commands;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import Data.Flat;
import ServerCode.CollectionManager;

import java.io.IOException;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class RemoveGreaterCommand extends AbstractCommand {

    public RemoveGreaterCommand(CollectionManager manager) {
        super(manager);
        setDescription("Removes all elements which area more than current.");
    }

    public synchronized String execute(String arg) {
        try {
            TreeSet<Flat> collection = getManager().getFlats();
            Flat currentFlat = new XmlMapper().readValue(arg, Flat.class);
            if (collection.size() != 0) {
                int beginSize = collection.size();
                collection.removeIf(p -> (p != null && p.compareTo(currentFlat) > 0));
                getManager().save();
                return "Amount of elements which were removed: " + (beginSize - collection.size());
            } else return "Collection is empty. Comparing is impossible.";
        } catch (IOException ioException) {
            return "Comparing error.";
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return arg;
    }
}
