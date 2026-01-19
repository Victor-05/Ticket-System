package users;

import java.util.ArrayList;

public final class Manager extends User {
    private String hireDate;
    private ArrayList<String> subordinates = new ArrayList<>();
    private Manager(final Builder builder) {
        super(builder.username, builder.email, Role.MANAGER);
        this.hireDate = builder.hireDate;
        this.subordinates = builder.subordinates;
    }

    /**
     * Returneaza copia unui manager
     * @return copia unui manager
     */
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

        public Builder(final String username, final String email) {
            this.username = username;
            this.email = email;
        }

        /**
         * Seteaza data de angajare a unui manager
         * @param date data de angajare
         * @return Builder
         */
        public Builder hireDate(final String date) {
            this.hireDate = date;
            return this;
        }

        /**
         * Seteaza lista de subordonati a unui manager
         * @param employeesInSubordinates lista de subordonati
         * @return Builder
         */
        public Builder subordinates(final ArrayList<String> employeesInSubordinates) {
            this.subordinates = employeesInSubordinates;
            return this;
        }

        /**
         * Creaza un nou manager
         * @return
         */
        public Manager build() {
            return new Manager(this);
        }
    }
}
