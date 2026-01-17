package actions;

public class StatusChanged extends Action {
    private String from;
    private String to;
    public StatusChanged(String action, String by, String timestamp, String from, String to) {
        setAction(action);
        setBy(by);
        setTimestamp(timestamp);
        this.from = from;
        this.to = to;
    }
}
