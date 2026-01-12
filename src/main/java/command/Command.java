package command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.Application;
import milestone.MilestoneStorage;
import ticket.Ticket;
import ticket.TicketStorage;

import java.util.ArrayList;

@Data
public abstract class Command {
    private String command;
    private String username;
    private String timestamp;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Ticket params;
    public abstract void execute(Application app, TicketStorage ticketStorage, ArrayList<Command>  commands, MilestoneStorage milestoneStorage);
}
