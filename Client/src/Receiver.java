import Data.*;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Receiver {

    public Long receiveId() {
        for ( ; ; ) {
            try {
                return (long) new Random().nextInt(Integer.MAX_VALUE);
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be integer-type of number. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
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

    public Coordinates receiveCoordinates() {
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
}
