package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Listeners.EventCallback;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Model.Event;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final Context context;
    private final List<Event> eventsData;
    private final EventCallback callback;


    public EventAdapter(Context context, List<Event> eventsData, EventCallback callback) {
        this.context = context;
        this.eventsData = eventsData;
        this.callback = callback;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        Event event = eventsData.get(position);

        holder.title.setText(event.getTitle());
        holder.address.setText(event.getAddress());
        holder.fromDate.setText(event.getFromDate());
        holder.toDate.setText(event.getToDate());

        Glide.with(holder.itemView.getContext())
                .load(eventsData.get(position).getPhotoSrc())
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(holder.imgEvent);
    }

    @Override
    public int getItemCount() {
        return eventsData.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        TextView title, address, fromDate, hyphen, toDate, fromHour, hyphen1, toHour;
        ImageView imgEvent, imgContainer;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            imgContainer = itemView.findViewById(R.id.item_event_container_img);
            imgEvent = itemView.findViewById(R.id.item_event_img);
            title = itemView.findViewById(R.id.item_event_title);
            address = itemView.findViewById(R.id.item_event_address);
            fromDate = itemView.findViewById(R.id.item_event_fromDate);
            hyphen = itemView.findViewById(R.id.hyphen);
            toDate = itemView.findViewById(R.id.item_event_toDate);

            itemView.setOnClickListener(v -> callback.onEventItemClick(getAdapterPosition(),
                    imgContainer,
                    imgEvent,
                    title,
                    address,
                    fromDate,
                    hyphen,
                    toDate));

        }
    }
}
