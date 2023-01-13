package com.progetto_ingegneria.pocketvenice.BottomNavbar.Places.Model;

import java.io.Serializable;
/**
 * Permette di costruire oggetti di tipo Place
 */
public class Place implements Serializable {

    private String title, district, address, photoSrc, description;
    private float rating;

    public Place() {
    }

    public Place(String title, String district, String street, String photoSrc, String description, float rating) {
        this.title = title;
        this.district = district;
        this.address = street;
        this.photoSrc = photoSrc;
        this.description = description;
        this.rating = rating;
    }

    /**
     * @return Ritorna il titolo del Place
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title Aggiorna il titolo del Place
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return Ritorna il sestiere del Place
     */
    public String getDistrict() {
        return district;
    }
    /**
     * @param district Aggiorna il sestiere del Place
     */
    public void setDistrict(String district) {
        this.district = district;
    }
    /**
     * @return Ritorna l'indirizzo del Place
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param address Aggiorna l'indirizzo del Place
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * @return Ritorna la sorgente dell'immagine del Place
     */
    public String getPhotoSrc() {
        return photoSrc;
    }
    /**
     * @param photoSrc Aggiorna la sorgente dell'immagine del Place
     */
    public void setPhotoSrc(String photoSrc) {
        this.photoSrc = photoSrc;
    }
    /**
     * @return Ritorna la descrizione del Place
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description Aggiorna la descrizione dell'immagine del Place
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return Ritorna il rating del Place
     */
    public float getRating() {
        return rating;
    }
    /**
     * @param rating Aggiorna il rating dell'immagine del Place
     */
    public void setRating(float rating) {
        this.rating = rating;
    }
}
