package com.progetto_ingegneria.pocketvenice.TestFrag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.API.RequestManager;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Adapter.CustomAdapter;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Listeners.OnFetchDataListener;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Listeners.SelectListener;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Models.NewsApiResponse;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Models.NewsHeadlines;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.List;

public class TestNews extends Fragment implements View.OnClickListener, SelectListener, SwipeRefreshLayout.OnRefreshListener {

    protected TextView textHeader;
    protected ProgressBar progressBar;
    protected RequestManager manager;
    protected RecyclerView recyclerView;
    protected CustomAdapter adapter;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected RelativeLayout errorLayout;
    protected ImageView errorImage;
    protected TextView errorTitle, errorMessage, btnRetry;
    protected View view;


    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            showNews(list);
            progressBar.setVisibility(View.GONE);
            errorLayout.setVisibility(View.GONE);
            requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        public void onError(int errorCode) {
            String errorText;

            progressBar.setVisibility(View.GONE);
            textHeader.setVisibility(View.GONE);
            errorLayout.setVisibility(View.GONE);
            requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            switch (errorCode) {
                case 404:
                    errorText = "Please try again! \n404 Not Found";
                    break;
                case 500:
                    errorText = "Please try again! \n500 Server Broken";
                    break;
                case 502:
                    errorText = "Network failure, Please Try Again";
                    break;
                default:
                    errorText = "Unknown error";
                    break;
            }

            showErrorMessage(R.drawable.no_result, "Oops.. No Results!", errorText);
        }
    };

    public TestNews() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_test_news, container, false);

        initView();
        startProgressBar();
        requestAPI();

        return view;
    }

    private void requestAPI() {
        manager = new RequestManager(getContext());
        manager.getNewsHeadlines(listener, swipeRefreshLayout, "it", "venezia", "", "veneziatoday.it", "publishedAt");
    }

    private void startProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void initView() {
        progressBar = view.findViewById(R.id.progress_bar);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        textHeader = view.findViewById(R.id.recent_news);
        errorLayout = view.findViewById(R.id.newsErrorLayout);
        errorImage = view.findViewById(R.id.errorImage);
        errorTitle = view.findViewById(R.id.errorTitle);
        errorMessage = view.findViewById(R.id.errorMessage);
        btnRetry = view.findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);

    }

    public void showErrorMessage(int imageView, String title, String message) {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }
        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

    }

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = view.findViewById(R.id.recycler_news);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter = new CustomAdapter(getContext(), list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        textHeader.setVisibility(View.VISIBLE);
        manager.getNewsHeadlines(listener, swipeRefreshLayout, "it", "venezia", "", "veneziatoday.it", "publishedAt");
    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        Fragment fragment = TestNewsDetails.newInstance(headlines);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame_layout, fragment).addToBackStack(null).commit();;

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRetry) {
            progressBar.setVisibility(View.VISIBLE);
            onRefresh();
        }
    }
}