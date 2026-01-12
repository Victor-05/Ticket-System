package main;

public class VerificationState implements ApplicationState {

    @Override
    public void next(Application app) {
        app.setState(new TestingState());
    }

    @Override
    public void prev(Application app) {
        app.setState(new DevelopmentState());
    }

    @Override
    public String getStatus() {
        return "VERIFICATION";
    }
}
