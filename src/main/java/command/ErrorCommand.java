package command;

import constants.Constants;
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

    public ErrorCommand(final String command,
                        final String username,
                        final String timestamp,
                        final String error) {
        this.command = command;
        this.username = username;
        this.timestamp = timestamp;
        this.error = error;
    }

    @Override
    public final void execute(final Application app,
                        final TicketStorage ticketStorage,
                        final ArrayList<Command> commands,
                        final MilestoneStorage milestoneStorage) {
        if (app.getEndTestingDate() == null) {
            LocalDate startTestingDate = LocalDate.parse(getTimestamp(),
                    app.getDateTimeFormatter());
            app.setEndTestingDate(startTestingDate);
            app.setEndTestingDate(startTestingDate
                    .plusDays(Constants.DAYS_TO_CLOSE_TESTING));
        }
        LocalDate currentDate = LocalDate.parse(getTimestamp(), app.getDateTimeFormatter());
        if (currentDate.isAfter(app.getEndTestingDate())) {
            app.nextState();
        }
    }
}
