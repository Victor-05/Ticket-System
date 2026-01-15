package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.Ticket;
import ticket.TicketStorage;
import users.Developer;
import users.UsersDatabase;

import java.util.ArrayList;

public class UndoAssignTicketCommand extends Command {
    private int ticketID;
    UndoAssignTicketCommand(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        this.ticketID = input.getTicketID();
    }
    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
        Developer user = (Developer) UsersDatabase.getUser(getUsername());
        ReportTicket assignedTicket = (ReportTicket) ticketStorage.getTicketsById(ticketID);
        if (!assignedTicket.getStatus().equals("IN_PROGRESS")) {
            Command error = new ErrorCommand(getCommand(),  getUsername(), getTimestamp(), "Only IN_PROGRESS tickets can be unassigned.");
            commands.add(error);
            return;
        }
        user.getTickets().remove(assignedTicket);
        assignedTicket.setStatus("OPEN");
        assignedTicket.setAssignedAt("");
        //commands.add(this);
    }
}
