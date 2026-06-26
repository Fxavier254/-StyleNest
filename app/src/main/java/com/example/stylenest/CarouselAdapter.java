package com.example.stylenest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private final List<CarouselItem> carouselItems;
    private final OnCarouselClickListener listener;

    public interface OnCarouselClickListener {
        void onShopNowClick(String category);
    }

    public CarouselAdapter(List<CarouselItem> carouselItems, OnCarouselClickListener listener) {
        this.carouselItems = carouselItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carousel, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        CarouselItem item = carouselItems.get(position);
        
        boolean hasTitle = item.title != null && !item.title.isEmpty();
        boolean hasPromo = item.promo != null && !item.promo.isEmpty();
        
        holder.title.setText(item.title);
        holder.title.setVisibility(hasTitle ? View.VISIBLE : View.GONE);
        
        holder.promo.setText(item.promo);
        holder.promo.setVisibility(hasPromo ? View.VISIBLE : View.GONE);

        boolean hasText = hasTitle || hasPromo;
        holder.textOverlay.setVisibility(hasText ? View.VISIBLE : View.GONE);
        holder.textContainer.setVisibility(hasText ? View.VISIBLE : View.GONE);
        holder.shopNow.setVisibility(hasText ? View.VISIBLE : View.GONE);

        // centerCrop ensures the image fills the entire banner area, creating a professional look
        Glide.with(holder.itemView.getContext())
                .load(item.imageResId)
                .centerCrop()
                .into(holder.image);

        holder.shopNow.setOnClickListener(v -> listener.onShopNowClick(item.category));
        holder.itemView.setOnClickListener(v -> listener.onShopNowClick(item.category));
    }

    @Override
    public int getItemCount() {
        return carouselItems.size();
    }

    static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, promo;
        MaterialButton shopNow;
        View textOverlay, textContainer;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.carouselImage);
            title = itemView.findViewById(R.id.carouselTitle);
            promo = itemView.findViewById(R.id.carouselPromo);
            shopNow = itemView.findViewById(R.id.shopNowButton);
            textOverlay = itemView.findViewById(R.id.textOverlay);
            textContainer = itemView.findViewById(R.id.textContainer);
        }
    }

    public static class CarouselItem {
        String title, promo, category;
        int imageResId;

        public CarouselItem(String title, String promo, String category, int imageResId) {
            this.title = title;
            this.promo = promo;
            this.category = category;
            this.imageResId = imageResId;
        }
    }
}
