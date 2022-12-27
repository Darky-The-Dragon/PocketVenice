package com.progetto_ingegneria.pocketvenice.BottomNavbar.Events.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Events.Listeners.EventClickListener;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Events.Model.Event;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private final Context context;
    private final List<Event> eventsData;
    private final EventClickListener listener;


    public EventAdapter(Context context, List<Event> eventsData, EventClickListener listener) {
        this.context = context;
        this.eventsData = eventsData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Event event = eventsData.get(position);

        holder.title.setText(event.getTitle());
        holder.address.setText(event.getAddress());
        holder.fromDate.setText(event.getFromDate());
        holder.toDate.setText(event.getToDate());

        Glide.with(holder.itemView.getContext())
                .load(eventsData.get(position).getPhotoSrc())
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(holder.imgEvent);

        holder.imgContainer.setOnClickListener(v -> listener.onEventItemClick(holder, position));
    }

    @Override
    public int getItemCount() {
        return eventsData.size();
    }

}

