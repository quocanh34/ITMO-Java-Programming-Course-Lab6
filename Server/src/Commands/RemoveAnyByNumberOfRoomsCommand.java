package Commands;

import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import Data.Flat;
import ServerCode.CollectionManager;


public class RemoveAnyByNumberOfRoomsCommand extends AbstractCommand {

    public RemoveAnyByNumberOfRoomsCommand(CollectionManager manager) {
        super(manager);
        setDescription("Removes an element if the number of rooms is similar with one in the collection");
    }


    public String execute (String numberOfRooms) {
        TreeSet<Flat> flats = getManager().getFlats();
        for (Flat flat : flats) {
            Long intNumberOfRooms = flat.getNumberOfRooms();
            String strNumberOfRooms = String.valueOf(intNumberOfRooms);
            if (strNumberOfRooms.equals(numberOfRooms)) {
                flats.remove(flat);
                try {
                    getManager().save();
                } catch (ParserConfigurationException | TransformerException e) {
                    e.printStackTrace();
                }
                return "Element was removed successfully.";
            }
        }
        try {
            getManager().save();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return "Element with this number of rooms is not exist.";
    }
}

