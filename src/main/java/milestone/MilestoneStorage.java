package milestone;

import ticket.Ticket;
import ticket.TicketStorage;

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

    public void getMilestoneByOpenTikcket(int ticketId) {
        for (Milestone x : milestones) {
            Boolean isThere = false;
            int [] openTickets = new int[x.getTickets().length];
            int contor = 0, changedTicket = 0;
            for (int id : x.getTickets()) {
                if (ticketId == id) {
                    changedTicket = id;
                    isThere = true;
                } else {
                    openTickets[contor] = id;
                }
                contor++;
            }
            if (isThere) {
                x.setTickets(openTickets);
                int[] closedTickets = new int[x.getClosedTickets().length + 1];
                closedTickets[x.getClosedTickets().length] = changedTicket;
                x.setClosedTickets(closedTickets);
            }
        }
    }

    public void reset() {
        milestones.clear();
    }

}
