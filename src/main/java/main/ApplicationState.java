package main;

import javax.swing.plaf.nimbus.State;

public interface ApplicationState {
    void next(Application app);
    void prev(Application app);
    String getStatus();
}
