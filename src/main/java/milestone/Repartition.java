package milestone;

import lombok.Data;

@Data
public class Repartition {
    private String developer;
    private int[] assignedTickets;
    Repartition() {
        assignedTickets = new int[0];
    }
}
