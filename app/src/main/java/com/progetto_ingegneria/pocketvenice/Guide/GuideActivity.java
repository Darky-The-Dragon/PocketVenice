package com.progetto_ingegneria.pocketvenice.Guide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.progetto_ingegneria.pocketvenice.Guide.Adapter.GuideViewPagerAdapter;
import com.progetto_ingegneria.pocketvenice.MainActivity;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {

    protected ViewPager screenPager;
    protected GuideViewPagerAdapter guideViewPagerAdapter;
    protected TabLayout tabIndicator;
    protected TextView btnNext, btnGetStarted;
    protected List<ScreenItem> screenItemList = new ArrayList<>();
    protected int position = 0;
    protected Animation btnLoadAnimation, btnUnloadAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*check if guide is opened before or not
        if(restorePrefData()){
            Intent news = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(news);
            finish();
        }*/

        setFullScreen();
        initViews();
        initDataGuide();
        initAdapter();

    }

    private void setFullScreen() {
        //make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    private void initAdapter() {
        //setup view page adapter
        screenPager = findViewById(R.id.screen_pager);
        guideViewPagerAdapter = new GuideViewPagerAdapter(this, screenItemList);
        screenPager.setAdapter(guideViewPagerAdapter);

        //setup tablayaout
        tabIndicator.setupWithViewPager(screenPager);
    }

    private void initDataGuide() {
        //da rivedere per descrizioni & img
        screenItemList.add(new ScreenItem("News", "Le news ti permettono di rimanere aggiornato su quello che accade a Venezia e provincia.",
                R.drawable.news_icon));
        screenItemList.add(new ScreenItem("Events", "Quando non ricordi che appuntamenti importanti ci sono in città puoi visualizzare qeusta pagina " +
                "per rimanere aggiornato sugli eventi importanti che si svolgono in città.",
                R.drawable.event_icon));
        screenItemList.add(new ScreenItem("Places", "Se vuoi girare per la città e non sai cosa quali posti visitare puoi usare questo elenco di luoghi per scegliere cosa visitare con anche qualche accenno storico per il luogo scelto.",
                R.drawable.marker_icon));
        screenItemList.add(new ScreenItem("Maps", "Se non sai come arrivare dalla tua posizione ad un evento o luogo di visita puoi utilizzare la mappa integrata per conoscere il percorso che devi effettuare.",
                R.drawable.maps_icon));
    }

    private void initViews() {
        setContentView(R.layout.activity_guide);
        //init
        btnNext = findViewById(R.id.next_button);
        btnNext.setOnClickListener(this);
        btnGetStarted = findViewById(R.id.get_started_button);
        btnGetStarted.setOnClickListener(this);
        tabIndicator = findViewById(R.id.tab_indicator);
        tabIndicator.addOnTabSelectedListener(this);
        btnLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_load_anim);
        btnUnloadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_unload_anim);
    }

    /*
        private boolean restorePrefData() {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("Guide", MODE_PRIVATE);
            return pref.getBoolean("isIntroOpened", false);

        }

        private void savePefesData() {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("Guide", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isIntroOpened", true);
            editor.apply();
        }
    */
    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        btnGetStarted.setAnimation(btnLoadAnimation);
        tabIndicator.setVisibility(View.INVISIBLE);

    }

    private void unloadLastScreen() {
        btnNext.setVisibility(View.VISIBLE);
        if (position == screenItemList.size() - 1) {
            btnGetStarted.setAnimation(btnUnloadAnimation);
        }
        btnGetStarted.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_button) {
            position = screenPager.getCurrentItem();

            if (position < screenItemList.size()) {
                position++;
                screenPager.setCurrentItem(position);
            }
            if (position == screenItemList.size() - 1) {
                loadLastScreen();
            }
        } else if (v.getId() == R.id.get_started_button) {
            Intent news = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(news);
            //savePefesData();
            finish();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (position != screenPager.getCurrentItem()) {
            position = screenPager.getCurrentItem();
        }
        if (position == screenItemList.size() - 1) {
            loadLastScreen();
        } else {
            unloadLastScreen();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (position != screenPager.getCurrentItem()) {
            position = screenPager.getCurrentItem();
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        if (position != screenPager.getCurrentItem()) {
            position = screenPager.getCurrentItem();
        }
    }
}