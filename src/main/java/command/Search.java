package command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ticket.Filter;
import java.util.ArrayList;

@Data
public abstract class Search<T> extends Command {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Filter filters;
    private String searchType;
    private ArrayList<T> results = new ArrayList<>();
    Search(final CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        this.filters = input.getFilters();
    }

}
