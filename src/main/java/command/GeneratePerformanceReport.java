package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.TicketStorage;

import java.util.ArrayList;

public class GeneratePerformanceReport extends Command {
    GeneratePerformanceReport(final CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
    }

    @Override
    public final void execute(final Application app,
                              final TicketStorage ticketStorage,
                              final ArrayList<Command> commands,
                              final MilestoneStorage milestoneStorage) {
    }
}
