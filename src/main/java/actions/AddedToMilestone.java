package actions;

public class AddedToMilestone extends Action {
    private String milestone;
    public AddedToMilestone(final String action, final String by, final String timestamp, final String milestone) {
        setAction(action);
        setBy(by);
        setTimestamp(timestamp);
        this.milestone = milestone;
    }
}
