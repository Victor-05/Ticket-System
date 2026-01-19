package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.TicketStorage;

import java.util.ArrayList;

public class UndoAddComment extends Command {
    private int ticketID;
    UndoAddComment(final CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        ticketID = input.getTicketID();
    }

    @Override
    public final void execute(final Application app,
                              final TicketStorage ticketStorage,
                              final ArrayList<Command> commands,
                              final MilestoneStorage milestoneStorage) {
        ReportTicket ticket = (ReportTicket) ticketStorage.getTicketsById(ticketID);
        if (ticket == null) {
            return;
        }
        if (ticket.getReportedBy().isEmpty()) {
            Command error = new ErrorCommand(getCommand(),  getUsername(),
                    getTimestamp(), "Comments are not allowed on anonymous tickets.");
            commands.add(error);
            return;
        }
        if (!ticket.getComments().isEmpty()) {
            ticket.getComments().removeLast();
        }
    }
}
