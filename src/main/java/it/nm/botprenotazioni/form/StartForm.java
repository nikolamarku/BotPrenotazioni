package it.nm.botprenotazioni.form;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class StartForm implements Form{
    private final String raggruppamento_aree="all";
    private final int area;
    private final int raggruppamento_servizi=0;
    private final int servizio;
    private final LocalDate data_inizio;
    private final String codice_fiscale;
    private final String email;
    private final String nominativo;

    public StartForm(String nominativo, int area, int servizio, LocalDate data_inizio, String codice_fiscale, String email) {
        this.nominativo = nominativo;
        this.area = area;
        this.servizio = servizio;
        this.data_inizio = data_inizio;
        this.codice_fiscale = codice_fiscale;
        this.email = email;
    }

    @Override
    public Map<String, String> paramsAsMap() {
        Map<String, String> params = new HashMap<>();
        params.put("raggruppamento_aree",raggruppamento_aree);
        params.put("area",""+area);
        params.put("raggruppamento_servizi",""+raggruppamento_servizi);
        params.put("servizio",""+servizio);
        params.put("data_inizio",data_inizio.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        params.put("codice_fiscale",codice_fiscale);
        params.put("cognome_nome",nominativo);
        params.put("email",email);
        params.put("1605188711","");
        return params;
    }

    public int getService() {
        return servizio;
    }

    public int getArea() {
        return area;
    }

    public LocalDate getStartDate(){
        return data_inizio;
    }

    public String getNominativo() {
        return nominativo;
    }

    public String getEmail() {
        return email;
    }

    public String getCF() {
        return codice_fiscale;
    }
}
