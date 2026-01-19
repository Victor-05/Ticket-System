package main;

public class DevelopmentState  implements ApplicationState {
    /**
     * Metoda care duce in starea de verificare
     * @param app aplicatia in sine
     */
    @Override
    public void next(final Application app) {
        app.setState(new VerificationState());
    }

    /**
     * Metoda care duce in starea de testare
     * @param app aplicatia in sine
     */
    @Override
    public void prev(final Application app) {
        app.setState(new TestingState());
    }

    /**
     * Metoda care returneaza statusul curent al aplicatiei
     * @return
     */
    @Override
    public String getStatus() {
        return "DEVELOPMENT";
    }
}
