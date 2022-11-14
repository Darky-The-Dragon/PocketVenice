package com.progetto_ingegneria.pocketvenice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewLogout;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewLogout = findViewById(R.id.logout);
        textViewLogout.setOnClickListener(this);

        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logout){
            progressBar.setVisibility(View.VISIBLE);
            FirebaseAuth.getInstance().signOut();
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}