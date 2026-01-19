package command;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ticket.ReportTicket;
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
    public SearchTicketOutput(final ReportTicket x,
                              final ArrayList<String> matchingWords) {
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
