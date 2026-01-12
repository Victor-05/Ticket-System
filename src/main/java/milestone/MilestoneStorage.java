package milestone;

import ticket.Ticket;
import ticket.TicketStorage;

import java.util.ArrayList;
import java.util.List;

public class MilestoneStorage {
    private static final MilestoneStorage INSTANCE = new MilestoneStorage();
    private final List<Milestone> milestones = new ArrayList<>();

    private MilestoneStorage() { }

    public static MilestoneStorage getInstance() {
        return INSTANCE;
    }

    public void addMilestone(Milestone milestone) {
        milestones.add(milestone);
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }
}
