import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// // Private member variables to store row, seat, price, and person associated with the ticket
public class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;

    // Constructor to initialize a Ticket object with row, seat, price, and person.
    public Ticket(char row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getter method to retrieve the row of the seat.
    public char getRow() {
        return row;
    }

    // Getter method to retrieve the seat number.
    public int getSeat() {
        return seat;
    }

    // Getter method to retrieve the price of the ticket.
    public double getPrice() {
        return price;
    }

    // Getter method to retrieve the person associated with the ticket.
    public Person getPerson() {
        return person;
    }

    // Setter method to set the row of the seat.
    public void setRow(char row) {
        this.row = row;
    }

    // Setter method to set the seat number.
    public void setSeat(int seat) {
        this.seat = seat;
    }

    // Setter method to set the price of the ticket.
    public void setPrice(double price) {
        this.price = price;
    }

    // Setter method to set the person associated with the ticket.
    public void setPerson(Person person) {
        this.person = person;
    }

    // Method to save the ticket information to a text file.
    public void save() {
        String filename = "" + row + seat + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Writing ticket information to the file
            writer.println("Ticket Information:");
            writer.println("Seat: " + row + seat);
            writer.println("Price: £" + w2051669_PlaneManagement.calculatePrice(getSeat()));
            writer.println("Passenger: " + person.getName() + " " + person.getSurname());
            writer.println("Email: " + person.getEmail());
        } catch (IOException e) {
            // Handling any IOException occurred during file writing
            System.out.println("Error occurred while saving the ticket: " + e.getMessage());
        }
    }
}
