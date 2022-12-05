package com.progetto_ingegneria.pocketvenice.models;

public class NewsHeadlines {

    private Source source = null;
    private String author = "";
    private String title = "";
    private String description = "";
    private String url = "";
    private String ulrToImage = "";
    private String publishedAt = "";

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
        return ulrToImage;
    }

    public void setUlrToImage(String ulrToImage) {
        this.ulrToImage = ulrToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
