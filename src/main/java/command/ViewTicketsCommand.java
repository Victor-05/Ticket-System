package command;

import main.Application;
import milestone.Milestone;
import milestone.MilestoneStorage;
import ticket.Ticket;
import ticket.TicketStorage;
import users.Role;
import users.UsersDatabase;
import java.util.ArrayList;

public class ViewTicketsCommand extends Command {
    private ArrayList<Ticket> tickets;
    ViewTicketsCommand(final CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        this.setParams(input.getParams());
    }
    @Override
    public final void execute(final Application app,
                              final TicketStorage ticketStorage,
                              final ArrayList<Command> commands,
                              final MilestoneStorage milestoneStorage) {
        tickets = new ArrayList<>();
        if (UsersDatabase.getUserRole(getUsername()) == Role.REPORTER) {
            tickets = new ArrayList<>(ticketStorage
                    .getTicketsByUsername(getUsername()));
            commands.add(this);
            return;
        }
        if (UsersDatabase.getUserRole(getUsername()) == Role.MANAGER) {
            tickets.addAll(new ArrayList<>(ticketStorage.getTickets()));
            commands.add(this);
            return;
        }
        if (UsersDatabase.getUserRole(getUsername()) == Role.DEVELOPER) {
            for (Ticket x : ticketStorage.getTickets()) {
                if (x.getStatus().equals("OPEN")) {
                    Milestone milestone = MilestoneStorage
                            .getMilestoneByTicketId(x.getId());
                    Boolean isAssigned = false;
                    for (String name : milestone.getAssignedDevs()) {
                        if (name.equals(getUsername())) {
                            isAssigned = true;
                            break;
                        }
                    }
                    if (isAssigned) {
                        tickets.add(x.copy());
                    }
                }
            }
            tickets = new ArrayList<>(tickets);
            commands.add(this);
        }
    }
}
