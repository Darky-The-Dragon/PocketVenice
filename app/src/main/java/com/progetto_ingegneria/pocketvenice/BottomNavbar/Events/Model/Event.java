package com.progetto_ingegneria.pocketvenice.BottomNavbar.Events.Model;

import java.io.Serializable;
/**
 * Permette di creare oggetti id tipo Event.
 */
public class Event implements Serializable {
    private String title, address, photoSrc, description, fromDate, toDate, fromHour, toHour, link;

    public Event() {

    }

    public Event(String title, String address, String photoSrc, String description, String fromDate, String toDate, String link, String fromHour, String toHour) {
        this.title = title;
        this.address = address;
        this.photoSrc = photoSrc;
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.link = link;
        this.fromHour = fromHour;
        this.toHour = toHour;
    }

    /**
     *
     * @return Ritorna il titolo di Event
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title Nuovo tittolo dell'evento
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     *
     * @return Ritorna l'indirzzo di Event
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address Nuovo indirizzo di Event
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     *
     * @return Ritorna la srogente dell'immagine di Event
     */
    public String getPhotoSrc() {
        return photoSrc;
    }
    /**
     *
     * @return Ritorna la descrizione di Event
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description Nuova descrizione di Event
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     *
     * @return Ritorna la data di inizio di Event
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     *
     * @return Ritorna la data di fine di Event
     */
    public String getToDate() {
        return toDate;
    }

    /**
     *
     * @return Ritorna l'ora di inizio per ogni giorno di Event
     */
    public String getFromHour() {
        return fromHour;
    }

    /**
     *
     * @return Ritorna l'ora di fine per ogni giorno di Event
     */
    public String getToHour() {
        return toHour;
    }

}
