package it.nm.botprenotazioni;

import it.nm.botprenotazioni.form.StartForm;

import java.io.IOException;

public class BookingManager {


    private final WebClient webClient;

    public BookingManager() throws IOException {
        this.webClient = new WebClient();
    }

    public BookingRequest newBookingRequest(StartForm form) throws IOException {
        return new BookingRequest(webClient,form);
    }

}
