package com.progetto_ingegneria.pocketvenice.Drawer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }
}