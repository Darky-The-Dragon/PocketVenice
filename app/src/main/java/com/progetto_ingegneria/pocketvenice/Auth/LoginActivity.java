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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView textViewLogin, textViewRegister, textViewGuest, textViewForgotPassword;
    protected EditText editTextUsername, editTextPassword;
    protected ImageView imageViewShowHidePassword;
    protected ProgressBar progressBar;
    private FirebaseAuth mAuth;

    /**
     * Questo metodo crea l'attività di Login, collegando il file xml contenente la struttura grafica al resto del codice.
     * @param savedInstanceState Usato per salvare uno stato dell'istanza dell'app.
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

    /**
     * setGuide cerca sul dispositivo se esiste in memoria un booleano che rappresenta il fatto che la guida dell'applicazione è stata visualizzata.
     * Se tale booleano non esiste viene salvato in memoria, altrimenti non viene modificato il contenuto di tale valore nella meomria interna.
     */
    private void setGuide() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Guide", MODE_PRIVATE);
        if (!pref.contains("isIntroOpened")) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isIntroOpened", true);
            editor.apply();
        }
    }

    /**
     * checkLogin verifica, dopo aver controllato se un utente è loggato, se la mail associata a quell'account è verificata oopure no chiamando oppurtune funzioni ultiriori che effettuano tale controllo.
     */
    private void checkLogin() {
        if (mAuth.getCurrentUser() != null) {
            progressBar.setVisibility(View.VISIBLE);
            emailVerification();
        }
    }

    /**
     * initView collega le variabili della classe LoginActivity agli elementi contenuti nel file xml colegata ad essa che formano l'interfaccia grafica attraverso la funzione findViewById
     */
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
     * onClick rappresenta come metodo l'unione di tutti i listeren dei vari componenti dell'activity
     * @param v Rappresenta quale elemento è stato cliccato dall'utente.
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

    /**
     * showHidePassword permente all'utente di visualizzare in chiaro il contenuto della password nel caso non si ricordasse quello che è stato digitato, così come permette di effettuare l'opposto trasformando il contenuto della password in chiaro in un contenuto non leggibile.
     */
    private void showHidePassword() {

        if (editTextPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            //Show Password
            editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //Hide Password
            editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    /**
     * authenticateUser controlla che i dati forniti dall'utente rispettino determinate proprietà.
     * Se tali proprietà sono state tutte verificate viene contattato il database cercando se i dati inseriti dall'utente corrispondo a dati reali.
     * Se i dati sono presenti nel database viene effettuato il controllo sulla verifica della mail da parte dell'utente.
     */
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

    /**
     * emailVerification verifica se la mail inserita dall'utente sia stata verificata in modo da evitare che gli utenti inseriscano mail non vere.
     * Se la mail è verificata l'utente accede a tutte le funzionalità interne dell'applicazione, altrimenti viene impedito all'utente di proseguire finche non verifichi la mail.
     */
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

    /**
     * showMainActivity permette all'utente di accedere alle funzionalità dell'app.
     */
    private void showMainActivity() {
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(this, MainActivity.class));
    }
}