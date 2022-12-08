package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.progetto_ingegneria.pocketvenice.Auth.LoginActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.EventsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.MapsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.API.RequestManager;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Adapter.CustomAdapter;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Listeners.OnFetchDataListener;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Listeners.SelectListener;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Models.NewsApiResponse;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Models.NewsHeadlines;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.PlacesActivity;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.FAQ;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Info;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Profile;
import com.progetto_ingegneria.pocketvenice.R;
import com.progetto_ingegneria.pocketvenice.databinding.ActivityNewsBinding;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener, SelectListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView textTitle, textHeader;
    private ImageView imageMenu;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActivityNewsBinding binding;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private RequestManager manager;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            showNews(list);
            progressBar.setVisibility(View.GONE);
            errorLayout.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        public void onError(int errorCode) {
            String errorText;

            progressBar.setVisibility(View.GONE);
            textHeader.setVisibility(View.GONE);
            errorLayout.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

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
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage, btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progress_bar);

        imageMenu = findViewById(R.id.menu_nav);
        imageMenu.setOnClickListener(this);

        textTitle = findViewById(R.id.menu_title);
        textTitle.setText(NewsActivity.class.getSimpleName());

        drawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        textHeader = findViewById(R.id.recent_news);
        errorLayout = findViewById(R.id.newsErrorLayout);
        errorImage = findViewById(R.id.errorImage);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        btnRetry = findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.news) {
                return true;
            } else if (item.getItemId() == R.id.events) {
                Intent intent2 = new Intent(NewsActivity.this, EventsActivity.class);
                startActivity(intent2);
            } else if (item.getItemId() == R.id.places) {
                Intent intent3 = new Intent(NewsActivity.this, PlacesActivity.class);
                startActivity(intent3);
            } else if (item.getItemId() == R.id.map) {
                Intent intent4 = new Intent(NewsActivity.this, MapsActivity.class);
                startActivity(intent4);
            }

            return true;
        });

        binding.navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.profile) {
                replaceFragment(new Profile());
                textTitle.setText(Profile.class.getSimpleName());
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (item.getItemId() == R.id.faq) {
                replaceFragment(new FAQ());
                textTitle.setText(FAQ.class.getSimpleName());
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (item.getItemId() == R.id.info) {
                replaceFragment(new Info());
                textTitle.setText(Info.class.getSimpleName());
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (item.getItemId() == R.id.logout) {
                logoutUser();
            }

            return true;
        });

        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, swipeRefreshLayout, "it", "venezia", "", "veneziatoday.it", "publishedAt");
    }

    public void onClick(View v) {

        if (v.getId() == R.id.menu_nav) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(NewsActivity.this, NewsDetailActivity.class)
                .putExtra("data", headlines));
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        textHeader.setVisibility(View.VISIBLE);
        manager.getNewsHeadlines(listener, swipeRefreshLayout, "it", "venezia", "", "veneziatoday.it", "publishedAt");
    }

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_news);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    public void showErrorMessage(int imageView, String title, String message) {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }
        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            onRefresh();
        });
    }

    private void logoutUser() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signOut();
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(NewsActivity.this, LoginActivity.class));
    }



}