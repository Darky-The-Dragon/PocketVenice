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
import com.progetto_ingegneria.pocketvenice.R;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    protected FirebaseAuth mAuth;
    protected TextView textViewSubmit, textViewLogin;
    protected EditText editTextEmail;
    protected ProgressBar progressBar;

    /**
     * Questo metodo crea l'attività di ResetPassword, collegando il file xml contenente la struttura grafica al resto del codice.
     * @param savedInstanceState Usato per salvare uno stato dell'istanza dell'app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initView();

    }
    /**
     * initView collega le variabili della classe ResetPasswordActivity agli elementi contenuti nel file xml colegata ad essa che formano l'interfaccia grafica attraverso la funzione findViewById
     */
    private void initView() {
        editTextEmail = findViewById(R.id.email);
        textViewSubmit = findViewById(R.id.submit);
        textViewSubmit.setOnClickListener(this);
        textViewLogin = findViewById(R.id.login);
        textViewLogin.setOnClickListener(this);
        progressBar = findViewById(R.id.progress_bar);
    }

    /**
     * onClick rappresenta come metodo l'unione di tutti i listeren dei vari componenti dell'activity
     * @param v Rappresenta quale elemento è stato cliccato dall'utente.
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.submit) {
            resetPassword();
        } else if (v.getId() == R.id.login) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    /**
     * resetPassword permette all'utente di resettale la propria password nel caso non dovesse più ricordarla.
     */
    private void resetPassword() {

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