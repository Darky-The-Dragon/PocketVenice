package com.progetto_ingegneria.pocketvenice.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.progetto_ingegneria.pocketvenice.MainActivity;
import com.progetto_ingegneria.pocketvenice.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewLogo;
    private TextView textViewRegister;
    private EditText editTextFullName, editTextAge, editTextEmail, editTextMobile, editTextPassword,
            editTextConfirmPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Check if the user is already authenticated:
        mAuth = FirebaseAuth.getInstance();

        imageViewLogo = findViewById(R.id.logo);
        imageViewLogo.setOnClickListener(this);

        textViewRegister = findViewById(R.id.register);
        textViewRegister.setOnClickListener(this);

        editTextFullName = findViewById(R.id.full_name);
        editTextAge = findViewById(R.id.age);
        editTextEmail = findViewById(R.id.email);
        editTextMobile = findViewById(R.id.mobile);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirm_password);

        progressBar = findViewById(R.id.progress_bar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logo:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String email = editTextEmail.getText().toString().toLowerCase().trim();
        String mobile = editTextMobile.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName.setError("Full Name is required!");
            editTextFullName.requestFocus();
        } else if (age.isEmpty()) {
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
        } else if (email.isEmpty()) {
            editTextEmail.setError("An email is required!");
            editTextEmail.requestFocus();
        } else if (mobile.isEmpty()) {
            editTextMobile.setError("A phone number is needed for verification!");
            editTextMobile.requestFocus();
        } else if (password.isEmpty()) {
            editTextPassword.setError("A password is required!");
            editTextPassword.requestFocus();
        } else if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Confirming your password is required!");
            editTextConfirmPassword.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide a valid email!");
            editTextEmail.requestFocus();
        } else if (!Patterns.PHONE.matcher(mobile).matches()) {
            editTextEmail.setError("Please provide a valid phone number!");
            editTextEmail.requestFocus();
        } else if (fullName.length() < 3) {
            editTextPassword.setError("Your full name must be at least 3 characters long!");
            editTextPassword.requestFocus();
        } else if (Integer.parseInt(age) < 14) {
            editTextAge.setError("You must be at least 14 year old to register!");
            editTextAge.requestFocus();
        } else if (password.length() < 6) {
            editTextPassword.setError("The password must be at least 6 characters long!");
            editTextPassword.requestFocus();
        } else if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("The passwords don't match!");
            editTextConfirmPassword.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            User user = new User(fullName, age, email, mobile);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(task1 -> {

                                        if (task1.isSuccessful()) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegisterActivity.this,
                                                    "User has been registered successfully!",
                                                    Toast.LENGTH_LONG).show();
                                            showLoginActivity();
                                        } else {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegisterActivity.this,
                                                    "Failed to register! Try again",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this,
                                    "An error occurred while creating your account! Try again.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void showLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}