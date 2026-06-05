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

public class ProductListFragment extends Fragment {
    private String categoryName;

    public static ProductListFragment newInstance(String category) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryName = getArguments().getString("category");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        
        TextView title = view.findViewById(R.id.categoryTitle);
        title.setText(categoryName);

        ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        GridLayout grid = view.findViewById(R.id.productGrid);
        populateProducts(grid, inflater);

        return view;
    }

    private void populateProducts(GridLayout grid, LayoutInflater inflater) {
        List<ProductRepository.ProductItem> products = ProductRepository.getInstance().getProductsByCategory(categoryName);
        int placeholderRes = categoryName.contains("WOMEN") ? R.drawable.ic_dress_placeholder : 
                           categoryName.contains("SHOES") ? R.drawable.ic_shoe_placeholder : R.drawable.ic_clothing_placeholder;

        if (products.isEmpty()) {
            return;
        }

        for (ProductRepository.ProductItem product : products) {
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

            // Use live stock status from repository
            if (overlay != null) {
                overlay.setVisibility(product.inStock ? View.GONE : View.VISIBLE);
            }

            productView.setOnClickListener(v -> {
                // We open the detail screen regardless of stock status so the user can see it's "Out of Stock" there
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("product_name", product.name);
                intent.putExtra("product_price", product.price);
                intent.putExtra("product_category", product.category);
                intent.putExtra("product_in_stock", product.inStock);
                
                if (product.imageSource instanceof String) {
                    intent.putExtra("product_url", (String) product.imageSource);
                } else if (product.imageSource instanceof Integer) {
                    intent.putExtra("product_image_res", (Integer) product.imageSource);
                }
                
                intent.putExtra("product_placeholder", placeholderRes);
                startActivity(intent);
            });

            grid.addView(productView);
        }
    }
}
