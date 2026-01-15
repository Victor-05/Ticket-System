package command;

import lombok.Data;
import main.Application;
import milestone.Milestone;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.Ticket;
import ticket.TicketStorage;
import users.*;

import java.util.ArrayList;

@Data
public class AssignTicketCommand extends Command {
    private int ticketID;
    AssignTicketCommand(CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
        this.setTicketID(input.getTicketID());
    }
    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
        ReportTicket assignedTicket = (ReportTicket) ticketStorage.getTicketsById(ticketID);
        if (assignedTicket == null) {
            return;
        }
        if (UsersDatabase.getUser(getUsername()).getRole() != Role.DEVELOPER) {
            System.out.println("123123123123123");
        }
        Developer user = (Developer) UsersDatabase.getUser(getUsername());
        if (user.getExpertiseArea() != assignedTicket.getExpertiseArea() ) {
            if (user.getExpertiseArea() == ExpertiseArea.FRONTEND) {
                Command error = new ErrorCommand(getCommand(), getUsername(), getTimestamp(), "Developer " + getUsername() + " cannot assign ticket " + ticketID + " due to expertise area. Required: BACKEND, DB, FULLSTACK" + "; Current: " + user.getExpertiseArea() + ".");
                commands.add(error);
                return;
            }
            if (user.getExpertiseArea() == ExpertiseArea.BACKEND) {
                Command error = new ErrorCommand(getCommand(), getUsername(), getTimestamp(), "Developer " + getUsername() + " cannot assign ticket " + ticketID + " due to expertise area. Required: FRONTEND, DEVOPS, DESIGN, FULLSTACK" + "; Current: " + user.getExpertiseArea() + ".");
                commands.add(error);
                return;
            }
            if (user.getExpertiseArea() == ExpertiseArea.DEVOPS) {
                Command error = new ErrorCommand(getCommand(), getUsername(), getTimestamp(), "Developer " + getUsername() + " cannot assign ticket " + ticketID + " due to expertise area. Required: FRONTEND, BACKEND, DESIGN, DB, FULLSTACK" + "; Current: " + user.getExpertiseArea() + ".");
                commands.add(error);
                return;
            }
            if (user.getExpertiseArea() == ExpertiseArea.DESIGN) {
                Command error = new ErrorCommand(getCommand(), getUsername(), getTimestamp(), "Developer " + getUsername() + " cannot assign ticket " + ticketID + " due to expertise area. Required: BACKEND, DEVOPS, DB, FULLSTACK" + "; Current: " + user.getExpertiseArea() + ".");
                commands.add(error);
                return;
            }
            if (user.getExpertiseArea() == ExpertiseArea.DB) {
                Command error = new ErrorCommand(getCommand(), getUsername(), getTimestamp(), "Developer " + getUsername() + " cannot assign ticket " + ticketID + " due to expertise area. Required: FRONTEND, BACKEND, DEVOPS, DESIGN, FULLSTACK" + "; Current: " + user.getExpertiseArea() + ".");
                commands.add(error);
                return;
            }
        }
        if (user.getSeniority() == Seniority.JUNIOR) {
            if (!assignedTicket.getType().equals("FEATURE_REQUEST")) {
                Command error = new ErrorCommand(getCommand(),  getUsername(), getTimestamp(), "Developer " + getUsername() + " cannot assign ticket " + ticketID + " due to seniority level. Required: MID, SENIOR" + "; Current: " +  user.getSeniority() + ".");
                commands.add(error);
                return;
            }
            if (assignedTicket.getBusinessPriority().equals("HIGH")) {
                Command error = new ErrorCommand(getCommand(),  getUsername(), getTimestamp(), "Developer " + getUsername() + " cannot assign ticket " + ticketID + " due to seniority level. Required: MID, SENIOR" + "; Current: " +  user.getSeniority() + ".");
                commands.add(error);
                return;
            }
            if (assignedTicket.getBusinessPriority().equals("CRITICAL")) {
                Command error = new ErrorCommand(getCommand(),  getUsername(), getTimestamp(), "Developer " + getUsername() + " cannot assign ticket " + ticketID + " due to seniority level. Required: SENIOR" + "; Current: " +  user.getSeniority() + ".");
                commands.add(error);
                return;
            }
        }
        if (user.getSeniority() == Seniority.MID) {
            if (assignedTicket.getBusinessPriority().equals("CRITICAL")) {
                Command error = new ErrorCommand(getCommand(),  getUsername(), getTimestamp(), "Developer " + getUsername() + " cannot assign ticket " + ticketID + " due to seniority level. Required: SENIOR" + "; Current: " +  user.getSeniority() + ".");
                commands.add(error);
                return;
            }
        }
        if (!assignedTicket.getStatus().equals("OPEN")) {
            Command error = new ErrorCommand(getCommand(),  getUsername(), getTimestamp(), "Only OPEN tickets can be assigned.");
            commands.add(error);
            return;
        }
        Milestone milestoneOfTheTicket = MilestoneStorage.getMilestoneByName(MilestoneStorage.nameOfTheMilestoneThatContaintsTicket(assignedTicket.getId()));
        if (milestoneOfTheTicket != null) {
            int ok = 0;
            for (String x : milestoneOfTheTicket.getAssignedDevs()) {
                if (user.getUsername().equals(x)) {
                    ok = 1;
                    break;
                }
            }
            if (ok == 0) {
                Command error = new ErrorCommand(getCommand(),  getUsername(), getTimestamp(), "Developer " + getUsername() + " is not assigned to milestone " + milestoneOfTheTicket.getName() + ".");
                commands.add(error);
                return;
            }
            if (milestoneOfTheTicket.getIsBlocked()) {
                Command error = new ErrorCommand(getCommand(),  getUsername(), getTimestamp(), "Cannot assign ticket " + ticketID + " from blocked milestone " + milestoneOfTheTicket.getName() + ".");
                commands.add(error);
                return;
            }
        }
        assignedTicket.setStatus("IN_PROGRESS");
        ReportTicket ticket = (ReportTicket) assignedTicket;
        ticket.setAssignedAt(Application.currentDate.toString());
        user.getTickets().add(ticket);
    }
}
