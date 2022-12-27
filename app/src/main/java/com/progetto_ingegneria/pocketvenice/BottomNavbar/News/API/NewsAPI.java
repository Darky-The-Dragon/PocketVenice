package com.progetto_ingegneria.pocketvenice.BottomNavbar.News.API;

import com.progetto_ingegneria.pocketvenice.BottomNavbar.News.Models.NewsApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {
    @GET("everything")
    Call<NewsApiResponse> callHeadlines(
            @Query("language") String language,
            @Query("q") String query,
            @Query("searchIn") String searchIn,
            @Query("domains") String domains,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String api_key
    );
}
