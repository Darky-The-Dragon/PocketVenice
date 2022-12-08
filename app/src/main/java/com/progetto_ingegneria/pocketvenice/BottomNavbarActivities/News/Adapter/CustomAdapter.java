package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Adapter;

import static com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Utility.DateTimeFormatting.DateFormat;
import static com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Utility.DateTimeFormatting.DateToTimeFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Listeners.SelectListener;
import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Models.NewsHeadlines;
import com.progetto_ingegneria.pocketvenice.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private final Context context;
    private final List<NewsHeadlines> headlines;
    private final SelectListener listener;

    public CustomAdapter(Context context, List<NewsHeadlines> headlines, SelectListener listener) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.news_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.text_title.setText(headlines.get(position).getTitle());
        holder.text_source.setText(headlines.get(position).getSource().getName());
        holder.text_desc.setText(headlines.get(position).getDescription());
        holder.text_published_at.setText(DateFormat(headlines.get(position).getPublishedAt()));
        holder.text_time.setText(DateToTimeFormat(headlines.get(position).getPublishedAt()));
        holder.text_author.setText(headlines.get(position).getAuthor());

        if (headlines.get(position).getUlrToImage() != null) {
            holder.progressBar.setVisibility(View.GONE);
            Picasso.get().load(headlines.get(position).getUlrToImage()).into(holder.img_headline);
        }

        holder.cardView.setOnClickListener(v -> listener.OnNewsClicked(headlines.get(position)));
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }
}
