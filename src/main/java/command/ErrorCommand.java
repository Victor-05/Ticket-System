package command;

import lombok.Data;
import main.Application;
import milestone.MilestoneStorage;
import ticket.TicketStorage;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class ErrorCommand extends Command {
    private String command;
    private String username;
    private String timestamp;
    private String error;

    public ErrorCommand(String command, String username, String timestamp, String error) {
        this.command = command;
        this.username = username;
        this.timestamp = timestamp;
        this.error = error;
    }

    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
        if (Application.endTestingDate == null) {
            LocalDate startTestingDate = LocalDate.parse(getTimestamp(), app.getDateTimeFormatter());
            Application.endTestingDate = startTestingDate;
            Application.endTestingDate = startTestingDate.plusDays(12);
        }
        LocalDate currentDate = LocalDate.parse(getTimestamp(), app.getDateTimeFormatter());
        if (currentDate.isAfter(Application.endTestingDate)) {
            app.nextState();
        }
    }
}
