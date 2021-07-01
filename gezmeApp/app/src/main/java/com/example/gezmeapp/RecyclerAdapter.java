package com.example.gezmeapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PostHolder> {
    private ArrayList<String> userEmailDataList,userCommentList,userImageUrlList;

    public RecyclerAdapter(ArrayList<String> userEmailDataList, ArrayList<String> userCommentList, ArrayList<String> userImageUrlList) {
        this.userEmailDataList = userEmailDataList;
        this.userCommentList = userCommentList;
        this.userImageUrlList = userImageUrlList;
    }


    class PostHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView emailTextView;
        TextView commentTextView;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.recyclerView_row_imageView);
           // emailTextView=itemView.findViewById(R.id.recyclerView_row_userEmail);
            commentTextView=itemView.findViewById(R.id.recyclerView_row_comment);
        }
    }
    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_row,parent,false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
       // holder.emailTextView.setText(userEmailDataList.get(position));
        holder.commentTextView.setText(userCommentList.get(position));
        Picasso.get().load(userImageUrlList.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return userCommentList.size();
    }

}
