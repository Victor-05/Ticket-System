package ticket;

import command.ReportTicketCommand;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ReportTicket extends Ticket {
    private String createdAt;
    private String assignedAt;
    private String solvedAt;
    private String assignedTo;
    private ArrayList<String> comments = new ArrayList<>();
    public ReportTicket(ReportTicketCommand command) {
        this.setType(command.getParams().getType());
        this.setTitle(command.getParams().getTitle());
        this.setBusinessPriority(command.getParams().getBusinessPriority());
        this.setCreatedAt(command.getTimestamp());
        this.setAssignedAt("");
        this.setSolvedAt("");
        this.setAssignedTo("");
        this.setReportedBy(command.getParams().getReportedBy());
    }
}
