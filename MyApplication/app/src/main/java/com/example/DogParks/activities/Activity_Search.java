package com.example.DogParks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.google.android.material.button.MaterialButton;
import com.example.DogParks.enums.Area;
import com.example.DogParks.enums.Type;
import com.example.DogParks.utils.myConstant;
import com.example.DogParks.R;

public class Activity_Search extends Activity_MyBase {
    private MaterialButton search_BTN_search;
    private ImageView search_IMG_park;
    private ImageView search_IMG_garden;
    private ImageView search_IMG_shop;
    private ImageView search_IMG_north;
    private ImageView search_IMG_sorth;
    private ImageView search_IMG_center;
    private MaterialButton search_BTN_addPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViews();
        initViews();
    }

    private void initViews() {
        search_BTN_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(area!=null&&type!=null) {
                    searchPlace();
                }else{
                    makeToast("Select a Type of place and type");
                }

            }
        });

        search_IMG_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewType(search_IMG_park);
             type=Type.park;

            }
        });
        search_IMG_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewType(search_IMG_garden);
                type=Type.garden;
            }
        });
        search_IMG_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewType(search_IMG_shop);
                type=Type.shop;
            }
        });




        search_IMG_north.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewArea(search_IMG_north);
                area=Area.north;
            }
        });
        search_IMG_sorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewArea(search_IMG_sorth);
                area=Area.south;
            }
        });
        search_IMG_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewArea(search_IMG_center);
                area=Area.center;
            }
        });

        search_BTN_addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddPlace();
            }
        });
    }

    private void searchPlace() {
        Intent intent=new Intent(this, Activity_List.class);
        if(area!=null&&type!=null) {
            intent.putExtra(myConstant.EXTRA_AREA, area.toString());
            intent.putExtra(myConstant.EXTRA_TYPE, type.toString());
        }
            startActivity(intent);
    }

    private void findViews() {
        search_BTN_search =findViewById(R.id.search_BTN_search);
        search_IMG_park =findViewById(R.id.search_IMG_park);
        search_IMG_garden =findViewById(R.id.search_IMG_garden);
        search_IMG_shop =findViewById(R.id.search_IMG_shop);
        search_IMG_north =findViewById(R.id.search_IMG_north);
        search_IMG_sorth =findViewById(R.id.search_IMG_sorth);
        search_IMG_center =findViewById(R.id.search_IMG_center);
        search_BTN_addPlace = findViewById(R.id.search_BTN_addPlace);
    }
    private void openAddPlace() {
        Intent intent=new Intent(this, Activity_Add.class);
        startActivity(intent);
    }

}