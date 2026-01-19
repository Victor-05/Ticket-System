package ticket;

import actions.Action;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import users.ExpertiseArea;

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
    @JsonIgnore
    private String expectedBehavior;
    @JsonIgnore
    private String actualBehavior;
    @JsonIgnore
    private Frequency frequency;
    @JsonIgnore
    private Severity severity;
    @JsonIgnore
    private String environment;
    @JsonIgnore
    private int errorCode;
    @JsonIgnore
    private BusinessValue businessValue;
    @JsonIgnore
    private CustomerDemand customerDemand;
    @JsonIgnore
    private String uiElementId;
    @JsonIgnore
    private int usabilityScore;
    @JsonIgnore
    private String screenshotUrl;
    @JsonIgnore
    private String suggestedFix;
    @JsonIgnore
    private int daysToResolve;
    public void changePriority() {
        if (this.getBusinessPriority().equals("LOW")) {
            this.setBusinessPriority("MEDIUM");
        } else if (this.getBusinessPriority().equals("MEDIUM")) {
            this.setBusinessPriority("HIGH");
        } else if (this.getBusinessPriority().equals("HIGH")) {
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

    public Ticket copy() {
        return null;
    }
}
