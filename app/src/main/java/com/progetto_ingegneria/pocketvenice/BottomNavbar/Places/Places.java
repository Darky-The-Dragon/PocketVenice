package com.progetto_ingegneria.pocketvenice.BottomNavbar.Places;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Places.Adapter.PlaceAdapter;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Places.Listeners.PlaceCallback;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Places.Model.Place;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Utility.Transition;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.ArrayList;
import java.util.List;

public class Places extends Fragment implements PlaceCallback {

    protected ProgressBar progressBar;
    protected RecyclerView recyclerView;
    protected DatabaseReference database;
    protected PlaceAdapter placeAdapter;
    protected List<Place> placesData;

    protected View view;

    public Places() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_places, container, false);

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

        Fragment placesDeatails = PlacesDetails.newInstance(placesData.get(pos));

        placesDeatails.setSharedElementEnterTransition(new Transition());
        placesDeatails.setEnterTransition(new Fade());
        placesDeatails.setSharedElementReturnTransition(new Transition());
        placesDeatails.setExitTransition(new Fade());

        ViewCompat.setTransitionName(imgContainer, "placeContainerTN");
        ViewCompat.setTransitionName(imgPlace, "placeTN");
        ViewCompat.setTransitionName(title, "placeTitleTN");
        ViewCompat.setTransitionName(address, "placeAddressTN");
        ViewCompat.setTransitionName(score, "placeScoreTN");
        ViewCompat.setTransitionName(ratingBar, "placeRateTN");

        getParentFragmentManager().beginTransaction()
                .addSharedElement(imgContainer, "placeContainerTN")
                .addSharedElement(imgPlace, "placeTN")
                .addSharedElement(title, "placeTitleTN")
                .addSharedElement(address, "placeAddressTN")
                .addSharedElement(score, "placeScoreTN")
                .addSharedElement(ratingBar, "placeRateTN")
                .replace(R.id.main_frame_layout, placesDeatails)
                .addToBackStack(null)
                .commit();

    }
}