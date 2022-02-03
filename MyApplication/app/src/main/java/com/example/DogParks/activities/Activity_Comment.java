package com.example.DogParks.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.DogParks.fragments.Fragment_Comment;
import com.example.DogParks.fragments.Fragment_List_Comments;
import com.example.DogParks.utils.myConstant;
import com.example.DogParks.R;

public class Activity_Comment extends AppCompatActivity {
    private String pid;
    private Fragment_List_Comments fragment_list;
    private Fragment_Comment fragment_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initView();
    }

private void initView(){
        pid=getIntent().getStringExtra(myConstant.EXTRA_PLACE_KEY);
        Bundle bundle=new Bundle();
        bundle.putString(myConstant.EXTRA_PLACE_KEY,pid);
        fragment_comment = new Fragment_Comment();
        fragment_comment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.LAY_addComment, fragment_comment).commit();
        fragment_list = new Fragment_List_Comments();
        fragment_list.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.LAY_list, fragment_list).commit();
    }
}