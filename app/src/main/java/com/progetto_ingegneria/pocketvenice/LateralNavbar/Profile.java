package com.progetto_ingegneria.pocketvenice.LateralNavbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.progetto_ingegneria.pocketvenice.R;
import com.progetto_ingegneria.pocketvenice.User.EditUserData;
import com.progetto_ingegneria.pocketvenice.User.User;

/**
 * Il fragment profile fornisce le informazioni dell'utente, se loggato, e le visualizza in textView che vengono prese dal file fragment_profile.xml.
 * La classe fornisce anche il modo per
 * @see EditUserData
 *  cambiare i dati dell'utente
 */
public class Profile extends Fragment implements View.OnClickListener {

    protected TextView titleWelcome, fullName, email, phone, birthdate, editBtn;
    protected FirebaseUser firebaseUser;
    protected DatabaseReference databaseReference;
    protected View view;


    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initView();
        initDatabase();
        loadDatabase();

        //Inflate the layout for this fragment*/
        return view;
    }

    private void loadDatabase() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                fullName.setText(user.getFullName());
                titleWelcome.setText("Welcome " + user.getFullName());
                email.setText(user.getEmail());
                phone.setText(user.getMobile());
                birthdate.setText(user.getBirthdate());
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
        titleWelcome = view.findViewById(R.id.show_Welcome);
        fullName = view.findViewById(R.id.show_full_name);
        email = view.findViewById(R.id.show_email);
        phone = view.findViewById(R.id.show_phone);
        birthdate = view.findViewById(R.id.show_birthdate);
        editBtn = view.findViewById(R.id.edit);
        editBtn.setOnClickListener(this);
    }

    /**
     * @param v Rappresenta quale elemento ha scatento l'evento.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit) {
            loadEditUserDataFragment();
        }
    }

    private void loadEditUserDataFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.main_frame_layout, new EditUserData())
                .addToBackStack(null)
                .commit();
    }
}