package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto_ingegneria.pocketvenice.R;

public class EventViewHolder extends RecyclerView.ViewHolder {

    public TextView title, address, fromDate, hyphen, toDate, fromHour, hyphen1, toHour;
    public ImageView imgEvent, imgContainer;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        imgContainer = (ImageView) itemView.findViewById(R.id.item_event_container_img);
        imgEvent = (ImageView) itemView.findViewById(R.id.item_event_img);
        title = (TextView) itemView.findViewById(R.id.item_event_title);
        address = (TextView) itemView.findViewById(R.id.item_event_address);
        fromDate = (TextView) itemView.findViewById(R.id.item_event_fromDate);
        hyphen = (TextView) itemView.findViewById(R.id.hyphen);
        toDate = (TextView) itemView.findViewById(R.id.item_event_toDate);
    }
}