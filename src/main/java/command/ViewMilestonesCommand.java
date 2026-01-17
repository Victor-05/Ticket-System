package command;

import main.Application;
import milestone.Milestone;
import milestone.MilestoneStorage;
import ticket.TicketStorage;
import users.Role;
import users.UsersDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ViewMilestonesCommand extends Command {
    private List<Milestone> milestones = new ArrayList<>();
    ViewMilestonesCommand(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        this.setParams(input.getParams());
    }
    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
        if (UsersDatabase.getUserRole(getUsername()) == Role.MANAGER) {
            for (Milestone x : milestoneStorage.getMilestones()) {
                Milestone copyMilestone = new Milestone(x);
                if (copyMilestone.getCreatedBy().equals(getUsername())) {
                    milestones.add(copyMilestone);
                }
            }
        }

        if (UsersDatabase.getUserRole(getUsername()) == Role.DEVELOPER) {
            for (Milestone x : milestoneStorage.getMilestones()) {
                Milestone copyMilestone = new Milestone(x);
                for (String name : copyMilestone.getAssignedDevs()) {
                    if (name.equals(getUsername())) {
                        milestones.add(copyMilestone);
                        break;
                    }
                }
            }
        }
        milestones.sort(Comparator.comparing(Milestone::getDueDate)
                        .thenComparing(Milestone::getName));
        commands.add(this);
    }
}
