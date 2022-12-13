package com.progetto_ingegneria.pocketvenice.Guide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.progetto_ingegneria.pocketvenice.R;

import java.util.List;

public class GuideViewPagerAdapter extends PagerAdapter {

    private final Context context;
    List<ScreenItem> screenItemList;

    public GuideViewPagerAdapter(Context context, List<ScreenItem> screenItemList) {
        this.context = context;
        this.screenItemList = screenItemList;
    }

    @Override
    public int getCount() {return this.screenItemList.size();}
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {return view == object;}
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewScreen = layoutInflater.inflate(R.layout.screen_guide, null);

        ImageView imageSlide = viewScreen.findViewById(R.id.intro_img);
        TextView title = viewScreen.findViewById(R.id.intro_title);
        TextView description = viewScreen.findViewById(R.id.intro_description);

        title.setText(screenItemList.get(position).getTitle());
        description.setText(screenItemList.get(position).getDescription());
        imageSlide.setImageResource(screenItemList.get(position).getScreenImg());

        container.addView(viewScreen);
        return viewScreen;

    }
}
