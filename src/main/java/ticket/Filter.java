package ticket;

import lombok.Data;
import users.ExpertiseArea;
import users.Seniority;

import java.util.ArrayList;

@Data
public class Filter {
    private String searchType;
    private ExpertiseArea expertiseArea;
    private Seniority seniority;
    private ArrayList<String> keywords = new ArrayList<>();
    private String createdAfter;
    private Boolean availableForAssignment;
    private String businessPriority;
    private String type;
    private String createdAt;
    private String createdBefore;
    private int performanceScoreAbove;
    private int performanceScoreBelow;

}
