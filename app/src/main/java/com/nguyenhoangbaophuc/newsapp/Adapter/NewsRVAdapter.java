package com.nguyenhoangbaophuc.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.nguyenhoangbaophuc.newsapp.DetailActivity;
import com.nguyenhoangbaophuc.newsapp.Model.Item;
import com.nguyenhoangbaophuc.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {
    private ArrayList<Item> list;
    private Context context;

    public NewsRVAdapter(ArrayList<Item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item,parent,false);
        return new NewsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.ViewHolder holder, int position) {
        Item item = list.get(position);

        holder.titleTV.setText(item.getTitle());
        holder.subTitleTV.setText(item.getDescription());
        Log.d("NewsRVAdapter", "Image URL: " + item.getImageUrl());
        Picasso.get().load(item.getImageUrl()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                String link = item.getLink();
                i.putExtra("link",link);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTV, subTitleTV;
        private CardView cardView;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.tvNewsHeading);
            subTitleTV = itemView.findViewById(R.id.tvSubTitle);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.ivNews);
        }
    }
}

