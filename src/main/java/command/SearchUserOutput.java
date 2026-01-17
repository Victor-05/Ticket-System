package command;

import lombok.Data;
import users.Developer;
import users.ExpertiseArea;
import users.Seniority;
import users.User;

@Data
public class SearchUserOutput {
    private String username;
    private ExpertiseArea expertiseArea;
    private Seniority seniority;
    private double performanceScore;
    private String hireDate;
    SearchUserOutput(Developer x) {
        this.username = x.getUsername();
        this.expertiseArea = x.getExpertiseArea();
        this.seniority = x.getSeniority();
        performanceScore = 0.0;
        hireDate = x.getHireDate();

    }
}
