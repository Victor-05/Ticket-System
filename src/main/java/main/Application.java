package main;

import command.CommandInput;
import constants.Constants;
import lombok.Data;
import milestone.Milestone;
import milestone.MilestoneStorage;
import milestone.Repartition;
import ticket.ReportTicket;
import ticket.TicketStorage;
import users.User;
import users.UsersDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Clasa principala a aplicatiei, aici se stocheaza
 * toate valorile importante ale acesteia: starea curenta,
 * formatterul datelor, data finala pentru testarea, data curenta.
 * Am utilizat design patternul State
 */

@Data
public class Application {
    private ApplicationState state = new TestingState();;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDate endTestingDate = null;
    private LocalDate currentDate = null;

    /**
     * Metoda care duce in starea anterioara
     */
    public void previousState() {
        state.prev(this);
    }

    /**
     * Metoda care duce in starea urmatoareea
     */
    public void nextState() {
        state.next(this);
    }

    /**
     * Metoda care afiseaza statusul curent al aplicatiei
     */

    public void printStatus() {
        System.out.println(state.getStatus());
    }


    /**
     * Getter pentru formatterul datelor
     * @return
     */
    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    /**
     * Metoda care actualizeaza toate valorile din aplicatie
     * Ea este apelata dupa ce se citeste o comanda
     * @param input datele de intrare
     * @param app aplicatia in sine
     * @param milestoneStorage milestoneurile stocate
     * @param ticketStorage ticketele stocate
     */
    public static final void update(final CommandInput input,
                                    final Application app,
                                    final MilestoneStorage milestoneStorage,
                                    final TicketStorage ticketStorage) {
        if (app.endTestingDate == null) {
            LocalDate startTestingDate = LocalDate.parse(input
                    .getTimestamp(), app.getDateTimeFormatter());
            app.endTestingDate = startTestingDate;
            app.endTestingDate = startTestingDate.plusDays(Constants
                    .DAYS_TO_CLOSE_TESTING);
        }

        app.currentDate = LocalDate.parse(input.getTimestamp(),
                app.getDateTimeFormatter());
        if (app.currentDate.isAfter(app.endTestingDate)) {
            app.nextState();
        }
        for (Milestone x : milestoneStorage.getMilestones()) {
            if (app.currentDate.isAfter(x.getPriorityChange())
                    && !x.getIsBlocked()) {
                x.changeTicketPriority(app, ticketStorage);
            }
            LocalDate due = LocalDate.parse(x.getDueDate(), app.dateTimeFormatter);
            x.setDaysUntilDue((int) ChronoUnit.DAYS.between(app.currentDate, due));
            if (x.getDaysUntilDue() <= 1) {
                for (String dev : x.getAssignedDevs()) {
                    User user = UsersDatabase.getUser(dev);
                    user.getNotifications().add("Milestone " + x.getName()
                            + " is due tomorrow. All unresolved"
                            + " tickets are now CRITICAL.");
                }
            }
            if (x.getDaysUntilDue() < 0) {
                x.setDaysUntilDue(x.getDaysUntilDue() - 1);
                x.setOverdueBy((-1) * x.getDaysUntilDue());
                x.setDaysUntilDue(0);
            } else {
                x.setDaysUntilDue(x.getDaysUntilDue() + 1);
            }
            if (!x.getTickets().isEmpty()) {
                x.setCompletionPercentage(Math.round((double) x
                        .getClosedTickets().size() / x.getTickets().size()
                        * Constants.PERCENATGE) / Constants.PERCENATGE);
            }
            for (Repartition repartition : x.getRepartition()) {
                repartition.setAssignedTickets(UsersDatabase
                        .getUser(repartition.getDeveloper())
                        .getTickets().stream().map(ReportTicket::getId)
                        .filter(x.getTickets()::contains)
                        .collect(Collectors.toCollection(ArrayList::new)));
            }
            if (x.getCompletionPercentage() == 1.0) {
                x.setStatus("COMPLETED");
            }
        }


    }
}
