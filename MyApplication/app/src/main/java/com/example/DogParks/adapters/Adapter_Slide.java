package com.example.DogParks.adapters;


import java.util.List;
import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.DogParks.utils.myConstant;



public class Adapter_Slide extends FragmentStatePagerAdapter {
    private List<Fragment> listFragment;

    @SuppressLint("WrongConstant")
    public Adapter_Slide(@NonNull FragmentManager fm, List<Fragment> listFragment) {
        super(fm, myConstant.BEHAVIOR);
        this.listFragment = listFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
}
