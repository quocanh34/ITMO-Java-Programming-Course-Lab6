package ServerCode;

import Commands.*;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ServerConnection implements Runnable {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf1 = new byte[65535];
    private byte[] buf2 = new byte[65535];

    private final CollectionManager serverCollection;
    private final HashMap<String, AbstractCommand> availableCommands;

    ServerConnection(CollectionManager serverCollection){
        try{
            socket = new DatagramSocket(4242);
        }catch(SocketException socketException){
            System.err.println("Host is busy. Try it later.");
            System.exit(1);
        }
        this.serverCollection = serverCollection;
        availableCommands = new HashMap<>();
        availableCommands.put("add", new AddCommand(serverCollection));
        availableCommands.put("add_if_max", new AddIfMaxCommand(serverCollection));
        availableCommands.put("add_if_min", new AddIfMinCommand(serverCollection));
        availableCommands.put("clear", new ClearCommand(serverCollection));
        availableCommands.put("execute_script", new ExecuteScriptCommand(serverCollection));
        availableCommands.put("exit", new ExitCommand(serverCollection));
        availableCommands.put("group_counting_by_id", new GroupCountingByIdCommand(serverCollection));
        availableCommands.put("help", new HelpCommand(serverCollection));
        availableCommands.put("info", new InfoCommand(serverCollection));
        availableCommands.put("max_by_furnish", new MaxByFurnishCommand(serverCollection));
        availableCommands.put("remove_any_by_number_of_rooms", new RemoveAnyByNumberOfRoomsCommand(serverCollection));
        availableCommands.put("remove_by_id", new RemoveByIdCommand(serverCollection));
        availableCommands.put("remove_greater", new RemoveGreaterCommand(serverCollection));
        availableCommands.put("save", new SaveCommand(serverCollection));
        availableCommands.put("show", new ShowCommand(serverCollection));
        availableCommands.put("update_by_id", new UpdateCommand(serverCollection));
    }
    public void run() {
        try {
            running = true;
            while (running) {
                DatagramPacket packet = new DatagramPacket(buf1, buf1.length);
                socket.receive(packet);
                System.out.println("received");
                AbstractCommand errorCommand = new AbstractCommand(null) {
                    @Override
                    public String execute() {
                        return "Unknown command. Write help for receiving list of available commands.";
                    }
                };
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                //packet = new DatagramPacket(buf, buf.length, address, port);
                String command = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Message [" + command + "] is received from client.");
                String[] parsedCommand = command.trim().split(" ", 2);
                String answer;
                if (parsedCommand.length == 1) {
                    answer = availableCommands.getOrDefault(parsedCommand[0], errorCommand).execute();
                } else if (parsedCommand.length == 2) {
                    answer = availableCommands.getOrDefault(parsedCommand[0], errorCommand).execute(parsedCommand[1]);
                } else answer = "Unknown command. Write [help] for receiving list of available commands";
                buf2 = answer.getBytes();
                DatagramPacket sendingPacket = new DatagramPacket(buf2, buf2.length, address, port);
                String check = new String(sendingPacket.getData(), 0, sendingPacket.getLength());
                socket.send(sendingPacket);
                System.out.println("Answer has been recent successfully. Content of answer: ");
                System.out.println(check);
            }
            socket.close();
        } catch (IOException exception) {
            System.err.println(socket + " is disconnected to server.");
        } catch (NoSuchElementException | ArrayIndexOutOfBoundsException exception) {
            System.out.println("Program will be finished now.");
            System.exit(0);
        }
    }

    @Override
    public String toString() {
        return "ServerConnection{" +
                "serverCollection=" + serverCollection +
                ", availableCommands=" + availableCommands +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerConnection)) return false;
        ServerConnection that = (ServerConnection) o;
        return Objects.equals(serverCollection, that.serverCollection) &&
                Objects.equals(availableCommands, that.availableCommands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverCollection, availableCommands);
    }
}

