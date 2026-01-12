package ticket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String expertiseArea;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  String description;
    private String reportedBy;
}
