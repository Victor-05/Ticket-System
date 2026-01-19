package users;

import java.util.List;

/**
 * UserFactory class care urmareste design patternul Factory
 */
public class UserFactory {
    private UserFactory() { }
    public static void create(final List<UserDTO> input,
                              final UsersDatabase usersDatabase) {
        for (UserDTO x : input) {
            Role userRole = x.getRole();
            switch (userRole) {
                case REPORTER -> usersDatabase.addUser(new Reporter.Builder(x
                        .getUsername(), x.getEmail()).build());
                case DEVELOPER -> usersDatabase.addUser(new Developer
                        .Builder(x.getUsername(), x.getEmail())
                        .hireDate(x.getHireDate())
                        .expertiseArea(x.getExpertiseArea())
                        .seniority(x.getSeniority())
                        .build());
                case MANAGER -> usersDatabase.addUser(new Manager.Builder(x
                        .getUsername(), x.getEmail())
                        .hireDate(x.getHireDate())
                        .subordinates(x.getSubordinates())
                        .build());
            }
        }
    }
}