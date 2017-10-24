package com.cqrify.followme.fragment;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqrify.followme.R;
import com.cqrify.followme.model.helper.ConstantHelper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Jocke Ådén on 20/10/2017.
 */

public class FollowMeFragment extends Fragment {
    private static final String LOG_TAG = FollowMeFragment.class.getSimpleName();

    private MapView mMapView;
    private GoogleMap googleMap;

    private LocationManager locationManager;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    // Store LocationManager.GPS_PROVIDER or LocationManager.NETWORK_PROVIDER information
    private String provider_info;

    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;

    private Location lastLocation;
    private Location location;

    public static FollowMeFragment newInstance(){
        return new FollowMeFragment();
    }

    public FollowMeFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setupLocation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_followme, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try{
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch(ExceptionInInitializerError e){
            Log.e(LOG_TAG, "There was an error in initialization.. ");
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                try{
                    googleMap.setMyLocationEnabled(true); // Display a "Move to location btn
                    Location location = locationManager.getLastKnownLocation(provider_info);
                    LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());

                    CameraUpdate center = CameraUpdateFactory.newLatLng(pos);
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(ConstantHelper.MAP_STANDARD_ZOOM_LEVEL);

                    googleMap.moveCamera(center);
                    googleMap.animateCamera(zoom);

                }catch(SecurityException e){
                    Log.d(LOG_TAG, "We are not allowed.. ");
                }
            }
        });

        return rootView;
    }

    // This should be moved to a separate service since we want to execute this even though its not in the fm view
    private void setupLocation(){
        Context mContext = getActivity().getApplicationContext();

        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled) {
                Log.d(LOG_TAG, "Application use GPS Service");
                provider_info = LocationManager.GPS_PROVIDER;

            } else if (isNetworkEnabled) { // Try to get location if you Network Service is enabled
                provider_info = LocationManager.NETWORK_PROVIDER;
                Log.d(LOG_TAG, "Application use Network State to get GPS coordinates");
            }

            try{
                if (!provider_info.isEmpty()) {
                    locationManager.requestLocationUpdates(
                            provider_info,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,
                            new LocationListener() {
                                @Override
                                public void onLocationChanged(Location newLocation) {

                                    lastLocation = location;
                                    location = newLocation;

                                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(newLocation.getLatitude(), newLocation.getLongitude()));
                                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(ConstantHelper.MAP_STANDARD_ZOOM_LEVEL);

                                    googleMap.moveCamera(center);
                                    googleMap.animateCamera(zoom);
                                }

                                @Override
                                public void onStatusChanged(String provider, int status, Bundle extras) {

                                }

                                @Override
                                public void onProviderEnabled(String provider) {
                                    Log.d(LOG_TAG, "onProviderEnabled");
                                }

                                @Override
                                public void onProviderDisabled(String provider) {
                                    Log.d(LOG_TAG, "onProviderDisabled");
                                }
                            }
                    );

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(provider_info);
                        lastLocation = location;
                    }
                }
            }catch(SecurityException e){
                Log.d(LOG_TAG, "SecurityException was caught");
            }
        }
        catch (Exception e){
            //e.printStackTrace();
            Log.e(LOG_TAG, "Impossible to connect to LocationManager", e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
