package ticket;

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

    public List<Ticket> getTickets() {
        return tickets;
    }
}