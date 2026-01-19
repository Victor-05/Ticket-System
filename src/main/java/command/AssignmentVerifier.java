package command;

import milestone.Milestone;
import milestone.MilestoneStorage;
import ticket.Ticket;
import users.Developer;
import users.ExpertiseArea;
import users.Seniority;


/*
    Clasa pentru a verifica daca un ticket este asignabil pentru un utilizator
 */
public final class AssignmentVerifier {

    private AssignmentVerifier() {
        System.out.println("Nu poate fi instantiata");
    }
    /**
     *  Metoda cu care se realizeaza verificarea
     *  @param user utilizatorul pentru care se face verificarea
     *  @param ticket ticketul cautat
     **/
    public static boolean canAssign(final Developer user,  final Ticket ticket) {
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

        Milestone milestone = MilestoneStorage.getMilestoneByName(MilestoneStorage
                .nameOfTheMilestoneThatContaintsTicket(ticket.getId()));

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

