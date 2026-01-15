package users;

import lombok.Data;
import lombok.NoArgsConstructor;
import ticket.ReportTicket;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public abstract class User {
    private String username;
    private String email;
    private Role role;
    private ArrayList<ReportTicket> tickets = new ArrayList<>();
    public User(String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
