package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.progetto_ingegneria.pocketvenice.Auth.LoginActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.PlacesActivity;
import com.progetto_ingegneria.pocketvenice.CustomAdapter;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.FAQ;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Info;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Profile;
import com.progetto_ingegneria.pocketvenice.OnFetchDataListener;
import com.progetto_ingegneria.pocketvenice.R;
import com.progetto_ingegneria.pocketvenice.RequestManager;
import com.progetto_ingegneria.pocketvenice.databinding.ActivityNewsBinding;
import com.progetto_ingegneria.pocketvenice.models.NewsApiResponse;
import com.progetto_ingegneria.pocketvenice.models.NewsHeadlines;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textTitle;
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
    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            showNews(list);
        }

        @Override
        public void onError(String message) {

        }
    };
    private List<NewsHeadlines> articles = new ArrayList<>();

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

        manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general", null);

        drawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.news:
                    break;

                case R.id.events:
                    Intent intent2 = new Intent(NewsActivity.this, EventsActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.places:
                    Intent intent3 = new Intent(NewsActivity.this, PlacesActivity.class);
                    startActivity(intent3);
                    break;

                case R.id.map:
                    Intent intent4 = new Intent(NewsActivity.this, MapsActivity.class);
                    startActivity(intent4);
                    break;
            }
            return true;
        });

        binding.navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    replaceFragment(new Profile());
                    textTitle.setText(Profile.class.getSimpleName());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.faq:
                    replaceFragment(new FAQ());
                    textTitle.setText(FAQ.class.getSimpleName());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.info:
                    replaceFragment(new Info());
                    textTitle.setText(Info.class.getSimpleName());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.logout:
                    logoutUser();
                    break;
            }
            return true;
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_nav:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_news);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void logoutUser() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signOut();
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(NewsActivity.this, LoginActivity.class));
    }
}