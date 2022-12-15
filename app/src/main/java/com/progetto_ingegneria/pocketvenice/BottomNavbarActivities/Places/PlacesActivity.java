package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.progetto_ingegneria.pocketvenice.Auth.LoginActivity;
import com.progetto_ingegneria.pocketvenice.Auth.User;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.EventsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.MapsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.NewsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.Adapter.PlaceAdapter;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.Listeners.PlaceCallback;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.Model.Place;
import com.progetto_ingegneria.pocketvenice.Guide.GuideActivity;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.FAQ;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Info;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Profile;
import com.progetto_ingegneria.pocketvenice.R;
import com.progetto_ingegneria.pocketvenice.databinding.ActivityPlacesBinding;

import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends AppCompatActivity implements View.OnClickListener, PlaceCallback {

    protected TextView textTitle, header_username;
    protected ActivityPlacesBinding binding;
    protected BottomNavigationView bottomNavigationView;
    protected ImageView imageMenu;
    protected NavigationView navigationView;
    protected NavController navController;
    protected NavController bottomNavController;
    protected DrawerLayout drawerLayout;
    protected View headerView;
    protected ProgressBar progressBar;
    protected FirebaseAuth mAuth;
    protected RecyclerView recyclerView;
    protected DatabaseReference database;
    protected PlaceAdapter placeAdapter;
    protected List<Place> placesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlacesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        initViews();
        setHeader_username();
        initDataPlaces();
        setupPlacesAdapter();
    }

    private void setupPlacesAdapter() {
        placeAdapter = new PlaceAdapter(this, placesData, this);
        recyclerView.setAdapter(placeAdapter);
    }

    private void initDataPlaces() {
        database = FirebaseDatabase.getInstance().getReference("Luoghi");

        // Database handler
        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Place place = dataSnapshot.getValue(Place.class);
                    placesData.add(place);

                }
                placeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initViews() {

        progressBar = findViewById(R.id.progress_bar);

        imageMenu = findViewById(R.id.menu_nav);
        imageMenu.setOnClickListener(this);

        textTitle = findViewById(R.id.menu_title);
        textTitle.setText(R.string.place);

        drawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        headerView = navigationView.getHeaderView(0);

        // Menu selection
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        // Bottom navbar
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.news) {
                Intent intent = new Intent(PlacesActivity.this, NewsActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.events) {
                Intent intent2 = new Intent(PlacesActivity.this, EventsActivity.class);
                startActivity(intent2);
            } else if (item.getItemId() == R.id.places) {
                return true;
            } else if (item.getItemId() == R.id.map) {
                Intent intent4 = new Intent(PlacesActivity.this, MapsActivity.class);
                startActivity(intent4);
            }

            return true;
        });

        // Sidebar
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
            } else if (item.getItemId() == R.id.guide) {
                Intent guide = new Intent(PlacesActivity.this, GuideActivity.class);
                startActivity(guide);
            }

            return true;
        });

        recyclerView = findViewById(R.id.rv_places);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        placesData = new ArrayList<>();
    }

    // Sidebar button
    public void onClick(View v) {

        if (v.getId() == R.id.menu_nav) {
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

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame_layout);
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        } else {
            super.onBackPressed();
        }
    }

    // Logout logic
    private void logoutUser() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signOut();
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(PlacesActivity.this, LoginActivity.class));
    }

    @Override
    public void onPlaceItemClick(int pos,
                                 ImageView imgContainer,
                                 ImageView imgPlace,
                                 TextView title,
                                 TextView address,
                                 TextView score,
                                 RatingBar ratingBar) {
        Intent intent = new Intent(this, PlaceDetailsActivity.class);
        intent.putExtra("placeObject", placesData.get(pos));

        Pair<View, String> p1 = Pair.create((View) imgContainer, "placeContainerTN");
        Pair<View, String> p2 = Pair.create((View) imgPlace, "placeTN");
        Pair<View, String> p3 = Pair.create((View) title, "placeTitleTN");
        Pair<View, String> p4 = Pair.create((View) address, "placeAddressTN");
        Pair<View, String> p5 = Pair.create((View) score, "placeScoreTN");
        Pair<View, String> p6 = Pair.create((View) ratingBar, "placeRateTN");

        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3, p4, p5, p6);

        startActivity(intent, optionsCompat.toBundle());
    }

    private void setHeader_username() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        header_username = headerView.findViewById(R.id.header_fullname);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null)
                    header_username.setText(user.getFullName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}