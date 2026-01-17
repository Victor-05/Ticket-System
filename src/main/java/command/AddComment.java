package command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import main.Application;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.Ticket;
import ticket.TicketStorage;
import users.Role;
import users.UsersDatabase;

import java.util.ArrayList;

@Data
public class AddComment extends Command{
    private int ticketID;
    private Comment comment;
    @JsonIgnore
    private String commentText;
    AddComment(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        this.setCommentText(input.getComment());
        this.setTicketID(input.getTicketID());
    }

    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
        ReportTicket ticket = (ReportTicket) ticketStorage.getTicketsById(ticketID);
        if (ticket == null) {
            return;
        }
        if (ticket.getReportedBy().isEmpty()) {
            Command error = new ErrorCommand(getCommand(),  getUsername(), getTimestamp(), "Comments are not allowed on anonymous tickets.");
            commands.add(error);
            return;
        }
        if (ticket.getStatus().equals("CLOSED") && UsersDatabase.getUserRole(getUsername()) != Role.REPORTER) {
            Command error = new ErrorCommand(getCommand(),  getUsername(), getTimestamp(), "Reporters cannot comment on CLOSED tickets.");
            commands.add(error);
            return;
        }
        if (getCommentText().length() < 10) {
            Command error = new ErrorCommand(getCommand(),  getUsername(), getTimestamp(), "Comment must be at least 10 characters long.");
            commands.add(error);
            return;
        }
        if (UsersDatabase.getUserRole(getUsername()) == Role.DEVELOPER) {
            Boolean isOk = false;
            for (ReportTicket x : UsersDatabase.getUser(getUsername()).getTickets()) {
                if (x.getId() == ticketID) {
                    isOk = true;
                    break;
                }
            }
            if (!isOk) {
                Command error = new ErrorCommand(getCommand(), getUsername(), getTimestamp(), "Ticket " + ticketID + " is not assigned to the developer " + getUsername() + ".");
                commands.add(error);
                return;
            }
        }
        if (UsersDatabase.getUserRole(getUsername()) == Role.REPORTER && !ticket.getReportedBy().contains(getUsername())) {
            Command error = new ErrorCommand(getCommand(),  getUsername(), getTimestamp(), "Reporter " + getUsername() + " cannot comment on ticket " + ticketID + ".");
            commands.add(error);
            return;
        }
        comment = new Comment(getUsername(), getCommentText(), getTimestamp());
        ticket.getComments().add(comment);
    }

}
