package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.Ticket;
import ticket.TicketStorage;

import java.util.ArrayList;

public class AppStabilityReport extends Command {
    private Report report;
    AppStabilityReport(final CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
    }

    @Override
    public final void execute(final Application app,
                              final TicketStorage ticketStorage,
                              final ArrayList<Command> commands,
                              final MilestoneStorage milestoneStorage) {
        report = new Report();
        report.setOpenTicketsByType(new Report.TicketsByType());
        report.setOpenTicketsByPriority(new Report.TicketsByPriority());
        report.setImpactByType(new Report.CustomerImpactByType());

        CommandInput input = new CommandInput();
        input.setCommand("generateTicketRiskReport");
        input.setUsername(getUsername());
        input.setTimestamp(getTimestamp());
        GenerateTicketRiskReport risk = new GenerateTicketRiskReport(input);
        risk.executeWithoutPrint(app, ticketStorage, commands, milestoneStorage);

        input.setCommand("generateCustomerImpactReport");
        input.setUsername(getUsername());
        input.setTimestamp(getTimestamp());
        GenerateCustomerImpactReport impact = new GenerateCustomerImpactReport(input);
        impact.executeWithoutPrint(app, ticketStorage, commands, milestoneStorage);

        report.setImpactByType(impact.getReport().getCustomerImpactByType());
        report.setRiskByType(risk.getReport().getRiskByType());

        Boolean thereAreOpenTickets = false;
        for (Ticket x : ticketStorage.getTickets()) {
            if (!(x.getStatus().equals("OPEN"))) {
                continue;
            }
            thereAreOpenTickets = true;
            if (x.getType().equals("BUG")) {
                report.getOpenTicketsByType().setBug(report.getOpenTicketsByType().getBug() + 1);
            } else if (x.getType().equals("FEATURE_REQUEST")) {
                report.getOpenTicketsByType().setFeatureRequest(report.getOpenTicketsByType()
                        .getFeatureRequest() + 1);
            } else if (x.getType().equals("UI_FEEDBACK")) {
                report.getOpenTicketsByType().setUiFeedback(report.getOpenTicketsByType()
                        .getUiFeedback() + 1);
            }
            if (x.getBusinessPriority().equals("LOW")) {
                report.getOpenTicketsByPriority().setLow(
                        report.getOpenTicketsByPriority().getLow() + 1);
            } else if (x.getBusinessPriority().equals("MEDIUM")) {
                report.getOpenTicketsByPriority().setMedium(
                        report.getOpenTicketsByPriority().getMedium() + 1);
            } else if (x.getBusinessPriority().equals("HIGH")) {
                report.getOpenTicketsByPriority().setHigh(
                        report.getOpenTicketsByPriority().getHigh() + 1);
            } else if (x.getBusinessPriority().equals("CRITICAL")) {
                report.getOpenTicketsByPriority().setCritical(
                        report.getOpenTicketsByPriority().getCritical() + 1);
            }
        }
        report.setTotalOpenTickets(report.getOpenTicketsByType().getBug() + report
                .getOpenTicketsByType().getFeatureRequest()
                + report.getOpenTicketsByType().getUiFeedback());
        if (!thereAreOpenTickets) {
            report.setAppStability("STABLE");
        } else if (risk.getReport().getRiskByType().getBug().equals("NEGLIGIBLE")
                && risk.getReport().getRiskByType()
                .getFeatureRequest().equals("NEGLIGIBLE")
                && risk.getReport().getRiskByType()
                .getUiFeedback().equals("NEGLIGIBLE")) {
            report.setAppStability("STABLE");
        } else if (risk.getReport().getRiskByType().getBug().equals("SIGNIFICANT")
                || risk.getReport().getRiskByType().getFeatureRequest().equals("SIGNIFICANT")
                || risk.getReport().getRiskByType().getUiFeedback().equals("SIGNIFICANT")) {
            report.setAppStability("UNSTABLE");
        } else {
            report.setAppStability("PARTIALLY STABLE");
        }
        commands.add(this);
    }
}
