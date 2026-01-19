package command;

import actions.Action;
import actions.AddedToMilestone;
import constants.Constants;
import lombok.Data;
import main.Application;
import milestone.Milestone;
import milestone.MilestoneStorage;
import ticket.Ticket;
import ticket.TicketStorage;
import users.Role;
import users.User;
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
    CreateMilestone(final CommandInput input) {
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
    public final void execute(final Application app,
                        final TicketStorage ticketStorage,
                        final ArrayList<Command> commands,
                        final MilestoneStorage milestoneStorage) {
        if (UsersDatabase.getUserRole(getUsername()) != Role.MANAGER) {
            Command error = new ErrorCommand(getCommand(), getUsername(),
                    getTimestamp(), "The user does not have permission"
                    + " to execute this command: required role MANAGER; user role "
                    + UsersDatabase.getUserRole(getUsername()) + ".");
            commands.add(error);
            return;
        }

        String assignedTickets = "";
        int ticketId = -1;
        for (int x : tickets) {
            Action historyAction = new AddedToMilestone("ADDED_TO_MILESTONE",
                    getUsername(), getTimestamp(), getName());
            Ticket ticket = ticketStorage.getTicketsById(x);
            if (ticket != null) {
                ticket.getHistory().add(historyAction);
            }
            if (ticketStorage.isAssignedToMilestone(x) != null) {
                assignedTickets = assignedTickets + tickets;
                ticketId = x;
                break;
            }
        }

        if (!assignedTickets.isEmpty()) {
            Command error = new ErrorCommand(getCommand(), getUsername(),
                    getTimestamp(), "Tickets " + assignedTickets
                    + " already assigned to milestone "
                    + ticketStorage.isAssignedToMilestone(ticketId) + ".");
            commands.add(error);
            return;
        }

        LocalDate currentDate = LocalDate.parse(getTimestamp(), app.getDateTimeFormatter());
        Milestone newMilestone = new Milestone(this, app);
        newMilestone.setPriorityChange(currentDate
                .plusDays(Constants.DAYS_TO_CHANGE_STATUS));
        milestoneStorage.addMilestone(newMilestone);
        for (String dev : assignedDevs) {
            User user = UsersDatabase.getUser(dev);
            user.getNotifications().add("New milestone "
                    + newMilestone.getName()
                    + " has been created with due date "
                    + newMilestone.getDueDate() + ".");
        }
        for (String x : blockingFor) {
            for (Milestone m : milestoneStorage.getMilestones()) {
                if (m.getName().equals(x)) {
                    m.setIsBlocked(true);
                }

            }
        }
    }
}
