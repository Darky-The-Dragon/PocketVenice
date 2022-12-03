package com.progetto_ingegneria.pocketvenice.BottomNavbar;

public class Place {
    public String Titolo, Sestiere, Indirizzo, FotoSrc, Descrizione;

    public Place() {
    }

    public Place(String Titolo, String Sestiere, String Indirizzo, String FotoSrc, String Descrizione) {
        this.Titolo = Titolo;
        this.Sestiere = Sestiere;
        this.Indirizzo = Indirizzo;
        this.FotoSrc = FotoSrc;
        this.Descrizione = Descrizione;
    }
}