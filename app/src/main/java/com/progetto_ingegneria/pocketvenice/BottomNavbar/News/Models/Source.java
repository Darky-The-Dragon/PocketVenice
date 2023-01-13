package com.progetto_ingegneria.pocketvenice.BottomNavbar.News.Models;

import java.io.Serializable;
/**
 * Consente la creazione di oggetti di tipo Source.
 */
public class Source implements Serializable {


    private String id = "";
    private String name = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
