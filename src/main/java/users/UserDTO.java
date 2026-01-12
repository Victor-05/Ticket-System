package users;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private Role role;
    private String hireDate;
    private Seniority seniority;
    private ExpertiseArea expertiseArea;
    private ArrayList<String> subordinates = new ArrayList<>();
}
