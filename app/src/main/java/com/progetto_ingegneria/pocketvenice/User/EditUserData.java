package com.progetto_ingegneria.pocketvenice.User;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.progetto_ingegneria.pocketvenice.Auth.LoginActivity;
import com.progetto_ingegneria.pocketvenice.LateralNavbar.Profile;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.Calendar;

public class EditUserData extends Fragment implements View.OnClickListener {

    protected EditText editTextFullName, editTextEmail, editTextMobile, editTextPassword;
    protected TextView TextViewBirthdate, cancelBtn, submitBtn;
    protected ImageView imageViewShowHidePassword;
    protected String fullName, age, email, mobile, password;
    protected FirebaseUser firebaseUser;
    protected DatabaseReference databaseReference;
    protected ProgressBar progressBar;
    protected User user;
    protected View view;

    public EditUserData() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_user_data, container, false);

        initView();
        initDatabase();
        loadDatabase();

        return view;
    }

    private void loadDatabase() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                assert user != null;
                editTextFullName.setHint(user.getFullName());
                editTextEmail.setHint(user.getEmail());
                editTextMobile.setHint(user.getMobile());
                TextViewBirthdate.setHint(user.getBirthdate());
                editTextPassword.setHint(R.string.default_password);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initDatabase() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
    }

    private void initView() {
        editTextFullName = view.findViewById(R.id.edit_full_name);
        editTextEmail = view.findViewById(R.id.edit_email);
        editTextMobile = view.findViewById(R.id.edit_phone);
        TextViewBirthdate = view.findViewById(R.id.edit_birthdate);
        TextViewBirthdate.setOnClickListener(this);
        editTextPassword = view.findViewById(R.id.password);
        imageViewShowHidePassword = view.findViewById(R.id.show_hide_password);
        imageViewShowHidePassword.setOnClickListener(this);
        cancelBtn = view.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(this);
        submitBtn = view.findViewById(R.id.submit);
        submitBtn.setOnClickListener(this);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.show_hide_password) {
            showHidePassword(editTextPassword);
        } else if (v.getId() == R.id.cancel) {
            loadProfileFragment();
        } else if (v.getId() == R.id.submit) {
            submitNewUserInfo();
        } else if (v.getId() == R.id.edit_birthdate) {

            final Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view, year1, month1, day1) -> {
                        month1++;
                        String date;
                        date = day1 + "/" + month1 + "/" + year1;
                        TextViewBirthdate.setText(date);
                    }, year, month, day);
            datePickerDialog.show();
            TextViewBirthdate.setError(null);
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

    private void submitNewUserInfo() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        fullName = editTextFullName.getText().toString().trim();
        age = TextViewBirthdate.getText().toString().trim();
        email = editTextEmail.getText().toString().toLowerCase().trim();
        mobile = editTextMobile.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if (!fullName.isEmpty() && fullName.length() < 3) {
            editTextFullName.setError("Your new full name can't be under 3 characters long");
            editTextFullName.requestFocus();
        } else if (fullName.equals(user.getFullName())) {
            editTextFullName.setError("Your new full name can't be the same as your previous one");
            editTextFullName.requestFocus();
        } else if (!age.isEmpty() && check_Age() < 14) {
            TextViewBirthdate.setError("You can't be older less than 14 year old");
            TextViewBirthdate.requestFocus();
        } else if (age.equals(user.getBirthdate())) {
            TextViewBirthdate.setError("Your new birthday date can't be the same as your previous one");
            TextViewBirthdate.requestFocus();
        } else if (!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide a valid email!");
            editTextEmail.requestFocus();
        } else if (email.equals(user.getEmail())) {
            editTextEmail.setError("Your new email can't be the same as your previous one");
            editTextEmail.requestFocus();
        } else if (!mobile.isEmpty() && !Patterns.PHONE.matcher(mobile).matches()) {
            editTextMobile.setError("Please provide a valid phone number!");
            editTextMobile.requestFocus();
        } else if (mobile.equals(user.getMobile())) {
            editTextMobile.setError("Your new phone number can't be the same as your previous one");
            editTextMobile.requestFocus();
        } else if (password.isEmpty()) {
            editTextPassword.setError("You must provide your currrent password to apply these changes!");
            editTextPassword.requestFocus();
        } else if (password.length() < 6) {
            editTextPassword.setError("The password can't be shorter than 6 characters long!");
            editTextPassword.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(user.getEmail(), password)
                    .addOnCompleteListener(requireActivity(), task -> {
                        if (task.isSuccessful()) {
                            boolean email_changed = false;

                            if (!fullName.isEmpty() && !fullName.equals(user.getFullName())) {
                                databaseReference.child("fullName").setValue(fullName);
                            }
                            if (!age.isEmpty() && !age.equals(user.getBirthdate())) {
                                databaseReference.child("age").setValue(age);
                            }
                            if (!email.isEmpty() && !email.equals(user.getEmail())) {
                                databaseReference.child("email").setValue(email);
                                firebaseUser.updateEmail(email);
                                email_changed = true;
                            }
                            if (!mobile.isEmpty() && !mobile.equals(user.getMobile())) {
                                databaseReference.child("mobile").setValue(mobile);
                            }
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(),
                                    "User profile updated successfully",
                                    Toast.LENGTH_SHORT).show();

                            if (email_changed) {
                                logoutUser();
                            } else {
                                loadProfileFragment();
                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(),
                                    "Wrong Password. Please check again and retry.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private int check_Age() {

        String[] start_string = age.split("/");
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

    private void logoutUser() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signOut();
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(),
                "Please login again to verify your identity",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    private void loadProfileFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.main_frame_layout, new Profile())
                .addToBackStack(null)
                .commit();
    }
}