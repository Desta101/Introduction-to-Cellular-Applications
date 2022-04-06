package com.example.carrace.Activities;
/**
 * shimon Desta 203670286
 * HW01 - Car Game
 **/

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.carrace.control.CallBack_List;
import com.example.carrace.score.Fragment_Map;
import com.example.carrace.score.Fragment_TOP10;
import com.example.carrace.R;

public class Points_Activity extends AppCompatActivity {

    private Fragment_TOP10 fTop10;
    private Fragment_Map fMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        fTop10 = new Fragment_TOP10();
        fTop10.setCallBackList(callBackList);
        getSupportFragmentManager().beginTransaction().add(R.id.TOP10, fTop10).commit();
        fMap = new Fragment_Map();
        getSupportFragmentManager().beginTransaction().add(R.id.MAP_Layout, fMap).commit();
    }

    CallBack_List callBackList = new CallBack_List() {
        public void rowSelected(String name, int score,double latitude, double longitude) {
            fMap.zoom(name,score,latitude,longitude);
        }
    };

}