package com.progetto_ingegneria.pocketvenice.TestFrag;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.Adapter.PlaceAdapter;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.Listeners.PlaceCallback;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.Model.Place;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.PlaceDetailsActivity;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.ArrayList;
import java.util.List;

public class TestPlaces extends Fragment implements PlaceCallback {

    protected ProgressBar progressBar;
    protected RecyclerView recyclerView;
    protected DatabaseReference database;
    protected PlaceAdapter placeAdapter;
    protected List<Place> placesData;
    protected View view;

    public TestPlaces() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_test_places, container, false);

        initViews();
        initDataPlaces();
        setupPlacesAdapter();

        return view;
    }

    private void setupPlacesAdapter() {
        placeAdapter = new PlaceAdapter(getContext(), placesData, this);
        recyclerView.setAdapter(placeAdapter);
    }

    private void initDataPlaces() {
        database = FirebaseDatabase.getInstance().getReference("Luoghi");

        // Database handler
        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Place place = dataSnapshot.getValue(Place.class);
                    placesData.add(place);

                }
                placeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initViews() {
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.rv_places);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        placesData = new ArrayList<>();
    }

    @Override
    public void onPlaceItemClick(int pos,
                                 ImageView imgContainer,
                                 ImageView imgPlace,
                                 TextView title,
                                 TextView address,
                                 TextView score,
                                 RatingBar ratingBar) {

        Fragment testPlacesDeatails = TestPlacesDetails.newInstance(placesData.get(pos));
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();

        Pair<View, String> p1 = Pair.create((View) imgContainer, "placeContainerTN");
        Pair<View, String> p2 = Pair.create((View) imgPlace, "placeTN");
        Pair<View, String> p3 = Pair.create((View) title, "placeTitleTN");
        Pair<View, String> p4 = Pair.create((View) address, "placeAddressTN");
        Pair<View, String> p5 = Pair.create((View) score, "placeScoreTN");
        Pair<View, String> p6 = Pair.create((View) ratingBar, "placeRateTN");

        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), p1, p2, p3, p4, p5, p6);

        ft.replace(R.id.main_frame_layout, testPlacesDeatails).addToBackStack(null).commit();

    }
}