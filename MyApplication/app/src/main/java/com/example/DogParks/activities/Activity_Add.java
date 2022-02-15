package com.example.DogParks.activities;

import java.util.List;
import java.util.UUID;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.example.DogParks.enums.Area;
import com.example.DogParks.enums.Type;
import com.example.DogParks.objects.Place;
import com.example.DogParks.utils.myConstant;
import com.example.DogParks.R;



public class Activity_Add extends Activity_MyBase  {

    private TextInputLayout add_EDT_description;
    private TextInputLayout add_EDT_time;
    private TextInputLayout add_EDT_address;
    private TextInputLayout add_EDT_name;
    private MaterialButton add_BTN_addPlace;
    private ImageView add_IMG_picture;
    private ImageView add_IMG_park;
    private ImageView add_IMG_garden;
    private ImageView add_IMG_shop;
    private ImageView add_IMG_sorth;
    private ImageView add_IMG_north;
    private ImageView add_IMG_center;
    private LocationManager locationManager;
    private double longitude;
    private double latitude;
    private Location gps_Location,network_Location,final_Location;
    private Area area;
    private Type type;
    private Uri picUrl;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findViews();
        initViews();
    }

    private void findViews() {
        add_EDT_description =findViewById(R.id.add_EDT_description);
        add_EDT_time =findViewById(R.id.add_EDT_time);
        add_EDT_address =findViewById(R.id.add_EDT_address);
        add_BTN_addPlace =findViewById(R.id.add_BTN_addPlace);
        add_IMG_park =findViewById(R.id.add_IMG_park);
        add_IMG_garden =findViewById(R.id.add_IMG_garden);
        add_IMG_shop =findViewById(R.id.add_IMG_shop);
        add_EDT_name =findViewById(R.id.add_EDT_name);
        add_IMG_picture =findViewById(R.id.add_IMG_picture);
        add_IMG_sorth =findViewById(R.id.add_IMG_sorth);
        add_IMG_north =findViewById(R.id.add_IMG_north);
        add_IMG_center =findViewById(R.id.add_IMG_center);

    }

    private void initViews() {
        add_BTN_addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(area!=null&&type!=null) {
                    setPlace();
                }else{
                makeToast("you need to choose area and type");
            }
            }
        });
        add_IMG_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewType(add_IMG_park);
                type=Type.park;
            }
        });
        add_IMG_garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewType(add_IMG_garden);
                type=Type.garden;
            }
        });
        add_IMG_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewType(add_IMG_shop);
                type=Type.shop;
            }
        });


        add_IMG_sorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewArea(add_IMG_sorth);
                area=Area.south;
            }
        });
        add_IMG_north.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewArea(add_IMG_north);
                area=Area.north;
            }
        });
        add_IMG_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewArea(add_IMG_center);
                area=Area.center;
            }
        });

        add_IMG_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void setPlace() {
        /*
        String activityTime="";
        try {
            activityTime = add_place_EDT_openTime.getEditText().getText().toString();
        }catch (Exception e){

        }
        */
        Place place = new Place()
                .setPid(UUID.randomUUID().toString())
                .setArea(area)
                .setType(type)
                .setName(add_EDT_name.getEditText().getText().toString())
                .setDescription(add_EDT_description.getEditText().getText().toString())
                .setTime(add_EDT_time.getEditText().getText().toString())
                .setAddress(add_EDT_address.getEditText().getText().toString());
        setAddress(place);
        saveImageToDB(place);
    }
    private void uploadImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), myConstant.PICK_IMAGE);
    }
    private void openSearchPlaces() {
        Intent intent=new Intent(this, Activity_Search.class);
        startActivity(intent);
        finish();
    }
    private void  setAddress(Place place){

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
        Geocoder coder = new Geocoder(this);
        try {


            List<Address> geoResults = coder.getFromLocationName(place.getAddress(), 1);
            while (geoResults.size()==0) {
                    geoResults = coder.getFromLocationName(place.getAddress(), 1);
            }
            if (geoResults.size()>0) {
                    Address addr = geoResults.get(0);
                //Log.d("-------0---------------",String.valueOf(addr.getLatitude()));
                //Log.d("--------0--------------",String.valueOf(addr.getLongitude()));
               // Log.d("-------1---------------",String.valueOf(longitude));
               // Log.d("--------1--------------",String.valueOf(latitude));
                    if(addr.getLatitude()!=0 && addr.getLongitude()!=0 ) {
                        place.setLatitude(addr.getLatitude());
                        place.setLongitude(addr.getLongitude());
                    }
                    else {
                       // Log.d("-------1---------------",String.valueOf(longitude));
                       // Log.d("--------1--------------",String.valueOf(latitude));
                        place.setLatitude(latitude);
                        place.setLongitude(longitude);
                    }

                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        /*



        Geocoder coder = new Geocoder(this);
        List<Address> address;
        try {
            Log.d("-------1---------------",String.valueOf(longitude));
            Log.d("--------1--------------",String.valueOf(latitude));
            //address = coder.getFromLocationName(place.getAddress(),5);
            address = coder.getFromLocation(latitude, longitude,5);

            Address location=null;
            if (address!=null) {
                Log.d("----------------------",String.valueOf(longitude));
                Log.d("----------------------",String.valueOf(latitude));
                //location = address.get(0);
                place.setLatitude(location.getLatitude());
                place.setLongitude(location.getLongitude());


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
*/



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == myConstant.PICK_IMAGE&&data!=null&&data.getData()!=null) {
            picUrl =data.getData();
            Glide.with(this)
                    .load(picUrl)
                    .into(add_IMG_picture);
        }
    }
    private void saveImageToDB(Place place){
        if(picUrl ==null) {
            savePlaceToDB(place);
            return;
        }
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference ref = storageReference.child("IMAGES/" + place.getPid());

        ref.putFile(picUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                readPictureImageUrl(place);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                makeToast("failed to save");
                openSearchPlaces();
            }
        });
    }
    private void readPictureImageUrl(Place place){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference ref = storageReference.child("IMAGES/" + place.getPid());
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                place.setPictureUrl(uri.toString());
                savePlaceToDB(place);
            }
        });
    }
    private void savePlaceToDB(Place place){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(myConstant.PLACES_DB);
        myRef.child(area.toString()).child(type.toString()).child(place.getPid()).setValue(place).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                openSearchPlaces();
            }
        });
    }

}
