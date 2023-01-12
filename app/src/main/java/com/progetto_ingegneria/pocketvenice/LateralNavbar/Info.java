package com.progetto_ingegneria.pocketvenice.LateralNavbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.progetto_ingegneria.pocketvenice.R;


public class Info extends Fragment {
    /**
     * Costruttore vuoto necessario per poter effeturare la costruzione di un nuovo fragment.
     */
    public Info() {
        // Required empty public constructor
    }
    /**
     * @return Ritorna la vista del fragment nella quale verranno visualizzati i nomi di coloro che hanno contribuito a creare l'applicazione Pocket Venice.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        TextView member1, member2, member3, member4, member5, appName;

        appName = view.findViewById(R.id.show_AppName);
        member1 = view.findViewById(R.id.show_member_1);
        member2 = view.findViewById(R.id.show_member_2);
        member3 = view.findViewById(R.id.show_member_3);
        member4 = view.findViewById(R.id.show_member_4);
        member5 = view.findViewById(R.id.show_member_5);

        appName.setText(R.string.app_name);
        member3.setText(R.string.Nicolo);
        member2.setText(R.string.Leonardo);
        member4.setText(R.string.Beatrice);
        member1.setText(R.string.Maisha);
        member5.setText(R.string.Darky);

        // Inflate the layout for this fragment
        return view;
    }

}