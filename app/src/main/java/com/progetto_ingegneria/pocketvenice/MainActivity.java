package com.progetto_ingegneria.pocketvenice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.progetto_ingegneria.pocketvenice.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewLogout;
    ProgressBar progressBar;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        replaceFragment(new News_Fragment());

        setContentView(R.layout.activity_main);
        textViewLogout = findViewById(R.id.login);
        textViewLogout.setOnClickListener(this);
        progressBar = findViewById(R.id.progress_bar);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.news:
                    replaceFragment(new News_Fragment());
                    break;
                case R.id.events:
                    replaceFragment(new Events_Fragment());
                    break;
                case R.id.place:
                    replaceFragment(new Place_Fragment());
                    break;
                case R.id.maps:
                    replaceFragment(new Map_Fragment());
                    break;
            }
            return true;
        });



    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            progressBar.setVisibility(View.VISIBLE);
            FirebaseAuth.getInstance().signOut();
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}