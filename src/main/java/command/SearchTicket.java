package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.Ticket;
import ticket.TicketStorage;
import users.Developer;
import users.Manager;
import users.Role;
import users.User;
import users.UsersDatabase;

import java.util.ArrayList;

public class SearchTicket extends Search {
    SearchTicket(final CommandInput input) {
        super(input);
    }

    @Override
    public final void execute(final Application app,
                              final TicketStorage ticketStorage,
                              final ArrayList<Command> commands,
                              final MilestoneStorage milestoneStorage) {
        setSearchType("TICKET");
        User gotUser =  UsersDatabase.getUser(getUsername());
        ArrayList<ReportTicket> possibleTicket = new ArrayList<>();
        if (gotUser.getRole() == Role.DEVELOPER) {
            Developer user = (Developer) gotUser;
            ArrayList<Integer> tickets = MilestoneStorage
                    .getTicketsOfMilestonesOfUser(getUsername());
            for (Integer ticketId : tickets) {
                possibleTicket.add((ReportTicket) ticketStorage
                        .getTicketsById(ticketId));
            }
            for (ReportTicket x : possibleTicket) {
                ArrayList<String> matchingWords = new ArrayList<>();
                if (!x.getStatus().equals("OPEN")) {
                    continue;
                }
                if (getFilters().getExpertiseArea() != null
                        && x.getExpertiseArea() != getFilters().getExpertiseArea()) {
                    continue;
                }
                if (getFilters().getBusinessPriority() != null
                        && !x.getBusinessPriority().equals(getFilters().getBusinessPriority())) {
                    continue;
                }
                if (getFilters().getCreatedAt() != null
                        && !x.getCreatedAt().equals(getFilters().getCreatedAt())) {
                    continue;
                }
                if (getFilters().getCreatedAfter() != null
                        && x.getCreatedAt().compareTo(getFilters().getCreatedAfter()) <= 0) {
                    continue;
                }
                if (getFilters().getCreatedBefore() != null
                        && x.getCreatedAt().compareTo(getFilters().getCreatedBefore()) >= 0) {
                    continue;
                }
                if (getFilters().getAvailableForAssignment() != null
                        && !AssignmentVerifier.canAssign(user, x)) {
                    continue;
                }
                if (getFilters().getType() != null
                        && !x.getType().equals(getFilters().getType())) {
                    continue;
                }
                if (!getFilters().getKeywords().isEmpty()) {
                    for (String keyword : getFilters().getKeywords()) {
                        if (x.getDescription() != null
                                && x.getDescription().toLowerCase()
                                .contains(keyword.toLowerCase())) {
                            matchingWords.add(keyword);
                        } else if (x.getTitle() != null
                                && x.getTitle().toLowerCase()
                                .contains(keyword.toLowerCase())) {
                            matchingWords.add(keyword);
                        }
                    }
                    if (matchingWords.isEmpty()) {
                        continue;
                    }
                }
                getResults().add(new SearchTicketOutput(x, matchingWords));
            }
        }

        if (gotUser.getRole() == Role.MANAGER) {
            Manager user = (Manager) gotUser;
            for (Ticket x : ticketStorage.getTickets()) {
                possibleTicket.add(new ReportTicket((ReportTicket) x));
            }
            for (ReportTicket x : possibleTicket) {
                ArrayList<String> matchingWords = new ArrayList<>();
                if (getFilters().getExpertiseArea() != null
                        && x.getExpertiseArea() != getFilters().getExpertiseArea()) {
                    continue;
                }
                if (getFilters().getBusinessPriority() != null
                        && !x.getBusinessPriority().equals(getFilters().getBusinessPriority())) {
                    continue;
                }
                if (getFilters().getCreatedAt() != null
                        && !x.getCreatedAt().equals(getFilters().getCreatedAt())) {
                    continue;
                }
                if (getFilters().getCreatedAfter() != null
                        && x.getCreatedAt().compareTo(getFilters().getCreatedAfter()) <= 0) {
                    continue;
                }
                if (getFilters().getCreatedBefore() != null
                        && x.getCreatedAt().compareTo(getFilters().getCreatedBefore()) >= 0) {
                    continue;
                }
                if (getFilters().getAvailableForAssignment() != null
                        && x.getAssignedTo() != null) {
                    continue;
                }
                if (getFilters().getExpertiseArea() != null
                        && x.getExpertiseArea() != getFilters().getExpertiseArea()) {
                    continue;
                }
                if (!getFilters().getKeywords().isEmpty()) {
                    for (String keyword : getFilters().getKeywords()) {
                        if (x.getDescription() != null
                                && x.getDescription().toLowerCase()
                                .contains(keyword.toLowerCase())) {
                            matchingWords.add(keyword);
                        } else if (x.getTitle() != null
                                && x.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                            matchingWords.add(keyword);
                        }
                    }
                    if (matchingWords.isEmpty()) {
                        continue;
                    }
                }
                getResults().add(new SearchTicketOutput(x, matchingWords));
            }
        }
        commands.add(this);
    }
}
