package main;

import command.CommandInput;
import lombok.Data;
import milestone.Milestone;
import milestone.MilestoneStorage;
import ticket.TicketStorage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

@Data
public class Application {
    private ApplicationState state = new TestingState();;
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static LocalDate endTestingDate = null;
    public static LocalDate currentDate = null;

    public void previousState() {
        state.prev(this);
    }

    public void nextState() {
        state.next(this);
    }

    public void printStatus() {
        //state.printStatus();
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public static void update(final CommandInput input, final Application app, MilestoneStorage milestoneStorage, TicketStorage ticketStorage) {
        if (Application.endTestingDate == null) {
            LocalDate startTestingDate = LocalDate.parse(input.getTimestamp(), app.getDateTimeFormatter());
            Application.endTestingDate = startTestingDate;
            Application.endTestingDate = startTestingDate.plusDays(12);
        }

        Application.currentDate = LocalDate.parse(input.getTimestamp(), app.getDateTimeFormatter());
        if (Application.currentDate.isAfter(Application.endTestingDate)) {
            app.nextState();
        }
        for (Milestone x : milestoneStorage.getMilestones()) {
            if (Application.currentDate.isAfter(x.getPriorityChange()) && x.getIsBlocked() == false) {
                x.changeTicketPriority(app, ticketStorage);
            }
            LocalDate due = LocalDate.parse(x.getDueDate(), Application.dateTimeFormatter);
            x.setDaysUntilDue((int) ChronoUnit.DAYS.between(Application.currentDate, due) + 1);
            if (x.getDaysUntilDue() < 0) {
                x.setOverdueBy((-1) * x.getDaysUntilDue());
            }
        }


    }
}
