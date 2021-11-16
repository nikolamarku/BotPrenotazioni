package it.nm.botprenotazioni;

import com.google.gson.Gson;
import it.nm.botprenotazioni.form.StartForm;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        Arguments arguments = new Gson().fromJson(new FileReader(args[0]),Arguments.class);
        LocalDate today = LocalDate.now();
        StartForm  startForm = new StartForm(arguments.getName(),
                25, //BICF
                50, //Primo piano - last minute
                today,
                arguments.getCf(),
                arguments.getEmail()
        );
        BookingRequest bookingRequest = new BookingManager().newBookingRequest(startForm);
        Integer chosenTime = arguments.getChosenTimeForDayOfWeek(today.getDayOfWeek().getValue()-1)
                .orElse(null);
        if(chosenTime == null)
            System.out.println("No booking for today");
        else
            book(bookingRequest,chosenTime);
    }

    private static void book(BookingRequest bookingRequest, int chosenTime) throws Exception {
        System.out.println("Trying to make a reservation at "+chosenTime+":00...");
        AvailableSeat foundSeat = bookingRequest.getSeatByTime(chosenTime);
        if(foundSeat != null)
            System.out.println(
                    bookingRequest.book(foundSeat) ?
                            "Booking confirmed at "+ chosenTime +":00" :
                            "Error while trying to make the reservation"
            );
        else
            System.out.println("No available seat at "+chosenTime+":00");
    }

}
