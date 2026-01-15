package users;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Developer extends User {
    private String hireDate;
    private ExpertiseArea expertiseArea;
    private Seniority seniority;
    public Developer(String username, String email) {
        super(username, email, Role.DEVELOPER);
    }

    private Developer(Builder builder) {
        super(builder.username, builder.email, Role.DEVELOPER);
        this.hireDate = builder.hireDate;
        this.expertiseArea = builder.expertiseArea;
        this.seniority = builder.seniority;
    }

    public static class Builder {
        private String username;
        private String email;
        private String hireDate;
        private ExpertiseArea expertiseArea;
        private Seniority seniority;

        public Builder(String username, String email) {
            this.username = username;
            this.email = email;
        }

        public Builder hireDate(String hireDate) {
            this.hireDate = hireDate;
            return this;
        }

        public Builder expertiseArea(ExpertiseArea expertiseArea) {
            this.expertiseArea = expertiseArea;
            return this;
        }

        public Builder seniority(Seniority seniority) {
            this.seniority = seniority;
            return this;
        }

        public Developer build() {
            return new Developer(this);
        }
    }
}
