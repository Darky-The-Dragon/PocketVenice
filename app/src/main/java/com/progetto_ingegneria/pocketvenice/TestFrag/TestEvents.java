package com.progetto_ingegneria.pocketvenice.TestFrag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Adapter.EventAdapter;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Adapter.EventViewHolder;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Listeners.EventClickListener;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Model.Event;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.ArrayList;
import java.util.List;

public class TestEvents extends Fragment implements EventClickListener {

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
    public void onEventItemClick(EventViewHolder holder, int pos) {

        TestEventsDetails details = TestEventsDetails.newInstance(eventsData.get(pos));
        //details.setSharedElementEnterTransition(new TestEventsDetails());
        //details.setEnterTransition(new Fade());
        //setExitTransition(new Fade());
        //details.setSharedElementReturnTransition(new TestEventsDetails());

        //Toast.makeText(getContext(), "abcd", Toast.LENGTH_SHORT).show();

        getParentFragmentManager().beginTransaction().
                //addSharedElement(holder.imgContainer, holder.imgContainer.getTransitionName()).
                //addSharedElement(holder.imgEvent, holder.imgEvent.getTransitionName()).
                //addSharedElement(holder.title, holder.title.getTransitionName()).
                //addSharedElement(holder.address, holder.address.getTransitionName()).
                //addSharedElement(holder.fromDate, holder.fromDate.getTransitionName()).
                //addSharedElement(holder.hyphen, holder.hyphen.getTransitionName()).
                //addSharedElement(holder.toDate, holder.toDate.getTransitionName()).
                replace(R.id.main_frame_layout, details).
                addToBackStack(null).
                commit();

    }
}