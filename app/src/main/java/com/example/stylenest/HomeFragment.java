package com.example.stylenest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import java.util.List;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        GridLayout mensGrid = view.findViewById(R.id.mensGrid);
        GridLayout womensGrid = view.findViewById(R.id.womensGrid);
        GridLayout essentialsGrid = view.findViewById(R.id.essentialsGrid);

        // Fetch products from central repository
        populateCategory(mensGrid, inflater, "MEN'S STORE", 6);
        populateCategory(womensGrid, inflater, "WOMEN'S STORE", 4);
        populateCategory(essentialsGrid, inflater, "ACCESSORIES", 6);

        return view;
    }

    private void populateCategory(GridLayout grid, LayoutInflater inflater, String category, int limit) {
        List<ProductRepository.ProductItem> products = ProductRepository.getInstance().getProductsByCategory(category);
        int placeholderRes = category.contains("WOMEN") ? R.drawable.ic_dress_placeholder : 
                           category.contains("SHOES") ? R.drawable.ic_shoe_placeholder : R.drawable.ic_clothing_placeholder;

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
            
            Glide.with(this)
                 .load(product.imageSource)
                 .placeholder(placeholderRes)
                 .centerInside()
                 .into(img);

            // Directly using the Admin Desk status
            if (overlay != null) {
                overlay.setVisibility(product.inStock ? View.GONE : View.VISIBLE);
            }

            productView.setOnClickListener(v -> {
                if (!product.inStock) {
                    Toast.makeText(getActivity(), product.name + " is out of stock (Controlled by Admin)", Toast.LENGTH_SHORT).show();
                    return; 
                }
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("product_name", product.name);
                intent.putExtra("product_price", product.price);
                intent.putExtra("product_in_stock", product.inStock);
                intent.putExtra("product_category", product.category);
                
                if (product.imageSource instanceof String) {
                    intent.putExtra("product_url", (String) product.imageSource);
                } else if (product.imageSource instanceof Integer) {
                    intent.putExtra("product_image_res", (Integer) product.imageSource);
                }
                
                intent.putExtra("product_placeholder", placeholderRes);
                startActivity(intent);
            });

            grid.addView(productView);
            count++;
        }
    }
}
