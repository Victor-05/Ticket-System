package command;

import constants.Constants;
import lombok.Data;
import main.Application;
import milestone.MilestoneStorage;
import ticket.Frequency;
import ticket.Severity;
import ticket.Ticket;
import ticket.TicketStorage;
import ticket.BusinessValue;
import ticket.CustomerDemand;

import java.util.ArrayList;
import java.util.List;

@Data
public class GenerateTicketRiskReport extends Command {
    private Report report;
    GenerateTicketRiskReport(final CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
    }

    @Override
    public final void execute(final Application app,
                        final TicketStorage ticketStorage,
                        final ArrayList<Command> commands,
                        final MilestoneStorage milestoneStorage) {
        executeWithoutPrint(app, ticketStorage, commands, milestoneStorage);
        commands.add(this);
    }

    /**
     * Executarea actiunii specifice a acestei comenzi
     * fara printarea rezultatului
     * @param app aplicatia in sine
     * @param ticketStorage locul unde sunt toate ticketele
     * @param commands lista de comenzi
     * @param milestoneStorage locul unde sunt toate milestoneurile
     */
    public final void executeWithoutPrint(final Application app,
                                    final TicketStorage ticketStorage,
                                    final ArrayList<Command> commands,
                                    final MilestoneStorage milestoneStorage) {
        List<Double> bugAverage = new ArrayList<>();
        List<Double> featureAverage = new ArrayList<>();
        List<Double> uiAverage = new ArrayList<>();

        report = new Report();
        report.setTicketsByType(new Report.TicketsByType());
        report.setTicketsByPriority(new Report.TicketsByPriority());
        report.setRiskByType(new Report.CustomerRiskByType());

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
                report.getTicketsByType().setBug(report.getTicketsByType().getBug() + 1);

                if (x.getFrequency() == Frequency.RARE) {
                    frequency = Constants.FREQUENCY_RARE;
                } else if (x.getFrequency() == Frequency.OCCASIONAL) {
                    frequency = Constants.FREQUENCY_OCCASIONAL;
                } else if (x.getFrequency() == Frequency.FREQUENT) {
                    frequency = Constants.FREQUENCY_FREQUENT;
                } else {
                    frequency = Constants.FREQUENCY_ALWAYS;
                }

                if (x.getSeverity() == Severity.MINOR) {
                    severityFactor = Constants.SEVERITY_MINOR;
                } else if (x.getSeverity() == Severity.MODERATE) {
                    severityFactor = Constants.SEVERITY_MODERATE;
                } else {
                    severityFactor = Constants.SEVERITY_CRITICAL;
                }


                if (x.getBusinessPriority().equals("LOW")) {
                    businessPriority = Constants.BUSINESS_PRIORITY_LOW;
                } else if (x.getBusinessPriority().equals("MEDIUM")) {
                    businessPriority = Constants.BUSINESS_PRIORITY_MEDIUM;
                } else if (x.getBusinessPriority().equals("HIGH")) {
                    businessPriority = Constants.BUSINESS_PRIORITY_HIGH;
                } else {
                    businessPriority = Constants.BUSINESS_PRIORITY_CRITICAL;
                }

                int bugValue = frequency * severityFactor;
                bugAverage.add((bugValue * Constants.PERCENATGE)
                        / Constants.BUG_RISK);
            } else if (x.getType().equals("FEATURE_REQUEST")) {
                report.getTicketsByType().setFeatureRequest(
                        report.getTicketsByType().getFeatureRequest() + 1);

                if (x.getBusinessValue() == BusinessValue.S) {
                    businessValue = Constants.BUSINESS_VALUE_S;
                } else if (x.getBusinessValue() == BusinessValue.M) {
                    businessValue = Constants.BUSINESS_VALUE_M;
                } else if (x.getBusinessValue() == BusinessValue.L) {
                    businessValue = Constants.BUSINESS_VALUE_L;
                } else {
                    businessValue = Constants.BUSINESS_VALUE_XL;
                }

                if (x.getCustomerDemand() == CustomerDemand.LOW) {
                    customerDemand = Constants.CUSTOMER_DEMAND_LOW;
                } else if (x.getCustomerDemand() == CustomerDemand.MEDIUM) {
                    customerDemand = Constants.CUSTOMER_DEMAND_MEDIUM;
                } else if (x.getCustomerDemand() == CustomerDemand.HIGH) {
                    customerDemand = Constants.CUSTOMER_DEMAND_HIGH;
                } else {
                    customerDemand = Constants.CUSTOMER_DEMAND_VERY_HIGH;
                }

                int featureValue = businessValue + customerDemand;
                featureAverage.add((featureValue * Constants.PERCENATGE)
                        / Constants.FEATURE_RISK);
            } else if (x.getType().equals("UI_FEEDBACK")) {
                report.getTicketsByType().setUiFeedback(
                        report.getTicketsByType().getUiFeedback() + 1);

                if (x.getBusinessValue() == BusinessValue.S) {
                    businessValue = Constants.BUSINESS_VALUE_S;
                } else if (x.getBusinessValue() == BusinessValue.M) {
                    businessValue = Constants.BUSINESS_VALUE_M;
                } else if (x.getBusinessValue() == BusinessValue.L) {
                    businessValue = Constants.BUSINESS_VALUE_L;
                } else {
                    businessValue = Constants.BUSINESS_VALUE_XL;
                }

                usabilityScore = x.getUsabilityScore();

                int uiValue = (Constants.UI_RISK - usabilityScore)
                        * businessValue;
                uiAverage.add((uiValue * Constants.PERCENATGE)
                        / Constants.PERCENATGE);
            }
            if (x.getBusinessPriority().equals("LOW")) {
                report.getTicketsByPriority().setLow(
                        report.getTicketsByPriority().getLow() + 1);
            } else if (x.getBusinessPriority().equals("MEDIUM")) {
                report.getTicketsByPriority().setMedium(
                        report.getTicketsByPriority().getMedium() + 1);
            } else if (x.getBusinessPriority().equals("HIGH")) {
                report.getTicketsByPriority().setHigh(
                        report.getTicketsByPriority().getHigh() + 1);
            } else if (x.getBusinessPriority().equals("CRITICAL")) {
                report.getTicketsByPriority().setCritical(
                        report.getTicketsByPriority().getCritical() + 1);
            }
        }

        double bugFinal = bugAverage.stream()
                .mapToDouble(Double::doubleValue).average().orElse(0.0);
        double featureFinal = featureAverage.stream()
                .mapToDouble(Double::doubleValue).average().orElse(0.0);
        double uiFinal = uiAverage.stream()
                .mapToDouble(Double::doubleValue).average().orElse(0.0);

        double bugRisk = Math.round(bugFinal * Constants.PERCENATGE)
                / Constants.PERCENATGE;
        double featureRisk = Math.round(featureFinal * Constants.PERCENATGE)
                / Constants.PERCENATGE;
        double uiRisk = Math.round(uiFinal * Constants.PERCENATGE)
                / Constants.PERCENATGE;
        report.getRiskByType().setBug(riskCategory(bugRisk));
        report.getRiskByType().setFeatureRequest(riskCategory(featureRisk));
        report.getRiskByType().setUiFeedback(riskCategory(uiRisk));

        report.setTotalTickets(report.getTicketsByType().getBug()
                + report.getTicketsByType().getFeatureRequest()
                + report.getTicketsByType().getUiFeedback());
    }

    private static String riskCategory(final double value) {
        if (value <= Constants.RISK_NEGLIGIBLE) {
            return "NEGLIGIBLE";
        }
        if (value <= Constants.RISK_MODERATE) {
            return "MODERATE";
        }
        if (value <= Constants.RISK_SIGNIFICANT) {
            return "SIGNIFICANT";
        }
        if (value <= Constants.RISK_MAJOR) {
            return "MAJOR";
        }
        return null;
    }
}
