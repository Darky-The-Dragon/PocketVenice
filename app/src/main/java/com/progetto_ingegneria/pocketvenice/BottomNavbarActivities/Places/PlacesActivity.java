package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places;

import android.annotation.SuppressLint;
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
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.progetto_ingegneria.pocketvenice.Auth.LoginActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.EventsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.MapsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.NewsActivity;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.FAQ;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Info;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Profile;
import com.progetto_ingegneria.pocketvenice.R;
import com.progetto_ingegneria.pocketvenice.databinding.ActivityPlacesBinding;

import java.util.ArrayList;

public class PlacesActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Place_Data> places_list;
    private TextView textTitle;
    private ActivityPlacesBinding binding;
    private BottomNavigationView bottomNavigationView;
    private ImageView imageMenu;
    private NavigationView navigationView;
    private NavController navController;
    private NavController bottomNavController;
    private DrawerLayout drawerLayout;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private DatabaseReference database;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlacesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progress_bar);

        imageMenu = findViewById(R.id.menu_nav);
        imageMenu.setOnClickListener(this);

        textTitle = findViewById(R.id.menu_title);
        textTitle.setText(PlacesActivity.class.getSimpleName());

        drawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        // RecyclerView
        recyclerView = findViewById(R.id.recycler_places);
        database = FirebaseDatabase.getInstance().getReference("Luoghi");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Menu selection
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        // List Recycle
        places_list = new ArrayList<>();
        myAdapter = new MyAdapter(this, places_list);
        recyclerView.setAdapter(myAdapter);

        // Bottom navbar
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.news){
                Intent intent = new Intent(PlacesActivity.this, NewsActivity.class);
                startActivity(intent);
            }
            else if(item.getItemId() == R.id.events){
                Intent intent2 = new Intent(PlacesActivity.this, EventsActivity.class);
                startActivity(intent2);
            }
            else if(item.getItemId() == R.id.places){
                return true;
            }
            else if(item.getItemId() == R.id.map){
                Intent intent4 = new Intent(PlacesActivity.this, MapsActivity.class);
                startActivity(intent4);
            }

            return true;
        });

        // Sidebar
        binding.navigationView.setNavigationItemSelectedListener(item -> {

            if(item.getItemId() == R.id.profile){
                replaceFragment(new Profile());
                textTitle.setText(Profile.class.getSimpleName());
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            else if(item.getItemId() == R.id.faq){
                replaceFragment(new FAQ());
                textTitle.setText(FAQ.class.getSimpleName());
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            else if(item.getItemId() == R.id.info){
                replaceFragment(new Info());
                textTitle.setText(Info.class.getSimpleName());
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            else if(item.getItemId() == R.id.logout){
                logoutUser();
            }

            return true;
        });

        // Database handler
        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Place_Data place = dataSnapshot.getValue(Place_Data.class);
                    places_list.add(place);


                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    // Sidebar button
    public void onClick(View v) {

        if(v.getId() == R.id.menu_nav){
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    // Fragment logic sidebar
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    // Logout logic
    private void logoutUser() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signOut();
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(PlacesActivity.this, LoginActivity.class));
    }
}