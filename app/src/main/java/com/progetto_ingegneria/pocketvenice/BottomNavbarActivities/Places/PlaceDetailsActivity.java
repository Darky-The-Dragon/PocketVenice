package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.Model.Place;
import com.progetto_ingegneria.pocketvenice.R;

public class PlaceDetailsActivity extends AppCompatActivity {

    protected TextView title, district, address, score, description;
    protected ImageView imgPlace;
    protected RatingBar ratingBar;
    protected String mTitle, mAddress, mDistrict, mDescription;
    protected float mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        imgPlace = findViewById(R.id.item_place_img);
        title = findViewById(R.id.item_place_title);
        address = findViewById(R.id.item_place_address);
        district = findViewById(R.id.item_place_district);
        description = findViewById(R.id.details_places_description);
        ratingBar = findViewById(R.id.ratingBar);

        Place item = (Place) getIntent().getExtras().getSerializable("placeObject");

        loadPlaceData(item);
    }

    private void loadPlaceData(Place item) {

        mTitle = item.getTitle();
        mAddress = item.getAddress();
        mDistrict = item.getDistrict();
        mDescription = item.getDescription();
        mRatingBar = item.getRating();

        title.setText(mTitle);
        address.setText(mAddress);
        district.setText(mDistrict);
        description.setText(mDescription);
        ratingBar.setRating(mRatingBar);

        Glide.with(this)
                .load(item.getPhotoSrc())
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(imgPlace);
    }
}