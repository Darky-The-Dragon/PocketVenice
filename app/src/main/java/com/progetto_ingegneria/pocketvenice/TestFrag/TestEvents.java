package com.progetto_ingegneria.pocketvenice.TestFrag;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Adapter.EventAdapter;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.EventDetailsActivity;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Listeners.EventCallback;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Model.Event;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.ArrayList;
import java.util.List;

public class TestEvents extends Fragment implements EventCallback {

    protected ProgressBar progressBar;
    protected RecyclerView recyclerView;
    protected DatabaseReference database;
    protected EventAdapter eventAdapter;
    protected List<Event> eventsData;
    protected View view;

    public TestEvents() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_test_events, container, false);

        initViews();
        initDataPlaces();
        setupEventsAdapter();

        return view;
    }

    private void setupEventsAdapter() {
        eventAdapter = new EventAdapter(getContext(), eventsData, this);
        recyclerView.setAdapter(eventAdapter);
    }

    private void initDataPlaces() {
        database = FirebaseDatabase.getInstance().getReference("Eventi");

        // Database handler
        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Event event = dataSnapshot.getValue(Event.class);
                    eventsData.add(event);

                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initViews() {

        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.rv_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        eventsData = new ArrayList<>();
    }

    @Override
    public void onEventItemClick(int pos,
                                 ImageView imgContainer,
                                 ImageView imgEvent,
                                 TextView title,
                                 TextView address,
                                 TextView fromDate,
                                 TextView hyphen,
                                 TextView toDate) {

        Fragment testEventsDeatails = TestEventsDeatails.newInstance(eventsData.get(pos));
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();


        Pair<View, String> p1 = Pair.create((View) imgContainer, "eventContainerTN");
        Pair<View, String> p2 = Pair.create((View) imgEvent, "eventTN");
        Pair<View, String> p3 = Pair.create((View) title, "eventTitleTN");
        Pair<View, String> p4 = Pair.create((View) address, "eventAddressTN");
        Pair<View, String> p5 = Pair.create((View) fromDate, "eventFromDateTN");
        Pair<View, String> p6 = Pair.create((View) hyphen, "eventHyphenTN");
        Pair<View, String> p7 = Pair.create((View) toDate, "eventToDateTN");

        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), p1, p2, p3, p4, p5, p6, p7);


        ft.replace(R.id.main_frame_layout, testEventsDeatails);
        ft.commit();
    }
}