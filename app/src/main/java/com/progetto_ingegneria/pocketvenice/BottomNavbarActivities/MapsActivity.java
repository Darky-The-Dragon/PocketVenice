package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.progetto_ingegneria.pocketvenice.Auth.LoginActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.NewsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.PlacesActivity;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.FAQ;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Info;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Profile;
import com.progetto_ingegneria.pocketvenice.R;
import com.progetto_ingegneria.pocketvenice.databinding.ActivityMapsBinding;

public class MapsActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private TextView textTitle;
    private ActivityMapsBinding binding;
    private BottomNavigationView bottomNavigationView;

    private ImageView imageMenu;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //nel video ha (R.layout.activity_maps)

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progress_bar);

        imageMenu = findViewById(R.id.menu_nav);
        imageMenu.setOnClickListener(this);

        textTitle = findViewById(R.id.menu_title);
        textTitle.setText(MapsActivity.class.getSimpleName());

        drawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.news) {
                Intent intent = new Intent(MapsActivity.this, NewsActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.events) {
                Intent intent2 = new Intent(MapsActivity.this, EventsActivity.class);
                startActivity(intent2);
            } else if (item.getItemId() == R.id.places) {
                Intent intent3 = new Intent(MapsActivity.this, PlacesActivity.class);
                startActivity(intent3);
            } else if (item.getItemId() == R.id.map) {
                return true;
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

        // errore nullptr per this (forse perché usa FragmentActivity?)
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onClick(View v) {

        if (v.getId() == R.id.menu_nav) {
            drawerLayout.openDrawer(GravityCompat.START);
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
        startActivity(new Intent(MapsActivity.this, LoginActivity.class));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        //Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker((new MarkerOptions().position(sydney).title("Marker in Sydney")));
        mMap.moveCamera((CameraUpdateFactory.newLatLng(sydney)));
    }
}