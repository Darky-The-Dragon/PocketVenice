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


public class Profile extends Fragment implements View.OnClickListener {

    protected TextView titleWelcome, fullName, email, phone, birthdate, editBtn;
    protected FirebaseUser firebaseUser;
    protected DatabaseReference databaseReference;
    protected View view;

    /**
     * Costruttore vuoto necessario per poter effeturare la costruzione di un nuovo fragment.
     */
    public Profile() {
        // Required empty public constructor
    }
    /**
     * @return Ritorna la vista del fragment nella quale verranno visuallizzate le informazioni dell'utente
     */
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
    /**
     * loadDatabase scarica dal database le informazioni riguardati l'utente attualmente loggato.
     */
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
    /**
     * initDatabase inizializza le risorse necessarie per effettuare download/upload di informazioni dal/al database.
     */
    private void initDatabase() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
    }
    /**
     * initView collega le variabili della classe Profile agli elementi contenuti nel file xml colegata ad essa che formano l'interfaccia grafica attraverso la funzione findViewById
     */
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
     * onClick rappresenta il lister che permette all'utente di caricare il frammento EditUserData che permette di modifcare le informazioni dell'utente.
     * @param v Rappresenta quale elemento ha scatenato l'evento
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_frame_layout, new EditUserData())
                    .addToBackStack(null)
                    .commit();
        }
    }
}