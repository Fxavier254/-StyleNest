package com.example.stylenest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager2 heroCarousel;
    private TabLayout carouselIndicator;
    private final Handler carouselHandler = new Handler(Looper.getMainLooper());
    private Runnable carouselRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        heroCarousel = view.findViewById(R.id.heroCarousel);
        carouselIndicator = view.findViewById(R.id.carouselIndicator);

        setupCarousel();

        GridLayout mensGrid = view.findViewById(R.id.mensGrid);
        GridLayout kidsGrid = view.findViewById(R.id.kidsGrid);
        GridLayout shoesGrid = view.findViewById(R.id.shoesGrid);
        GridLayout womensGrid = view.findViewById(R.id.womensGrid);
        GridLayout essentialsGrid = view.findViewById(R.id.essentialsGrid);

        populateCategory(mensGrid, inflater, "MEN'S STORE", 4);
        populateCategory(kidsGrid, inflater, "KIDS STORE", 4);
        populateCategory(shoesGrid, inflater, "SHOES", 4);
        populateCategory(womensGrid, inflater, "WOMEN'S STORE", 4);
        populateCategory(essentialsGrid, inflater, "ACCESSORIES", 6);

        return view;
    }

    private void setupCarousel() {
        List<CarouselAdapter.CarouselItem> items = new ArrayList<>();
        // Correct Category Mapping: Each slide matches its image, title, and navigation target
        items.add(new CarouselAdapter.CarouselItem("WOMEN'S FASHION", "Premium Collection", "WOMEN'S STORE", R.drawable.girl6));
        items.add(new CarouselAdapter.CarouselItem("MEN'S FASHION", "New Arrivals", "MEN'S STORE", R.drawable.jacket1));
        items.add(new CarouselAdapter.CarouselItem("SHOES", "Trending Footwear", "SHOES", R.drawable.shoe9));
        items.add(new CarouselAdapter.CarouselItem("KIDS STORE", "Playful Styles", "KIDS STORE", R.drawable.kid1));
        items.add(new CarouselAdapter.CarouselItem("ACCESSORIES", "Essential Add-ons", "ACCESSORIES", R.drawable.es5));

        CarouselAdapter adapter = new CarouselAdapter(items, category -> {
            if (category != null) openCategory(category);
        });
        heroCarousel.setAdapter(adapter);

        // Standard transition to avoid overlapping text during fade animations
        heroCarousel.setPageTransformer(null);

        new TabLayoutMediator(carouselIndicator, heroCarousel, (tab, position) -> {}).attach();

        // Auto-scroll logic (every 4 seconds)
        carouselHandler.removeCallbacks(carouselRunnable);
        carouselRunnable = () -> {
            if (heroCarousel == null) return;
            int currentItem = heroCarousel.getCurrentItem();
            int nextItem = (currentItem + 1) % items.size();
            heroCarousel.setCurrentItem(nextItem, true);
            carouselHandler.postDelayed(carouselRunnable, 4000);
        };
        carouselHandler.postDelayed(carouselRunnable, 4000);
    }

    private void openCategory(String category) {
        Fragment productListFragment = ProductListFragment.newInstance(category);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, productListFragment)
                .addToBackStack(null)
                .commit();
    }

    private void populateCategory(GridLayout grid, LayoutInflater inflater, String category, int limit) {
        if (grid == null) return;
        grid.removeAllViews();
        
        List<ProductRepository.ProductItem> products = ProductRepository.getInstance().getProductsByCategory(category);
        grid.setVisibility(products.isEmpty() ? View.GONE : View.VISIBLE);

        int count = 0;
        for (ProductRepository.ProductItem product : products) {
            if (count >= limit) break;
            
            View productView = inflater.inflate(R.layout.item_product, grid, false);
            ImageView img = productView.findViewById(R.id.productImage);
            TextView name = productView.findViewById(R.id.productName);
            TextView price = productView.findViewById(R.id.productPrice);
            View overlay = productView.findViewById(R.id.outOfStockOverlay);

            name.setText(product.name);
            price.setText(product.price);

            int itemPlaceholder;
            String lowerName = product.name.toLowerCase();
            if (lowerName.contains("shoe") || lowerName.contains("sneaker") || lowerName.contains("boot")) {
                itemPlaceholder = R.drawable.ic_shoe_placeholder;
            } else if (lowerName.contains("dress") || lowerName.contains("skirt")) {
                itemPlaceholder = R.drawable.ic_dress_placeholder;
            } else {
                itemPlaceholder = R.drawable.ic_clothing_placeholder;
            }
            
            Glide.with(this)
                 .load(product.imageSource)
                 .placeholder(itemPlaceholder)
                 .error(itemPlaceholder)
                 .fitCenter()
                 .into(img);

            if (overlay != null) {
                overlay.setVisibility(product.inStock ? View.GONE : View.VISIBLE);
            }

            productView.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("product_name", product.name);
                intent.putExtra("product_price", product.price);
                intent.putExtra("product_category", product.category);
                intent.putExtra("product_placeholder", itemPlaceholder);
                intent.putExtra("is_in_stock", product.inStock);
                startActivity(intent);
            });

            grid.addView(productView);
            count++;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        carouselHandler.removeCallbacks(carouselRunnable);
    }
}
