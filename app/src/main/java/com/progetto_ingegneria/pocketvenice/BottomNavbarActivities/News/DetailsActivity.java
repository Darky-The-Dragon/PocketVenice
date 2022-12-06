package com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.progetto_ingegneria.pocketvenice.BottomNavbarActivities.News.Models.NewsHeadlines;
import com.progetto_ingegneria.pocketvenice.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    NewsHeadlines headlines;
    TextView txt_title, txt_author, txt_time, txt_detail, txt_content;
    ImageView img_news, back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txt_title = findViewById(R.id.text_detail_title);
        txt_author = findViewById(R.id.text_detail_author);
        txt_time = findViewById(R.id.text_detail_time);
        txt_detail = findViewById(R.id.text_detail_detail);
        txt_content = findViewById(R.id.text_detail_content);
        img_news = findViewById(R.id.img_detail_news);

        back_button = findViewById(R.id.menu_details_back);
        back_button.setOnClickListener(this);

        //String test = headlines.getUlrToImage();

        headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");

        txt_title.setText(headlines.getTitle());
        txt_author.setText(headlines.getAuthor());
        txt_time.setText(headlines.getPublishedAt());
        txt_detail.setText(headlines.getDescription());
        txt_content.setText(headlines.getContent());
        Picasso.get().load(headlines.getUlrToImage()).into(img_news);

        if (headlines.getUlrToImage() == null) {
            Toast.makeText(this, "It is null!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        
        if(v.getId() == R.id.menu_details_back){
            finish();
        }
    }
}