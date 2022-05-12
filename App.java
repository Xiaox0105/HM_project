/* Final Project: Hotel Management System
*  This program illustrate a simple hotel management system, the user can choose to enter guess mode or manager mode. 
*  In the guess mode, the user can make room reservations
*  In the manager mode can view the expected profit of the hotel for a specified range of dates and cancel bookings.
*/

import java.util.Arrays;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class App {
    static Scanner input = new Scanner(System.in); // Construct a new Scanner for user input.
    static Booking bookings = new Booking(); // Construct new Booking object
    static Manager manager = new Manager(); // Construct new Manager object

    static SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy"); // Construct a SimpleDateFormat for parsing
                                                                           // String to Date

    static Room DR = new Room(150, Room.roomType.DR); // Construct new Room objects, assign price and room type.
    static Room KR = new Room(180, Room.roomType.KR);
    static Room KS = new Room(240, Room.roomType.KS);
    static Room PS = new Room(400, Room.roomType.PS);

    static Date first;

    public static void main(String[] args) throws ParseException {

        bookings.getStarted(); // initiate the program.

        first = myFormat.parse("01/01/2021"); // set the first day of the year to 01/01/2021, which corresponds to the
                                              // 0th index of the arrays.

        System.out.println("Welcome to Nancy's Hotel. （Bookings open for 2021）");

        while (true) {
            System.out.println("\nGuest or Manager?"); // user interaction window
            String inp = input.next();
            if (inp.equalsIgnoreCase("Guest")) { // Enter the Guest Mode, make new bookings or cancel existing bookings.

                Add(); // Call the Add method to add new bookings.
                continue;

            } else if (inp.equalsIgnoreCase("Manager")) { // Enter the Manager Mode, check the total income within a
                                                          // date range.
                System.out
                        .println("(1) View expected income for a range of dates \n(2) Cancel an existing reservation");
                int inp2 = input.nextInt();

                if (inp2 == 1) {
                    manager.totalIncome(DR, KR, KS, PS); // Call the totalIncome method in the Manager class to
                                                         // calculate the total hotel income in the date range

                } else if (inp2 == 2) {
                    bookings.Cancel(); // Call the Cancel method in the Booking class to cancel existing orders

                } else {
                    System.out.println("Invalid input.");
                }

            } else {
                System.out.println("Invalid input.");
                continue;
            }
        }
    }

    public static int getStartdate() throws ParseException {
        while (true) {
            System.out.println("Starting date: (dd/MM/yyyy)");
            String Lowerbound = input.next(); // User input a date range
            if (Lowerbound.charAt(2) != '/' || Lowerbound.charAt(5) != '/') {
                System.out.println("Invalid date. Please re-enter the date with '/' in between");
                continue;
            } else {
                Date LB = myFormat.parse(Lowerbound); // Parse String input to Date.
                int start = (int) daysBetween(LB, first); // Call the daysBetween method to calculate the days between
                                                          // 01/01/2021 and the starting(checkin) date
                return start;
            }
        }
    }

    public static int getEnddate() throws ParseException {
        while (true) {
            System.out.println("Ending date: (dd/MM/yyyy)");
            String Upperbound = input.next(); // User input a date range
            if (Upperbound.charAt(2) != '/' || Upperbound.charAt(5) != '/') {
                System.out.println("Invalid date. Please re-enter the date with '/' in between");
                continue;
            } else {
                Date UB = myFormat.parse(Upperbound);
                int end = (int) daysBetween(UB, first); // Call the daysBetween method to calculate the days between the
                                                        // 01/01/2021 and the ending(checkout) date
                return end;
            }
        }
    }

    public static long daysBetween(Date one, Date two) { // calculate days between two dates.
        long difference = ((one.getTime() - two.getTime()) / 86400000);// milliseconds
        return Math.abs(difference); // take the absolute value to make sure the method returns a positive number.
    }

    public static void Add() throws ParseException {
        System.out.println("When would you like to check in?");
        int startDay = getStartdate(); // get the array index of the starting date
        System.out.println("When would you like to check out?");
        int endDay = getEnddate(); // get the array index of the ending date

        if (startDay < endDay) { // check if the date range is set properly
            String[] available = bookings.checkAvailability(startDay, endDay); // Call checkAvailability method in the Booking class to check
                                                                               // which room type is available in the date range.
            while(true){
                String roomSelected = input.next(); // Let user select room type.
                if (Arrays.asList(available).contains(roomSelected)){
                    bookings.confirmation(startDay, endDay, roomSelected); // confirm booking and return total price.
                    return;   
                }else{
                    System.out.println("Roomtype unavailable, please enter again");
                    continue;
                }
            }                                              
                 
        } else {
            System.out.println("Invalid date range. (Note: You have to spend at least one night at the hotel.)");
        }
    }
}

