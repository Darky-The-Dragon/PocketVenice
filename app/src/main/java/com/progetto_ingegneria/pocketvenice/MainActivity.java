package com.progetto_ingegneria.pocketvenice;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.progetto_ingegneria.pocketvenice.Auth.LoginActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewLogout;
    private TextView textTitle;
    private ImageView imageMenu;
    private ProgressBar progressBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NavController navController;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //textViewLogout = findViewById(R.id.logout);
        //textViewLogout.setOnClickListener(this);
        textTitle = findViewById(R.id.menu_title);
        imageMenu = findViewById(R.id.menu_nav);
        progressBar = findViewById(R.id.progress_bar);
        drawerLayout = findViewById(R.id.drawer_layout);

        imageMenu.setOnClickListener(this);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);

        navController = Navigation.findNavController(this, R.id.NavHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        /*
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.logout:
                        logoutUser();
                        return true;
                    default:
                        return false;
                }
            }
        });

        */

        navController.addOnDestinationChangedListener((navController, navDestination, bundle) ->
                textTitle.setText(navDestination.getLabel()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_nav:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }

        /*
        if (v.getId() == R.id.logout) {
            progressBar.setVisibility(View.VISIBLE);
            FirebaseAuth.getInstance().signOut();
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        */
    }

    private void logoutUser() {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signOut();
        progressBar.setVisibility(View.VISIBLE);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

}