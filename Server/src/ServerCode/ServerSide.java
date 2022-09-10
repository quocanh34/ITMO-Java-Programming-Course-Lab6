package ServerCode;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class ServerSide {
    public static void main(String[] args) {
        try {
            CollectionManager serverCollection = new CollectionManager("C:\\Programming\\Programming Laboratory\\Mylab6\\Server\\flat.xml");
            System.out.println("Starting a server moodle.\nWaiting a client...");
            ServerConnection serverConnection = new ServerConnection(serverCollection);
            serverConnection.run();
        } catch (ArrayIndexOutOfBoundsException | SAXException | IOException | ParserConfigurationException arrayIndexOutOfBoundsException) {
            System.err.println("You forgot enter path to file. Use [java -jar Server.jar /path/to/file] for correct using.");
            System.exit(1);
        }
    }
}

