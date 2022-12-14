package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events;

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
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Adapter.EventAdapter;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Listeners.EventCallback;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Model.Event;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.MapsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.NewsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.PlacesActivity;
import com.progetto_ingegneria.pocketvenice.Guide.GuideActivity;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.FAQ;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Info;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Profile;
import com.progetto_ingegneria.pocketvenice.R;
import com.progetto_ingegneria.pocketvenice.databinding.ActivityEventsBinding;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity implements View.OnClickListener, EventCallback {

    protected TextView textTitle;
    protected ActivityEventsBinding binding;
    protected BottomNavigationView bottomNavigationView;

    protected ImageView imageMenu;
    protected NavigationView navigationView;
    protected DrawerLayout drawerLayout;
    protected ProgressBar progressBar;
    protected FirebaseAuth mAuth;
    protected RecyclerView recyclerView;
    protected DatabaseReference database;
    protected EventAdapter eventAdapter;
    protected List<Event> eventsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initDataPlaces();
        setupEventsAdapter();
    }

    private void setupEventsAdapter() {
        eventAdapter = new EventAdapter(this, eventsData, this);
        recyclerView.setAdapter(eventAdapter);
    }

    private void initDataPlaces() {
        database = FirebaseDatabase.getInstance().getReference("Eventi");

        // Database handler
        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Event event = dataSnapshot.getValue(Event.class);
                    eventsData.add(event);

                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initViews() {

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progress_bar);

        imageMenu = findViewById(R.id.menu_nav);
        imageMenu.setOnClickListener(this);

        textTitle = findViewById(R.id.menu_title);
        textTitle.setText(EventsActivity.class.getSimpleName());

        drawerLayout = findViewById(R.id.drawerLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.news) {
                Intent intent = new Intent(EventsActivity.this, NewsActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.events) {
                return true;
            } else if (item.getItemId() == R.id.places) {
                Intent intent3 = new Intent(EventsActivity.this, PlacesActivity.class);
                startActivity(intent3);
            } else if (item.getItemId() == R.id.map) {
                Intent intent4 = new Intent(EventsActivity.this, MapsActivity.class);
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
            } else if (item.getItemId() == R.id.guide) {
                Intent guide = new Intent(EventsActivity.this, GuideActivity.class);
                startActivity(guide);
            }

            return true;
        });

        recyclerView = findViewById(R.id.rv_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        eventsData = new ArrayList<>();
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

    private void logoutUser() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signOut();
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(EventsActivity.this, LoginActivity.class));
    }

    @Override
    public void onEventItemClick(int pos,
                                 ImageView imgContainer,
                                 ImageView imgEvent,
                                 TextView title,
                                 TextView address,
                                 TextView fromDate,
                                 TextView hyphen,
                                 TextView toDate) {
        Intent intent = new Intent(this, EventDetailsActivity.class);
        intent.putExtra("eventObject", eventsData.get(pos));

        Pair<View, String> p1 = Pair.create((View) imgContainer, "eventContainerTN");
        Pair<View, String> p2 = Pair.create((View) imgEvent, "eventTN");
        Pair<View, String> p3 = Pair.create((View) title, "eventTitleTN");
        Pair<View, String> p4 = Pair.create((View) address, "eventAddressTN");
        Pair<View, String> p5 = Pair.create((View) fromDate, "eventFromDateTN");
        Pair<View, String> p6 = Pair.create((View) hyphen, "eventHyphenTN");
        Pair<View, String> p7 = Pair.create((View) toDate, "eventToDateTN");

        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3, p4, p5, p6, p7);

        startActivity(intent, optionsCompat.toBundle());
    }
}