package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.Ticket;
import ticket.TicketStorage;
import users.Role;
import users.UsersDatabase;

import java.util.ArrayList;

public class ViewTicketsCommand extends Command {
    private ArrayList<Ticket> tickets;
    ViewTicketsCommand(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        this.setParams(input.getParams());
    }
    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
        tickets = new ArrayList<>();
        if (UsersDatabase.getUserRole(getUsername()) == Role.REPORTER) {
            tickets = ticketStorage.getTicketsByUsername(getUsername());
            commands.add(this);
            return;
        }
        if (UsersDatabase.getUserRole(getUsername()) == Role.MANAGER) {
            tickets.addAll(ticketStorage.getTickets());
            commands.add(this);
            return;
        }
        if (UsersDatabase.getUserRole(getUsername()) ==Role.DEVELOPER) {

        }
    }
}
