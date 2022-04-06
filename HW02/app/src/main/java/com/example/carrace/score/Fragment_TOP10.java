package com.example.carrace.score;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.carrace.R;
import com.example.carrace.control.CallBack_List;
import com.example.carrace.objects.MSP;
import com.example.carrace.objects.Record;
import com.example.carrace.objects.RecordsDB;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Fragment_TOP10 extends Fragment {


    private CallBack_List callBackList;
    private MaterialButton[] btns_records;

    public void setCallBackList(CallBack_List callBackList) {
        this.callBackList = callBackList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void initViews() {
        String js = MSP.getMe().getString("DB", "");
        RecordsDB database = new Gson().fromJson(js, RecordsDB.class);
        ArrayList<Record> records = database.getRecords();
        for (int i = 0,j = 1; i < records.size(); i++,j++) {
            Record record = records.get(i);
            btns_records[i].setText(j+". "+ record.getName() + " - " + record.getScore());
            btns_records[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackList.rowSelected(record.getName(),record.getScore(),record.getLatitude(),record.getLongitude());
                }
            });

        }
    }

    private void findViews(View view) {
        btns_records = new MaterialButton[]{view.findViewById(R.id.Player_1), view.findViewById(R.id.Player_2), view.findViewById(R.id.Player_3), view.findViewById(R.id.Player_4), view.findViewById(R.id.Player_5), view.findViewById(R.id.Player_6), view.findViewById(R.id.Player_7), view.findViewById(R.id.Player_8), view.findViewById(R.id.Player_9), view.findViewById(R.id.Player_10),
        };
    }

}
