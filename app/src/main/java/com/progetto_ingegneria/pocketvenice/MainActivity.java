package com.progetto_ingegneria.pocketvenice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.progetto_ingegneria.pocketvenice.Auth.LoginActivity;
import com.progetto_ingegneria.pocketvenice.Guide.GuideActivity;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.FAQ;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Info;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Profile;
import com.progetto_ingegneria.pocketvenice.TestFrag.TestEvents;
import com.progetto_ingegneria.pocketvenice.TestFrag.TestMaps;
import com.progetto_ingegneria.pocketvenice.TestFrag.TestNews;
import com.progetto_ingegneria.pocketvenice.TestFrag.TestPlaces;
import com.progetto_ingegneria.pocketvenice.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationBarView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {


    protected TextView textTitle;
    protected ImageView imageMenu;
    protected BottomNavigationView bottomNavigationView;
    protected NavigationView navigationView;
    protected DrawerLayout drawerLayout;
    protected ActivityMainBinding binding;
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
        setup();
    }

    private void setup() {

        replaceFragment(new TestNews());
        textTitle.setText(R.string.news);
        imageMenu.setOnClickListener(this);
        navigationView.setItemIconTintList(null);
    }

    private void initview() {

        drawerLayout = findViewById(R.id.drawerLayout);
        progressBar = findViewById(R.id.progress_bar);
        textTitle = findViewById(R.id.menu_title);
        imageMenu = findViewById(R.id.menu_nav);
        drawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menu_nav) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void replaceFragment(Fragment fragment) {
        String backStateName =  fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.main_frame_layout, fragment, backStateName);
            ft.addToBackStack(backStateName);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }

    }

    @Override
    public void onBackPressed(){
        // if your using fragment then you can do this way
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1){
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }else{
            super.onBackPressed();


            //manca il risettaggio navigationview, per il resto funziona

            //se setti la navigation bar come abbiamo fatto finche sono fragment senza reichiesta
            //dell'activity funziona altrimenti no

        }
    }

    private void logoutUser() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signOut();
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.news) {
            replaceFragment(new TestNews());
            textTitle.setText(R.string.news);
        } else if (item.getItemId() == R.id.events) {
            replaceFragment(new TestEvents());
            textTitle.setText(R.string.events);
        } else if (item.getItemId() == R.id.places) {
            replaceFragment(new TestPlaces());
            textTitle.setText(R.string.place);
        } else if (item.getItemId() == R.id.map) {
            replaceFragment(new TestMaps());
            textTitle.setText(R.string.map);
        } else if (item.getItemId() == R.id.profile) {
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
        }else if (item.getItemId() == R.id.guide) {
            startActivity(new Intent(this, GuideActivity.class));
        } else if (item.getItemId() == R.id.logout) {
            logoutUser();
        }

        return true;
    }


}
