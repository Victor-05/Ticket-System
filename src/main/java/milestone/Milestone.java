package milestone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import command.CreateMilestone;
import constants.Constants;
import lombok.Data;
import main.Application;
import ticket.Ticket;
import ticket.TicketStorage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Data
public class Milestone {
    private String name;
    private ArrayList<String> blockingFor;
    private String dueDate;
    private String createdAt;
    private ArrayList<Integer> tickets;
    private ArrayList<String> assignedDevs;
    private String createdBy;
    private String status;
    private Boolean isBlocked = false;
    private int daysUntilDue;
    private int overdueBy;
    private ArrayList<Integer> openTickets;
    private ArrayList<Integer> closedTickets;
    private double completionPercentage;
    @JsonIgnore
    private LocalDate priorityChange;
    private ArrayList<Repartition> repartition = new ArrayList<>();
    public Milestone(final CreateMilestone command,
                     final Application app) {
        this.name = command.getName();
        this.blockingFor = command.getBlockingFor();
        this.dueDate = command.getDueDate();
        this.tickets = command.getTickets();
        this.assignedDevs = command.getAssignedDevs();
        createdAt = command.getTimestamp();
        createdBy = command.getUsername();
        status = "ACTIVE";
        LocalDate due = LocalDate.parse(dueDate,
                app.getDateTimeFormatter());
        daysUntilDue = (int) ChronoUnit.DAYS.between(app
                .getCurrentDate(), due);
        if (daysUntilDue < 0) {
            overdueBy = (-1) * daysUntilDue;
        }
        openTickets = new ArrayList<>(tickets);
        closedTickets = new ArrayList<>();
        for (String x : assignedDevs) {
            Repartition newRepartition = new Repartition();
            newRepartition.setDeveloper(x);
            repartition.add(newRepartition);
        }

    }

    /**
     * Metoda care schimba prioritatea unor tickete
     * @param app aplicatia in sine
     * @param ticketStorage locul unde sunt toate ticketele
     */
    public final void changeTicketPriority(final Application app,
                                     final TicketStorage ticketStorage) {
        setPriorityChange(priorityChange.plusDays(Constants
                .DAYS_TO_CHANGE_STATUS));
        for (int id : this.tickets) {
            for (Ticket x : ticketStorage.getTickets()) {
                if (x.getId() == id) {
                    x.changePriority();
                    break;
                }
            }
        }
    }
    public Milestone(final Milestone milestone) {
        this.name = milestone.name;
        this.blockingFor = new ArrayList<>(milestone.blockingFor);
        this.dueDate = milestone.dueDate;
        this.createdAt = milestone.createdAt;
        this.tickets = new ArrayList<>(milestone.tickets);
        this.assignedDevs = new ArrayList<>(milestone.assignedDevs);
        this.createdBy = milestone.createdBy;
        this.status = milestone.status;
        this.isBlocked = milestone.isBlocked;
        this.daysUntilDue = milestone.daysUntilDue;
        this.overdueBy = milestone.overdueBy;
        this.openTickets = new ArrayList<>(milestone.openTickets);
        this.closedTickets = new ArrayList<>(milestone.closedTickets);
        this.completionPercentage = milestone.completionPercentage;
        this.priorityChange = milestone.priorityChange;

        this.repartition = new ArrayList<>();
        for (Repartition r : milestone.repartition) {
            this.repartition.add(new Repartition(r));
        }
    }


}
