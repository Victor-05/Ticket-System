package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.TicketStorage;

public final class CommandFactory {
    private CommandFactory() { }

    /**
     * Metoda pentru crearea unei comenzi in functie
     * de tipul ei citit de la intrare
     * @param input datele de intrare
     * @param app aplicatia in sine
     * @param milestoneStorage milestoneurile stocate
     * @param ticketStorage ticketele stocate
     * @return
     */
    public static Command create(final CommandInput input,
                                 final Application app,
                                 final MilestoneStorage milestoneStorage,
                                 final TicketStorage ticketStorage) {
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
            case "undoChangeStatus" -> new UndoChangeStatus(input);
            case "search" -> switch (input.getFilters().getSearchType()) {
                case "TICKET" -> new SearchTicket(input);
                case "DEVELOPER" -> new SearchUser(input);
                default -> null;
            };
            case "viewNotifications" -> new ViewNotifications(input);
            case "generateCustomerImpactReport" -> new GenerateCustomerImpactReport(input);
            case "generateTicketRiskReport" -> new GenerateTicketRiskReport(input);
            case "generateResolutionEfficiencyReport"
                    -> new GenerateResolutionEfficiencyReport(input);
            case "appStabilityReport" -> new AppStabilityReport(input);
            case "generatePerformanceReport" -> new GeneratePerformanceReport(input);
            case "startTestingPhase" -> new StartTestingPhase(input);
            default -> null;
        };
    }
}
