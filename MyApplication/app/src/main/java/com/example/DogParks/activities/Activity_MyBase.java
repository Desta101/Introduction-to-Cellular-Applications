package com.example.DogParks.activities;

import android.os.Bundle;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.DogParks.enums.Area;
import com.example.DogParks.enums.Type;
import com.example.DogParks.utils.myConstant;
import androidx.appcompat.app.AppCompatActivity;



public class Activity_MyBase extends AppCompatActivity {
    protected ImageView clickViewArea;
    protected ImageView clickViewType;
    protected Type type;
    protected Area area;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    protected  void updateViewArea(ImageView imageView){
        if(clickViewArea !=null) {
            clickViewArea.setBackgroundColor(Color.WHITE);
        }
        imageView.setBackgroundColor(Color.rgb(myConstant.COLOR_NUMBER, myConstant.COLOR_NUMBER, myConstant.COLOR_NUMBER));
        clickViewArea =imageView;
    }
    protected void updateViewType(ImageView imageView){
        if(clickViewType !=null) {
            clickViewType.setBackgroundColor(Color.WHITE);
        }
        imageView.setBackgroundColor(Color.rgb(myConstant.COLOR_NUMBER, myConstant.COLOR_NUMBER, myConstant.COLOR_NUMBER));
        clickViewType =imageView;
    }
    protected void makeToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}