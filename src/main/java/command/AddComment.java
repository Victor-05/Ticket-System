package command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import main.Application;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.Ticket;
import ticket.TicketStorage;

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
        comment = new Comment(getUsername(), getCommentText(), getTimestamp());
        ticket.getComments().add(comment);
    }

}
