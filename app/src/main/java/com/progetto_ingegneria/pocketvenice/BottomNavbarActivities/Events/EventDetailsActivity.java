package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Model.Event;
import com.progetto_ingegneria.pocketvenice.R;

public class EventDetailsActivity extends AppCompatActivity {

    protected TextView title, address, fromDate, toDate, fromHour, toHour, description;
    protected ImageView imgEvent;
    protected String mTitle, mAddress, mFromDate, mToDate, mFromHour, mToHour, mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        imgEvent = findViewById(R.id.item_event_img);
        title = findViewById(R.id.item_event_title);
        address = findViewById(R.id.item_event_address);
        fromDate = findViewById(R.id.item_event_fromDate);
        toDate = findViewById(R.id.item_event_toDate);
        fromHour = findViewById(R.id.item_event_fromHour);
        toHour = findViewById(R.id.item_event_toHour);
        description = findViewById(R.id.details_event_description);

        Event item = (Event) getIntent().getExtras().getSerializable("eventObject");

        loadEventData(item);
    }

    private void loadEventData(Event item) {
        mTitle = item.getTitle();
        mAddress = item.getAddress();
        mFromDate = item.getFromDate();
        mToDate = item.getToDate();
        mFromHour = item.getFromHour();
        mToHour = item.getToHour();
        mDescription = item.getDescription();


        title.setText(mTitle);
        address.setText(mAddress);
        fromDate.setText(mFromDate);
        toDate.setText(mToDate);
        fromHour.setText(mFromHour);
        toHour.setText(mToHour);
        description.setText(mDescription);

        Glide.with(this)
                .load(item.getPhotoSrc())
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(imgEvent);
    }
}