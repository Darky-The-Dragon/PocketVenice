package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.API;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Listeners.OnFetchDataListener;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Models.NewsApiResponse;
import com.progetto_ingegneria.pocketvenice.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getNewsHeadlines(OnFetchDataListener<NewsApiResponse> listener, String language, String query) {
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<NewsApiResponse> call = callNewsApi.callHeadlines(language, query, context.getString(R.string.api_key));

        try {
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<NewsApiResponse> call, @NonNull Response<NewsApiResponse> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "An error occured while loading the news!", Toast.LENGTH_SHORT).show();
                    }

                    assert response.body() != null;
                    listener.onFetchData(response.body().getArticles(), response.message());

                }

                @Override
                public void onFailure(@NonNull Call<NewsApiResponse> call, @NonNull Throwable t) {
                    listener.onError("Request Failed!");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CallNewsApi {
        @GET("everything")
        Call<NewsApiResponse> callHeadlines(
                @Query("language") String language,
                @Query("q") String query,
                @Query("apiKey") String api_key
        );
    }
}
