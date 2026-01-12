package milestone;

import command.CreateMilestone;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Milestone {
    private String name;
    private ArrayList<String> blockingFor;
    private String dueDate;
    private int[] tickets;
    private ArrayList<String> assignedDevs;
    private LocalDate priorityChange;
    public Milestone(CreateMilestone command) {
        this.name = command.getName();
        this.blockingFor = command.getBlockingFor();
        this.dueDate = command.getDueDate();
        this.tickets = command.getTickets();
        this.assignedDevs = command.getAssignedDevs();
    }
}
