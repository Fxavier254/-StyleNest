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
        if (title != null) {
            title.setText(categoryName);
        }

        ImageView backButton = view.findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        GridLayout grid = view.findViewById(R.id.productGrid);
        if (grid != null) {
            populateProducts(grid, inflater);
        }

        return view;
    }

    private void populateProducts(GridLayout grid, LayoutInflater inflater) {
        grid.removeAllViews();
        List<ProductRepository.ProductItem> products = ProductRepository.getInstance().getProductsByCategory(categoryName);
        
        if (products.isEmpty()) {
            View emptyView = inflater.inflate(R.layout.layout_empty_products, grid, false);
            grid.addView(emptyView);
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

            // Dynamic placeholder logic based on product name/type (Image-to-name validation)
            final int itemPlaceholder;
            String lowerName = product.name.toLowerCase();
            if (lowerName.contains("shoe") || lowerName.contains("sneaker") || lowerName.contains("boot") || 
                lowerName.contains("velcro") || lowerName.contains("runner") || lowerName.contains("trainer") || 
                lowerName.contains("oxford") || lowerName.contains("stiletto") || lowerName.contains("heel") || lowerName.contains("loafer")) {
                itemPlaceholder = R.drawable.ic_shoe_placeholder;
            } else if (lowerName.contains("dress") || lowerName.contains("skirt") || lowerName.contains("tutu")) {
                itemPlaceholder = R.drawable.ic_dress_placeholder;
            } else {
                itemPlaceholder = R.drawable.ic_clothing_placeholder;
            }

            // Using fitCenter() to ensure images are fully visible and not cropped, meeting professional requirements
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
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtra("product_name", product.name);
                intent.putExtra("product_price", product.price);
                intent.putExtra("product_category", product.category);
                intent.putExtra("is_in_stock", product.inStock);
                intent.putExtra("product_placeholder", itemPlaceholder);
                startActivity(intent);
            });

            // Admin toggle for stock status
            productView.setOnLongClickListener(v -> {
                product.inStock = !product.inStock;
                if (overlay != null) {
                    overlay.setVisibility(product.inStock ? View.GONE : View.VISIBLE);
                }
                String status = product.inStock ? "AVAILABLE" : "OUT OF STOCK";
                Toast.makeText(getContext(), product.name + " is now " + status, Toast.LENGTH_SHORT).show();
                return true;
            });

            grid.addView(productView);
        }
    }
}
