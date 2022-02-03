package com.example.DogParks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.DogParks.R;
import com.example.DogParks.objects.Place;
import com.example.DogParks.utils.myConstant;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class Activity_Info extends AppCompatActivity {
    private Place place;
    private Toolbar TLB_toolbar;
    private ImageView IMG_image;
    private RatingBar RTB_rating;
    private MaterialButton BTN_Comment;
    private TextView LBL_name;
    private TextView LBL_area;
    private TextView LBL_type;
    private TextView LBL_address;
    private TextView LBL_description;
    private TextView LBL_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        findViews();
        initPlace();
        setData();
        initViews();
    }

    private void initPlace() {
        String placeString=getIntent().getStringExtra(myConstant.EXTRA_PLACE_DATA);
        place=new Gson().fromJson(placeString,Place.class);
    }
    private void initViews() {
        setSupportActionBar(TLB_toolbar);
        BTN_Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCommentsActivity();
            }
        });
    }

    private void openCommentsActivity() {
        Intent intent=new Intent(this, Activity_Comment.class);
        intent.putExtra(myConstant.EXTRA_PLACE_KEY,place.getPid());
        startActivity(intent);
    }

    private void findViews() {
        TLB_toolbar =findViewById(R.id.TLB_toolbar);
         IMG_image =findViewById(R.id.IMG_image);
         RTB_rating =findViewById(R.id.RTB_rating);
         BTN_Comment =findViewById(R.id.BTN_Comment);
         LBL_name =findViewById(R.id.LBL_name);
         LBL_area =findViewById(R.id.LBL_area);
         LBL_type =findViewById(R.id.LBL_type);
         LBL_address =findViewById(R.id.LBL_address);
         LBL_description =findViewById(R.id.LBL_description);
         LBL_time =findViewById(R.id.LBL_time);

    }
    private void setData(){
        TLB_toolbar.setTitle(place.getName());
        RTB_rating.setRating(place.getStars());
        LBL_time.setText("Time: "+place.getTime());
        LBL_area.setText("area: "+place.getArea());
        LBL_type.setText("type: "+place.getType());
        LBL_address.setText("address: "+place.getAddress());
        LBL_name.setText("name: "+place.getName());
        LBL_description.setText("description: "+place.getDescription());
        if(!place.getPictureUrl().isEmpty()) {
            Glide.with(this).load(place.getPictureUrl()).into(IMG_image);
        }
    }
}