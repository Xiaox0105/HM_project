// Nancy  - Final Project: Hotel Management System

import java.text.ParseException;

public class Manager {


    int start;
    int end;
    

    public void totalIncome(Room DR, Room KR, Room KS, Room PS) throws ParseException {
        
        System.out.println("Please enter the date range ");
        start = App.getStartdate();
        end = App.getEnddate();

        int DRbooking = 0; // initialize values each time we go through the loop
        int KRbooking = 0;
        int KSbooking = 0;
        int PSbooking = 0;

        for (int x = start; x < end + 1; x++) {
                DRbooking += Booking.DR[x][1];  //count how many days that the checkin status is occupied for each of the room type within the date range
                KRbooking += Booking.KR[x][1];
                KSbooking += Booking.KS[x][1];
                PSbooking += Booking.PS[x][1];
        }

        double totalIncome = DRbooking * DR.getPrice() + KRbooking * KR.getPrice() + KSbooking * KS.getPrice() + PSbooking * PS.getPrice(); // checkin days times room price.
        System.out.println("The total income is $" + totalIncome);
    }  


}
