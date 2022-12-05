package com.progetto_ingegneria.pocketvenice.BottomNavbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto_ingegneria.pocketvenice.Adapter;
import com.progetto_ingegneria.pocketvenice.MainActivity;
import com.progetto_ingegneria.pocketvenice.Utils;
import com.progetto_ingegneria.pocketvenice.api.ApiClient;
import com.progetto_ingegneria.pocketvenice.api.ApiInterface;
import com.progetto_ingegneria.pocketvenice.models.NewsApiResponse;
import com.progetto_ingegneria.pocketvenice.models.NewsHeadlines;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class News_Frag extends Fragment {

    public static final String API_KEY = "1dd430c786bb4fd38e650493cb32755c";
    private final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<NewsHeadlines> articles = new ArrayList<>();
    private Adapter adapter;

    public News_Frag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.recycle_view);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        //LoadJson();
        return view;

        */
        return null;

    }

    public void LoadJson() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String country = Utils.getCountry();

        Call<NewsApiResponse> call;
        call = apiInterface.getNews(country, API_KEY);

        call.enqueue(new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                if (response.isSuccessful() && response.body().getArticle() != null) {
                    if (articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticle();
                    adapter = new Adapter(articles, News_Frag.this.getContext());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(News_Frag.this.getContext(), "NO RESULT!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NewsApiResponse> call, Throwable t) {

            }
        });

    }
}