package ServerCode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;


import java.io.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;

import Data.*;

public class CollectionManager {

    private final TreeSet<Flat> flats;
    private File xmlCollection;
    private ZonedDateTime initializationDate;
    private boolean wasStart;
    private final HashMap<String, String> commandsInfo;
    {
        wasStart = false;
        flats = new TreeSet<Flat>();

        commandsInfo = new HashMap<>();
        commandsInfo.put("help", " - display help for available commands");
        commandsInfo.put("info", " - print information about the collection to standard output (type, initialization date, number of elements, etc.)");
        commandsInfo.put("show", " - print to standard output all elements of the collection in string representation");
        commandsInfo.put("add", " - add a new element to the collection");
        commandsInfo.put("update_id", " - update the value of the collection element whose id is equal to the given one");
        commandsInfo.put("remove_by_id", " - remove an element from the collection by its id    ");
        commandsInfo.put("clear", " - clear collection");
        commandsInfo.put("save", " - save collection to file");
        commandsInfo.put("execute_script", " - read and execute a script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode.");
        commandsInfo.put("exit", " - end program (without saving to file)");
        commandsInfo.put("add_if_max", " - add a new element to the collection if its value is greater than the value of the largest element in this collection");
        commandsInfo.put("add_if_min", " - add a new element to the collection if its value is less than the smallest element of this collection");
        commandsInfo.put("remove_greater", " - remove from the collection all elements greater than the given");
        commandsInfo.put("remove_any_by_number_of_rooms", " - remove one element from the collection whose numberOfRooms field value is equivalent to the given one");
        commandsInfo.put("max_by_furnish", " - output any object from the collection whose furnish field value is the maximum");
        commandsInfo.put("group_counting_by_id", " - group the elements of the collection by the value of the id field, display the number of elements in each group");
    }


    public CollectionManager(String arg) throws SAXException, IOException, ParserConfigurationException {
        // Scanner scanner = new Scanner(System.in);
        try{
            if(checkFile(arg)){
                DocumentBuilderFactory docBuild = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuild.newDocumentBuilder();
                Document doc = docBuilder.parse(new File(arg));
                doc.getDocumentElement().normalize();
                System.out.println("\nRoot element: " + doc.getDocumentElement().getNodeName());
                NodeList flatList = doc.getElementsByTagName("Flat");

                for (int temp = 0; temp < flatList.getLength(); temp++ ){
                    Node flatNode = flatList.item(temp);
                    System.out.println("-----------------------------------");
                    System.out.println("\nCurrent element: " + flatNode.getNodeName());
                    if (flatNode.getNodeType() == Node.ELEMENT_NODE){
                        Element eElement =(Element) flatNode;
                        System.out.println("ID: " + eElement.getElementsByTagName("ID").item(0).getTextContent());
                        System.out.println("Name: " + eElement.getElementsByTagName("name").item(0).getTextContent());

                        NodeList coordinatesList = eElement.getElementsByTagName("Coordinates");
                        for (int t = 0; t < coordinatesList.getLength(); t++){
                            Node coordinatesNote = coordinatesList.item(t);
                            System.out.println(coordinatesNote.getNodeName()+ ": ");
                            if (coordinatesNote.getNodeType() == Node.ELEMENT_NODE){
                                Element coElement = (Element) coordinatesNote;
                                System.out.println("   X coordinate: " + coElement.getElementsByTagName("x").item(0).getTextContent());
                                System.out.println("   Y coordinate: " + coElement.getElementsByTagName("y").item(0).getTextContent());
                            }
                        }

                        System.out.println("Creation Date: " + eElement.getElementsByTagName("creationDate").item(0).getTextContent());
                        System.out.println("Area: " + eElement.getElementsByTagName("area").item(0).getTextContent() + " m^2");
                        System.out.println("Number of Rooms: " + eElement.getElementsByTagName("numberOfRooms").item(0).getTextContent());
                        System.out.println("New Condition: " + eElement.getElementsByTagName("new").item(0).getTextContent());
                        System.out.println("Furnish: " + eElement.getElementsByTagName("furnish").item(0).getTextContent());
                        System.out.println("View: " + eElement.getElementsByTagName("view").item(0).getTextContent());

                        NodeList houseList = eElement.getElementsByTagName("House");
                        for (int t = 0; t < houseList.getLength(); t++){
                            Node houseNote = houseList.item(t);
                            System.out.println(houseNote.getNodeName() + ": ");
                            if (houseNote.getNodeType() == Node.ELEMENT_NODE){
                                Element hoElement = (Element) houseNote;
                                System.out.println("   Name: " + hoElement.getElementsByTagName("name").item(0).getTextContent());
                                System.out.println("   Year: " + hoElement.getElementsByTagName("year").item(0).getTextContent());
                                System.out.println("   Number of floors: " + hoElement.getElementsByTagName("numberOfFloors").item(0).getTextContent());
                                System.out.println("   Number of flats on floor: " + hoElement.getElementsByTagName("numberOfFlatsOnFloor").item(0).getTextContent());
                                System.out.println("   Number of lifts: " + hoElement.getElementsByTagName("numberOfLifts").item(0).getTextContent());
                            }
                        }
                    }
                }
            }
        }catch(NoSuchElementException e){
            System.out.println("Program will be finished now");
            System.exit(0);
        }
    }


    public boolean checkFile(String pathToFile) {
        File checkingFile = new File(pathToFile);
        if(!checkingFile.exists()){
            System.out.println("File not found. Please try again.");
            return false;
        }
        if(!checkingFile.canRead()){
            System.out.println("File cannot be readable. You should have this permission.");
            return false;
        }
        if(!checkingFile.canWrite()){
            System.out.println("File cannot be writable. You should have this permission.");
            return false;
        }
        return true;
    }

    public void help(){
        Map<String, String> sorted = commandsInfo .entrySet().stream().sorted(comparingByKey()).collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
        for(Map.Entry<String, String> entry : sorted.entrySet()){
            System.out.println(entry.getKey() + entry.getValue());
        }
    }

    public String info(){
        StringBuilder result = new StringBuilder();
        result.append("Type of collection: java.util.HashSet" + "\n");
        result.append("Initialization date: " + initializationDate + "\n");
        result.append("Amount of elements in the collection: " + flats.size() + "\n");
        result.append("Collection manager is active: " + wasStart);
        return result.toString();
    }

    public ArrayList<String> show(){

        ArrayList<String> printedFlats = new ArrayList<>();

        System.out.println("The information of all flats in the collection: ");
        if(flats.size() == 0){
            System.out.println("There is no element in the collection now. Please add element.");
        }
        else{
            for(Flat flat : flats){
                printedFlats.add(flat.toString() + "\n");
            }
        }
        return printedFlats;
    }

    public Long receiveId(){
        Long maxId = (long) 0;
        for (Flat flat : flats){
            if(flat.getId() > maxId){
                maxId = flat.getId();
            }
        }
        return maxId +1;
    }

    public String receiveName(){
        for ( ; ; ){
            try{
                System.out.println("Attention! If name will be so long, you may lose some part of this information");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a flat name: ");
                String name = scanner.nextLine().trim();
                if(name.equals("")){
                    System.out.println("This value cannot be empty. Please try again.");
                    continue;
                }
                return name;
            }catch(InputMismatchException inputMismatchException){
                System.out.println("This value must be string. Please try again.");
            }catch(NoSuchElementException noSuchElementException){
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    public Double receiveX(){
        for( ; ; ){
            try{
                System.out.print("Max value is 623. Enter X coordinate: ");
                Scanner scanner = new Scanner(System.in);
                Double x = scanner.nextDouble();
                String xx = Double.toString(x);
                if(x > 623){
                    System.out.println("The maximum value is 623. Please try again.");
                    continue;
                }
                if(xx.equals("")){
                    System.out.println("This value cannot be empty. Please try again.");
                    continue;
                }
                return x;
            }catch(InputMismatchException inputMismatchException){
                System.out.println("This value must be long-type number. Please try again.");

            }catch(NoSuchElementException noSuchElementException){
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    public int receiveY(){
        for( ; ; ){
            try{
                System.out.print("Max value is 623. Enter Y coordinate: ");
                Scanner scanner = new Scanner(System.in);
                int y = scanner.nextInt();
                String yy = Integer.toString(y);
                if(y > 301){
                    System.out.println("The maximum value is 301. Please try again.");
                    continue;
                }
                if(yy.equals("")){
                    System.out.println("This value cannot be empty. Please try again.");
                    continue;
                }
                return y;
            }catch(InputMismatchException inputMismatchException){
                System.out.println("This value must be int-type number. Please try again.");

            }catch(NoSuchElementException noSuchElementException){
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    public Coordinates receiveCoordinates(){
        return new Coordinates(receiveX(), receiveY());
    }

    public String receiveCreationDate(){
        return LocalDateTime.now().toString();
    }

    public Integer receiveArea(){
        for( ; ; ){
            try{
                System.out.print("Value must be greater than 0. Enter flat area: ");
                Scanner scanner = new Scanner(System.in);
                int area = scanner.nextInt();
                String areaString = Integer.toString(area);
                if(area < 0){
                    System.out.println("Value must be greater than 0. Please try again.");
                    continue;
                }
                if(areaString.equals("")){
                    System.out.println("This value cannot be empty. Please try again.");
                    continue;
                }
                return area;
            }catch(InputMismatchException inputMismatchException){
                System.out.println("This value must be int-type number. Please try again.");

            }catch(NoSuchElementException noSuchElementException){
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    public Long receiveNumberOfRooms(){
        for( ; ; ){
            try{
                System.out.print("Value must be greater than 0. Enter number of rooms: ");
                Scanner scanner = new Scanner(System.in);
                Long numberOfRooms = scanner.nextLong();
                String numberOfRoomsString = Long.toString(numberOfRooms);
                if(numberOfRooms < 0){
                    System.out.println("Value must be greater than 0. Please try again.");
                    continue;
                }
                if(numberOfRoomsString.equals("")){
                    System.out.println("This value cannot be empty. Please try again.");
                    continue;
                }
                return numberOfRooms;
            }catch(InputMismatchException inputMismatchException){
                System.out.println("This value must be long-type number. Please try again.");

            }catch(NoSuchElementException noSuchElementException){
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    public Boolean receiveNew(){
        for( ; ; ){
            try{
                System.out.print("Value cannot be null. Please type true/false. The flat is new or not: ");
                Scanner scanner = new Scanner(System.in);
                Boolean newbie = scanner.nextBoolean();
                String newbieString = Boolean.toString(newbie);

                if(newbieString.equals("")){
                    System.out.println("This value cannot be empty. Please try again.");
                    continue;
                }
                return newbie;

            }catch(InputMismatchException inputMismatchException){
                System.out.println("This value must be boolean-type number. Please try again.");

            }catch(NoSuchElementException noSuchElementException){
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    public Furnish receiveFurnish(){
        for ( ; ;){
            try{
                System.out.println("Choose variant of furnish. Enter the number corresponding to the desired option. ");
                System.out.print("Variant: 1 - DESIGNER, 2 - NONE, 3 - LITTLE. Enter 1, 2, or 3:  ");
                Scanner scanner  = new Scanner(System.in);
                int furnishChoose = scanner.nextInt();
                switch (furnishChoose){
                    case 1:
                        return Furnish.DESIGNER;
                    case 2:
                        return Furnish.NONE;
                    case 3:
                        return Furnish.LITTLE;
                }
                System.out.println("You should enter 1, 2, or 3. Please try again.");
            }catch(InputMismatchException inputMismatchException){
                System.out.println("This value must be int-type number. Please try again.");

            }catch(NoSuchElementException noSuchElementException){
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    public View receiveView(){
        for ( ; ;){
            try{
                System.out.println("Choose variant of view. Enter the number corresponding to the desired option. ");
                System.out.print("Variant: 1 - STREET, 2 - PARK, 3 - YARD, 4 - GOOD, 5 - TERRIBLE. Enter 1, 2, 3, 4 or 5:  ");
                Scanner scanner  = new Scanner(System.in);
                int viewChoose = scanner.nextInt();
                switch (viewChoose){
                    case 1:
                        return View.STREET;
                    case 2:
                        return View.PARK;
                    case 3:
                        return View.YARD;
                    case 4:
                        return View.GOOD;
                    case 5:
                        return View.TERRIBLE;
                }
                System.out.println("You should enter 1, 2, 3, 4 or 5. Please try again.");
            }catch(InputMismatchException inputMismatchException){
                System.out.println("This value must be int-type number. Please try again.");

            }catch(NoSuchElementException noSuchElementException){
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    public String receiveNameHouse(){
        for ( ; ; ){
            try{
                System.out.println("Attention! If name will be so long, you may lose some part of this information");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a house name: ");
                String name = scanner.nextLine().trim();
                if(name.equals("")){
                    System.out.println("This value cannot be empty. Please try again.");
                    continue;
                }
                return name;
            }catch(InputMismatchException inputMismatchException){
                System.out.println("This value must be string. Please try again.");
            }catch(NoSuchElementException noSuchElementException){
                System.out.println("Program was stopped successfully.");
                System.exit(1);
                System.exit(1);
            }
        }
    }

    public int receiveYearHouse(){
        for ( ; ; ){
            try{
                System.out.println("The value must be greater than 0. Enter the year which the house was built.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a year: ");
                int year = scanner.nextInt();
                String yearString = scanner.toString();

                if (year < 0){
                    System.out.println("The value must be greater than 0. Please try again.");
                    continue;
                }
                if(yearString.equals("")){
                    System.out.println("This value cannot be empty. Please try again.");
                    continue;
                }
                return year;
            }catch(InputMismatchException inputMismatchException){
                System.out.println("This value must be string. Please try again.");
            }catch(NoSuchElementException noSuchElementException){
                System.out.println("Program was stopped successfully.");
                System.exit(1);
                System.exit(1);
            }
        }
    }

    public Integer receiveNumberOfFloors(){
        for ( ; ; ){
            try{
                System.out.println("Enter the number of floors. The maximum value is 75. ");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the number of floors: ");
                Integer numberOfFloors = scanner.nextInt();
                String numberOfFloorsString = scanner.toString();

                if (numberOfFloors < 0){
                    System.out.println("The value must be greater than 0. Please try again.");
                    continue;
                }
                if (numberOfFloors > 75){
                    System.out.println("The maximum value is 75. Please try again.");
                    continue;
                }
                if(numberOfFloorsString.equals("")){
                    System.out.println("This value cannot be empty. Please try again.");
                    continue;
                }
                return numberOfFloors;
            }catch(InputMismatchException inputMismatchException){
                System.out.println("This value must be string. Please try again.");
            }catch(NoSuchElementException noSuchElementException){
                System.out.println("Program was stopped successfully.");
                System.exit(1);
                System.exit(1);
            }
        }
    }

    public Long receiveNumberOfFlatsOnFloor(){
        for ( ; ; ){
            try{
                System.out.println("Enter the number of flats on floor. The value must be greater than 0. ");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a number of flats on each floor: ");
                Long numberOfFlatOnFloor = scanner.nextLong();
                String numberOfFlatOnFloorString = scanner.toString();

                if (numberOfFlatOnFloor < 0){
                    System.out.println("The value must be greater than 0. Please try again.");
                    continue;
                }
                if(numberOfFlatOnFloorString.equals("")){
                    System.out.println("This value cannot be empty. Please try again.");
                    continue;
                }
                return numberOfFlatOnFloor;
            }catch(InputMismatchException inputMismatchException){
                System.out.println("This value must be string. Please try again.");
            }catch(NoSuchElementException noSuchElementException){
                System.out.println("Program was stopped successfully.");
                System.exit(1);
                System.exit(1);
            }
        }
    }

    public Long receiveNumberOfLifts(){
        for ( ; ; ){
            try{
                System.out.println("Enter the number of lifts. The value must be greater than 0. ");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a number of lifts: ");
                Long numberOfLifts = scanner.nextLong();
                String numberOfLiftsString = scanner.toString();

                if (numberOfLifts < 0){
                    System.out.println("The value must be greater than 0. Please try again.");
                    continue;
                }
                if(numberOfLiftsString.equals("")){
                    System.out.println("This value cannot be empty. Please try again.");
                    continue;
                }
                return numberOfLifts;
            }catch(InputMismatchException inputMismatchException){
                System.out.println("This value must be string. Please try again.");
            }catch(NoSuchElementException noSuchElementException){
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    public House receiveHouse(){
        return new House(receiveNameHouse(), receiveYearHouse(), receiveNumberOfFloors(), receiveNumberOfFlatsOnFloor(), receiveNumberOfLifts());
    }

    public void add(){
        Flat newFlat = new Flat(receiveId(), receiveName(), receiveCoordinates(), receiveCreationDate(), receiveArea(), receiveNumberOfRooms(), receiveNew(), receiveFurnish(), receiveView(), receiveHouse());
        flats.add(newFlat);
    }

    public String update_id(String id){
        for (Flat flat: flats){
            Long longId = flat.getId();
            String strId = String.valueOf(longId);
            if (strId.equals(id)){
                flats.remove(flat);
                Flat updateFlat = new Flat(receiveId(), receiveName(), receiveCoordinates(), receiveCreationDate(), receiveArea(), receiveNumberOfRooms(), receiveNew(), receiveFurnish(), receiveView(), receiveHouse());
                flats.add(updateFlat);
                System.out.println("Element was updated successfully.");
            }
            System.out.println("Element with this ID is not defined. Try again.");
        }
        return null;
    }

    public void remove_id(String id){
        try{
            for (Flat flat: flats){
                Long longId = flat.getId();
                String strId = String.valueOf(longId);
                if (strId.equals(id)){
                    flats.remove(flat);
                    System.out.println("Element was deleted successfully.");
                }
                System.out.println("Element with this ID is not defined.");
            }
        }catch(ConcurrentModificationException concurrentModificationException){
            System.out.println("Command is completed");
        }
    }

    public void clear(){
        flats.clear();
        System.out.println("Collection was cleaned successfully. " );

    }

    public void save() throws ParserConfigurationException, TransformerException{
        String pathToFile = "C:\\Programming\\Programming Laboratory\\Mylab5\\Last Test\\flats.xml";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("Flats");
        document.appendChild(root);

        for(Flat flat: flats){
            Element newflat = document.createElement("Flat");
            root.appendChild(newflat);

            String idString = String.valueOf(flat.getId());
            Element id = document.createElement("ID");
            id.appendChild(document.createTextNode(idString));
            newflat.appendChild(id);

            Element name = document.createElement("Name");
            name.appendChild(document.createTextNode(flat.getName()));
            newflat.appendChild(name);

            Element coordinates  = document.createElement("Coordinates");
            newflat.appendChild(coordinates);

            String xString = String.valueOf(flat.getCoordinates().getX());
            String yString = String.valueOf(flat.getCoordinates().getY());
            Element x = document.createElement("x");
            x.appendChild(document.createTextNode(xString));
            Element y = document.createElement("y");
            y.appendChild(document.createTextNode(yString));

            coordinates.appendChild(x);
            coordinates.appendChild(y);

            String creationDateString = String.valueOf(flat.getCreationDate());
            Element creationDate  = document.createElement("creationDate");
            creationDate.appendChild(document.createTextNode(creationDateString));
            newflat.appendChild(creationDate);

            String areaString = String.valueOf(flat.getArea());
            Element area  = document.createElement("area");
            area.appendChild(document.createTextNode(areaString));
            newflat.appendChild(area);

            String numberOfRoomsString = String.valueOf(flat.getNumberOfRooms());
            Element numberOfRooms  = document.createElement("numberOfRooms");
            numberOfRooms.appendChild(document.createTextNode(numberOfRoomsString));
            newflat.appendChild(numberOfRooms);

            String newbieString = String.valueOf(flat.getNew());
            Element newbie  = document.createElement("new");
            newbie.appendChild(document.createTextNode(newbieString));
            newflat.appendChild(newbie);

            String furnishString = String.valueOf(flat.getFurnish());
            Element furnish  = document.createElement("furnish");
            furnish.appendChild(document.createTextNode(furnishString));
            newflat.appendChild(furnish);

            String viewString = String.valueOf(flat.getView());
            Element view  = document.createElement("view");
            view.appendChild(document.createTextNode(viewString));
            newflat.appendChild(view);

            Element house  = document.createElement("House");
            newflat.appendChild(house);

            String nameString = String.valueOf(flat.getHouse().getName());
            String yearString = String.valueOf(flat.getHouse().getYear());
            String numberOfFloorsString = String.valueOf(flat.getHouse().getNumberOfFloors());
            String numberOfFlatsOnFloorString = String.valueOf(flat.getHouse().getNumberOfFlatsOnFloor());
            String numberOfLiftsString = String.valueOf(flat.getHouse().getNumberOfLifts());

            Element nameHouse = document.createElement("name");
            nameHouse.appendChild(document.createTextNode(nameString));
            Element yearHouse = document.createElement("year");
            yearHouse.appendChild(document.createTextNode(yearString));
            Element numberOfFloorsHouse = document.createElement("numberOfFloors");
            numberOfFloorsHouse.appendChild(document.createTextNode(numberOfFloorsString));
            Element numberOfFlatsOnFloorHouse = document.createElement("numberOfFlatsOnFloor");
            numberOfFlatsOnFloorHouse.appendChild(document.createTextNode(numberOfFlatsOnFloorString));
            Element numberOfLiftsHouse = document.createElement("numberOfLifts");
            numberOfLiftsHouse.appendChild(document.createTextNode(numberOfLiftsString));

            house.appendChild(nameHouse);
            house.appendChild(yearHouse);
            house.appendChild(numberOfFloorsHouse);
            house.appendChild(numberOfFlatsOnFloorHouse);
            house.appendChild(numberOfLiftsHouse);

            System.out.println("Done creating XML File");
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(pathToFile));
        transformer.transform(source, result);
    }


    public void executive_script(String nameOfFile){
        try {
            System.out.println("WARNING. To avoid recursion, your file cannot contain execute script commands.");
            BufferedReader reader = new BufferedReader(new FileReader(nameOfFile));
            String[] finalUserCommand;
            String command;
            while((command = reader.readLine()) != null) {
                finalUserCommand = command.trim().toLowerCase().split(" ", 2);
                switch (finalUserCommand[0]) {
                    case "":
                        break;
                    case "help":
                        help();
                        break;

                    case "info":
                        info();
                        break;

                    case "show":
                        show();
                        break;

                    case "add":
                        add();
                        break;

                    case "update_id":
                        update_id(finalUserCommand[1]);
                        break;

                    case "remove_by_id":
                        remove_id(finalUserCommand[1]);
                        break;

                    case "clear":
                        clear();
                        break;

                    case "save":
                        save();
                        break;

                    case "executive_script":
                        System.out.println("This script cannot to contain this command.");
                        break;

                    case "exit":
                        exit();
                        break;

                    case "add_if_max":
                        System.out.println("Enter characteristics of element, which will be compared with elements in collection.");
                        add_if_max(new Flat(receiveId(), receiveName(), receiveCoordinates(), receiveCreationDate(), receiveArea(), receiveNumberOfRooms(), receiveNew(), receiveFurnish(), receiveView(), receiveHouse()));
                        break;

                    case "add_if_min":
                        System.out.println("Enter characteristics of element, which will be compared with elements in collection.");
                        add_if_min(new Flat(receiveId(), receiveName(), receiveCoordinates(), receiveCreationDate(), receiveArea(), receiveNumberOfRooms(), receiveNew(), receiveFurnish(), receiveView(), receiveHouse()));
                        break;

                    case "remove_greater":
                        System.out.println("Enter characteristics of element, which will be compared with elements in collection.");
                        remove_greater(receiveArea());
                        break;

                    case "remove_any_by_number_of_rooms":
                        System.out.println("Enter characteristics of element, which will be compared with elements in collection.");
                        remove_any_by_number_of_rooms(receiveNumberOfRooms());
                        break;

                    case "max_by_furnish":
                        System.out.println("Enter characteristics of element, which will be compared with elements in collection.");
                        max_by_furnish();
                        break;

                    case "group_counting_by_id":
                        System.out.println("Enter characteristics of element, which will be compared with elements in collection.");
                        group_counting_by_id();
                        break;
                }
                System.out.println("Command is ended.");
            }
            System.out.println("Commands are ended.");
            reader.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found. Try again.");
        } catch (IOException ioException) {
            System.out.println("File reading exception. Try again.");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    public void exit() {
        try {
            System.out.println("Program will be finished now. ");
            TimeUnit.SECONDS.sleep(3);
            System.exit(0);
        } catch (InterruptedException interruptedException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
        }
    }
    public void add_if_max(Flat example){
        Integer maximalArea = Integer.MIN_VALUE;

        for (Flat flat: flats){
            if (flat.getArea() > maximalArea ){
                maximalArea = flat.getArea();
            }
            if (example.getArea() > maximalArea){
                flats.add(example);
                System.out.println("Element was added succesfully.");
            }else{
                System.out.println("Element was not added succesfully because its area is less than the maximum area in the collection.");
            }
        }
    }
    public void add_if_min(Flat example){
        Integer minimalArea = Integer.MAX_VALUE;

        for (Flat flat: flats){
            if (flat.getArea() < minimalArea ){
                minimalArea = flat.getArea();
            }
            if (example.getArea() < minimalArea){
                flats.add(example);
                System.out.println("Element was added succesfully.");
            } else{
                System.out.println("Element was not added succesfully because its area is greater than the minimum area in the collection.");
            }
        }
    }

    public void remove_greater(Integer area){
        Integer counter = 0;
        for (Flat flat: flats){
            if (flat.getArea() > area){
                flats.remove(flat);
                counter += 1;
            }
        }
        System.out.println("Operation was finished successfully. " + counter + " elements were deleted.");
    }

    public void remove_any_by_number_of_rooms(Long numberOfRooms){
        for (Flat flat: flats){
            if (flat.getNumberOfRooms() == numberOfRooms){
                flats.remove(flat);
                System.out.println("The element that has the same value number of rooms was deleted succesfully.");
            }
        }
    }

    public void max_by_furnish(){
        TreeSet<String> furnishMax = new TreeSet<String>();
        for (Flat flat:flats){
            if (flat.getFurnish() == Furnish.DESIGNER){
                furnishMax.add(flat.getName());
            }
        }
        System.out.println("The flat name with max option of furnish: ");
        for (String value : furnishMax){
            System.out.println(value + ", ");
        }
    }

    public String group_counting_by_id(){
        int oneDigitId = 0;
        int twoDigitId = 0;
        int threeDigitId = 0;
        int bigId = 0;
        for (Flat flat: flats){
            if (flat.getId() > 0 & flat.getId() < 10){
                oneDigitId += 1;
            }
            else if (flat.getId() >= 10 & flat.getId() < 10){
                twoDigitId += 1;
            }
            else if (flat.getId() >= 100 & flat.getId() < 1000){
                threeDigitId += 1;
            }
            else{
                bigId += 1;
            }
        }
        StringBuilder result = new StringBuilder();
        result.append("Element of this collection were grouped by ID." + "\n");
        result.append("First group: 1-digit ID. Amount of elements: " + oneDigitId + "\n");
        result.append("Second group: 2-digit ID. Amount of elements: " + twoDigitId + "\n");
        result.append("Third group: 3-digit ID. Amount of elements: " + threeDigitId + "\n");
        result.append("Fourth group: Big ID (greater than 4-digit ID). Amount of elements: " + bigId);
        return result.toString();
    }

    public TreeSet<Flat> getFlats() {
        return flats;
    }

    public HashMap getInfoCommands() {
        return commandsInfo;
    }

}
