package com.progetto_ingegneria.pocketvenice.LateralNavbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.progetto_ingegneria.pocketvenice.Auth.User;
import com.progetto_ingegneria.pocketvenice.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private TextView titleWelcome, fullName, email, phone, age;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;


    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        titleWelcome = view.findViewById(R.id.show_Welcome);
        fullName = view.findViewById(R.id.show_full_name);
        email = view.findViewById(R.id.show_email);
        phone = view.findViewById(R.id.show_phone);
        age = view.findViewById(R.id.show_age);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());


        //manca guest profile setting
        if (firebaseUser == null) {
            Toast.makeText(inflater.getContext(), "You are not logged", Toast.LENGTH_SHORT).show();
        } else {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    fullName.setText(user.getFullName());
                    titleWelcome.setText("Welcome " + user.getFullName());
                    email.setText(user.getEmail());
                    phone.setText(user.getMobile());
                    age.setText(user.getAge());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        //Inflate the layout for this fragment*/
        return view;
    }


}