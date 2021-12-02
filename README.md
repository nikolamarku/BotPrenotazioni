Build: sulla root del progetto eseguire ./gradlew build\
\
L'eseguibile in build/libs/\
\
Run: java -jar BotPrenotazioni-1.0-SNAPSHOT.jar file.json\
\
Formato JSON:

    {
        "name": "Cognome nome",
        "cf": "Codice fiscale",
        "email": "email",
        "area": 22,
        "servizio": 50,
        "day": [
            {
                "hour": 15,
                "duration": 2
            },
            null
            ,
            ... fino a size(day) = 7
        ] 
    }

Area: 25 (BICF)\
Servizio: 50 (Primo piano last minute BICF)\
days: vettore di 7 elementi in cui ciascun elemento indica l'ora e durata da prenotare per quel giorno. null se non si vuole prenotare.
