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
    UndoChangeStatus(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        ticketID = input.getTicketID();
    }

    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
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
            //ticket.setT
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
            Action historyAction = new StatusChanged("STATUS_CHANGED", getUsername(), getTimestamp(), from, ticket.getStatus());
            ticket.getHistory().add(historyAction);
        } else {
            Command error = new ErrorCommand(getCommand(), getUsername(), getTimestamp(), "Ticket " + ticketID + " is not assigned to developer " + getUsername() + ".");
            commands.add(error);
            return;
        }
    }
}
