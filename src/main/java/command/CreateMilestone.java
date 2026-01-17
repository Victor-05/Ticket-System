package command;

import actions.Action;
import actions.AddedToMilestone;
import actions.Assigned;
import lombok.Data;
import main.Application;
import milestone.Milestone;
import milestone.MilestoneStorage;
import ticket.Ticket;
import ticket.TicketStorage;
import users.Role;
import users.UsersDatabase;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class CreateMilestone extends Command {
    private String name;
    private String dueDate;
    private ArrayList<String> blockingFor;
    private ArrayList<Integer> tickets;
    private ArrayList<String> assignedDevs;
    CreateMilestone(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        this.setName(input.getName());
        this.setDueDate(input.getDueDate());
        this.setBlockingFor(input.getBlockingFor());
        this.setTickets(input.getTickets());
        this.setAssignedDevs(input.getAssignedDevs());
    }
    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
        if (UsersDatabase.getUserRole(getUsername()) != Role.MANAGER) {
            Command error = new ErrorCommand(getCommand(), getUsername(), getTimestamp(), "The user does not have permission to execute this command: required role MANAGER; user role " + UsersDatabase.getUserRole(getUsername()) + ".");
            commands.add(error);
            return;
        }

        String assignedTickets = "";
        int ticketId = -1;
        for (int tickets : tickets) {
            Action historyAction = new AddedToMilestone("ADDED_TO_MILESTONE", getUsername(), getTimestamp(), getName());
            Ticket ticket = ticketStorage.getTicketsById(tickets);
            ticket.getHistory().add(historyAction);
            if (ticketStorage.isAssignedToMilestone(tickets) != null) {
                assignedTickets = assignedTickets + tickets;
                ticketId = tickets;
                break;
            }
        }

        if (!assignedTickets.isEmpty()) {
            Command error = new ErrorCommand(getCommand(), getUsername(), getTimestamp(), "Tickets " + assignedTickets + " already assigned to milestone " + ticketStorage.isAssignedToMilestone(ticketId) + ".");
            commands.add(error);
            return;
        }

        LocalDate currentDate = LocalDate.parse(getTimestamp(), app.getDateTimeFormatter());
        Milestone newMilestone = new Milestone(this);
        newMilestone.setPriorityChange(currentDate.plusDays(3));
        milestoneStorage.addMilestone(newMilestone);
        for (String x : blockingFor) {
            for (Milestone m : milestoneStorage.getMilestones()) {
                if (m.getName().equals(x)) {
                    m.setIsBlocked(true);
                }

            }
        }
    }
}
