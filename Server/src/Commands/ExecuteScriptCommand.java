package Commands;

import ServerCode.CollectionManager;
import java.io.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class ExecuteScriptCommand extends AbstractCommand {

    public ExecuteScriptCommand(CollectionManager manager) {
        super(manager);
        setDescription("Executes script from a file.");
    }

    public String execute(String nameOfFile) {
        try {
            System.out.println("WARNING. To avoid recursion, your file cannot contain execute script commands.");
            BufferedReader reader = new BufferedReader(new FileReader(new File(nameOfFile)));
            StringBuilder message = new StringBuilder();
            String[] finalUserCommand;
            String command;
            while((command = reader.readLine()) != null) {
                finalUserCommand = command.trim().toLowerCase().split(" ", 2);
                //String regex = "//s+";
                switch (finalUserCommand[0]) {
                    case "help":
                        HelpCommand help = new HelpCommand(getManager());
                        message.append(help.execute()).append("\n");
                        break;
                    case "info":
                        InfoCommand info = new InfoCommand(getManager());
                        message.append(info.execute()).append("\n");
                        break;
                    case "show":
                        ShowCommand show = new ShowCommand(getManager());
                        message.append(show.execute()).append("\n");
                        break;
                    case "add":
                        AddCommand add = new AddCommand(getManager());
                        message.append(add.execute(finalUserCommand[1])).append("\n");
                        break;
                    case "update_by_id":
                        UpdateCommand update_by_id = new UpdateCommand(getManager());
                        message.append(update_by_id.execute(finalUserCommand[1])).append("\n");
                        break;
                    case "clear":
                        ClearCommand clear = new ClearCommand(getManager());
                        message.append(clear.execute()).append("\n");
                        break;
                    case "save":
                        SaveCommand save = new SaveCommand(getManager());
                        message.append(save.execute()).append("\n");
                        break;
                    case "execute_script":
                        message.append("This script cannot to contain this command.").append("\n");
                        break;
                    case "exit":
                        ExitCommand exit = new ExitCommand(getManager());
                        message.append(exit.execute()).append("\n");
                        break;
                    case "add_if_max":
                        AddIfMaxCommand add_if_max = new AddIfMaxCommand(getManager());
                        message.append(add_if_max.execute(finalUserCommand[1])).append("\n");
                        break;
                    case "add_if_min":
                        AddIfMinCommand add_if_min = new AddIfMinCommand(getManager());
                        message.append(add_if_min.execute(finalUserCommand[1])).append("\n");
                        break;
                    case "remove_any_by_number_of_rooms":
                        RemoveAnyByNumberOfRoomsCommand remove_any_by_number_of_rooms = new RemoveAnyByNumberOfRoomsCommand(getManager());
                        message.append(remove_any_by_number_of_rooms.execute(finalUserCommand[1])).append("\n");
                        break;
                    case "remove_greater":
                        RemoveGreaterCommand remove_greater = new RemoveGreaterCommand(getManager());
                        message.append(remove_greater.execute(finalUserCommand[1])).append("\n");
                        break;
                    case "remove_by_id":
                        RemoveByIdCommand remove_by_id = new RemoveByIdCommand(getManager());
                        message.append(remove_by_id.execute(finalUserCommand[1])).append("\n");
                        break;
                    case "group_counting_by_id":
                        GroupCountingByIdCommand group_counting_by_id = new GroupCountingByIdCommand(getManager());
                        message.append(group_counting_by_id.execute()).append("\n");
                        break;
                    case "max_by_furnish":
                        MaxByFurnishCommand max_by_furnish = new MaxByFurnishCommand(getManager());
                        message.append(max_by_furnish.execute()).append("\n");
                        break;
                    default:
                        message.append("Unknown command").append("\n");
                        //reader.readLine();
                        break;
                }
            }
            reader.close();
            try {
                getManager().save();
            } catch (ParserConfigurationException | TransformerException e) {
                e.printStackTrace();
            }
            return message.toString();
            //return "Commands are ended.";
        } catch (FileNotFoundException fileNotFoundException) {
            return "File not found. Try again.";
        } catch (IOException ioException) {
            return "File reading exception. Try again.";
        }
    }
}
