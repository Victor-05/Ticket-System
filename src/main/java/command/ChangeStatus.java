package command;

import actions.Action;
import actions.Assigned;
import actions.StatusChanged;
import lombok.Data;
import main.Application;
import milestone.Milestone;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.TicketStorage;
import users.Developer;
import users.UsersDatabase;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class ChangeStatus extends Command {
    private int ticketID;
    ChangeStatus(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        this.setTicketID(input.getTicketID());
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
            if (ticket.getStatus().equals("OPEN")) {
                ticket.setStatus("IN_PROGRESS");
                from = "OPEN";
            } else if (ticket.getStatus().equals("IN_PROGRESS")) {
                ticket.setStatus("RESOLVED");
                Milestone milestone = milestoneStorage.getMilestoneByName(MilestoneStorage.nameOfTheMilestoneThatContaintsTicket(ticket.getId()));
                ticket.setDaysToResolve(milestone.getDaysUntilDue());
                from = "IN_PROGRESS";
            } else if (ticket.getStatus().equals("RESOLVED")) {
                Milestone milestoneOfTicket = MilestoneStorage.getMilestoneByName(MilestoneStorage.nameOfTheMilestoneThatContaintsTicket(ticketID));
//                if (milestoneOfTicket.getTickets().getLast() == ticketID) {
//
//                }

//                LocalDate dueDate = LocalDate.parse(ticket.getd, app.getDateTimeFormatter());
                Milestone milestone = milestoneStorage.getMilestoneByName(MilestoneStorage.nameOfTheMilestoneThatContaintsTicket(ticket.getId()));
                ticket.setDaysToResolve(milestone.getDaysUntilDue());
                ticket.setStatus("CLOSED");
                from = "RESOLVED";
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
