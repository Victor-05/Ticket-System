package ticket;

import actions.Action;
import command.Comment;

import java.util.ArrayList;

public class HistoryTicket {
    private int id;
    private String title;
    private String status;
    private ArrayList<Action> actions;
    private ArrayList<Comment> comments;
    public HistoryTicket (int id, String title, String status, ArrayList<Action> actions, ArrayList<Comment> comments) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.actions = actions;
        this.comments = comments;
    }
}
