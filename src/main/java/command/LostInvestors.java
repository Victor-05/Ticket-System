package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.TicketStorage;
import users.UsersDatabase;

import java.util.ArrayList;

public class LostInvestors extends Command {
    LostInvestors(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
    }

    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command>  commands, MilestoneStorage milestoneStorage) {
        ticketStorage.reset();
        milestoneStorage.reset();
        UsersDatabase.reset();
    }
}
