package com.example.carrace.score;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.carrace.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment_Map extends Fragment {

    private AppCompatActivity activity;
    MapView mMapView;
    private GoogleMap googleMap;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_map, container, false);
            findViews(view);
            initViews(savedInstanceState);
            return view;
    }



    private void initViews(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

            }
        });
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }
    private void findViews(View view) {
        mMapView = (MapView) view.findViewById(R.id.mapView);
    }

    public void zoom(String name, int score, double latitude,double longitude) {
        LatLng point = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(point).title(name+": "+score));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(point).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


}