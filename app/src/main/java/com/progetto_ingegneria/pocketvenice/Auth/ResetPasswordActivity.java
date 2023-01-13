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
/**
 * La classe resetPassword fornisce gli strumenti per poter modificare la password di un utente, nel caso venga smarrita o dimenticata.
 * Per poter cambiare la password la classe si collega al database, dopo aver controllato se la mail sia contenente nel database stesso.
 */
public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    protected FirebaseAuth mAuth;
    protected TextView textViewSubmit, textViewLogin;
    protected EditText editTextEmail;
    protected ProgressBar progressBar;
    /**
     * @param savedInstanceState salva i dati di stato dell'applicazione.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initView();

    }

    private void initView() {
        editTextEmail = findViewById(R.id.email);
        textViewSubmit = findViewById(R.id.submit);
        textViewSubmit.setOnClickListener(this);
        textViewLogin = findViewById(R.id.login);
        textViewLogin.setOnClickListener(this);
        progressBar = findViewById(R.id.progress_bar);
    }
    /**
     * @param v Rappresenta quale elemento ha scatento l'evento.
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.submit) {
            resetPassword();
        } else if (v.getId() == R.id.login) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

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