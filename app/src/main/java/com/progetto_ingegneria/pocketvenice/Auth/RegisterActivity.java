package com.progetto_ingegneria.pocketvenice.Auth;

import android.app.DatePickerDialog;
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
import com.progetto_ingegneria.pocketvenice.User.User;

import java.util.Calendar;
import java.util.Objects;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView imageViewShowHidePassword, imageViewShowHideConfirmPassword;
    protected TextView textViewRegister, textViewLogin, age_tv;
    protected EditText editTextFullName, editTextEmail, editTextMobile, editTextPassword, editTextConfirmPassword;
    protected ProgressBar progressBar;
    protected FirebaseAuth mAuth;
    protected String fullName, birthdate, email, mobile, password, confirmPassword;

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
        age_tv = findViewById(R.id.birthdate);
        editTextMobile = findViewById(R.id.mobile);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirm_password);
        imageViewShowHidePassword = findViewById(R.id.show_hide_password);
        age_tv.setOnClickListener(this);
        imageViewShowHidePassword.setOnClickListener(this);
        imageViewShowHideConfirmPassword = findViewById(R.id.show_hide_confirm_password);
        imageViewShowHideConfirmPassword.setOnClickListener(this);
        progressBar = findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();
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
        } else if (v.getId() == R.id.birthdate) {

            final Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    RegisterActivity.this,
                    (view, year1, month1, day1) -> {
                        month1++;
                        String date;
                        date = day1 + "/" + month1 + "/" + year1;
                        age_tv.setText(date);
                    }, year, month, day);
            datePickerDialog.show();
            age_tv.setError(null);
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
        birthdate = age_tv.getText().toString().trim();
        email = editTextEmail.getText().toString().toLowerCase().trim();
        mobile = editTextMobile.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName.setError("Full Name is required!");
            editTextFullName.requestFocus();
        } else if (fullName.length() < 3) {
            editTextFullName.setError("Your full name must be at least 3 characters long!");
            editTextFullName.requestFocus();
        } else if (birthdate.equals("dd/MM/yyyy")) {
            age_tv.setError("Age is required!");
            age_tv.requestFocus();
        } else if (check_Age() < 14) {
            age_tv.setError("You must be at least 14 year old to register!");
            age_tv.requestFocus();
        } else if (mobile.isEmpty()) {
            editTextMobile.setError("A phone number is needed for verification!");
            editTextMobile.requestFocus();
        } else if (!Patterns.PHONE.matcher(mobile).matches()) {
            editTextMobile.setError("Please provide a valid phone number!");
            editTextMobile.requestFocus();
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

    private int check_Age() {

        String[] start_string = birthdate.split("/");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        //0 giorni, 1 mesi, 2 anni

        int diff = year - Integer.parseInt(start_string[2]);


        if (diff != 0 && month - Integer.parseInt(start_string[1]) == 0
                && day - Integer.parseInt(start_string[0]) < 0) {
            diff--;
        }
        return diff;

    }

    private void createNewUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        User user = new User(fullName, birthdate, email, mobile);

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