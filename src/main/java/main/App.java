package main;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import command.Command;
import command.CommandFactory;
import command.CommandInput;
import fileio.InputLoader;
import milestone.MilestoneStorage;
import ticket.TicketStorage;
import users.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * main.App represents the main application logic that processes input commands,
 * generates outputs, and writes them to a file
 */
public class App {
    private App() {
    }

    private static final String INPUT_USERS_FIELD = "input/database/users.json";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectWriter WRITER = MAPPER.writer().withDefaultPrettyPrinter();

    /**
     * Runs the application: reads commands from an input file,
     * processes them, generates results, and writes them to an output file
     *
     * @param inputPath path to the input file containing commands
     * @param outputPath path to the file where results should be written
     */
    public static void run(final String inputPath, final String outputPath) throws IOException {
        // feel free to change this if needed
        // however keep 'outputs' variable name to be used for writing
        List<ObjectNode> outputs = new ArrayList<>();

        /*
            TODO 1 :
            Load initial user data and commands. we strongly recommend using jackson library.
            you can use the reading from hw1 as a reference.
            however you can use some of the more advanced features of
            jackson library, available here: https://www.baeldung.com/jackson-annotations
        */
        InputLoader inputLoader = new InputLoader(inputPath);
        ArrayNode output = MAPPER.createArrayNode();
        MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);

        ArrayList<CommandInput> inputs = inputLoader.getCommands();

        TicketStorage ticketStorage = TicketStorage.getInstance();
        UsersDatabase usersDatabase = UsersDatabase.getInstance();
        ArrayList<Command> commands = new ArrayList<>();
        MilestoneStorage milestoneStorage =  MilestoneStorage.getInstance();

        Application app =  new Application();

        UserFactory.create(inputLoader.getUsers(), usersDatabase);

        for (CommandInput x : inputs) {
            Command newCommand = CommandFactory.create(x, app, milestoneStorage, ticketStorage);
            newCommand.execute(app, ticketStorage, commands, milestoneStorage);
        }


        for (Command x : commands) {
            ObjectNode cmdNode = MAPPER.valueToTree(x);
            outputs.add(cmdNode);
        }

//        String prettyJson = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(milestoneStorage.getMilestones());
//        System.out.println(prettyJson);


        // TODO 2: process commands.

        // TODO 3: create objectnodes for output, add them to outputs list.

        // DO NOT CHANGE THIS SECTION IN ANY WAY
        try {
            File outputFile = new File(outputPath);
            outputFile.getParentFile().mkdirs();
            WRITER.withDefaultPrettyPrinter().writeValue(outputFile, outputs);
        } catch (IOException e) {
            System.out.println("error writing to output file: " + e.getMessage());
        }
    }
}
