package com.progetto_ingegneria.pocketvenice.BottomNavbar.News.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto_ingegneria.pocketvenice.R;
/**
 * Fornisce il settaggio pportuno delle informazioni delle news nello specifico per ogni elemento da visualizzare in News.java
 * @see com.progetto_ingegneria.pocketvenice.BottomNavbar.News.News
 * News
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView text_title, text_source, text_desc, text_author, text_published_at, text_time;
    ImageView img_headline;
    ProgressBar progressBar;
    CardView cardView;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        text_title = itemView.findViewById(R.id.text_title);
        text_source = itemView.findViewById(R.id.text_source);
        text_desc = itemView.findViewById(R.id.text_desc);
        text_author = itemView.findViewById(R.id.text_author);
        text_published_at = itemView.findViewById(R.id.text_publishedAt);
        text_time = itemView.findViewById(R.id.text_time);
        img_headline = itemView.findViewById(R.id.img_headline);
        progressBar = itemView.findViewById(R.id.progressbar_load_photo);
        cardView = itemView.findViewById(R.id.item_news_container);
    }
}
