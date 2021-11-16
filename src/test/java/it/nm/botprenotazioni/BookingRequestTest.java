package it.nm.botprenotazioni;

import it.nm.botprenotazioni.form.StartForm;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

public class BookingRequestTest {




    @Test
    public void getAvailableSeatsRunAsExpected() throws Exception {
        StartForm startForm = new StartForm("Nikola Marku",
                22, //Mediateca
                23, //Sala centrale
                LocalDate.now(),
                "MRKNKL97M10Z100H",
                "nikola.marku@studenti.unimi.it"
        );
        BookingRequest bookingRequest = new BookingManager().newBookingRequest(startForm);
        System.out.println(bookingRequest.getAvailableSeats().size());
    }
}
