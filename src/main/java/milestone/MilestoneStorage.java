package milestone;

import java.util.ArrayList;
import java.util.List;

public class MilestoneStorage {
    private static final MilestoneStorage INSTANCE = new MilestoneStorage();
    private static final List<Milestone> milestones = new ArrayList<>();

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

    public static String nameOfTheMilestoneThatContaintsTicket(int id) {
        for (Milestone x : milestones) {
            for (int ticketId : x.getTickets()) {
                if (ticketId == id) {
                    return x.getName();
                }
            }
        }
        return null;
    }

    public static Milestone getMilestoneByName(String name) {
        for (Milestone x : milestones) {
            if (x.getName().equals(name)) {
                return x;
            }
        }
        return null;
    }

    public static ArrayList<Integer> getTicketsOfMilestonesOfUser(String name) {
        ArrayList<Integer> tickets = new ArrayList<>();
        for (Milestone x : milestones) {
            for (String username : x.getAssignedDevs()) {
                if (username.equals(name)) {
                    tickets.addAll(x.getTickets());
                }
            }
        }
        tickets.sort((a, b) -> a - b);
        return tickets;
    }

    public void changeOpenToClosed(int ticketId) {
        for (Milestone x : milestones) {
            int counter = 0;
            for (int id : x.getTickets()) {
                if (ticketId == id) {
                    x.getOpenTickets().remove(counter);
                    x.getClosedTickets().addLast(ticketId);
                    x.setCompletionPercentage(x.getClosedTickets().size() / x.getTickets().size());
                    return;
                }
                counter++;
            }
        }
    }

    public void reset() {
        milestones.clear();
    }

}
