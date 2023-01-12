package com.progetto_ingegneria.pocketvenice.LateralNavbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.progetto_ingegneria.pocketvenice.R;

/**
 * La classe crea la view FAQ che contengono le fast asked question contenute nel file fragment_FAQ.xml
 */
public class FAQ extends Fragment {

    public FAQ() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.faq_fragment, container, false);
    }
}