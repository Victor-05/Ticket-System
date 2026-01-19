package main;

public class VerificationState implements ApplicationState {

    /**
     * Metoda care duce in starea de testare
     * @param app aplicatia in sine
     */
    @Override
    public void next(final Application app) {
        app.setState(new TestingState());
    }

    /**
     * Metoda care duce in starea de dezvoltare
     * @param app aplicatia in sine
     */
    @Override
    public void prev(final Application app) {
        app.setState(new DevelopmentState());
    }

    /**
     * Metoda care returneaza statusul curent al aplicatiei
     * @return
     */
    @Override
    public String getStatus() {
        return "VERIFICATION";
    }
}
