package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Model;

import java.io.Serializable;

public class Event implements Serializable
{
    private String title, address, photoSrc, description, fromDate, toDate, fromHour, toHour, link;

    public Event(){

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFromHour() {
        return fromHour;
    }

    public void setFromHour(String fromHour) {
        this.fromHour = fromHour;
    }

    public String getToHour() {
        return toHour;
    }

    public void setToHour(String toHour) {
        this.toHour = toHour;
    }
}
