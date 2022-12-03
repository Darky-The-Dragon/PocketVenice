package com.progetto_ingegneria.pocketvenice.BottomNavbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto_ingegneria.pocketvenice.BottomNavbar.News_API.Adapter;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.News_API.ApiClient;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.News_API.ApiInterface;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.News_API.Article;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.News_API.Utils;
import com.progetto_ingegneria.pocketvenice.MainActivity;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class News extends Fragment {

    public static final String API_KEY = "1dd430c786bb4fd38e650493cb32755c";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private Adapter adapter;
    private String TAG = MainActivity.class.getSimpleName();

    private String status;
    private int totalResult;

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
    public int getTotalResult() {return totalResult;}
    public void setTotalResult(int totalResult) {this.totalResult = totalResult;}
    public List<Article> getArticles() {return articles;}
    public void setArticles(List<Article> articles) {this.articles = articles;}



    public News() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = recyclerView.findViewById(R.id.recycle_view);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        LoadJson();
        return inflater.inflate(R.layout.fragment_news, container, false);

    }

    public void LoadJson(){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<News> call;
        call = apiInterface.getNews(Utils.getCountry(), API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful() && response.body().getArticles() != null){
                    if(articles.isEmpty()){
                        articles.clear();
                    }
                    articles = response.body().getArticles();
                    adapter = new Adapter(articles, News.this.getContext());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(News.this.getContext(),"NO RESULT!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });

    }
}