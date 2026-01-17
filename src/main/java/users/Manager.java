package users;

import java.util.ArrayList;

public class Manager extends User {
    private String hireDate;
    private ArrayList<String> subordinates = new ArrayList<>();
    public Manager(String username, String email) {
        super(username, email, Role.MANAGER);
    }
    private Manager(Builder builder) {
        super(builder.username, builder.email, Role.MANAGER);
        this.hireDate = builder.hireDate;
        this.subordinates = builder.subordinates;
    }

    @Override
    public User copy() {
        return new Manager.Builder(this.getUsername(), this.getEmail())
                .hireDate(this.hireDate)
                .subordinates(new ArrayList<>(this.subordinates))
                .build();
    }

    public static class Builder {
        private String username;
        private String email;
        private String hireDate;
        private ArrayList<String> subordinates;

        public Builder(String username, String email) {
            this.username = username;
            this.email = email;
        }

        public Builder hireDate(String hireDate) {
            this.hireDate = hireDate;
            return this;
        }

        public Builder subordinates(ArrayList<String> subordinates) {
            this.subordinates = subordinates;
            return this;
        }

        public Manager build() {
            return new Manager(this);
        }
    }
}
