package command;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import main.Application;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.Ticket;
import ticket.TicketStorage;
import users.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class ViewAssignedTickets extends Command {
    private ArrayList<AssignedTicketView> assignedTickets;
    ViewAssignedTickets(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
    }
    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
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
                Comparator.comparingInt((AssignedTicketView x) -> switch (x.getBusinessPriority()) {
                            case "LOW" -> 3;
                            case "MEDIUM" -> 2;
                            case "HIGH" -> 1;
                            case "CRITICAL" -> 0;
                            default -> -1;
                        })
                        .thenComparing(x -> LocalDate.parse(x.getCreatedAt()))
                        .thenComparingInt(Ticket::getId)
        );
        commands.add(this);
    }
    private static class AssignedTicketView extends Ticket {
        private String createdAt;
        private String assignedAt;
        private ArrayList<Comment> comments = new ArrayList<>();

        public AssignedTicketView(ReportTicket x) {
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
