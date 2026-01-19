package users;

import lombok.Data;

/**
 * Clasa reprezinta un developer
 * Este creata sub forma design pattern-ului Builder
 */
@Data
public class Developer extends User {
    private String hireDate;
    private ExpertiseArea expertiseArea;
    private Seniority seniority;

    public Developer(final String username, final String email) {
        super(username, email, Role.DEVELOPER);
    }

    private Developer(final Builder builder) {
        super(builder.username, builder.email, Role.DEVELOPER);
        this.hireDate = builder.hireDate;
        this.expertiseArea = builder.expertiseArea;
        this.seniority = builder.seniority;
    }

    /**
     * Returneaza copia unui developer
     * @return copia unui developer
     */
    @Override
    public User copy() {
        return new Developer.Builder(this.getUsername(), this.getEmail())
                .hireDate(this.hireDate)
                .expertiseArea(this.expertiseArea)
                .seniority(this.seniority)
                .build();
    }

    public static class Builder {
        private String username;
        private String email;
        private String hireDate;
        private ExpertiseArea expertiseArea;
        private Seniority seniority;

        public Builder(final String username, final String email) {
            this.username = username;
            this.email = email;
        }

        /**
         * Seteaza data de angajare a unui developer
         * @param date data de angajare
         * @return Builder
         */
        public Builder hireDate(final String date) {
            this.hireDate = date;
            return this;
        }

        /**
         * Seteaza zona de lucru a unui developer
         * @param area zona de lucru
         * @return Builder
         */
        public Builder expertiseArea(final ExpertiseArea area) {
            this.expertiseArea = area;
            return this;
        }

        /**
         * Seteaza nivelul de senioritate a unui developer
         * @param experience nivelul de senioritate
         * @return Builder
         */
        public Builder seniority(final Seniority experience) {
            this.seniority = experience;
            return this;
        }

        /**
         * Creaza un nou developer
         * @return un nou developer
         */
        public Developer build() {
            return new Developer(this);
        }
    }
}
