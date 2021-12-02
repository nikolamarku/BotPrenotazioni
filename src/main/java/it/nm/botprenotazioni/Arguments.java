package it.nm.botprenotazioni;

import java.util.Map;
import java.util.Optional;

public class Arguments {

    private String name;
    private String cf;
    private String email;
    //array di 7 elementi in cui ciascun elemento indica l'orario per quel giorno.
    // Se elemento = -1 non prenota per quel giorno
    private Day[] days;
    private int area;
    private int servizio;

    public Arguments(String name, String cf, String email, Day[] days, int area, int servizio) {
        this.name = name;
        this.cf = cf;
        this.email = email;
        this.days = days;
        this.area = area;
        this.servizio = servizio;
    }

    public int getArea() {
        return area;
    }

    public int getServizio() {
        return servizio;
    }

    public String getName() {
        return name;
    }

    public String getCf() {
        return cf;
    }

    public String getEmail() {
        return email;
    }

    public Optional<Day> getChosenTimeForDayOfWeek(int dayOfWeek) {
        return days[dayOfWeek] == null ? Optional.empty() : Optional.of(days[dayOfWeek]);
    }


    public class Day{
        private int hour;
        private int duration;

        public Day(int hour, int duration) {
            this.hour = hour;
            this.duration = duration;
        }

        public int getHour() {
            return hour;
        }

        public int getDuration() {
            return duration;
        }
    }
}
