package ticket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class FeatureRequest extends Ticket {
    private BusinessValue businessValue;
    private CustomerDemand customerDemand;
}
