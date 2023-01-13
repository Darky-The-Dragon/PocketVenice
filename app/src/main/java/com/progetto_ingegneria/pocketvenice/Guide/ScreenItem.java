package com.progetto_ingegneria.pocketvenice.Guide;
/**
 * Permette di costruire oggetti di tipo ScreenItem
 */
public class ScreenItem {

    private String title, description;
    private int screenImg;

    public ScreenItem(String title, String description, int screenImg) {
        this.title = title;
        this.description = description;
        this.screenImg = screenImg;
    }

    /**
     * @return Ritorna il titolo della schermata.
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title Nuovo titolo della schermata
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return Ritorna la descrizione della schermata.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description Nuova descrizione della schermata
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return Ritorna id dell'immagine della schermata.
     */
    public int getScreenImg() {
        return screenImg;
    }
    /**
     * @param screenImg Nuovo id dell'immagine della schermata
     */
    public void setScreenImg(int screenImg) {
        this.screenImg = screenImg;
    }
}
