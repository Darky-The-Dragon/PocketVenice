package com.progetto_ingegneria.pocketvenice.Auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.progetto_ingegneria.pocketvenice.MainActivity;
import com.progetto_ingegneria.pocketvenice.R;
/**
 * La classe si collega al database per poter effettuare il login di un utente dopo aver effettuato i controlli necessari.
 * E' possibile fare la registrazione nel caso si volgia creare un nuovo utente.
 * E' possibile fare il login anche come utente guest
 * Alla fine l'activity lancia la mainActivity
 * @see RegisterActivity
 * RegisterActivity
 * @see MainActivity
 * MainActivity
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView textViewLogin, textViewRegister, textViewGuest, textViewForgotPassword;
    protected EditText editTextUsername, editTextPassword;
    protected ImageView imageViewShowHidePassword;
    protected ProgressBar progressBar;

    private FirebaseAuth mAuth;
    /**
     * @param savedInstanceState salva i dati di stato dell'applicazione.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        initView();
        setGuide();
        checkLogin();

    }

    private void setGuide() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Guide", MODE_PRIVATE);
        if (!pref.contains("isIntroOpened")) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isIntroOpened", true);
            editor.apply();
        }
    }

    private void checkLogin() {
        if (mAuth.getCurrentUser() != null) {
            progressBar.setVisibility(View.VISIBLE);
            emailVerification();
        }
    }

    private void initView() {
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress_bar);
        textViewLogin = findViewById(R.id.login);
        textViewLogin.setOnClickListener(this);
        textViewRegister = findViewById(R.id.register);
        textViewRegister.setOnClickListener(this);
        textViewGuest = findViewById(R.id.guest);
        textViewGuest.setOnClickListener(this);
        textViewForgotPassword = findViewById(R.id.forgotPassword);
        textViewForgotPassword.setOnClickListener(this);
        imageViewShowHidePassword = findViewById(R.id.show_hide_password);
        imageViewShowHidePassword.setOnClickListener(this);
    }
    /**
     * @param v Rappresenta quale elemento ha scatento l'evento.
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.show_hide_password) {
            showHidePassword();
        } else if (v.getId() == R.id.login) {
            authenticateUser();
        } else if (v.getId() == R.id.register) {
            startActivity(new Intent(this, RegisterActivity.class));
        } else if (v.getId() == R.id.guest) {
            showMainActivity();
        } else if (v.getId() == R.id.forgotPassword) {
            startActivity(new Intent(this, ResetPasswordActivity.class));
        }
    }

    private void showHidePassword() {

        if (editTextPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            //Show Password
            editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //Hide Password
            editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
    }
}