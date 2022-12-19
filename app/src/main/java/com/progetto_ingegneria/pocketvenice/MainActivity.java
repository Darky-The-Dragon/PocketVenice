package com.progetto_ingegneria.pocketvenice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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
import com.progetto_ingegneria.pocketvenice.Guide.GuideActivity;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.FAQ;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Info;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Profile;
import com.progetto_ingegneria.pocketvenice.TestFrag.TestEvents;
import com.progetto_ingegneria.pocketvenice.TestFrag.TestMaps;
import com.progetto_ingegneria.pocketvenice.TestFrag.TestNews;
import com.progetto_ingegneria.pocketvenice.TestFrag.TestPlaces;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationBarView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    protected View headerView;
    protected TextView textTitle, header_username;
    protected ImageView imageMenu;
    protected BottomNavigationView bottomNavigationView;
    protected NavigationView navigationView;
    protected DrawerLayout drawerLayout;
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
        setHeaderUsername();
        setup();
    }

    private void setHeaderUsername() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
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

    private void setup() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            Toast.makeText(this, "Sono dentro l'IF", Toast.LENGTH_SHORT).show();
            replaceFragment(new TestNews());
            textTitle.setText(R.string.news);
        }
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
        headerView = navigationView.getHeaderView(0);
        header_username = headerView.findViewById(R.id.header_fullname);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menu_nav) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void replaceFragment(Fragment fragment) {
        /*
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
        */
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame_layout, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        Toast.makeText(this, "Fragment after replace: " + getSupportFragmentManager().getBackStackEntryCount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        // if your using fragment then you can do this way
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        Toast.makeText(this, "FRAGMENTS: " + fragments, Toast.LENGTH_SHORT).show();
        if (fragments == 1) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> finish())
                    .setNegativeButton("No", null)
                    .show();
        } else {
            super.onBackPressed();
            Menu menu = bottomNavigationView.getMenu();
            List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
            Fragment fragment = fragmentList.get(fragmentList.size() - 1);
            //fragment corretto da settare.
            if (fragment instanceof TestMaps && bottomNavigationView.getSelectedItemId() != R.id.map) {
                MenuItem menuItem = menu.getItem(3);
                menuItem.setChecked(true);
            }
            if (fragment instanceof TestEvents && bottomNavigationView.getSelectedItemId() != R.id.events) {
                MenuItem menuItem = menu.getItem(1);
                menuItem.setChecked(true);
            }
            if (fragment instanceof TestNews && bottomNavigationView.getSelectedItemId() != R.id.news) {
                MenuItem menuItem = menu.getItem(0);
                menuItem.setChecked(true);
            }
            if (fragment instanceof TestPlaces && bottomNavigationView.getSelectedItemId() != R.id.places) {
                MenuItem menuItem = menu.getItem(2);
                menuItem.setChecked(true);
            }


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
        } else if (item.getItemId() == R.id.guide) {
            startActivity(new Intent(this, GuideActivity.class));
        } else if (item.getItemId() == R.id.logout) {
            logoutUser();
        }

        return true;
    }


}
