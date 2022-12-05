package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto_ingegneria.pocketvenice.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Place_Data> list;

    public MyAdapter(Context context, ArrayList<Place_Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_list_items, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Place_Data posto = list.get(position);
        holder.Titolo.setText(posto.Titolo);
        holder.Sestiere.setText(posto.Sestiere);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Titolo, Sestiere;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //Titolo = itemView.findViewById(R.id.text_titolo);
            //Sestiere = itemView.findViewById(R.id.text_sestiere);
        }
    }

}