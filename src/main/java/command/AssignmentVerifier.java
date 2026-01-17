package command;

import milestone.Milestone;
import milestone.MilestoneStorage;
import ticket.Ticket;
import users.Developer;
import users.ExpertiseArea;
import users.Seniority;

public class AssignmentVerifier {

    public static boolean canAssign(Developer user, Ticket ticket) {

        if (user.getExpertiseArea() != ticket.getExpertiseArea()) {
            if (user.getExpertiseArea() != ExpertiseArea.FULLSTACK) {
                return false;
            }
        }
        if (user.getSeniority() == Seniority.JUNIOR) {
            if (!ticket.getType().equals("FEATURE_REQUEST")) {
                return false;
            }
            if (ticket.getBusinessPriority().equals("HIGH")) {
                return false;
            }
            if (ticket.getBusinessPriority().equals("CRITICAL")) {
                return false;
            }
        }

        if (user.getSeniority() == Seniority.MID) {
            if (ticket.getBusinessPriority().equals("CRITICAL")) {
                return false;
            }
        }

        if (!ticket.getStatus().equals("OPEN")) {
            return false;
        }

        Milestone milestone = MilestoneStorage.getMilestoneByName(MilestoneStorage.nameOfTheMilestoneThatContaintsTicket(ticket.getId()));

        if (milestone != null) {
            if (!milestone.getAssignedDevs().contains(user.getUsername())) {
                return false;
            }
            if (milestone.getIsBlocked()) {
                return false;
            }
        }
        return true;
    }
}

