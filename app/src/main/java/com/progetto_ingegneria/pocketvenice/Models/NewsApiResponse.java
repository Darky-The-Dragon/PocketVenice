package com.progetto_ingegneria.pocketvenice.Models;

import java.io.Serializable;
import java.util.List;

public class NewsApiResponse implements Serializable {

    private String status;
    private int totalResult;
    private List<NewsHeadlines> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public List<NewsHeadlines> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsHeadlines> articles) {
        this.articles = articles;
    }
}
