package com.progetto_ingegneria.pocketvenice.BottomNavbar.News.API;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.progetto_ingegneria.pocketvenice.BottomNavbar.News.Listeners.OnFetchDataListener;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.News.Models.NewsApiResponse;
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

    public void getNewsHeadlines(OnFetchDataListener<NewsApiResponse> listener, SwipeRefreshLayout swipeRefreshLayout, String language, String query, String searchIn, String domains, String sortBy) {

        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<NewsApiResponse> call = callNewsApi.callHeadlines(language, query, searchIn, domains, sortBy, context.getString(R.string.api_key));

        try {
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<NewsApiResponse> call, @NonNull Response<NewsApiResponse> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "An error occured while loading the news!", Toast.LENGTH_SHORT).show();
                        listener.onError(response.code());
                    }

                    assert response.body() != null;
                    listener.onFetchData(response.body().getArticles(), response.message());

                    swipeRefreshLayout.setRefreshing(false);

                }

                @Override
                public void onFailure(@NonNull Call<NewsApiResponse> call, @NonNull Throwable t) {
                    listener.onError(502);
                    swipeRefreshLayout.setRefreshing(false);
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
                @Query("searchIn") String searchIn,
                @Query("domains") String domains,
                @Query("sortBy") String sortBy,
                @Query("apiKey") String api_key
        );
    }
}
