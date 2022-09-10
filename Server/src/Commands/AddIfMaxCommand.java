package Commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import Data.*;
import ServerCode.CollectionManager;
import java.io.IOException;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class AddIfMaxCommand extends AbstractCommand{

    public AddIfMaxCommand(CollectionManager manager) {
        super(manager);
        setDescription("Adds an element to collection if it`s area greater than max area in this collection");
    }

    public synchronized String execute(String arg) {
        try {
            TreeSet<Flat> flats = getManager().getFlats();
            long maximalArea = Long.MAX_VALUE;
            for (Flat flat : flats) {
                if (flat.getArea() > maximalArea) {
                    maximalArea = flat.getArea();
                }
            }
            Flat flat = new XmlMapper().readValue(arg, Flat.class);
            if (flat.getArea() > maximalArea) {
                getManager().getFlats().add(flat);
                getManager().save();
                return "Element was added successfully.";

            } else {
                getManager().save();
                return "Element was not added to collection because its area  " +
                        "not greater than lower element`s area in the collection.";
            }
        } catch (JsonProcessingException jsonProcessingException) {
            System.out.println("Something bad with serializing a command.");
        } catch (IOException ioException) {
            System.out.println("Something bad with deserializing object to xml string");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return "Element is not added";
    }
}
