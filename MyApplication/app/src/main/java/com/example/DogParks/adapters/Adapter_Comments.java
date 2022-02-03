package com.example.DogParks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.DogParks.R;
import com.example.DogParks.objects.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Adapter_Comments extends RecyclerView.Adapter<Adapter_Comments.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<Comment> comments;

    public Adapter_Comments(Context context, ArrayList<Comment> comments) {
        if(context!=null) {
            this.layoutInflater = LayoutInflater.from(context);
            this.comments = comments;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.comment_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Comment comment = comments.get(position);
        holder.comment_LBL_name.setText("name: "+comment.getUserName());
        holder.comment_LBL_date.setText("date: "+new SimpleDateFormat("dd-MM-yyyy    HH:mm:ss").format(comment.getDate()));
        holder.comment_RTB_rating.setRating(comment.getStars());
        holder.comment_LBL_comment.setText("comment: "+comment.getComment());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


      public  Comment getItem(int id) {
        return comments.get(id);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView comment_LBL_name;
        TextView comment_LBL_date;
        RatingBar comment_RTB_rating;
        TextView comment_LBL_comment;


        MyViewHolder(View itemView) {
            super(itemView);
            comment_LBL_name = itemView.findViewById(R.id.comment_LBL_name);
            comment_LBL_date = itemView.findViewById(R.id.comment_LBL_date);
            comment_RTB_rating = itemView.findViewById(R.id.comment_RTB_rating);
            comment_LBL_comment = itemView.findViewById(R.id.comment_LBL_comment);

        }
    }
}


