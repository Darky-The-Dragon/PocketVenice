package com.progetto_ingegneria.pocketvenice.Guide.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.progetto_ingegneria.pocketvenice.Guide.ScreenItem;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.List;
/**
 * Popola opportunamente l'elemento di screen_guide.xml con un elemento presente nella lista screenItemList.
 * Tale procedura viene replicata per ogni elemento della lista che corrisponde ad una tab visualizzata dal'activity GuideActivity.
 * @see com.progetto_ingegneria.pocketvenice.Guide.GuideActivity
 * GuideActivity
 * @see ScreenItem
 * Screen item
 */
public class GuideViewPagerAdapter extends PagerAdapter {

    private final Context context;
    List<ScreenItem> screenItemList;

    public GuideViewPagerAdapter(Context context, List<ScreenItem> screenItemList) {
        this.context = context;
        this.screenItemList = screenItemList;
    }

    /**
     * @return Ritorna la lunghezza della lista di ScreenItem
     */
    @Override
    public int getCount() {
        return this.screenItemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
