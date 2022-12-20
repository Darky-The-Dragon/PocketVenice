package com.progetto_ingegneria.pocketvenice.Auth;

import android.content.Intent;
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
import com.google.firebase.database.FirebaseDatabase;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.Objects;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView imageViewShowHidePassword, imageViewShowHideConfirmPassword;
    protected TextView textViewRegister, textViewLogin;
    protected EditText editTextFullName, editTextAge, editTextEmail, editTextMobile, editTextPassword, editTextConfirmPassword;
    protected ProgressBar progressBar;
    protected FirebaseAuth mAuth;
    protected String fullName, age, email, mobile, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        textViewRegister = findViewById(R.id.register);
        textViewRegister.setOnClickListener(this);
        textViewLogin = findViewById(R.id.login);
        textViewLogin.setOnClickListener(this);

        editTextFullName = findViewById(R.id.full_name);
        editTextAge = findViewById(R.id.age);
        editTextMobile = findViewById(R.id.mobile);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirm_password);
        imageViewShowHidePassword = findViewById(R.id.show_hide_password);
        imageViewShowHidePassword.setOnClickListener(this);
        imageViewShowHideConfirmPassword = findViewById(R.id.show_hide_confirm_password);
        imageViewShowHideConfirmPassword.setOnClickListener(this);

        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.show_hide_password) {
            showHidePassword(editTextPassword);
        } else if (v.getId() == R.id.show_hide_confirm_password) {
            showHidePassword(editTextConfirmPassword);
        } else if (v.getId() == R.id.login) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (v.getId() == R.id.register) {
            registerUser();
        }
    }

    private void showHidePassword(EditText password) {

        if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            //Show Password
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //Hide Password
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void registerUser() {
        fullName = editTextFullName.getText().toString().trim();
        age = editTextAge.getText().toString().trim();
        email = editTextEmail.getText().toString().toLowerCase().trim();
        mobile = editTextMobile.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName.setError("Full Name is required!");
            editTextFullName.requestFocus();
        } else if (fullName.length() < 3) {
            editTextPassword.setError("Your full name must be at least 3 characters long!");
            editTextPassword.requestFocus();
        } else if (age.isEmpty()) {
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
        } else if (Integer.parseInt(age) < 14) {
            editTextAge.setError("You must be at least 14 year old to register!");
            editTextAge.requestFocus();
        } else if (mobile.isEmpty()) {
            editTextMobile.setError("A phone number is needed for verification!");
            editTextMobile.requestFocus();
        } else if (!Patterns.PHONE.matcher(mobile).matches()) {
            editTextEmail.setError("Please provide a valid phone number!");
            editTextEmail.requestFocus();
        } else if (email.isEmpty()) {
            editTextEmail.setError("An email is required!");
            editTextEmail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide a valid email!");
            editTextEmail.requestFocus();
        } else if (password.isEmpty()) {
            editTextPassword.setError("A password is required!");
            editTextPassword.requestFocus();
        } else if (password.length() < 6) {
            editTextPassword.setError("The password must be at least 6 characters long!");
            editTextPassword.requestFocus();
        } else if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Confirming your password is required!");
            editTextConfirmPassword.requestFocus();
        } else if (confirmPassword.length() < 6) {
            editTextConfirmPassword.setError("The password must be at least 6 characters long!");
            editTextConfirmPassword.requestFocus();
        } else if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("The passwords don't match!");
            editTextConfirmPassword.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            createNewUser();
        }
    }

    private void createNewUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        User user = new User(fullName, age, email, mobile);

                        FirebaseDatabase.getInstance().getReference("users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
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
                        editTextEmail.setError("An account is already associated to this email!");
                        editTextEmail.requestFocus();
                    }
                });
    }

    private void showLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}