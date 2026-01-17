package ticket;

import actions.Action;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import users.ExpertiseArea;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Bug.class, name = "BUG"),
        @JsonSubTypes.Type(value = FeatureRequest.class, name = "FEATURE_REQUEST"),
        @JsonSubTypes.Type(value = UIFeedback.class, name = "UI_FEEDBACK")
})
public abstract class Ticket {
    private int id;
    private String type;
    private String title;
    private String businessPriority;
    private String status;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ExpertiseArea expertiseArea;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  String description;
    private String reportedBy;
    @JsonIgnore
    private Boolean isAssignedToMilestone = false;
    @JsonIgnore
    private String createdAt;
    @JsonIgnore
    private ArrayList<Action> history = new ArrayList<>();
    public void changePriority() {
        if (this.getBusinessPriority().equals("LOW")) {
            this.setBusinessPriority("MEDIUM");
        }
        if (this.getBusinessPriority().equals("MEDIUM")) {
            this.setBusinessPriority("HIGH");
        }
        if (this.getBusinessPriority().equals("HIGH")) {
            this.setBusinessPriority("CRITICAL");
        }
    }

    public void changePriority(String newPriority) {
        this.setBusinessPriority(newPriority);
    }

    public void changeIsAssignedToMilestone() {
        if (isAssignedToMilestone == true) {
            this.setIsAssignedToMilestone(false);
        } else {
            this.setIsAssignedToMilestone(true);
        }
    }

}
