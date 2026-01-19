package main;

public interface ApplicationState {
    /**
     * Metoda care duce in starea urmatoare
     * @param app aplicatia in sine
     */
    void next(Application app);
    /**
     * Metoda care duce in starea anterioara
     * @param app aplicatia in sine
     */
    void prev(Application app);

    /**
     * Metoda care returneaza statusul curent al aplicatiei
     * @return
     */
    String getStatus();
}
