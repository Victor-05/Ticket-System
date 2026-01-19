package ticket;

import lombok.Data;

@Data
public class FeatureRequest extends Ticket {
    private BusinessValue businessValue;
    private CustomerDemand customerDemand;
}
