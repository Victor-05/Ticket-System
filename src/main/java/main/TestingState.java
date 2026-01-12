package main;

public class TestingState implements ApplicationState {

    @Override
    public void next(Application app) {
        app.setState(new DevelopmentState());
    }

    @Override
    public void prev(Application app) {
        System.out.println();
    }

    @Override
    public String getStatus() {
        return "TESTING";
    }
}
