package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.Places;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto_ingegneria.pocketvenice.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Place_Data> list;

    private OnRecyclerViewClickListener listener;

    public MyAdapter(Context context, ArrayList<Place_Data> list) {
        this.context = context;
        this.list = list;
    }

    public void OnRecyclerViewClickListener(OnRecyclerViewClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.places_list_items, parent, false);
        return new MyViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Place_Data posto = list.get(position);
        holder.titolo.setText(posto.Titolo);
        holder.sestiere.setText(posto.Sestiere);
        Picasso.get().load(posto.FotoSrc).into(holder.foto);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnRecyclerViewClickListener {
        void OnItemClick(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titolo, sestiere;
        ImageView foto;

        public MyViewHolder(@NonNull View itemView, OnRecyclerViewClickListener listener) {
            super(itemView);
            titolo = itemView.findViewById(R.id.text_titolo);
            sestiere = itemView.findViewById(R.id.text_sestiere);
            foto = itemView.findViewById(R.id.img_place);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                    listener.OnItemClick(getAdapterPosition());
            });
        }


    }

}