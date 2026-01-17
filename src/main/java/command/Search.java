package command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.Application;
import milestone.MilestoneStorage;
import ticket.Filter;
import ticket.Ticket;
import ticket.TicketStorage;
import users.ExpertiseArea;
import users.Seniority;
import users.User;
import users.UsersDatabase;

import java.util.ArrayList;

@Data
public abstract class Search<T> extends Command {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Filter filters;
    private String searchType;
    private ArrayList<T> results = new ArrayList<>();
    Search(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        this.filters = input.getFilters();
    }

}
