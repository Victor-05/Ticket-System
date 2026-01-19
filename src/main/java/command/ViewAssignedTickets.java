package command;

import constants.Constants;
import main.Application;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.Ticket;
import ticket.TicketStorage;
import users.Developer;
import users.Manager;
import users.User;
import users.UsersDatabase;
import users.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class ViewAssignedTickets extends Command {
    private ArrayList<AssignedTicketView> assignedTickets;
    ViewAssignedTickets(final CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
    }
    @Override
    public final void execute(final Application app,
                        final TicketStorage ticketStorage,
                        final ArrayList<Command> commands,
                        final MilestoneStorage milestoneStorage) {
        User user = UsersDatabase.getUser(getUsername());
        if (UsersDatabase.getUser(getUsername()).getRole() != Role.DEVELOPER) {
            user = (Manager) UsersDatabase.getUser(getUsername());
        } else {
            user = (Developer) UsersDatabase.getUser(getUsername());
        }
        assignedTickets = new ArrayList<>();
        for (ReportTicket x : user.getTickets()) {
            assignedTickets.add(new AssignedTicketView(x));
        }
        assignedTickets.sort(
                Comparator.comparingInt((AssignedTicketView x)
                                -> switch (x.getBusinessPriority()) {
                            case "LOW" -> Constants.BUSINESS_PRIORITY_CRITICAL;
                            case "MEDIUM" -> Constants.BUSINESS_PRIORITY_HIGH;
                            case "HIGH" -> Constants.BUSINESS_PRIORITY_MEDIUM;
                            case "CRITICAL" -> Constants.BUSINESS_PRIORITY_LOW;
                            default -> -1;
                        })
                        .thenComparing(x
                                -> LocalDate.parse(x.getCreatedAt()))
                        .thenComparingInt(Ticket::getId)
        );
        commands.add(this);
    }
    private static class AssignedTicketView extends Ticket {
        private String createdAt;
        private String assignedAt;
        private ArrayList<Comment> comments = new ArrayList<>();

        AssignedTicketView(final ReportTicket x) {
            this.setId(x.getId());
            this.setType(x.getType());
            this.setTitle(x.getTitle());
            this.setBusinessPriority(x.getBusinessPriority());
            this.setStatus(x.getStatus());
            this.setCreatedAt(x.getCreatedAt());
            this.assignedAt = x.getAssignedAt();
            this.comments = new ArrayList<>(x.getComments());
            this.setReportedBy(x.getReportedBy());

        }
    }
}
