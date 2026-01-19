package main;

public class TestingState implements ApplicationState {

    /**
     * Metoda care duce in starea de dezvoltare
     * @param app aplicatia in sine
     */
    @Override
    public void next(final Application app) {
        app.setState(new DevelopmentState());
    }

    /**
     * Metoda aceasta ne lasa in continuare
     * la starea de testare
     * @param app aplicatia in sine
     */
    @Override
    public void prev(final Application app) {
        System.out.println();
    }

    /**
     * Metoda care returneaza statusul curent al aplicatiei
     * @return
     */
    @Override
    public String getStatus() {
        return "TESTING";
    }
}
