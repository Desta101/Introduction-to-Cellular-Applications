package com.example.DogParks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import com.example.DogParks.R;
import com.example.DogParks.utils.myConstant;

public class Activity_Menu extends Activity_MyBase {

    private MaterialButton search_BTN_search;
    private MaterialButton search_BTN_addPlace;

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

        search_BTN_addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddPlace();
            }
        });
    }
    private void findViews() {
        search_BTN_search =findViewById(R.id.search_BTN_search);
        search_BTN_addPlace = findViewById(R.id.search_BTN_addPlace);
    }
    private void searchPlace() {
        Intent intent=new Intent(this, Activity_List.class);
        if(area!=null&&type!=null) {
            intent.putExtra(myConstant.EXTRA_AREA, area.toString());
            intent.putExtra(myConstant.EXTRA_TYPE, type.toString());
        }
        startActivity(intent);
    }
    private void openAddPlace() {
        Intent intent=new Intent(this, Activity_Add.class);
        startActivity(intent);
    }
}
