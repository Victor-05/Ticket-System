package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.ReportTicket;
import ticket.Ticket;
import ticket.TicketStorage;
import users.*;

import java.util.ArrayList;

public class SearchUser extends Search {

    SearchUser(CommandInput input) {
        super(input);
    }
    @Override
    public void execute(Application app, TicketStorage ticketStorage, ArrayList<Command> commands, MilestoneStorage milestoneStorage) {
        setSearchType("DEVELOPER");
        UsersDatabase usersDatabase = UsersDatabase.getInstance();
        ArrayList<User> users = (ArrayList<User>) usersDatabase.getUsers();
        ArrayList<User> possibleUsers = new ArrayList<>();
        ArrayList<SearchUserOutput> results = new ArrayList<>();
        for (User x : users) {
            possibleUsers.add(x.copy());
        }
        for (User x : possibleUsers) {
            if (x.getRole() == Role.DEVELOPER) {
                Developer user = (Developer) x;
                if (getFilters().getExpertiseArea() != null && user.getExpertiseArea() != getFilters().getExpertiseArea()) {
                    continue;
                }
                if (getFilters().getSeniority() != null && user.getSeniority() != getFilters().getSeniority()) {
                    continue;
                }
                results.add(new SearchUserOutput(user));
            }
        }
        results.sort((a, b) -> a.getHireDate().compareTo(b.getHireDate()));
        setResults(results);
        commands.add(this);
    }
}
