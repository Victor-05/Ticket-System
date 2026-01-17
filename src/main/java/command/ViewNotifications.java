package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.TicketStorage;
import users.UsersDatabase;

import java.util.ArrayList;

public class ViewNotifications  extends Command {
    ViewNotifications(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
    }

    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
        ticketStorage.reset();
        milestoneStorage.reset();
        UsersDatabase.reset();
    }
}
