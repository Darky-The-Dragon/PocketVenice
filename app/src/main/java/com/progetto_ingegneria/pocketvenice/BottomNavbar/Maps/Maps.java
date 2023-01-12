package com.progetto_ingegneria.pocketvenice.BottomNavbar.Maps;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.progetto_ingegneria.pocketvenice.R;

/**
 * Il fragment Maps fornisce gli strumenti per creare, visualizzare e utilizzare una mappa in stile google maps.
 * Può essere chiamata da PlaceDetails quando si cerca la posizione del luogo sulla mappa.
 * @see com.progetto_ingegneria.pocketvenice.BottomNavbar.Places.PlacesDetails
 * PlaceDetails
 */
public class Maps extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    protected static final String TAG = "Maps";
    protected static final int DEFAULT_ZOOM = 15;
    protected static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    protected static final String KEY_CAMERA_POSITION = "camera_position";      // Keys for storing activity state.
    protected static final String KEY_LOCATION = "location";                    // Keys for storing activity state.
    private static final String DETAILS = "param1";
    protected final LatLng defaultLocation = new LatLng(45.440975, 12.321038);  // A default location venezia santa lucia
    protected ProgressBar progressBar;
    protected Marker marker;
    protected EditText etCity;
    protected FloatingActionButton floatingActionButton;
    protected GoogleMap mMap;
    protected CameraPosition cameraPosition;
    protected FusedLocationProviderClient fusedLocationProviderClient;          // The entry point to the Fused Location Provider.
    protected boolean locationPermissionGranted;
    protected Location lastKnownLocation;           // The geographical location where the device is currently located. That is, the last-known location retrieved by the Fused Location Provider.
    protected View view;
    private String address = null;


    public Maps() {
        // Required empty public constructor
    }

    public static Maps newInstance(String address) {
        Maps fragment = new Maps();
        Bundle args = new Bundle(1);
        args.putString(DETAILS, address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            address = getArguments().getString(DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_maps, container, false);

        initView();
        getLocationPermission(savedInstanceState);

        return view;
    }

    private void initMaps(Bundle savedInstanceState) {

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void initView() {
        floatingActionButton = view.findViewById(R.id.fabSearchCity);
        floatingActionButton.setOnClickListener(this);
        etCity = view.findViewById(R.id.et_SearchFor);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabSearchCity) {
            address = etCity.getText().toString();
            set_pos();
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.mMap = googleMap;

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
        if (address != null) {
            set_pos();
        }
    }


    private void set_pos() {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            Address address_place = geocoder.getFromLocationName(address, 1).get(0);
            LatLng place = new LatLng(address_place.getLatitude(), address_place.getLongitude());
            if (marker != null) {
                marker.remove();
            }

            marker = mMap.addMarker(new MarkerOptions().position(place).title(address));


            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(place)
                    .zoom(DEFAULT_ZOOM)
                    .bearing(300)
                    .tilt(50)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        } catch (Exception e) {
            Toast.makeText(getContext(), "Hai inserito un indirizzo non valido", Toast.LENGTH_SHORT).show();
        }

    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        mMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission(Bundle savedInstanceState) {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
            initMaps(savedInstanceState);
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
}