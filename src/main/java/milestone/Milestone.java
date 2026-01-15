package milestone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import command.CreateMilestone;
import lombok.Data;
import main.Application;
import ticket.Ticket;
import ticket.TicketStorage;
import users.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Milestone {
    private String name;
    private ArrayList<String> blockingFor;
    private String dueDate;
    private String createdAt;
    private int[] tickets;
    private ArrayList<String> assignedDevs;
    private String createdBy;
    private String status;
    private Boolean isBlocked = false;
    private int daysUntilDue;
    private int overdueBy;
    private ArrayList openTickets = new int[0];
    private int[] closedTickets =  new int[0];
    private double completionPercentage;
    @JsonIgnore
    private LocalDate priorityChange;
    private ArrayList<Repartition> repartition = new ArrayList<>();
    public Milestone(CreateMilestone command) {
        this.name = command.getName();
        this.blockingFor = command.getBlockingFor();
        this.dueDate = command.getDueDate();
        this.tickets = command.getTickets();
        this.assignedDevs = command.getAssignedDevs();
        createdAt = command.getTimestamp();
        createdBy = command.getUsername();
        status = "ACTIVE";
        LocalDate due = LocalDate.parse(dueDate, Application.dateTimeFormatter);
        daysUntilDue = (int)ChronoUnit.DAYS.between(Application.currentDate, due);
        if (daysUntilDue < 0) {
            overdueBy = (-1) * daysUntilDue;
        }
        openTickets = tickets;
        for (String x : assignedDevs) {
            Repartition newRepartition = new Repartition();
            newRepartition.setDeveloper(x);
            repartition.add(newRepartition);
        }

    }

    public void changeTicketPriority(Application app, TicketStorage ticketStorage) {
        setPriorityChange(priorityChange.plusDays(3));
        for (int id : this.tickets) {
            for (Ticket x : ticketStorage.getTickets()) {
                if (x.getId() == id) {
                    x.changePriority();
                    break;
                }
            }
        }
        LocalDate dueDate = LocalDate.parse(getDueDate(), app.getDateTimeFormatter());
        for (Ticket x : ticketStorage.getTickets()) {
            if (x.getStatus().equals("ACTIVE") && Application.currentDate.isAfter(dueDate.minusDays(2))) {
                x.changePriority("CRITICAL");
                break;
            }
        }
    }
}
