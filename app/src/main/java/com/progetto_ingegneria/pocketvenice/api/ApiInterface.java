package com.progetto_ingegneria.pocketvenice.api;

import com.progetto_ingegneria.pocketvenice.models.NewsApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<NewsApiResponse> getNews(

            @Query("country") String country,
            @Query("apikey") String apikey
    );
}
