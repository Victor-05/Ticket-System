package ticket;

import lombok.Data;
import milestone.Milestone;
import milestone.MilestoneStorage;

import java.util.ArrayList;
import java.util.List;

// singleton
public class TicketStorage {
    private static final TicketStorage INSTANCE = new TicketStorage();
    private final List<Ticket> tickets = new ArrayList<>();

    private int id = 0;

    private TicketStorage() { }

    public static TicketStorage getInstance() {
        return INSTANCE;
    }

    public void addTicket(Ticket ticket) {
        ticket.setId(this.id);
        this.id++;
        ticket.setStatus("OPEN");
        tickets.add(ticket);
    }

    public ArrayList<Ticket> getTicketsByUsername(String username) {
        ArrayList<Ticket> outTickets = new ArrayList<>();
        for (Ticket x : tickets) {
            if (x.getReportedBy().equals(username)) {
                outTickets.add(x);
            }
        }
        return outTickets;
    }

    public Ticket getTicketsById(int id) {
        for (Ticket x : tickets) {
            if (x.getId() == id) {
                return x;
            }
        }
        return null;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public String isAssignedToMilestone(int id) {
        for (Ticket x : tickets) {
            if (x.getId() == id) {
                if (MilestoneStorage.nameOfTheMilestoneThatContaintsTicket(id) != null) {
                    x.changeIsAssignedToMilestone();
                    return MilestoneStorage.nameOfTheMilestoneThatContaintsTicket(id);
                }
            }
        }
        return null;
    }

    public void reset() {
        tickets.clear();
        this.id = 0;
    }


}