package com.progetto_ingegneria.pocketvenice.BottomNavbar.Places.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Places.Listeners.PlaceCallback;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Places.Model.Place;
import com.progetto_ingegneria.pocketvenice.R;

import java.util.List;
/**
 * Popola opportunamente l'elemento di item_place.xml con un evento presente nella lista placesData.
 * Tale procedura viene replicata per ogni elemento della lista che corrisponde a un luogo visualizzato dal fragment Places.java
 * @see com.progetto_ingegneria.pocketvenice.BottomNavbar.Places.Places
 * Places
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private final Context context;
    private final List<Place> placesData;
    private final PlaceCallback callback;


    public PlaceAdapter(Context context, List<Place> placeData, PlaceCallback callback) {
        this.context = context;
        this.placesData = placeData;
        this.callback = callback;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlaceViewHolder(LayoutInflater.from(context).inflate(R.layout.item_place, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {

        Place place = placesData.get(position);

        holder.title.setText(place.getTitle());
        holder.address.setText(place.getAddress());
        holder.ratingBar.setRating(place.getRating());

        Glide.with(holder.itemView.getContext())
                .load(placesData.get(position).getPhotoSrc())
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(holder.imgPlace);
    }

    @Override
    public int getItemCount() {
        return placesData.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        TextView title, district, address, score, description;
        ImageView imgPlace, imgContainer;
        RatingBar ratingBar;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            imgContainer = itemView.findViewById(R.id.item_place_container_img);
            imgPlace = itemView.findViewById(R.id.item_place_img);
            title = itemView.findViewById(R.id.item_place_title);
            address = itemView.findViewById(R.id.item_place_address);
            score = itemView.findViewById(R.id.item_place_rating);
            ratingBar = itemView.findViewById(R.id.item_place_ratingbar);

            itemView.setOnClickListener(v -> callback.onPlaceItemClick(getAdapterPosition(),
                    imgContainer,
                    imgPlace,
                    title,
                    address,
                    score,
                    ratingBar));
        }
    }
}
