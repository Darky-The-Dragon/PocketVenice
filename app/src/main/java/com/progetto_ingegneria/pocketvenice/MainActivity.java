package com.progetto_ingegneria.pocketvenice;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.progetto_ingegneria.pocketvenice.Auth.LoginActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Events;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Map;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.News;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Places;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.FAQ;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Info;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Profile;
import com.progetto_ingegneria.pocketvenice.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textTitle;
    private ImageView imageMenu;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private NavController navController;
    private NavController bottomNavController;
    private DrawerLayout drawerLayout;
    private ActivityMainBinding binding;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        setContentView(binding.getRoot());
        drawerLayout = findViewById(R.id.drawerLayout);
        replaceFragment(new News());

        progressBar = findViewById(R.id.progress_bar);

        textTitle = findViewById(R.id.menu_title);
        textTitle.setText(News.class.getSimpleName());

        imageMenu = findViewById(R.id.menu_nav);
        imageMenu.setOnClickListener(this);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.news:
                    replaceFragment(new News());
                    textTitle.setText(News.class.getSimpleName());

                    break;
                case R.id.events:
                    replaceFragment(new Events());
                    textTitle.setText(Events.class.getSimpleName());
                    break;
                case R.id.places:
                    replaceFragment(new Places());
                    textTitle.setText(Places.class.getSimpleName());
                    break;
                case R.id.map:
                    replaceFragment(new Map());
                    textTitle.setText(Map.class.getSimpleName());
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

        imageMenu = findViewById(R.id.menu_nav);
        imageMenu.setOnClickListener(this);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_nav:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
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
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

}