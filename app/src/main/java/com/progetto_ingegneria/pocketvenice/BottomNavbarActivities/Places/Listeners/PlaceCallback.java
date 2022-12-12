package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.Listeners;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public interface PlaceCallback {

    void onPlaceItemClick(int pos,
                          ImageView imgContainer,
                          ImageView imgPlace,
                          TextView title,
                          TextView address,
                          TextView district,
                          TextView score,
                          RatingBar ratingBar);
}