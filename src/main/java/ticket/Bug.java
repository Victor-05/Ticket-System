package ticket;

import lombok.Data;

@Data
public class Bug extends Ticket {
    private String expectedBehavior;
    private String actualBehavior;
    private Frequency frequency;
    private Severity severity;
    private String environment;
    private int errorCode;
}
