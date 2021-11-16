package it.nm.botprenotazioni;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public static String formatDate(LocalDate date, String pattern){
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
    public static String formatDate(LocalDateTime date, String pattern){
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
}
