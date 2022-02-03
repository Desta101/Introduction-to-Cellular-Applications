package com.example.DogParks.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.DogParks.R;
import com.example.DogParks.adapters.Adapter_Comments;
import com.example.DogParks.objects.Comment;
import com.example.DogParks.utils.myConstant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Fragment_List_Comments extends Fragment {
    private RecyclerView LST_comments;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_comments, container, false);
        findViews(view);
        readComments();
        return view;
    }

    private void readComments(){
        String pid=getArguments().getString(myConstant.EXTRA_PLACE_KEY);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(myConstant.COMMENTS_DB);
        myRef.child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Comment> comments =new ArrayList<>();
                for (DataSnapshot commentSnapshot:dataSnapshot.getChildren()) {
                    Comment comment =commentSnapshot.getValue(Comment.class);
                    comments.add(comment);
                }
                displayComments(comments);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getContext(),"error reading comments from data base",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayComments(ArrayList<Comment> comments){
        LST_comments.setLayoutManager(new LinearLayoutManager(getContext()));
        Adapter_Comments adapter_comment = new Adapter_Comments( getContext(), comments);
        LST_comments.setAdapter(adapter_comment);
    }


    private void findViews(View view){
        LST_comments = view.findViewById(R.id.LST_comments);
    }
}