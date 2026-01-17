package actions;

public class DeAssigned extends Action {
    public DeAssigned(String action, String by, String timestamp) {
        setAction(action);
        setBy(by);
        setTimestamp(timestamp);
    }
}
