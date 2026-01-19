package command;

import main.Application;
import milestone.MilestoneStorage;
import ticket.TicketStorage;
import users.User;
import users.UsersDatabase;

import java.util.ArrayList;

public class ViewNotifications  extends Command {
    private ArrayList<String> notifications;
    ViewNotifications(final CommandInput input) {
        this.setCommand(input.getCommand());
        this.setUsername(input.getUsername());
        this.setTimestamp(input.getTimestamp());
    }

    @Override
    public final void execute(final Application app,
                        final TicketStorage ticketStorage,
                        final ArrayList<Command> commands,
                        final MilestoneStorage milestoneStorage) {
        User user = UsersDatabase.getUser(getUsername());
        if (user.getNotifications() != null) {
            notifications = new ArrayList<>(user.getNotifications());
        } else {
            notifications = new ArrayList<>();
        }
        commands.add(this);
    }
}
