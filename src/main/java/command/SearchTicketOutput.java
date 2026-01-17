package command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;
import ticket.ReportTicket;
import ticket.Ticket;

import java.util.ArrayList;

@Data
public class SearchTicketOutput {
    private int id;
    private String type;
    private String title;
    private String businessPriority;
    private String status;
    private String createdAt;
    private String solvedAt;
    private String reportedBy;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ArrayList<String> matchingWords;
    public SearchTicketOutput(ReportTicket x, ArrayList<String> matchingWords) {
        this.id = x.getId();
        this.type = x.getType();
        this.title = x.getTitle();
        this.businessPriority = x.getBusinessPriority();
        this.status = x.getStatus();
        this.createdAt = x.getCreatedAt();
        this.solvedAt = x.getSolvedAt();
        this.reportedBy = x.getReportedBy();
        this.matchingWords = new ArrayList<>(matchingWords);
    }
}
