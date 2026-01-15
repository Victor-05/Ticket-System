package command;

import lombok.Data;
import main.Application;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.Ticket;
import ticket.TicketStorage;
import users.Developer;
import users.UsersDatabase;

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
            if (ticket.getStatus().equals("OPEN")) {
                ticket.setStatus("IN_PROGRESS");
            } else if (ticket.getStatus().equals("IN_PROGRESS")) {
                ticket.setStatus("CLOSED");
            }
           milestoneStorage.getMilestoneByOpenTikcket(ticketID);
            //commands.add(this);
        } else {
            Command error = new ErrorCommand(getCommand(), getUsername(), getTimestamp(), "Ticket " + ticketID + " is not assigned to developer " + getUsername() + ".");
            commands.add(error);
            return;
        }
    }

}
