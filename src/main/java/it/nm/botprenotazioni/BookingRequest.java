package it.nm.botprenotazioni;

import com.google.gson.Gson;
import it.nm.botprenotazioni.form.StartForm;
import it.nm.botprenotazioni.form.TimetableForm;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BookingRequest {

    private final static String FORM_PAGE = "https://orari-be.divsi.unimi.it/PortaleEasyPlanning/biblio/index.php?include=form";
    private final static String TIMETABLE_PAGE = "https://orari-be.divsi.unimi.it/PortaleEasyPlanning/biblio/index.php?include=timetable";
    private final static String REVIEW_PAGE = "https://orari-be.divsi.unimi.it/PortaleEasyPlanning/biblio/index.php?include=review";
    private final static String CONFIRM_PAGE = "https://orari-be.divsi.unimi.it/PortaleEasyPlanning/biblio/index.php?include=confirmed";

    private final WebClient webClient;
    private final StartForm startForm;

    public BookingRequest(WebClient webClient, StartForm form) throws IOException {
        this.startForm = form;
        this.webClient = webClient;
    }


    /**
     * Fa schifo ma il json è peggio di quelli di sirax
     */
    public List<AvailableSeat> getAvailableSeats() throws Exception {
        String dateStr = startForm.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //occhio durata servizio potrebbe cambiare
        String url = "https://orari-be.divsi.unimi.it/PortaleEasyPlanning/biblio/ajax.php?lang=en&area="+ startForm.getArea()+"&data_inizio="+dateStr+"&servizio="+ startForm.getService()+"&tentativi=10&tipo=timetable_available&associazione_risorse_servizi=1&chiave_primaria="+ startForm.getCF()+"&durata_servizio=16200";
        String response = webClient.get(url);
        Map<String, Object> seats = new Gson().fromJson(response,Map.class);
        List<AvailableSeat> availableSeats = new ArrayList<>();
        for(Map.Entry<String, Object> months : seats.entrySet()){
            for(Map.Entry<String, Object> date : ( (Map<String,Object>) months.getValue()).entrySet())
                if(! (date.getValue() instanceof String)){
                    for(Map.Entry<String,Object> rangeSeat : ( (Map<String,Object>) date.getValue()).entrySet())
                        for(Map.Entry<String,Object> seat : ( (Map<String,Object>) rangeSeat.getValue()).entrySet()) {
                            Map<String, Object> seatMap = (Map<String, Object>) seat.getValue();
                            if(seatMap.get("type").equals("libera")) {
                                long startTime = ((Double)seatMap.get("start_time")).longValue();
                                long endTime = ((Double)seatMap.get("end_time")).longValue();
                                int resource = ((Double)seatMap.get("risorsa")).intValue();
                                availableSeats.add(new AvailableSeat(startTime,endTime,resource));
                            }
                        }

                }

        }
        return availableSeats;
    }

    public AvailableSeat getSeatByTime( int hour) throws Exception {
        return getAvailableSeats()
                .stream()
                .filter(seat -> seat.getDate().getHour() == hour)
                .findFirst()
                .orElse(null);
    }


    public boolean book(AvailableSeat availableSeat) throws Exception {
        String[] tokenAndEntry = getReviewPageTokenAndEntry(availableSeat);
        Map<String,String> params = new HashMap<>();
        params.put("_token",tokenAndEntry[0]);
        params.put("entry",tokenAndEntry[1]);
        params.put("public_primary", startForm.getCF());
        return webClient.post(CONFIRM_PAGE,params).contains("check.png");
    }


    private String[] getReviewPageTokenAndEntry(AvailableSeat availableSeat) throws Exception {
        TimetableForm timetableForm = new TimetableForm(startForm, availableSeat);
        Map<String,String> params = timetableForm.paramsAsMap();
        params.put("_token", getTimetablePageToken());
        Element body =  Jsoup
                .parse(webClient.post(REVIEW_PAGE,params))
                .body();

        Elements formElement = body.select("form[action='index.php?include=confirmed']");
        return new String[]{
                formElement.select("input[name=\"_token\"]").first().attr("value"),
                formElement.select("input[name=\"entry\"]").first().attr("value")
        };
    }


    private String getTimetablePageToken() throws Exception {
        Map<String,String> formMapParams = startForm.paramsAsMap();
        formMapParams.put("_token", getFormPageToken());
        return Jsoup
                .parse(webClient.post(TIMETABLE_PAGE,formMapParams))
                .getElementById("form_confirm")
                .select("input[name=\"_token\"]")
                .first()
                .attr("value");
    }


    private String getFormPageToken() throws IOException {
        return Jsoup
                .parse(webClient.get(FORM_PAGE))
                .body()
                .select("input[name=\"_token\"]")
                .first()
                .attr("value");
    }
}
