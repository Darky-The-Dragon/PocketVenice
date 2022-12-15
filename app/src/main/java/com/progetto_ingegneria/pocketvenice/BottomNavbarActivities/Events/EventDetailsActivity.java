package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Model.Event;
import com.progetto_ingegneria.pocketvenice.R;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class EventDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView title, address, fromDate, toDate, fromHour, toHour, description, addEventBtn;
    protected ImageView imgEvent;
    protected String mTitle, mAddress, mFromDate, mToDate, mFromHour, mToHour, mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        initView();
        loadEventData();
    }

    private void initView() {
        imgEvent = findViewById(R.id.item_event_img);
        title = findViewById(R.id.item_event_title);
        address = findViewById(R.id.item_event_address);
        fromDate = findViewById(R.id.item_event_fromDate);
        toDate = findViewById(R.id.item_event_toDate);
        fromHour = findViewById(R.id.item_event_fromHour);
        toHour = findViewById(R.id.item_event_toHour);
        description = findViewById(R.id.details_event_description);
        addEventBtn = findViewById(R.id.details_event_addEvent);
        addEventBtn.setOnClickListener(this);
    }

    private void loadEventData() {
        Event item = (Event) getIntent().getExtras().getSerializable("eventObject");

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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.details_event_addEvent) {

            getCalendarPermission();

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String startDate = mFromDate.replace("/", "-") + " " + mFromHour + ":00:00";
            LocalDateTime start = LocalDateTime.parse(startDate, format);
            String endDate = mToDate.replace("/", "-") + " " + mToHour + ":00:00";
            LocalDateTime end = LocalDateTime.parse(endDate, format);

            // Bug nel calcolare il tempo
            long startMillis = start.atZone(ZoneId.of("Europe/Rome")).toInstant().toEpochMilli();
            long endMillis = end.atZone(ZoneId.of("Europe/Rome")).toInstant().toEpochMilli();

            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setData(CalendarContract.Events.CONTENT_URI);
            intent.putExtra(CalendarContract.Events.CALENDAR_ID, 1);
            intent.putExtra(CalendarContract.Events.TITLE, mTitle);
            intent.putExtra(CalendarContract.Events.DESCRIPTION, "Time" + mFromHour + " " + mToHour);
            intent.putExtra(CalendarContract.Events.DTSTART, startMillis);
            intent.putExtra(CalendarContract.Events.DTEND, endMillis);
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, mAddress);
            intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, "Europe/Rome");

            startActivity(intent);
        }
    }

    private void getCalendarPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, 1);
        }
    }
}