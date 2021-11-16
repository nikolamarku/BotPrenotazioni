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
        "hours": [15,15,15,-1,15,-1,-1] 
    }

Area: 25 (BICF)\
Servizio: 50 (Primo piano last minute BICF)\
hours: vettore di 7 elementi in cui ciascun elemento indica l'ora da prenotare per quel giorno. -1 Se non si vuole prenotare.
