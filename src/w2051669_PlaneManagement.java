import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class w2051669_PlaneManagement {
    public static void main(String[] args) {
        System.out.println("   Welcome to the Plane Management application");

        // Array to hold ticket objects
        Ticket[][] tickets = new Ticket[4][];
        tickets[0] = new Ticket[14];
        tickets[1] = new Ticket[12];
        tickets[2] = new Ticket[12];
        tickets[3] = new Ticket[14];

        // Array to represent seat occupancy
        int[][] Seats = new int[4][];
        Seats[0] = new int[14]; // Row A
        Seats[1] = new int[12]; // Row B
        Seats[2] = new int[12]; // Row C
        Seats[3] = new int[14]; // Row D

        Scanner sc = new Scanner(System.in);
        int option = 0;

        // Main menu loop
        do {
            System.out.println("*".repeat(50));
            System.out.println("*                  MENU OPTIONS                  *");
            System.out.println("*".repeat(50));
            System.out.println("      1) Buy a Seat");
            System.out.println("      2) Cancel a Seat");
            System.out.println("      3) Find first available Seat");
            System.out.println("      4) Show seating plan");
            System.out.println("      5) Print tickets information and total sales");
            System.out.println("      6) Search ticket");
            System.out.println("      0) Quit");
            System.out.println("*".repeat(50));

            // Input validation loop for menu option
            while (true) {
                System.out.print("Please select an option: ");
                if (sc.hasNextInt()) {
                    option = sc.nextInt();
                    if (option >= 0 && option <= 6) {
                        break; // Only valid numbers 1 to 6
                    } else {
                        System.out.println("Invalid input!");
                    }
                } else {
                    System.out.println("Invalid input!");
                    sc.next();
                }
            }

            // Calling each method based on selected option
            switch (option) {
                case 1:
                    buy_seat(Seats, sc, tickets);
                    break;

                case 2:
                    cancel_seats(Seats, sc, tickets);
                    break;

                case 3:
                    first_available_seat(Seats);
                    break;

                case 4:
                    show_seating_plan(Seats);
                    break;

                case 5:
                    print_ticket_info(tickets);
                    break;

                case 6:
                    search_ticket(sc, tickets, Seats);
                    break;
            }
        } while (option != 0);

        // Exiting message when user input 0
        System.out.println("Application exiting...");
        System.out.println("Have a good day!");
        sc.close();
    }

    // Method to get row from the user
    public static char getRow() {
        Scanner getRow = new Scanner(System.in);
        char rowNumber;
        while (true) {
            // A try-catch block was used only to insert A, B, C and D
            try {
                System.out.print("Enter a Row (A-D): ");
                rowNumber = getRow.next().toUpperCase().charAt(0);
                if (rowNumber >= 'A' && rowNumber <= 'D') {
                    break;
                } else {
                    System.out.println("Invalid input Please enter a character between A and D.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input enter a valid character.");
                getRow.next();
            }
        }
        return rowNumber;
    }
    // Method to get seat number from user input
    public static int getNumber() {
        Scanner getNumber = new Scanner(System.in);
        int seatNumber;
        while (true) {
            try {
                System.out.print("Enter a seat number: ");
                seatNumber = getNumber.nextInt();
                if (seatNumber < 1 || seatNumber > 14) {
                    System.out.println("Invalid input Seat number must be between 1 and 14.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input enter a valid integer (1-14).");
                // Discard the invalid input from the scanner to prevent an infinite loop
                getNumber.next();
            }
        }
        // Return the valid seat number entered by the user
        return seatNumber;
    }

    // Method to buy a seat
    public static void buy_seat(int[][] Seats, Scanner sc, Ticket[][] tickets) {
        System.out.println("---Buy a Seat---");
        char row = getRow();
        int seatNumber = getNumber();
        // Calculate the index of the row based on its alphabetical value ('A' is 0, 'B' is 1, 'C' is 2 and 'D' is 3.)
        int rowIndex = row - 'A';
        // Check if the row index and seat number are within valid ranges
        if (rowIndex >= 0 && rowIndex < Seats.length && seatNumber > 0 && seatNumber <= Seats[rowIndex].length) {
            // Check if the seat is available
            if (Seats[rowIndex][seatNumber - 1] == 0) {
                // Mark the seat as taken
                Seats[rowIndex][seatNumber - 1] = 1;

                String name = "";
                String surname = "";
                String email = "";

                while (true) {
                    System.out.print("Enter your name: ");
                    name = sc.next();
                    if (name.matches("[a-zA-Z]+")) {
                        break; // Exit the loop if input is valid
                    } else {
                        System.out.println("Invalid name. Please enter alphabetic characters only.");
                    }
                }

                while (true) {
                    System.out.print("Enter your surname: ");
                    surname = sc.next();
                    if (surname.matches("[a-zA-Z]+")) {
                        break; // Exit the loop if input is valid
                    } else {
                        System.out.println("Invalid surname. Please enter alphabetic characters only.");
                    }
                }

                while (true) {
                    System.out.print("Enter your e-mail: ");
                    email = sc.next();
                    if (Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)) {
                        break; // Exit the loop if input is valid
                    } else {
                        System.out.println("Invalid email. Please enter a valid email address.");
                    }
                }

                // Create a Person object with the provided information
                Person person = new Person(name, surname, email);
                // Create a Ticket object representing the purchased seat
                Ticket ticket = new Ticket(row, seatNumber, calculatePrice(row), person);
                // Store the Ticket object in the tickets array
                tickets[rowIndex][seatNumber - 1] = ticket;
                // Save the ticket information
                ticket.save();


                System.out.println("Your seat " + row + seatNumber + " was successfully bought.");
            } else {
                System.out.println("Seat is already taken. Please choose another.");
            }
        } else {
            System.out.println("Invalid row or seat number.");
        }
    }

    // Method to cancel a seat
    public static void cancel_seats(int[][] Seats, Scanner sc, Ticket[][] tickets) {
        System.out.println("---Cancel a Seat---");
        char row = getRow();
        int seatNumber = getNumber();
        // Calculate the index of the row based on its alphabetical value ('A' is 0, 'B' is 1, 'C' is 2 and 'D' is 3)
        int rowIndex = row - 'A';
        // Initialize a boolean variable to track if a booking is found for the selected seat
        boolean found = false;

        // Check if the row index and seat number are within valid ranges
        if (rowIndex >= 0 && rowIndex < Seats.length && seatNumber > 0 && seatNumber <= Seats[rowIndex].length) {
            // Check if the seat is booked
            if (Seats[rowIndex][seatNumber - 1] == 1) {
                // Mark the seat as available
                Seats[rowIndex][seatNumber - 1] = 0;
                // Remove the ticket information for the canceled seat
                tickets[rowIndex][seatNumber - 1] = null;
                System.out.println("Your seat " + row + seatNumber + " was successfully cancelled.");
            } else {
                System.out.println("No booking found for this seat.");
            }
        } else {
            System.out.println("Invalid row or seat number.");
        }
    }

    // Method to find the first available seat
    public static void first_available_seat(int[][] Seats) {
        // Iterate over each row of the seating arrangement
        for (int i = 0; i < Seats.length; i++) {
            // Iterate over each seat in the current row
            for (int j = 0; j < Seats[i].length; j++) {
                // Check if the current seat is available (marked as 0)
                if (Seats[i][j] == 0) {
                    // Calculate the row and seat number based on the indices
                    char row = (char) ('A' + i);
                    int seatNumber = j + 1;
                    System.out.println("First available seat: " + row + seatNumber);
                    return;
                }
            }
        }
        System.out.println("Sorry, no available seats found.");
    }
// Method to display seating plan
    public static void show_seating_plan(int[][] Seats) {
        System.out.println("  -----Seating Plan-----");
        // Iterate over each row of the seating arrangement
        for (int i = 0; i < Seats.length; i++) {
            // If the current row index is 2, print a newline to separate sections of the seating plan
            if (i==2) {
                System.out.println();
            }
            // Iterate over each seat in the current row
            for (int j = 0; j < Seats[i].length; j++) {
                // Check the availability of the current seat
                if (Seats[i][j] == 0) {
                    // Print 'O' if the seat is available
                    System.out.print("O ");
                } else {
                    // Print 'X' if the seat is unavailable (already booked)
                    System.out.print("X ");
                }
            }
            // Move to the next line after printing all seats in the current row
            System.out.println();
        }
    }

    // Method to calculate ticket price based on seat number
    public static double calculatePrice(int seatNumber) {
        // If the seat number is between 1 and 5, return a price of £200
        if (seatNumber >= 1 && seatNumber <= 5) {
            return 200.0;
            // If the seat number is between 6 and 9, return a price of £150
        } else if (seatNumber >= 6 && seatNumber <= 9) {
            return 150.0;
            // If the seat number is between 10 and 14, return a price of £180
        } else if (seatNumber >= 10 && seatNumber <= 14) {
            return 180.0;
        }
        // If the seat number does not fall within any of the specified ranges return a price of £0.0
        return 0.0;
    }
    // Method to print ticket information and total sales
    public static void print_ticket_info(Ticket[][] tickets) {
        double totalSales = 0.0;

        System.out.println("Ticket Information:");
        // Iterate over the 2D array of tickets
        for (int i = 0; i < tickets.length; i++) {
            for (int j = 0; j < tickets[i].length; j++) {
                // Retrieve the ticket object at the current position
                Ticket ticket = tickets[i][j];
                // Check if the seat is booked or not
                if (ticket != null) {
                    // Print ticket details: ticket ID (combination of row and seat number), price, passenger name, and email
                    System.out.println("Ticket: " + ticket.getRow() + ticket.getSeat());
                    System.out.println("Price: £" + calculatePrice(ticket.getSeat()));
                    System.out.println("Passenger: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
                    System.out.println("Email: " + ticket.getPerson().getEmail());
                    System.out.println();
                    // Accumulate the price of the ticket to the total sales
                    totalSales += calculatePrice(ticket.getSeat());
                }
            }
        }
        System.out.println("Total Sales: £" + totalSales);
    }

    // Method to search for a ticket and display buyers information
    public static void search_ticket(Scanner sc, Ticket[][] tickets, int[][] Seats) {
        char row = getRow();
        int seatNumber = getNumber();
        // Calculate the index of the row based on its alphabetical value ('A' is 0, 'B' is 1, 'C' is 2 and 'D' is 3.)
        int rowIndex = row - 'A';

        // Check if the selected row and seat number are within valid ranges
        if (rowIndex >= 0 && rowIndex < Seats.length && seatNumber > 0 && seatNumber <= Seats[rowIndex].length) {
            // Retrieve the ticket object at the specified row and seat number
            Ticket ticket = tickets[rowIndex][seatNumber - 1];
            // If a ticket is found for the specified seat print ticket information
            if (ticket != null) {
                System.out.println("Ticket Information:");
                System.out.println("Seat: " + ticket.getRow() + ticket.getSeat());
                System.out.println("Price: £" + calculatePrice(ticket.getSeat()));
                System.out.println("Passenger: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
                System.out.println("Email: " + ticket.getPerson().getEmail());
            } else {
                System.out.println("This seat is available");
            }
        } else {
            System.out.println("Invalid row or seat number.");
        }
    }
}
