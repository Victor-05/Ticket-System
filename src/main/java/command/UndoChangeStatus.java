package command;

import actions.Action;
import actions.StatusChanged;
import main.Application;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.TicketStorage;
import users.Developer;
import users.UsersDatabase;

import java.util.ArrayList;

public class UndoChangeStatus extends Command {
    private int ticketID;
    UndoChangeStatus(final CommandInput input) {
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
        Developer user = (Developer) UsersDatabase.getUser(getUsername());
        if (ticket == null) {
            return;
        }
        ArrayList<ReportTicket> assignedTickets = user.getTickets();
        Boolean isOk = false;
        for (ReportTicket x : assignedTickets) {
            if (x.getId() == ticketID) {
                isOk = true;
            }
        }
        if (isOk) {
            String from = "";
            if (ticket.getStatus().equals("IN_PROGRESS")) {
                ticket.setStatus("OPEN");
                from = "IN_PROGRESS";
            } else if (ticket.getStatus().equals("RESOLVED")) {
                ticket.setStatus("IN_PROGRESS");
                from = "RESOLVED";
            } else if (ticket.getStatus().equals("CLOSED")) {
                ticket.setStatus("RESOLVED");
                from = "CLOSED";
                milestoneStorage.changeOpenToClosed(ticketID);
            }
            Action historyAction = new StatusChanged("STATUS_CHANGED",
                    getUsername(), getTimestamp(), from, ticket.getStatus());
            ticket.getHistory().add(historyAction);
        } else {
            Command error = new ErrorCommand(getCommand(), getUsername(),
                    getTimestamp(), "Ticket " + ticketID
                    + " is not assigned to developer " + getUsername() + ".");
            commands.add(error);
        }
    }
}
