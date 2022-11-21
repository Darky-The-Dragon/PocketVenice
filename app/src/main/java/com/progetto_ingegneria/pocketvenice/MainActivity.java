package com.progetto_ingegneria.pocketvenice;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        setContentView(binding.getRoot());
        drawerLayout = findViewById(R.id.drawerLayout);
        replaceFragment(new News());


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.news:
                    replaceFragment(new News());
                    break;
                case R.id.events:
                    replaceFragment(new Events());
                    break;
                case R.id.places:
                    replaceFragment(new Places());
                    break;
                case R.id.map:
                    replaceFragment(new Map());
                    break;
            }
            return true;
        });

        binding.navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    replaceFragment(new Profile());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.faq:
                    replaceFragment(new FAQ());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.info:
                    replaceFragment(new Info());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }
            return true;
        });

        imageMenu = findViewById(R.id.menu_nav);
        imageMenu.setOnClickListener(this);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);


        /*

        bottomNavigationView = findViewById(R.id.bottom_navigation_view_test);
        bottomNavController = Navigation.findNavController(this, R.id.frame_layout);
        NavigationUI.setupWithNavController(bottomNavigationView, bottomNavController);

        navController = Navigation.findNavController(this, R.id.navHostFragmentTest);
        NavigationUI.setupWithNavController(navigationView, navController);

        textTitle = findViewById(R.id.menu_title);
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) ->
                textTitle.setText(navDestination.getLabel()));

        */


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_nav:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }

        /*
        if (v.getId() == R.id.login) {
            progressBar.setVisibility(View.VISIBLE);
            FirebaseAuth.getInstance().signOut();
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        */
    }

}