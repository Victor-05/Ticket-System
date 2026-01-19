package users;

public final class Reporter extends User {
    private Reporter(final Builder builder) {
        super(builder.username, builder.email, Role.REPORTER);
    }

    /**
     * Returneaza copia unui reporter
     * @return copia unui reporter
     */
    @Override
    public User copy() {
        return new Reporter.Builder(this.getUsername(), this.getEmail()).build();
    }

    public static class Builder {
        private String username;
        private String email;

        public Builder(final String username, final String email) {
            this.username = username;
            this.email = email;
        }

        /**
         * Creaza un nou reporter
         * @return reporter
         */
        public Reporter build() {
            return new Reporter(this);
        }
    }
}
