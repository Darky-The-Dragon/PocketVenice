package com.progetto_ingegneria.pocketvenice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto_ingegneria.pocketvenice.models.NewsHeadlines;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<NewsHeadlines> headlines;

    public CustomAdapter(Context context, List<NewsHeadlines> headlines) {
        this.context = context;
        this.headlines = headlines;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.news_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.text_title.setText(headlines.get(position).getTitle());
        holder.text_source.setText(headlines.get(position).getSource().getName());
        holder.text_desc.setText(headlines.get(position).getDescription());
        holder.text_published_at.setText(headlines.get(position).getPublishedAt());
        holder.text_author.setText(headlines.get(position).getAuthor());

        if (headlines.get(position).getUlrToImage() != null) {
            Picasso.get().load(headlines.get(position).getUlrToImage()).into(holder.img_headline);
        }
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }
}
