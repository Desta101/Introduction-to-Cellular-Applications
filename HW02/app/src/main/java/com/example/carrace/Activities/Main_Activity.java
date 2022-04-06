package com.example.carrace.Activities;
/**
 * shimon Desta 203670286
 * HW01 - Car Game
 **/
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.carrace.R;
import com.google.android.material.button.MaterialButton;


public class Main_Activity extends AppCompatActivity {


    private MaterialButton Button_slider;
    private MaterialButton Button_buttom;
    private MaterialButton Button_TOP10;
    public static final String BUNDLE = "BUNDLE";
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findView();
        setOnClick();
    }

    private void findView() {
        Button_slider = findViewById(R.id.Button_slider);
        Button_buttom = findViewById(R.id.Button_buttom);
        Button_TOP10 = findViewById(R.id.Button_TOP10);
    }

    private void setOnClick() {
        Button_slider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameActivity(true);
            }
        });
        Button_buttom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameActivity(false);
            }
        });
        Button_TOP10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent;
                myIntent = new Intent(Main_Activity.this, Points_Activity.class);
                startActivity(myIntent);
            }
        });
    }

    private void GameActivity(boolean Flag) {
        Intent myIntent;
        bundle = new Bundle();
        bundle.putBoolean(CarGame_Activity.SENSOR_TYPE,Flag);
        myIntent = new Intent(this, CarGame_Activity.class);
        myIntent.putExtra(BUNDLE, bundle);
        startActivity(myIntent);
    }


}