package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.security.PrivateKey;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder>{
    private  String[] data;
    public FeedAdapter(String[] data) {
        this.data = data;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.feedviewlayout,parent,false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {

        String text = data[position];
        holder.textfeed.setText(text);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView imgfeed;
        TextView textfeed;
        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            imgfeed = itemView.findViewById(R.id.feedImage);
            textfeed = itemView.findViewById(R.id.feedtext);
        }
    }
}
