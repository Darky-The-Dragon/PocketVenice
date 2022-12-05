package com.progetto_ingegneria.pocketvenice;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.navigation.NavController;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.progetto_ingegneria.pocketvenice.Auth.LoginActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.EventsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.MapsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.NewsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.PlacesActivity;
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
        //replaceFragment(new News_Frag());

        progressBar = findViewById(R.id.progress_bar);

        textTitle = findViewById(R.id.menu_title);
        textTitle.setText(NewsActivity.class.getSimpleName());

        imageMenu = findViewById(R.id.menu_nav);
        imageMenu.setOnClickListener(this);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.news:
                    Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                    startActivity(intent);
                    //textTitle.setText(News_Frag.class.getSimpleName());
                    break;

                case R.id.events:
                    Intent intent2 = new Intent(MainActivity.this, EventsActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.places:
                    Intent intent3 = new Intent(MainActivity.this, PlacesActivity.class);
                    startActivity(intent3);
                    break;

                case R.id.map:
                    Intent intent4 = new Intent(MainActivity.this, MapsActivity.class);
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