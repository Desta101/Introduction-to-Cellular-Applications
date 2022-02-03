package com.example.DogParks.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.DogParks.R;
import com.example.DogParks.activities.Activity_Info;
import com.example.DogParks.enums.Area;
import com.example.DogParks.enums.Type;
import com.example.DogParks.objects.Comment;
import com.example.DogParks.objects.Place;
import com.example.DogParks.utils.myConstant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;


public class Fragment_Map extends Fragment {
    private SupportMapFragment mapFragment;
    private Area area;
    private Type type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_placesMap);
        initView();
        readPlaces();
        return view;
    }

    private void initView() {
        area=Area.valueOf(getArguments().getString(myConstant.EXTRA_AREA));
        type=Type.valueOf(getArguments().getString(myConstant.EXTRA_TYPE));
    }


    private void readPlaces(){
        ArrayList<Place> places =new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(myConstant.PLACES_DB);
        myRef.child(area.toString()).child(type.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot placeSnapshot:dataSnapshot.getChildren()) {
                    places.add(placeSnapshot.getValue(Place.class));
                }
                readStars(places);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getContext(),"error reading places from data base",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readStars(ArrayList<Place> places) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(myConstant.COMMENTS_DB);
        for (Place place:places) {
            myRef.child(place.getPid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Comment> comments=new ArrayList<>();
                    float sum=0f;
                    for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                        Comment comment=commentSnapshot.getValue(Comment.class);
                        comments.add(comment);
                        sum+=comment.getStars();
                    }
                    if(comments.size()!=0) {
                        place.setStars(sum / comments.size());
                    }
                    place.setNumberOfComments(comments.size());
                    addMarkersToMap(places);
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(getContext(), "error reading places from data base", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void addMarkersToMap(ArrayList<Place> places) {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                HashMap<String,Place> locations = new HashMap<>();
                LatLng latLng=null;
                for (Place place : places) {
                    latLng = new LatLng(place.getLatitude(), place.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                    markerOptions.title(place.getName());
                    googleMap.addMarker(markerOptions);
                    locations.put(place.getName(), place);

                    if (latLng != null) {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));
                    }
                }
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        openDescription((Place) locations.get(marker.getTitle()));
                        return false;
                    }
                });
            }
        });
    }

    private void openDescription(Place place) {
        Intent intent=new Intent(getContext(), Activity_Info.class);
        intent.putExtra(myConstant.EXTRA_PLACE_DATA,new Gson().toJson(place));
        startActivity(intent);
    }

}