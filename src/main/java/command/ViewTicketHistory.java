package command;

import actions.Action;
import main.Application;
import milestone.MilestoneStorage;
import ticket.HistoryTicket;
import ticket.ReportTicket;
import ticket.TicketStorage;
import users.Role;
import users.User;
import users.UsersDatabase;

import java.util.ArrayList;

public class ViewTicketHistory extends Command {
    private ArrayList<HistoryTicket> ticketHistory;
    ViewTicketHistory(final CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        ticketHistory = new ArrayList<>();
    }

    @Override
    public final void execute(final Application app,
                              final TicketStorage ticketStorage,
                              final ArrayList<Command> commands,
                              final MilestoneStorage milestoneStorage) {
        User user = UsersDatabase.getUser(getUsername());
        if (user.getRole() == Role.DEVELOPER) {
            for (ReportTicket x : user.getTickets()) {
                HistoryTicket addedToHistory = new HistoryTicket(x.getId(),
                        x.getTitle(), x.getStatus(),
                        new ArrayList<Action>(x.getHistory()),
                        x.getComments());
                ticketHistory.add(addedToHistory);
            }
        }
        commands.add(this);
    }
}
