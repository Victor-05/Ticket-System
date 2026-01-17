package users;

public class Reporter extends User {
    public Reporter(String username, String email) {
        super(username, email, Role.REPORTER);
    }

    private Reporter(Builder builder) {
        super(builder.username, builder.email, Role.REPORTER);
    }

    @Override
    public User copy() {
        return new Reporter.Builder(this.getUsername(), this.getEmail()).build();
    }

    public static class Builder {
        private String username;
        private String email;

        public Builder(String username, String email) {
            this.username = username;
            this.email = email;
        }

        public Reporter build() {
            return new Reporter(this);
        }
    }
}
