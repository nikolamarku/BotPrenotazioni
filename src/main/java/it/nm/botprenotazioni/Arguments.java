package it.nm.botprenotazioni;

import java.util.Optional;

public class Arguments {

    private String name;
    private String cf;
    private String email;
    //array di 7 elementi in cui ciascun elemento indica l'orario per quel giorno.
    // Se elemento = -1 non prenota per quel giorno
    private Integer[] hours;
    private int area;
    private int servizio;

    public Arguments(String name, String cf, String email, Integer[] hours, int area, int servizio) {
        this.name = name;
        this.cf = cf;
        this.email = email;
        this.hours = hours;
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

    public Optional<Integer> getChosenTimeForDayOfWeek(int dayOfWeek) {
        return hours[dayOfWeek] == -1 ? Optional.empty() : Optional.of(hours[dayOfWeek]);
    }
}
