package command;

import lombok.Data;
import ticket.Filter;
import ticket.Ticket;

import java.util.ArrayList;

@Data
public class CommandInput {
    private String command;
    private String username;
    private String timestamp;
    private Ticket params;
    private String name;
    private String dueDate;
    private ArrayList<String> blockingFor;
    private ArrayList<Integer> tickets;
    private ArrayList<String> assignedDevs;
    private int ticketID;
    private ArrayList<String> expertiseArea;
    private String comment;
    private Filter filters;

}
