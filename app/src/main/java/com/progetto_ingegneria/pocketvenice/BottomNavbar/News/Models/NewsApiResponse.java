package com.progetto_ingegneria.pocketvenice.BottomNavbar.News.Models;

import java.io.Serializable;
import java.util.List;
/**
 * Consente la creazione di oggetti di tipo NewsApiResponse.
 */
public class NewsApiResponse implements Serializable {

    private String status;
    private int totalResult;
    private List<NewsHeadlines> articles;

    /**
     *
     * @return Ritorna la lista di NewsHeadlines
     */
    public List<NewsHeadlines> getArticles() {
        return articles;
    }
}
