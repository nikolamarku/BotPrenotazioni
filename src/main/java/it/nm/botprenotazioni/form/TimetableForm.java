package it.nm.botprenotazioni.form;
import it.nm.botprenotazioni.AvailableSeat;
import it.nm.botprenotazioni.Util;
import java.util.HashMap;
import java.util.Map;

public class TimetableForm implements Form{
    private final StartForm startForm;
    private final AvailableSeat availableSeat;

    public TimetableForm(StartForm startForm, AvailableSeat availableSeat) {
        this.startForm = startForm;
        this.availableSeat = availableSeat;
    }

    @Override
    public Map<String, String> paramsAsMap() {
        Map<String,String> params = new HashMap<>();
        params.put("servizio",""+ startForm.getService());
        params.put("risorsa",""+availableSeat.getResource());
        params.put("area",""+ startForm.getArea());
        params.put("cognome_nome", startForm.getNominativo());
        params.put("email", startForm.getEmail());
        params.put("codice_fiscale", startForm.getCF());
        params.put("1605188711","");
        params.put("data_inizio", Util.formatDate(availableSeat.getDate(),"dd-MM-yyyy"));
        params.put("timestamp",""+availableSeat.getStartTime());
        params.put("end_time",""+availableSeat.getEndTime());
        params.put("durata_servizio",""+ (availableSeat.getEndTime() - availableSeat.getStartTime()));
        return params;
    }
}
