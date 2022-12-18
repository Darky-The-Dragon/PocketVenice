package com.progetto_ingegneria.pocketvenice.TestFrag;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Events.Model.Event;
import com.progetto_ingegneria.pocketvenice.R;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class TestEventsDeatails extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DETAILS = "param1";
    private Event event;
    protected View view;
    protected TextView title, address, fromDate, toDate, fromHour, toHour, description, addEventBtn;
    protected ImageView imgEvent, shareBtn;
    protected String mTitle, mAddress, mFromDate, mToDate, mFromHour, mToHour, mDescription;


    // TODO: Rename and change types of parameters

    public TestEventsDeatails() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TestEventsDeatails newInstance(Event event) {
        TestEventsDeatails fragment = new TestEventsDeatails();
        Bundle args = new Bundle(1);
        args.putSerializable(DETAILS, event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = (Event)getArguments().getSerializable(DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_test_events_deatails, container, false);

        initView();
        loadEventData();

        return view;
    }

    private void loadEventData() {
        mTitle = event.getTitle();
        mAddress = event.getAddress();
        mFromDate = event.getFromDate();
        mToDate = event.getToDate();
        mFromHour = event.getFromHour();
        mToHour = event.getToHour();
        mDescription = event.getDescription();

        title.setText(mTitle);
        address.setText(mAddress);
        fromDate.setText(mFromDate);
        toDate.setText(mToDate);
        fromHour.setText(mFromHour);
        toHour.setText(mToHour);
        description.setText(mDescription);

        Glide.with(this)
                .load(event.getPhotoSrc())
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(imgEvent);
    }

    private void initView() {
        imgEvent = view.findViewById(R.id.item_event_img);
        title = view.findViewById(R.id.item_event_title);
        address = view.findViewById(R.id.item_event_address);
        fromDate = view.findViewById(R.id.item_event_fromDate);
        toDate = view.findViewById(R.id.item_event_toDate);
        fromHour = view.findViewById(R.id.item_event_fromHour);
        toHour = view.findViewById(R.id.item_event_toHour);
        description = view.findViewById(R.id.details_event_description);
        shareBtn = view.findViewById(R.id.item_event_share);
        shareBtn.setOnClickListener(this);
        addEventBtn = view.findViewById(R.id.details_event_addEvent);
        addEventBtn.setOnClickListener(this);
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
        } else if (v.getId() == R.id.item_event_share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plan");
                i.putExtra(Intent.EXTRA_SUBJECT, mTitle);
                String body = mTitle + "\n" + mAddress + "\n" + mFromDate + " " + mToDate + "\n" + "\n" + "Shared from PocketVenice App" + "\n";
                i.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(i, "Share with: "));
            } catch (Exception e) {
                //Toast.makeText(this, "Something went wrong. Cannot share at this moment. Try again", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void getCalendarPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, 1);
        }
    }
}