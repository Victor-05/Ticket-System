package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.TicketStorage;
import users.Developer;
import users.Role;
import users.User;
import users.UsersDatabase;

import java.util.ArrayList;
import java.util.Comparator;

public class SearchUser extends Search {

    SearchUser(final CommandInput input) {
        super(input);
    }
    @Override
    public final void execute(final Application app,
                        final TicketStorage ticketStorage,
                        final ArrayList<Command> commands,
                        final MilestoneStorage milestoneStorage) {
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
                if (getFilters().getExpertiseArea() != null
                        && user.getExpertiseArea() != getFilters()
                        .getExpertiseArea()) {
                    continue;
                }
                if (getFilters().getSeniority() != null
                        && user.getSeniority() != getFilters()
                        .getSeniority()) {
                    continue;
                }
                results.add(new SearchUserOutput(user));
            }
        }
        results.sort(Comparator.comparing(SearchUserOutput::getHireDate));
        setResults(results);
        commands.add(this);
    }
}
