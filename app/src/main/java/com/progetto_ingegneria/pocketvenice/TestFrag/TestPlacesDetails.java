package com.progetto_ingegneria.pocketvenice.TestFrag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places.Model.Place;
import com.progetto_ingegneria.pocketvenice.R;


public class TestPlacesDetails extends Fragment {


    private static final String DETAILS = "param1";
    protected TextView title, district, address, description;
    protected ImageView imgPlace;
    protected RatingBar ratingBar;
    protected String mTitle, mAddress, mDistrict, mDescription;
    protected float mRatingBar;
    protected Place place;
    protected View view;


    public TestPlacesDetails() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TestPlacesDetails newInstance(Place place) {
        TestPlacesDetails fragment = new TestPlacesDetails();
        Bundle args = new Bundle();
        args.putSerializable(DETAILS, place);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            place = (Place) getArguments().getSerializable(DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_test_places_details, container, false);

        initView();
        loadPlaceData();


        return view;
    }

    private void loadPlaceData() {

        mTitle = place.getTitle();
        mAddress = place.getAddress();
        mDistrict = place.getDistrict();
        mDescription = place.getDescription();
        mRatingBar = place.getRating();

        title.setText(mTitle);
        address.setText(mAddress);
        district.setText(mDistrict);
        description.setText(mDescription);
        ratingBar.setRating(mRatingBar);

        Glide.with(this)
                .load(place.getPhotoSrc())
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(imgPlace);
    }

    private void initView() {
        imgPlace = view.findViewById(R.id.item_place_img);
        title = view.findViewById(R.id.item_place_title);
        address = view.findViewById(R.id.item_place_address);
        district = view.findViewById(R.id.item_place_district);
        description = view.findViewById(R.id.details_places_description);
        ratingBar = view.findViewById(R.id.item_place_ratingbar);
    }
}