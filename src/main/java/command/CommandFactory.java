package command;

import main.Application;
import milestone.Milestone;
import milestone.MilestoneStorage;
import ticket.TicketStorage;

import java.time.LocalDate;

public class CommandFactory {
    private CommandFactory() { }
    public static Command create(final CommandInput input, final Application app, MilestoneStorage milestoneStorage, TicketStorage ticketStorage) {
        Application.update(input, app, milestoneStorage, ticketStorage);
        return switch (input.getCommand()) {
            case "reportTicket" -> new ReportTicketCommand(input);
            case "viewTickets" -> new ViewTicketsCommand(input);
            case "lostInvestors" -> new LostInvestors(input);
            case "createMilestone" -> new CreateMilestone(input);
            case "assignTicket" -> new AssignTicketCommand(input);
            case "viewMilestones" -> new ViewMilestonesCommand(input);
            case "undoAssignTicket" -> new UndoAssignTicketCommand(input);
            case "viewAssignedTickets" -> new ViewAssignedTickets(input);
            case "addComment" -> new AddComment(input);
            case "undoAddComment" -> new UndoAddComment(input);
            case "changeStatus" -> new ChangeStatus(input);
            case "viewTicketHistory" -> new ViewTicketHistory(input);
            default -> null;
        };
    }
}
