package com.progetto_ingegneria.pocketvenice.BottomNavbar.News.Models;

import java.io.Serializable;
/**
 * Consente la creazione di oggetti di tipo NewsHeadlines.
 */
public class NewsHeadlines implements Serializable {

    private Source source = null;
    private String author = "";
    private String title = "";
    private String description = "";
    private String url = "";
    private String urlToImage = "";
    private String publishedAt = "";
    private String content = "";

    /**
     * @return Ritorna un oggetto di tipo Souce
     */
    public Source getSource() {
        return source;
    }

    /**
     *
     * @param source Nuovo oggetto di tipo Sourced a aggiornare
     */
    public void setSource(Source source) {
        this.source = source;
    }

    /**
     *
     * @return Ritorna l'autore di NewsHeadlines
     */
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @param author nuovo autore di NewsHeadlines
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     *
     * @return Ritorna il titolo di NewsHeadlines
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title Nuovo titolo di NewsHeadlines
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return Ritorna la descrizione di NewsHeadlines
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description Nuova descrizione di NewsHeadlines
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return Ritorna l'URL della NewsHeadlines
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url nuovo URL della NewsHeadlines
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return Ritorna l'URL dell'immagine della NewsHeadlines
     */
    public String getUlrToImage() {
        return urlToImage;
    }

    /**
     *
     * @param ulrToImage nuovo URL dell'immagine di NewsHeadlines
     */
    public void setUlrToImage(String ulrToImage) {
        this.urlToImage = ulrToImage;
    }

    /**
     *
     * @return Ritorna la data di pubblicazione della NewsHeadlines
     */
    public String getPublishedAt() {
        return publishedAt;
    }

    /**
     *
     * @param publishedAt Nuova data di pubblicazione di NewsHeadlines
     */
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    /**
     *
     * @return Ritorna il content della NewsHeadlines
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content nuovo content per NewsHeadlines
     */
    public void setContent(String content) {
        this.content = content;
    }
}
