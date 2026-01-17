package milestone;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Repartition {
    private String developer;
    private ArrayList<Integer> assignedTickets = new ArrayList<>();
    Repartition() {

    }
    public Repartition(Repartition repartition) {
        this.developer = repartition.developer;
        this.assignedTickets = new ArrayList<>(repartition.assignedTickets);
    }

}
