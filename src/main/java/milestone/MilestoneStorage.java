package milestone;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class MilestoneStorage {
    private static final MilestoneStorage INSTANCE = new MilestoneStorage();
    private static final List<Milestone> milestones = new ArrayList<>();

    private MilestoneStorage() { }

    public static MilestoneStorage getInstance() {
        return INSTANCE;
    }

    /**
     * Adauga un nou milestone in lista de milestoneuri
     * @param milestone
     */
    public void addMilestone(final Milestone milestone) {
        milestones.add(milestone);
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    /**
     * Returneaza numele milestone-ului care contine ticketul cu id-ul dat
     * @param id id-ul ticketului
     * @return
     */
    public static String nameOfTheMilestoneThatContaintsTicket(final  int id) {
        for (Milestone x : milestones) {
            for (int ticketId : x.getTickets()) {
                if (ticketId == id) {
                    return x.getName();
                }
            }
        }
        return null;
    }

    /**
     * Returneaza milestone-ul cu numele dat
     * @param name numele milestone-ului
     * @return
     */
    public static Milestone getMilestoneByName(final String name) {
        for (Milestone x : milestones) {
            if (x.getName().equals(name)) {
                return x;
            }
        }
        return null;
    }

    /**
     * Returneaza id-urile tuturor ticketelor din milestone-urile
     * asociate unui user dat
     * @param name numele user-ului
     * @return
     */
    public static ArrayList<Integer> getTicketsOfMilestonesOfUser(final String name) {
        ArrayList<Integer> tickets = new ArrayList<>();
        for (Milestone x : milestones) {
            for (String username : x.getAssignedDevs()) {
                if (username.equals(name)) {
                    tickets.addAll(x.getTickets());
                }
            }
        }
        tickets.sort(Comparator.comparingInt(a -> a));
        return tickets;
    }

    /**
     * Metoda care muta un ticket din open in closed
     * @param ticketId id-ul ticketului
     */
    public void changeOpenToClosed(final int ticketId) {
        for (Milestone x : milestones) {
            int counter = 0;
            for (int id : x.getTickets()) {
                if (ticketId == id) {
                    if (counter < x.getOpenTickets().size()) {
                        x.getOpenTickets().remove(counter);
                        x.getClosedTickets().addLast(ticketId);
                        x.setCompletionPercentage(x.getClosedTickets().size()
                                / x.getTickets().size());
                        return;
                    }
                }
                counter++;
            }
        }
    }

    /**
     * Returneaza milestone-ul care contine ticketul cu id-ul dat
     * @param id id-ul ticketului
     * @return
     */
    public static Milestone getMilestoneByTicketId(final int id) {
        for (Milestone x : milestones) {
            for (int ticketId : x.getTickets()) {
                if (ticketId == id) {
                    return x;
                }
            }
        }
        return null;
    }

    /**
     * Reseteaza lista de milestoneuri
     */
    public void reset() {
        milestones.clear();
    }

}
