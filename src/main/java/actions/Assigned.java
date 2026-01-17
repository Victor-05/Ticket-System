package actions;

public class Assigned extends Action {
    public Assigned(String action, String by, String timestamp) {
        setAction(action);
        setBy(by);
        setTimestamp(timestamp);
    }
}
