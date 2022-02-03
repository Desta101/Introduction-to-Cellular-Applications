package com.example.DogParks.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.fragment.app.Fragment;

import com.example.DogParks.R;
import com.example.DogParks.objects.Comment;
import com.example.DogParks.utils.myConstant;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Fragment_Comment extends Fragment {
    private TextInputLayout add_EDT_name;
    private MaterialButton add_BTN_addComment;
    private TextInputLayout add_EDT_comment;
    private RatingBar add_RTB_rating;
    private float Stars =0f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void initViews() {
        add_BTN_addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });

        add_RTB_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               Stars =rating;
            }
        });
    }

    private void  addComment(){
        Comment comment=new Comment()
                .setCid(UUID.randomUUID().toString())
                .setUserName(add_EDT_name.getEditText().getText().toString())
                .setComment(add_EDT_comment.getEditText().getText().toString())
                .setDate(System.currentTimeMillis())
                .setStars(Stars);
        saveCommentToDB(comment);
    }

    private void saveCommentToDB(Comment comment) {
        String pid=getArguments().getString(myConstant.EXTRA_PLACE_KEY);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(myConstant.COMMENTS_DB);
        myRef.child(pid).push().setValue(comment);
    }

    private void findViews(View view) {
        add_EDT_name =view.findViewById(R.id.add_EDT_name);
        add_EDT_comment =view.findViewById(R.id.add_EDT_comment);
        add_BTN_addComment =view.findViewById(R.id.add_BTN_addComment);
        add_RTB_rating =view.findViewById(R.id.add_RTB_rating);
    }
}