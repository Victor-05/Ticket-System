package actions;

public class DeAssigned extends Action {
    public DeAssigned(final String action, final String by, final String timestamp) {
        setAction(action);
        setBy(by);
        setTimestamp(timestamp);
    }
}
