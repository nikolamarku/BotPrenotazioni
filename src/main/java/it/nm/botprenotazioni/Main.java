package it.nm.botprenotazioni;

import com.google.gson.Gson;
import it.nm.botprenotazioni.form.StartForm;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        Arguments arguments = new Gson().fromJson(new FileReader(args[0]),Arguments.class);
        LocalDate today = LocalDate.now(ZoneId.of("GMT+1"));
        Arguments.Day currentBookingDay = arguments.getChosenTimeForDayOfWeek(today.getDayOfWeek().getValue()-1)
                .orElse(null);

        if(currentBookingDay == null)
            logger.info("No booking for today");
        else{

            StartForm  startForm = new StartForm(arguments.getName(),
                    arguments.getArea(),
                    arguments.getServizio(),
                    today,
                    arguments.getCf(),
                    arguments.getEmail(),
                    currentBookingDay.getDuration()
            );
            BookingRequest bookingRequest = new BookingManager().newBookingRequest(startForm);
            book(bookingRequest,currentBookingDay.getHour());
        }
    }

    private static void book(BookingRequest bookingRequest, int chosenTime) throws Exception {
        logger.info("Trying to make a reservation at "+chosenTime+":00...");
        AvailableSeat foundSeat = bookingRequest.getSeatByTime(chosenTime);
        if(foundSeat != null)
            logger.info(
                    bookingRequest.book(foundSeat) ?
                            "Booking confirmed at "+ chosenTime +":00" :
                            "Error while trying to make the reservation"
            );
        else
            logger.info("No available seat at "+chosenTime+":00");
    }

}
