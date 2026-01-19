package actions;

public class StatusChanged extends Action {
    private String from;
    private String to;
    public StatusChanged(final String action, final String by,
                         final String timestamp, final String from, final String to) {
        setAction(action);
        setBy(by);
        setTimestamp(timestamp);
        this.from = from;
        this.to = to;
    }
}
