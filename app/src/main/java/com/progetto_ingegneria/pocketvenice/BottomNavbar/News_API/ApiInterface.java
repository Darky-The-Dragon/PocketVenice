package com.progetto_ingegneria.pocketvenice.BottomNavbar.News_API;

import com.progetto_ingegneria.pocketvenice.BottomNavbar.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<News> getNews(
            @Query("country") String country,
            @Query("apikey") String apikey
    );
}
