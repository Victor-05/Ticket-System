package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.TicketStorage;
import users.UsersDatabase;

import java.util.ArrayList;

public class ReportTicketCommand extends Command {
    ReportTicketCommand(final CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        this.setParams(input.getParams());
    }

    /**
     * Se efectueaza actiunea specifica acestei comenzi
     * Crearea unui ticket de report
     * @param app aplicatia in sine cu data, state etc
     * @param ticketStorage locul unde sunt toate ticketele
     * @param commands lista de comenzi
     * @param milestoneStorage locul unde sunt toate milestoneurile
     */
    public final void execute(final Application app,
                        final TicketStorage ticketStorage,
                        final ArrayList<Command> commands,
                        final MilestoneStorage milestoneStorage) {
        if (app.getCurrentDate().isAfter(app.getEndTestingDate())) {
            Command error = new ErrorCommand("reportTicket", getUsername(),
                    getTimestamp(),
                    "Tickets can only be reported during testing phases.");
            commands.add(error);
            app.nextState();
            return;
        }
        if (!app.getState().getStatus().equals("TESTING")) {
            Command error = new ErrorCommand("reportTicket",
                    getUsername(), getTimestamp(),
                    "Tickets can only be reported during testing phases.");
            commands.add(error);
            return;
        }
        if (!this.getParams().getType().equals("BUG")
                && this.getParams().getReportedBy().isEmpty()) {
            Command error = new ErrorCommand("reportTicket",
                    getUsername(), getTimestamp(),
                    "Anonymous reports are only allowed for tickets of type BUG.");
            commands.add(error);
            return;
        }

        if (!UsersDatabase.checkIfUserExists(this.getUsername())) {
            Command error = new ErrorCommand("reportTicket", getUsername(),
                    getTimestamp(), "The user " + getUsername() + " does not exist.");
            commands.add(error);
            return;
        }
        ReportTicket ticket = new ReportTicket(this);
        if (this.getParams().getReportedBy().isEmpty()) {
            ticket.setBusinessPriority("LOW");
        }
        ticket.setCreatedAt(app.getCurrentDate().toString());
        ticketStorage.addTicket(ticket);

    }
}
