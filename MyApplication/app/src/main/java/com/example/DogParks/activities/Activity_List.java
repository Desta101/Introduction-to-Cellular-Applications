package com.example.DogParks.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.DogParks.adapters.Adapter_Slide;
import com.example.DogParks.fragments.Fragment_List;
import com.example.DogParks.fragments.Fragment_Map;
import com.example.DogParks.R;
import java.util.ArrayList;
import java.util.List;

public class Activity_List extends AppCompatActivity {

    private ViewPager places_viewPager;
    private PagerAdapter Adapter_pager;
    private Fragment_List fragment_list;
    private Fragment_Map fragment_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
    }

    private void initView() {
        List<Fragment> list = new ArrayList<>();
        fragment_map = new Fragment_Map();
        fragment_list = new Fragment_List();
        fragment_map.setArguments(getIntent().getExtras());
        fragment_list.setArguments(getIntent().getExtras());
        list.add(fragment_map);
        list.add(fragment_list);
        places_viewPager = findViewById(R.id.places_viewPager);
        Adapter_pager = new Adapter_Slide(getSupportFragmentManager(), list);
        places_viewPager.setAdapter(Adapter_pager);
    }
}
