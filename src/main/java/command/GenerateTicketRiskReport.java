package command;

import lombok.Data;
import main.Application;
import milestone.MilestoneStorage;
import ticket.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class GenerateTicketRiskReport extends Command {
    private Report report;
    GenerateTicketRiskReport(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
    }

    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
        executeWithoutPrint(app, ticketStorage, commands, milestoneStorage);
        commands.add(this);
    }

    public void executeWithoutPrint(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
        List<Double> bug_average = new ArrayList<>();
        List<Double> feature_average = new ArrayList<>();
        List<Double> ui_average = new ArrayList<>();

        report = new Report();
        report.ticketsByType = new Report.TicketsByType();
        report.ticketsByPriority = new Report.TicketsByPriority();
        report.riskByType = new Report.CustomerRiskByType();

        for (Ticket x : ticketStorage.getTickets()) {
            int frequency = 0;
            int businessPriority = 0;
            int severityFactor = 0;

            int businessValue = 0;
            int customerDemand = 0;
            int usabilityScore = 0;
            if (!(x.getStatus().equals("OPEN") || x.getStatus().equals("IN_PROGRESS"))) {
                continue;
            }
            if (x.getType().equals("BUG")) {
                report.ticketsByType.setBug(report.ticketsByType.getBug() + 1);

                if (x.getFrequency() == Frequency.RARE) {
                    frequency = 1;
                } else if (x.getFrequency() == Frequency.OCCASIONAL) {
                    frequency = 2;
                } else if (x.getFrequency() == Frequency.FREQUENT) {
                    frequency = 3;
                } else {
                    frequency = 4;
                }

                if (x.getSeverity() == Severity.MINOR) {
                    severityFactor = 1;
                } else if (x.getSeverity() == Severity.MODERATE) {
                    severityFactor = 2;
                } else {
                    severityFactor = 3;
                }

                if (x.getBusinessPriority().equals("LOW")) {
                    businessPriority = 1;
                } else if (x.getBusinessPriority().equals("MEDIUM")) {
                    businessPriority = 2;
                } else if (x.getBusinessPriority().equals("HIGH")) {
                    businessPriority = 3;
                } else {
                    businessPriority = 4;
                }

                int bug_value = frequency * severityFactor;
                bug_average.add((bug_value * 100.0) / 12.0);
            } else if (x.getType().equals("FEATURE_REQUEST")) {
                report.ticketsByType.setFeatureRequest(
                        report.ticketsByType.getFeatureRequest() + 1);

                if (x.getBusinessValue() == BusinessValue.S) {
                    businessValue = 1;
                } else if (x.getBusinessValue() == BusinessValue.M) {
                    businessValue = 3;
                } else if (x.getBusinessValue() == BusinessValue.L) {
                    businessValue = 6;
                } else {
                    businessValue = 10;
                }

                if (x.getCustomerDemand() == CustomerDemand.LOW) {
                    customerDemand = 1;
                } else if (x.getCustomerDemand() == CustomerDemand.MEDIUM) {
                    customerDemand = 3;
                } else if (x.getCustomerDemand() == CustomerDemand.HIGH) {
                    customerDemand = 6;
                } else {
                    customerDemand = 10;
                }

                int feature_value = businessValue + customerDemand;
                feature_average.add((feature_value * 100.0) / 20.0);
            } else if (x.getType().equals("UI_FEEDBACK")) {
                report.ticketsByType.setUiFeedback(
                        report.ticketsByType.getUiFeedback() + 1);

                if (x.getBusinessValue() == BusinessValue.S) {
                    businessValue = 1;
                } else if (x.getBusinessValue() == BusinessValue.M) {
                    businessValue = 3;
                } else if (x.getBusinessValue() == BusinessValue.L) {
                    businessValue = 6;
                } else {
                    businessValue = 10;
                }

                usabilityScore = x.getUsabilityScore();

                int uiValue = (11 - usabilityScore) * businessValue;
                ui_average.add((uiValue * 100.0) / 100.0);
            }
            if (x.getBusinessPriority().equals("LOW")) {
                report.ticketsByPriority.setLow(
                        report.ticketsByPriority.getLow() + 1);
            } else if (x.getBusinessPriority().equals("MEDIUM")) {
                report.ticketsByPriority.setMedium(
                        report.ticketsByPriority.getMedium() + 1);
            } else if (x.getBusinessPriority().equals("HIGH")) {
                report.ticketsByPriority.setHigh(
                        report.ticketsByPriority.getHigh() + 1);
            } else if (x.getBusinessPriority().equals("CRITICAL")) {
                report.ticketsByPriority.setCritical(
                        report.ticketsByPriority.getCritical() + 1);
            }
        }

        double bug_final = bug_average.stream()
                .mapToDouble(Double::doubleValue).average().orElse(0.0);
        double feature_final = feature_average.stream()
                .mapToDouble(Double::doubleValue).average().orElse(0.0);
        double ui_final = ui_average.stream()
                .mapToDouble(Double::doubleValue).average().orElse(0.0);

        double bugRisk = Math.round(bug_final * 100.0) / 100.0;
        double featureRisk = Math.round(feature_final * 100.0) / 100.0;
        double uiRisk = Math.round(ui_final * 100.0) / 100.0;
        report.riskByType.setBug(riskCategory(bugRisk));
        report.riskByType.setFeatureRequest(riskCategory(featureRisk));
        report.riskByType.setUiFeedback(riskCategory(uiRisk));

        report.setTotalTickets(report.ticketsByType.getBug() + report.ticketsByType.getFeatureRequest() + report.ticketsByType.getUiFeedback());
    }

    private static String riskCategory(double value) {
        if (value <= 24) return "NEGLIGIBLE";
        if (value <= 49) return "MODERATE";
        if (value <= 74) return "SIGNIFICANT";
        if (value <= 100) return "MAJOR";
        return "INVALID";
    }
}
