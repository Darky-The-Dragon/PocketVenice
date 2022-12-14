package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Listeners;

import android.widget.ImageView;
import android.widget.TextView;

public interface EventCallback {

    void onEventItemClick(int pos,
                          ImageView imgContainer,
                          ImageView imgEvent,
                          TextView title,
                          TextView address,
                          TextView fromDate,
                          TextView hyphen,
                          TextView toDate);
}
