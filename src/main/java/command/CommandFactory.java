package command;

import main.App;
import main.Application;
import milestone.Milestone;

import java.time.LocalDate;

public class CommandFactory {
    private CommandFactory() { }
    public static Command create(final CommandInput input, final Application app) {
        if (Application.endTestingDate == null) {
            LocalDate startTestingDate = LocalDate.parse(input.getTimestamp(), app.getDateTimeFormatter());
            Application.endTestingDate = startTestingDate;
            Application.endTestingDate = startTestingDate.plusDays(12);
        }
        Application.currentDate = LocalDate.parse(input.getTimestamp(), app.getDateTimeFormatter());
        if (Application.currentDate.isAfter(Application.endTestingDate)) {
            app.nextState();
        }
        return switch (input.getCommand()) {
            case "reportTicket" -> new ReportTicketCommand(input);
            case "viewTickets" -> new ViewTicketsCommand(input);
            case "lostInvestors" -> new LostInvestors(input);
            case "createMilestone" -> new CreateMilestone(input);
            case "viewMilestones" -> new ViewMilestonesCommand(input);
            default -> null;
        };
    }
}
