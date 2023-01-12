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

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUlrToImage() {
        return urlToImage;
    }

    public void setUlrToImage(String ulrToImage) {
        this.urlToImage = ulrToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
