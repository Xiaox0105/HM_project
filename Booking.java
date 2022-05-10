import java.text.ParseException;
import java.util.Scanner;
public class Booking {

    Room room;

    int checkDRbooking;
    int checkKRbooking;
    int checkKSbooking;
    int checkPSbooking;

    int startCancel;
    int endCancel;

    static Scanner inputManager = new Scanner(System.in);

    public static int[][] DR = new int[365][2];
    public static int[][] KR = new int[365][2];
    public static int[][] KS = new int[365][2];
    public static int[][] PS = new int[365][2];
    /* Create one array for each type of room.
    * each array consist of 365 rows and 2 columns 
    *   each row represent a day in the year; Its index number tells it is n days after 01/01/2021
    *   the first column is the checkout status of the day, the second column is the checkin status). 
    *   In the array, 0 = available， 1 = occupied.
    */ 

     public void getStarted(){ // All the 4 types of room are initially set to allow check-in and check-out at any day during the year.
        for (int i = 0; i < 365; i++) {
            for (int j = 0; j < 2; j++) {
                DR[i][j] = 0;
                KR[i][j] = 0;
                KS[i][j] = 0;
                PS[i][j] = 0;
            }
        }
    }   

    public void checkAvailability(int startDay, int endDay) { // The integer startDay and endDay correspond to the array index of starting date and it of the ending date, therefore we could locate the date range in the arrays.

        System.out.println("Please choose your room:"); // choose room type.
        System.out.println("The room types currently availabe are:"); // The result of the following loops tells which room types are available within the date range.

        int checkDR = 0;
        int checkKR = 0;
        int checkKS = 0;
        int checkPS = 0;

        if (DR[startDay][1] == 0 && DR[endDay][0] <= 0) { // First, check if the starting date allows check in, and the ending date allows check out.
            for (int a = startDay + 1; a < endDay - 1; a++) {
                for (int b = 0; b < 2; b++) {
                    checkDR += DR[a][b];//  check if the days within the date range is available 
                }
            }
            if (checkDR == 0) { // if confirmed there is no checkin or checkout activity within the date range, the room type is available.
                System.out.println("(DR) Double Room");
            }
        }

        if (KR[startDay][1] == 0 && KR[endDay][0] <= 0) { // check the availbility of all the four types of room.
            for (int c = startDay + 1; c < endDay - 1; c++) {
                for (int d = 0; d < 2; d++) {
                    checkKR += KR[c][d];
                }
            }
            if (checkKR == 0) { 
                System.out.println("(KR) King Room");
            }
        }

        if (KS[startDay][1] == 0 && KS[endDay][0] <= 0) {
            for (int e = startDay + 1; e < endDay - 1; e++) {
                for (int f = 0; f < 2; f++) {
                    checkKS += KS[e][f];
                }
            }
            if (checkKS == 0) {
                System.out.println("(KS) King Suite");
            }
        }

        if (PS[startDay][1] == 0 && PS[endDay][0] <= 0) {
            for (int g = startDay + 1; g < endDay - 1; g++) {
                for (int h = 0; h < 2; h++) {
                    checkPS += PS[g][h];
                }
            }
            if (checkPS == 0) {
                System.out.println("(PS) Presidential Suit");
            }
        }
    }

    public void confirmation(int startDay, int endDay, String roomSelected) { 
        // confirmation will change the check-in and check-out status of the room type selected by the user to Occupied during the booked date range, so new bookings will not able to select this room type within the dates.
       
        Room.roomType confirm = Room.roomType.valueOf(roomSelected);

        if (Room.roomType.DR == confirm){{
            for (int x = startDay; x < endDay; x++) {
                DR[x][1] = 1; 
            }
            for (int y = startDay + 1; y < endDay + 1; y++) {
                DR[y][0] = 1;
            }
            getTotal(startDay, endDay, App.DR);
        }

        }else if (Room.roomType.KR == confirm){
            for (int x = startDay; x < endDay; x++) {
                KR[x][1] = 1; 
            }
            for (int y = startDay + 1; y < endDay + 1; y++) {
                KR[y][0] = 1;
            }
            getTotal(startDay, endDay, App.KR);

        }else if (Room.roomType.KS == confirm){
            for (int x = startDay; x < endDay; x++) {
                KS[x][1] = 1; 
            }
            for (int y = startDay + 1; y < endDay +1  ; y++) {
                KS[y][0] = 1;
            }
            getTotal(startDay, endDay, App.KS);

        }else {
            for (int x = startDay; x < endDay; x++) {
                PS[x][1] = 1; 
            }
            for (int y = startDay + 1; y < endDay + 1; y++) {
                PS[y][0] = 1;
            }
            getTotal(startDay, endDay, App.PS);
        }

        System.out.println("Booking confirmed.");
    }
 

    public void getTotal(int startDay, int endDay, Room room) { //The total price is calculated by multiplying the number of days the guest will stay in the hotel by the daily price of the room type
        int Nights = endDay - startDay; // calculate days between checkin date and ckeckout date
        System.out.println("The total cost is $" + room.getPrice() * Nights);  // call the get price method to get the price of the specific room type.  
    }

    public void Cancel() throws ParseException{
        System.out.println("Please enter the date range of the booking you would like To cancel");
        startCancel = App.getStartdate(); // call the get Startdate method and getEnddate method to take user input and then convert them to integers.
        endCancel = App.getEnddate();
        
        System.out.println("Please enter the room type: (DR); (KR); (KS); (PS).");
        String roomCancel = inputManager.next(); // manager inputs the room type of the to-be-canceled booking

        // check if the input date and roomtype match the existing booking
        if (roomCancel.equalsIgnoreCase("DR")) { 
            for (int s = startCancel; s < endCancel + 1; s++) {
                checkDRbooking += DR[s][1]; // count the number of days the checkin status is occupied within the input date range
            }
            if (checkDRbooking == endCancel - startCancel){ // if the number of days that the checkin status is occupied matches the total days within the input date range， and the room type matches, the booking exists. 
                for (int t = startCancel; t < endCancel; t++) { // then change the Checkin and Checkout status previously occupied by the booking to available.
                    DR[t][1] = 0; 
                }
                for (int z = startCancel + 1; z < endCancel+ 1; z++) {
                    DR[z][0] = 0;
                }
                System.out.println("Booking successfully canceled.");
            }

        } else if (roomCancel.equalsIgnoreCase("KR")) {  // same thing for the other three roomtypes.
            for (int s = startCancel; s < endCancel + 1; s++) {
                checkKRbooking += KR[s][1]; 
            }
            if (checkKRbooking == endCancel - startCancel){
                for (int t = startCancel; t < endCancel; t++) {
                    KR[t][1] = 0; 
                }
                for (int z = startCancel + 1; z < endCancel + 1; z++) {
                    KR[z][0] = 0;
                }
                System.out.println("Booking successfully canceled.");
            }
           
        } else if (roomCancel.equalsIgnoreCase("KS")) {
            for (int s = startCancel; s < endCancel + 1; s++) {
                checkKSbooking += KS[s][1]; 
            }
            if (checkKSbooking == endCancel - startCancel){
                for (int t = startCancel; t < endCancel; t++) {
                    KS[t][1] = 0; 
                }
                for (int z = startCancel + 1; z < endCancel + 1; z++) {
                    KS[z][0] = 0;
                }
                System.out.println("Booking successfully canceled.");
            }
            
        } else if (roomCancel.equalsIgnoreCase("PS")) {
            for (int s = startCancel; s < endCancel+ 1; s++) {
                checkPSbooking += PS[s][1]; 
            }
            if (checkPSbooking == endCancel - startCancel){
                for (int t = startCancel; t < endCancel; t++) {
                    PS[t][1] = 0; 
                }
                for (int z = startCancel + 1; z < endCancel + 1; z++) {
                    PS[z][0] = 0;
                }
                System.out.println("Booking successfully canceled.");
            }

        } else {
            System.out.println("Input doesn't match the record."); // if the input doesn't match the booking record. 
        }
    }
}
