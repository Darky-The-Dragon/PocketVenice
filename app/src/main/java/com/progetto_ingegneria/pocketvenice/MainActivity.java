package com.progetto_ingegneria.pocketvenice;

import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.appcompat.widget.Toolbar;
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
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Events.Events;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Maps.Maps;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.News.News;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Places.Places;
import com.progetto_ingegneria.pocketvenice.Guide.GuideActivity;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.FAQ;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Info;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Profile;
import com.progetto_ingegneria.pocketvenice.User.User;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationBarView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    protected View headerView;
    protected TextView textTitle, header_username;
    protected ImageView imageMenu;
    protected Toolbar toolbar;
    protected BottomNavigationView bottomNavigationView;
    protected NavigationView navigationView;
    protected DrawerLayout drawerLayout;
    protected ProgressBar progressBar;
    protected FirebaseUser user;
    protected boolean isLogged = false;

    /**
     * Questo metodo crea l'attività Main, collegando il file xml contenente la struttura grafica al resto del codice.
     * @param savedInstanceState Usato per salvare uno stato dell'istanza dell'app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGuide();
        checkAuth();
        initview();
        setHeaderUsername();
        setup();
    }

    /**
     * startGuide controlla se la guida dell'app è stata visualizzata.
     * Se un utente usa l'applicazione per la prima volta gli viene visualizzata la guida su come funziona l'applicazione.
     */
    private void startGuide() {
        if (restorePrefData()) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("Guide", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isIntroOpened", false);
            editor.apply();
            startActivity(new Intent(MainActivity.this, GuideActivity.class));
        }
    }

    /**
     * @return Ritorna il valore booleano salvato nella memoria del dispositivo, il quale rappresenta se la guida dell'applicazione è stata visualizzata oppure no.
     */
    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Guide", MODE_PRIVATE);
        return pref.getBoolean("isIntroOpened", true);
    }

    /**
     * setHeaderUsername controlla se l'utente è collegato e setta il nome utente nella barra laterale con quello inserito al momento della registrazione.
     * Se l'untente è collegato tramite modalità Guest tale nome nella barra laterale sarà settato a Guest.
     */
    private void setHeaderUsername() {

        if (isLogged) {
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
        } else {
            header_username.setText(R.string.guest);
        }
    }

    /**
     * setup inizializza i listener delle varie componenti dell'activity main.
     */
    private void setup() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            replaceFragment(new News());
            textTitle.setText(R.string.news);
        }
        imageMenu.setOnClickListener(this);
        navigationView.setItemIconTintList(null);
    }


    /**
     * initView collega le variabili della classe MainActivity agli elementi contenuti nel file xml colegata ad essa che formano l'interfaccia grafica attraverso la funzione findViewById
     */
    private void initview() {
        drawerLayout = findViewById(R.id.drawerLayout);
        progressBar = findViewById(R.id.progress_bar);
        textTitle = findViewById(R.id.menu_title);
        imageMenu = findViewById(R.id.menu_nav);
        toolbar = findViewById(R.id.layoutToolBar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        header_username = headerView.findViewById(R.id.header_fullname);

        if (!isLogged) {
            Menu menu = navigationView.getMenu();
            for (int menuItemIndex = 0; menuItemIndex < menu.size(); menuItemIndex++) {
                MenuItem menuItem = menu.getItem(menuItemIndex);
                if (menuItem.getItemId() == R.id.login_sign_up) {
                    menuItem.setVisible(true);
                }
                if (menuItem.getItemId() == R.id.logout) {
                    menuItem.setVisible(false);
                }
            }
        }
    }

    /**
     * checkAuth controlla se un utente è collegato.
     */
    private void checkAuth() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            isLogged = true;
        }
    }

    /**
     * onClick rappresenta come metodo l'unione di tutti i listeren dei vari componenti dell'activity
     * @param v Rappresenta quale elemento è stato cliccato dall'utente.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menu_nav) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    /**
     * Il metodo sostituisce il fragment in uso con un nuovo fragment aggiungendo quest'ultimo allo stack.
     * Tale stack permette all'utente di poter tornare indietro cliccando il pulsante indietro del tefono.
     * @param fragment è il fragment da sostituire
     */
    private void replaceFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame_layout, fragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    /**
     * onBackPressed permette di poter navigare a ritroso nell'applicazione settando opportunamente ciò che si vede sullo schermo con ciò che è selezionato nella barra di navigazione.
     * Nel caso l'utente si trovi nella schermata iniziale viene viusalizzato un messaggio nel quale si chiede all'utente se vuole uscire dall'app oppure no.
     */
    @Override
    public void onBackPressed() {

        int fragments = getSupportFragmentManager().getBackStackEntryCount();

        toolbar.getMenu().clear();

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

            if (fragment instanceof News && bottomNavigationView.getSelectedItemId() != R.id.news) {
                textTitle.setText(R.string.news);
                MenuItem menuItem = menu.getItem(0);
                menuItem.setChecked(true);
            }
            if (fragment instanceof Events && bottomNavigationView.getSelectedItemId() != R.id.events) {
                textTitle.setText(R.string.events);
                MenuItem menuItem = menu.getItem(1);
                menuItem.setChecked(true);
            }
            if (fragment instanceof Places && bottomNavigationView.getSelectedItemId() != R.id.places) {
                textTitle.setText(R.string.place);
                MenuItem menuItem = menu.getItem(2);
                menuItem.setChecked(true);
            }
            if (fragment instanceof Maps && bottomNavigationView.getSelectedItemId() != R.id.map) {
                textTitle.setText(R.string.map);
                MenuItem menuItem = menu.getItem(3);
                menuItem.setChecked(true);
            }
        }
    }
    /**
     * logoutUser permette all'utente di scollegarsi dall'applicazione permettendo ad un altro utente di poter utilizzare l'app.
     */
    private void logoutUser() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signOut();
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    /**
     * onNavigationItemSelected permette la gestione degli eventi sugli elementi di navigazione
     * @param item Rappresenta quale elemento ha scatenato l'evento.
     * @return Ritrona true per segnalare che operazione è avvenuta con successo.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        toolbar.getMenu().clear();
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        Fragment fragment = fragmentList.get(fragmentList.size() - 1);

        if (item.getItemId() == R.id.news && !item.isChecked()) {
            replaceFragment(new News());
            textTitle.setText(R.string.news);
        } else if (item.getItemId() == R.id.events && !item.isChecked()) {
            replaceFragment(new Events());
            textTitle.setText(R.string.events);
        } else if (item.getItemId() == R.id.places && !item.isChecked()) {
            replaceFragment(new Places());
            textTitle.setText(R.string.place);
        } else if (item.getItemId() == R.id.map && !item.isChecked()) {
            replaceFragment(new Maps());
            textTitle.setText(R.string.map);
        } else if (item.getItemId() == R.id.profile) {
            if (!(fragment instanceof Profile)) {
                if (isLogged) {
                    replaceFragment(new Profile());
                    textTitle.setText(Profile.class.getSimpleName());
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    Toast.makeText(this,
                            "You need to be logged in to access your profile",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                drawerLayout.closeDrawer(GravityCompat.START);
            }

        } else if (item.getItemId() == R.id.login_sign_up) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (item.getItemId() == R.id.faq) {
            if (!(fragment instanceof FAQ)) {
                replaceFragment(new FAQ());
                textTitle.setText(FAQ.class.getSimpleName());
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        } else if (item.getItemId() == R.id.info) {
            if (!(fragment instanceof Info)) {
                replaceFragment(new Info());
                textTitle.setText(Info.class.getSimpleName());
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        } else if (item.getItemId() == R.id.guide) {
            startActivity(new Intent(this, GuideActivity.class));
        } else if (item.getItemId() == R.id.logout) {
            logoutUser();
        }

        return true;
    }


}
