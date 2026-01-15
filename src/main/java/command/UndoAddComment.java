package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.TicketStorage;

import java.util.ArrayList;

public class UndoAddComment extends Command {
    private int ticketID;
    UndoAddComment(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
    }

    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
        ReportTicket ticket = (ReportTicket) ticketStorage.getTicketsById(ticketID);
        if (ticket == null) {
            return;
        }
        if (ticket.getComments().isEmpty()) {
            return;
        }
        ticket.getComments().removeLast();
    }
}
