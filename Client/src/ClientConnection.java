import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import Data.Flat;

import java.io.*;
import java.net.*;
import java.time.ZonedDateTime;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientConnection {

    public ClientConnection(){}

    private Scanner fromKeyboard;
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf;

    /** Method which allows to send commands to server and receive an answer */
    public void work() {
        try {
            socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");
            System.out.println("You can write a command.");
            fromKeyboard = new Scanner(System.in);
            while (true) {
                String command = fromKeyboard.nextLine();
                String[] parsedCommand = command.trim().split(" ", 3);
                if (parsedCommand[0].equals("add")) {
                    Receiver receiver = new Receiver();
                    Flat flat = new Flat(receiver.receiveId(), receiver.receiveName(), receiver.receiveCoordinates(),
                            receiver.receiveCreationDate(), receiver.receiveArea(), receiver.receiveNumberOfRooms(),
                            receiver.receiveNew(), receiver.receiveFurnish(), receiver.receiveView(), receiver.receiveHouse());
                    StringBuilder result = new StringBuilder();
                    XmlMapper mapper = new XmlMapper();
                    result.append("add").append(" ").append(mapper.writeValueAsString(flat));
                    buf = result.toString().getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4242);
                    socket.send(packet);
                }
                else if (parsedCommand[0].equals("update_by_id")) {
                    Receiver receiver = new Receiver();
                    int id;
                    for ( ; ; ) {
                        try {
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("Enter ID of element.");
                            id = scanner.nextInt();
                            if (id > 0) {
                                break;
                            } else {
                                System.out.println("ID must be a positive number. Try again.");
                            }
                        } catch (InputMismatchException inputMismatchException) {
                            System.out.println("Value must be a number less than 2 billion and positive.");
                        } catch (NoSuchElementException noSuchElementException) {
                            System.out.println("Program will be finished now.");
                            System.exit(0);
                        }
                    }
                    Flat flat = new Flat(receiver.receiveId(), receiver.receiveName(), receiver.receiveCoordinates(),
                            receiver.receiveCreationDate(), receiver.receiveArea(), receiver.receiveNumberOfRooms(),
                            receiver.receiveNew(), receiver.receiveFurnish(), receiver.receiveView(), receiver.receiveHouse());
                    StringBuilder result = new StringBuilder();
                    XmlMapper mapper = new XmlMapper();
                    result.append("update_by_id").append(" ").append(mapper.writeValueAsString(flat));
                    buf = result.toString().getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4242);
                    socket.send(packet);
                }
                else if (parsedCommand[0].equals("add_if_min")) {
                    Receiver receiver = new Receiver();
                    Flat flat = new Flat(receiver.receiveId(), receiver.receiveName(), receiver.receiveCoordinates(),
                            receiver.receiveCreationDate(), receiver.receiveArea(), receiver.receiveNumberOfRooms(),
                            receiver.receiveNew(), receiver.receiveFurnish(), receiver.receiveView(), receiver.receiveHouse());
                    StringBuilder result = new StringBuilder();
                    XmlMapper mapper = new XmlMapper();
                    result.append("add_if_min").append(" ").append(mapper.writeValueAsString(flat));
                    buf = result.toString().getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4242);
                    socket.send(packet);
                }
                else if (parsedCommand[0].equals("exit")) {
                    buf = command.getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4242);
                    socket.send(packet);
                    System.out.println("Finishing a program");
                    System.exit(0);
                } else {
                    buf = command.getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4242);
                    socket.send(packet);
                }
                byte[] bufOut = new byte[65535];
                DatagramPacket fromServer = new DatagramPacket(bufOut, bufOut.length);
                socket.receive(fromServer);
                String answer = new String(fromServer.getData(), 0, fromServer.getLength());
                System.out.println(answer);
            }
        } catch (IOException exception) {
            System.err.println("Server is not available at the moment. Reconnect? (enter {yes} or {no})?");
            String answer;
            while (!(answer = fromKeyboard.nextLine()).equals("yes")) {
                switch (answer) {
                    case "":
                        break;
                    case "no":
                        exit();
                        break;
                    default: System.out.println("Please write a correct answer.");
                }
            }
            System.out.print("Connecting...");
        } catch (NoSuchElementException | ArrayIndexOutOfBoundsException exception) {
            System.out.println("Program will be finished now.");
            System.exit(0);
        }
    }
    /** Method which allows to switch off a server */
    private void exit() {
        System.out.println("Finishing a program.");
        System.exit(0);
    }
}
