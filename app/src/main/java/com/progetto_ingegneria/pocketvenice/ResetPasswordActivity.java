package com.progetto_ingegneria.pocketvenice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewSubmit, textViewLogin;
    private EditText editTextEmail;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.email);

        progressBar = findViewById(R.id.progress_bar);

        textViewSubmit = findViewById(R.id.submit);
        textViewSubmit.setOnClickListener(this);
        textViewLogin = findViewById((R.id.login));
        textViewLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                resetPassword();
                break;
            case R.id.login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    private void resetPassword () {

        String email = editTextEmail.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email field can't be empty!");
            editTextEmail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide a valid email!");
            editTextEmail.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ResetPasswordActivity.this,
                            "Check your email to reset your password!",
                            Toast.LENGTH_LONG).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ResetPasswordActivity.this,
                            "Something went wrong! Try again!",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}