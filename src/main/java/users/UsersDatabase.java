package users;

import ticket.Ticket;
import ticket.TicketStorage;

import java.util.ArrayList;
import java.util.List;

public class UsersDatabase {
    private static final UsersDatabase INSTANCE = new UsersDatabase();
    private static final List<User> users = new ArrayList<>();

    private UsersDatabase() { }

    public static UsersDatabase getInstance() {
        return INSTANCE;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    static public Boolean checkIfUserExists(String username) {
        for (User x : users) {
            if (x.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    static public Role getUserRole(String username) {
        for (User x : users) {
            if (x.getUsername().equals(username)) {
                return x.getRole();
            }
        }
        return null;
    }

    public static User getUser(String username) {
        for (User x : users) {
            if (x.getUsername().equals(username)) {
                return x;
            }
        }
        return null;
    }

    public static void reset() {
        users.clear();
    }
}
