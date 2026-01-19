package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.TicketStorage;
import users.UsersDatabase;

import java.util.ArrayList;

public class LostInvestors extends Command {
    LostInvestors(final CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
    }

    @Override
    public final void execute(final Application app,
                        final TicketStorage ticketStorage,
                        final ArrayList<Command> commands,
                        final MilestoneStorage milestoneStorage) {
        ticketStorage.reset();
        milestoneStorage.reset();
        UsersDatabase.reset();
    }
}
