package com.progetto_ingegneria.pocketvenice.LateralNavbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.progetto_ingegneria.pocketvenice.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Info extends Fragment {

    public Info() {
        // Required empty public constructor
    }

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

        appName.setText("Pocket Venice");
        member3.setText("Bregantin Nicolò");
        member2.setText("Bilardi Leonardo");
        member4.setText("Spagnolo Beatrice");
        member1.setText("Bhuiyan Maisha Fahmida");
        member5.setText("Zotea Dumitru");



        // Inflate the layout for this fragment
        return view;
    }
}