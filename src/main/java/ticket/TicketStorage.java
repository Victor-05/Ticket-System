package ticket;

import milestone.MilestoneStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * TicketStorage class care urmareste design patternul Singleton
 */

public final class TicketStorage {
    private static final TicketStorage INSTANCE = new TicketStorage();
    private final List<Ticket> tickets = new ArrayList<>();

    private int id = 0;

    private TicketStorage() { }

    public static TicketStorage getInstance() {
        return INSTANCE;
    }

    /**
     * Adauga un nou ticket in lista de tickete
     * @param ticket ticketul ce trebuie adaugat
     */
    public void addTicket(final Ticket ticket) {
        ticket.setId(this.id);
        this.id++;
        ticket.setStatus("OPEN");
        tickets.add(ticket);
    }

    /**
     * Returneaza lista de tickete ce au fost raportate de un user specific
     * @param username numele user-ului
     * @return lista de tickete
     */
    public ArrayList<Ticket> getTicketsByUsername(final String username) {
        ArrayList<Ticket> outTickets = new ArrayList<>();
        for (Ticket x : tickets) {
            if (x.getReportedBy().equals(username)) {
                outTickets.add(x);
            }
        }
        return outTickets;
    }

    /**
     * Returneaza un ticket din lista de tickete dupa id-ul lui
     * @param ticketId id-ul ticketului
     * @return ticketul
     */
    public Ticket getTicketsById(final int ticketId) {
        for (Ticket x : tickets) {
            if (x.getId() == ticketId) {
                return x;
            }
        }
        return null;
    }

    /**
     * Returneaza lista de tickete
     * @return lista de tickete
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Verifica daca un ticket este asociat cu un milestone
     * @param ticketId id-ul ticketului
     * @return
     */
    public String isAssignedToMilestone(final int ticketId) {
        for (Ticket x : tickets) {
            if (x.getId() == ticketId) {
                if (MilestoneStorage.nameOfTheMilestoneThatContaintsTicket(id) != null) {
                    x.changeIsAssignedToMilestone();
                    return MilestoneStorage.nameOfTheMilestoneThatContaintsTicket(id);
                }
            }
        }
        return null;
    }

    /**
     * Reseteaza lista de tickete
     */
    public void reset() {
        tickets.clear();
        this.id = 0;
    }

}
