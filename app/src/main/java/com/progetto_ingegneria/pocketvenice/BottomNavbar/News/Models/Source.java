package com.progetto_ingegneria.pocketvenice.BottomNavbar.News.Models;

import java.io.Serializable;
/**
 * Consente la creazione di oggetti di tipo Source.
 */
public class Source implements Serializable {


    private String id = "";
    private String name = "";

    /**
     *
     * @return Ritorna id di source
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id Nuovo id di source
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Ritona il nome di source
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Nuovo nome di Source
     */
    public void setName(String name) {
        this.name = name;
    }
}
