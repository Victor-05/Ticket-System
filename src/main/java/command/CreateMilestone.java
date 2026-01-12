package command;

import lombok.Data;
import main.Application;
import milestone.Milestone;
import milestone.MilestoneStorage;
import ticket.TicketStorage;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class CreateMilestone extends Command {
    private String name;
    private String dueDate;
    private ArrayList<String> blockingFor;
    private int[] tickets;
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
        LocalDate currentDate = LocalDate.parse(getTimestamp(), app.getDateTimeFormatter());
        Milestone newMilestone = new Milestone(this);
        milestoneStorage.addMilestone(newMilestone);
    }
}
