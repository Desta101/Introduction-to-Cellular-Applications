package com.example.carrace.Activities;
/**
 * shimon Desta 203670286
 * HW01 - Car Game
 **/

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.carrace.R;
import com.example.carrace.objects.MSP;
import com.example.carrace.objects.Record;
import com.example.carrace.objects.RecordsDB;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import java.util.List;
import java.util.Locale;

public class EndGame_Activity extends AppCompatActivity {

    private MaterialButton Button_save;
    private Location gps_Location,network_Location,final_Location;
    private String userCountry,userAddress;
    private TextInputLayout text_name;
    private TextView text_score;
    private int score;
    private RecordsDB data;
    private LocationManager locationManager;
    private double longitude;
    private double latitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        findViews();
        initButton();
        score = getIntent().getExtras().getInt(CarGame_Activity.SCORE);
        text_score.setText(""+score);
        getLocation();
    }


    private void getLocation(){

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {
            gps_Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_Location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (gps_Location != null) {
            final_Location = gps_Location;
            latitude = final_Location.getLatitude();
            longitude = final_Location.getLongitude();

        }

        else if (network_Location != null) {
            final_Location = network_Location;
            latitude = final_Location.getLatitude();
            longitude = final_Location.getLongitude();
        }
        else {
            //latitude = 32.08326705623331;
            //longitude = 34.82129553531872;
            latitude = 0.0;
            longitude = 0.0;
        }

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 1);

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                userCountry = addresses.get(0).getCountryName();
                userAddress = addresses.get(0).getAddressLine(0);
            }
            else { userCountry = "Unknown"; }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.i("x-----------", String.valueOf(latitude));
        //Log.i("y-----------", String.valueOf(longitude));
    }

    private String getName() {
        return text_name.getEditText().getText().toString();
    }

    private void initButton() {
        Button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = getName();
                getLocation();
                insertRecord(name,score,latitude,longitude);
                Toast.makeText(EndGame_Activity.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent Intent = new Intent(EndGame_Activity.this, Main_Activity.class);
                startActivity(Intent);
                EndGame_Activity.this.finish();
            }
        });
    }

    private void insertRecord(String name, int score, double latitude, double longitude) {
        String js = MSP.getMe().getString("DB", "");
        data = new Gson().fromJson(js, RecordsDB.class);
        data.addRecord(new Record().setName(name).setScore(score).setLatitude(latitude).setLongitude(longitude));
        String json = new Gson().toJson(data);
        MSP.getMe().putString("DB", json);
    }

    private void findViews() {
        text_score = findViewById(R.id.text_score);
        text_name = findViewById(R.id.text_name);
        Button_save = findViewById(R.id.Button_save);
    }
}