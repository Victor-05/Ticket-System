package actions;

import lombok.Data;

@Data
public abstract class Action {
    private String action;
    private String by;
    private String timestamp;
}
