package users;

import lombok.Data;

@Data
public abstract class User {
    private String username;
    private String email;
    private Role role;
    public User(String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
