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
    public ReportTicket(final ReportTicketCommand command) {
        this.setType(command.getParams().getType());
        this.setTitle(command.getParams().getTitle());
        this.setBusinessPriority(command.getParams().getBusinessPriority());
        this.setCreatedAt(command.getTimestamp());
        this.setAssignedAt("");
        this.setSolvedAt("");
        this.setAssignedTo("");
        this.setReportedBy(command.getParams().getReportedBy());
        this.setCreatedAt(command.getTimestamp());
        this.setExpertiseArea(command.getParams().getExpertiseArea());
        this.setExpectedBehavior(command.getParams().getExpectedBehavior());
        this.setActualBehavior(command.getParams().getActualBehavior());
        this.setFrequency(command.getParams().getFrequency());
        this.setSeverity(command.getParams().getSeverity());
        this.setEnvironment(command.getParams().getEnvironment());
        this.setErrorCode(command.getParams().getErrorCode());
        this.setBusinessValue(command.getParams().getBusinessValue());
        this.setCustomerDemand(command.getParams().getCustomerDemand());
        this.setUiElementId(command.getParams().getUiElementId());
        this.setUsabilityScore(command.getParams().getUsabilityScore());
        this.setScreenshotUrl(command.getParams().getScreenshotUrl());
        this.setSuggestedFix(command.getParams().getSuggestedFix());
    }
    public ReportTicket(final ReportTicket ticket) {
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

    /**
     * Metoda ce returneaza copia unui ticket
     * @return
     */
    @Override
    public Ticket copy() {
        return new ReportTicket(this);
    }
    public ReportTicket() {
    }
}
