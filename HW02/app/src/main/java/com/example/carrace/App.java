package com.example.carrace;
/**
 * shimon Desta 203670286
 * HW01 - Car Game
 **/
import android.app.Application;
import com.example.carrace.objects.MSP;
import com.example.carrace.objects.RecordsDB;
import com.google.gson.Gson;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final MSP msp = MSP.initHelper(this);
        String js = MSP.getMe().getString("DB", "");
        RecordsDB database = new Gson().fromJson(js, RecordsDB.class);

        if(database==null){
            RecordsDB myDB = new RecordsDB();
            String json = new Gson().toJson(myDB);
            MSP.getMe().putString("DB", json);
        }
    }
}
