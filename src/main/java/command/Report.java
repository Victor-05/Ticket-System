package command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Report {
    private Integer totalTickets;
    private Integer totalOpenTickets;
    TicketsByType ticketsByType;
    TicketsByType openTicketsByType;
    TicketsByPriority openTicketsByPriority;
    CustomerImpactByType customerImpactByType;
    TicketsByPriority ticketsByPriority;
    CustomerRiskByType riskByType;
    CustomerImpactByType impactByType;
    private String appStability;

    Report() {
    }

    @Data
    public static class TicketsByType {
        @JsonProperty("BUG")
        private int bug;
        @JsonProperty("FEATURE_REQUEST")
        private int featureRequest;
        @JsonProperty("UI_FEEDBACK")
        private int uiFeedback;
    }
    @Data
    public static class TicketsByPriority {
        @JsonProperty("LOW")
        private int low;
        @JsonProperty("MEDIUM")
        private int medium;
        @JsonProperty("HIGH")
        private int high;
        @JsonProperty("CRITICAL")
        private int critical;
    }
    @Data
    public static class CustomerImpactByType {
        @JsonProperty("BUG")
        private double bug;
        @JsonProperty("FEATURE_REQUEST")
        private double featureRequest;
        @JsonProperty("UI_FEEDBACK")
        private double uiFeedback;
    }

    @Data
    public static class CustomerRiskByType {
        @JsonProperty("BUG")
        private String bug;
        @JsonProperty("FEATURE_REQUEST")
        private String featureRequest;
        @JsonProperty("UI_FEEDBACK")
        private String uiFeedback;
    }
}
