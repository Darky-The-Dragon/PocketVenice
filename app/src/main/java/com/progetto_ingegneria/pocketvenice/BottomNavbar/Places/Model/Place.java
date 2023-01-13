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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoSrc() {
        return photoSrc;
    }

    public void setPhotoSrc(String photoSrc) {
        this.photoSrc = photoSrc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
