package com.progetto_ingegneria.pocketvenice.BottomNavbar.Events.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto_ingegneria.pocketvenice.R;

/**
 * Permette di creare oggetti di tipo EventViewHolder.
 */
public class EventViewHolder extends RecyclerView.ViewHolder {

    public TextView title, address, fromDate, hyphen, toDate, fromHour, hyphen1, toHour;
    public ImageView imgEvent, imgContainer;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        imgContainer = itemView.findViewById(R.id.item_event_container_img);
        imgEvent = itemView.findViewById(R.id.item_event_img);
        title = itemView.findViewById(R.id.item_event_title);
        address = itemView.findViewById(R.id.item_event_address);
        fromDate = itemView.findViewById(R.id.item_event_fromDate);
        hyphen = itemView.findViewById(R.id.hyphen);
        toDate = itemView.findViewById(R.id.item_event_toDate);
    }
}