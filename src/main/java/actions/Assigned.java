package actions;

public class Assigned extends Action {
    public Assigned(final String action, final String by, final String timestamp) {
        setAction(action);
        setBy(by);
        setTimestamp(timestamp);
    }
}
