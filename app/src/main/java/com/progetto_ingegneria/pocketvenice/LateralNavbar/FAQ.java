package com.progetto_ingegneria.pocketvenice.LateralNavbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.progetto_ingegneria.pocketvenice.R;


public class FAQ extends Fragment {
    /**
     * Costruttore vuoto necessario per poter effeturare la costruzione di un nuovo fragment.
     */
    public FAQ() {
        // Required empty public constructor
    }
    /**
     * @return Ritorna la vista del fragment nella quale verranno visualizzate le FAQ dell'applicazione
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.faq_fragment, container, false);
    }
}