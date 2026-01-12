package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.Ticket;
import ticket.TicketStorage;
import users.UsersDatabase;

import java.util.ArrayList;

public class ReportTicketCommand extends Command {
    ReportTicketCommand(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        this.setParams(input.getParams());
    }

    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
        if (Application.currentDate.isAfter(Application.endTestingDate)) {
            Command error = new ErrorCommand("reportTicket",getUsername(), getTimestamp(), "Tickets can only be reported during testing phases.");
            commands.add(error);
            app.nextState();
            return;
        }
        if (!app.getState().getStatus().equals("TESTING")) {
            Command error = new ErrorCommand("reportTicket",getUsername(), getTimestamp(), "Tickets can only be reported during testing phases.");
            commands.add(error);
            return;
        }
        if (!this.getParams().getType().equals("BUG") && this.getParams().getReportedBy().isEmpty()) {
            Command error = new ErrorCommand("reportTicket",getUsername(), getTimestamp(), "Anonymous reports are only allowed for tickets of type BUG.");
            commands.add(error);
            return;
        }

        if (!UsersDatabase.checkIfUserExists(this.getUsername())) {
            Command error = new ErrorCommand("reportTicket",getUsername(), getTimestamp(), "The user " + getUsername() + " does not exist.");
            commands.add(error);
            return;
        }
        Ticket ticket = new ReportTicket(this);
        if (this.getParams().getReportedBy().isEmpty()) {
            ticket.setBusinessPriority("LOW");
        }
        ticketStorage.addTicket(ticket);

    }
}
