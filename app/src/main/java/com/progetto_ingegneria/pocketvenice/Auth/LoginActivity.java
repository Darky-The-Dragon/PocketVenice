package com.progetto_ingegneria.pocketvenice.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.progetto_ingegneria.pocketvenice.MainActivity;
import com.progetto_ingegneria.pocketvenice.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView textViewLogin, textViewRegister, textViewForgotPassword;
    protected EditText editTextUsername, editTextPassword;
    protected ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);

        progressBar = findViewById(R.id.progress_bar);

        textViewLogin = findViewById(R.id.login);
        textViewLogin.setOnClickListener(this);
        textViewRegister = findViewById(R.id.register);
        textViewRegister.setOnClickListener(this);
        textViewForgotPassword = findViewById(R.id.forgotPassword);
        textViewForgotPassword.setOnClickListener(this);

        if (mAuth.getCurrentUser() != null) {
            progressBar.setVisibility(View.VISIBLE);
            emailVerification();
        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.login) {
            authenticateUser();
        } else if (v.getId() == R.id.register) {
            startActivity(new Intent(this, RegisterActivity.class));
        } else if (v.getId() == R.id.forgotPassword) {
            startActivity(new Intent(this, ResetPasswordActivity.class));
        }
    }

    private void authenticateUser() {
        String email = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextUsername.setError("Email field can't be empty!");
            editTextUsername.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextUsername.setError("Please provide a valid email!");
            editTextUsername.requestFocus();
        } else if (password.isEmpty()) {
            editTextPassword.setError("Password field can't be empty!");
            editTextPassword.requestFocus();
        } else if (password.length() < 6) {
            editTextPassword.setError("The password must be at least 6 characters long!");
            editTextPassword.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            emailVerification();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this,
                                    "Wrong Credentials. Invalid Username or Password.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void emailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        if (user.isEmailVerified()) {
            showMainActivity();
        } else {
            progressBar.setVisibility(View.GONE);
            user.sendEmailVerification();
            Toast.makeText(LoginActivity.this,
                    "Verify your email address first",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showMainActivity() {
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}