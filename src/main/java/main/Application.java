package main;

import command.CommandInput;
import lombok.Data;
import milestone.Milestone;
import milestone.MilestoneStorage;
import milestone.Repartition;
import ticket.ReportTicket;
import ticket.TicketStorage;
import users.UsersDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

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
            x.setDaysUntilDue((int) ChronoUnit.DAYS.between(Application.currentDate, due));
            if (x.getDaysUntilDue() < 0) {
                x.setDaysUntilDue(x.getDaysUntilDue() - 1);
                x.setOverdueBy((-1) * x.getDaysUntilDue());
                x.setDaysUntilDue(0);
            } else {
                x.setDaysUntilDue(x.getDaysUntilDue() + 1);
            }
            if (x.getTickets().size() != 0) {
                x.setCompletionPercentage(Math.round((double)x.getClosedTickets().size() / x.getTickets().size() * 100.0) / 100.0);
            }
            for (Repartition repartition : x.getRepartition()) {
                repartition.setAssignedTickets(UsersDatabase.getUser(repartition.getDeveloper()).getTickets().stream().map(ReportTicket::getId).filter(x.getTickets()::contains).collect(Collectors.toCollection(ArrayList::new)));
            }
            if (x.getCompletionPercentage() == 1.0) {
                x.setStatus("COMPLETED");
            }
        }


    }
}
