package actions;

public class AddedToMilestone extends Action {
    private String milestone;
    public AddedToMilestone(String action, String by, String timestamp, String milestone) {
        setAction(action);
        setBy(by);
        setTimestamp(timestamp);
        this.milestone = milestone;
    }
}
