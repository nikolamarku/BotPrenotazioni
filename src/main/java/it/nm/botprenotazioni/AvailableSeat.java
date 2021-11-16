package it.nm.botprenotazioni;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AvailableSeat {

    private final long startTime;
    private final long endTime;
    private final int resource;

    public AvailableSeat(long startTime, long endTime, int resource) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.resource = resource;
    }

    public LocalDateTime getDate(){
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(startTime), ZoneId.of("GMT+1")); //Arrivano in "GMT"
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public int getResource() {
        return resource;
    }

    @Override
    public String toString() {
        return ""+LocalDateTime.ofInstant(Instant.ofEpochSecond(startTime), ZoneId.of("GMT+1")).getHour();
    }
}

