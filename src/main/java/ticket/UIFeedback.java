package ticket;

import lombok.Data;

@Data
public class UIFeedback extends Ticket {
    private String uiElementId;
    private BusinessValue businessValue;
    private int usabilityScore;
    private String screenshotUrl;
    private String suggestedFix;
}
