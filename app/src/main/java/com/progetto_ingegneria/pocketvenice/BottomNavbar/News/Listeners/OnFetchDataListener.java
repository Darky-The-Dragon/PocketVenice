package com.progetto_ingegneria.pocketvenice.BottomNavbar.News.Listeners;

import com.progetto_ingegneria.pocketvenice.BottomNavbar.News.Models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {

    void onFetchData(List<NewsHeadlines> list, String message);

    void onError(int errorCode);
}
