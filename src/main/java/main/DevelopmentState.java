package main;

public class DevelopmentState  implements ApplicationState {
    @Override
    public void next(Application app) {
        app.setState(new VerificationState());
    }

    @Override
    public void prev(Application app) {
        app.setState(new TestingState());
    }

    @Override
    public String getStatus() {
        return "DEVELOPMENT";
    }
}
