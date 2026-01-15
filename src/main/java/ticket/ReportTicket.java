package ticket;

import command.Comment;
import command.ReportTicketCommand;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ReportTicket extends Ticket {
    private String createdAt;
    private String assignedAt;
    private String solvedAt;
    private String assignedTo;
    private ArrayList<Comment> comments = new ArrayList<>();
    public ReportTicket(ReportTicketCommand command) {
        this.setType(command.getParams().getType());
        this.setTitle(command.getParams().getTitle());
        this.setBusinessPriority(command.getParams().getBusinessPriority());
        this.setCreatedAt(command.getTimestamp());
        this.setAssignedAt("");
        this.setSolvedAt("");
        this.setAssignedTo("");
        this.setReportedBy(command.getParams().getReportedBy());
        this.setExpertiseArea(command.getParams().getExpertiseArea());
        this.setCreatedAt(command.getTimestamp());
        this.setExpertiseArea(command.getParams().getExpertiseArea());
    }
    public ReportTicket(ReportTicket ticket) {
        this.setId(ticket.getId());
        this.setType(ticket.getType());
        this.setTitle(ticket.getTitle());
        this.setBusinessPriority(ticket.getBusinessPriority());
        this.setStatus(ticket.getStatus());
        this.setReportedBy(ticket.getReportedBy());
        this.setExpertiseArea(ticket.getExpertiseArea());
        this.setCreatedAt(ticket.getCreatedAt());
        this.setAssignedAt(ticket.getAssignedAt());
        this.setSolvedAt(ticket.getSolvedAt());
        this.setAssignedTo(ticket.getAssignedTo());
        this.setComments(new ArrayList<>(ticket.getComments()));
        this.setExpertiseArea(ticket.getExpertiseArea());
    }
}
