package com.example.stylenest;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private String selectedSize = "M"; // Default selection
    private String selectedColor = ""; // Selected color name
    private ProductRepository.ProductItem product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        // Get product name from intent to look it up in repo
        String productName = getIntent().getStringExtra("product_name");
        
        // Fetch the most up-to-date data from the central repository (which Admin toggles)
        List<ProductRepository.ProductItem> allItems = ProductRepository.getInstance().getAllProducts();
        for (ProductRepository.ProductItem item : allItems) {
            if (item.name.equals(productName)) {
                product = item;
                break;
            }
        }

        if (product == null) {
            finish();
            return;
        }

        TextView nameTv = findViewById(R.id.detailProductName);
        TextView priceTv = findViewById(R.id.detailProductPrice);
        ImageView imageIv = findViewById(R.id.detailProductImage);
        TextView stockTv = findViewById(R.id.stockStatusDetail);

        nameTv.setText(product.name);
        priceTv.setText(product.price);
        
        // Display stock status based on Repository (Admin Desk control)
        if (stockTv != null) {
            if (!product.inStock) {
                stockTv.setVisibility(View.VISIBLE);
                stockTv.setText("OUT OF STOCK");
                stockTv.setTextColor(Color.RED);
            } else {
                stockTv.setVisibility(View.VISIBLE);
                stockTv.setText("IN STOCK");
                stockTv.setTextColor(Color.parseColor("#CCFF00"));
            }
        }

        Glide.with(this)
             .load(product.imageSource)
             .placeholder(R.drawable.ic_clothing_placeholder)
             .centerInside()
             .into(imageIv);

        setupSizeSelection(product.category);
        setupColorSelection();

        MaterialButton addToCart = findViewById(R.id.addToCartButton);
        MaterialButton buyNow = findViewById(R.id.buyNowButton);

        // If marked out of stock by Admin, disable buttons
        if (!product.inStock) {
            addToCart.setEnabled(false);
            addToCart.setAlpha(0.3f);
            buyNow.setEnabled(false);
            buyNow.setAlpha(0.3f);
            buyNow.setText("UNAVAILABLE");
        }

        addToCart.setOnClickListener(v -> {
            addItemToCart();
            String message = getString(R.string.added_to_cart, product.name, selectedSize, selectedColor);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        buyNow.setOnClickListener(v -> {
            addItemToCart();
            startActivity(new Intent(this, CheckoutActivity.class));
        });
    }

    private void addItemToCart() {
        CartManager.getInstance().addItem(product.name, product.price, selectedSize, selectedColor, product.imageSource, R.drawable.ic_clothing_placeholder);
    }

    private void setupColorSelection() {
        LinearLayout colorContainer = findViewById(R.id.colorContainer);
        colorContainer.removeAllViews(); // Clear existing layout placeholders

        if (product.colors == null || product.colors.length == 0) {
            findViewById(R.id.tvColorLabel).setVisibility(View.GONE);
            selectedColor = "Default";
            return;
        }

        float density = getResources().getDisplayMetrics().density;
        int sizePx = (int) (40 * density);
        int marginPx = (int) (16 * density);

        for (int i = 0; i < product.colors.length; i++) {
            final int colorInt = product.colors[i];
            final String colorName = product.colorNames[i];
            
            View colorView = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizePx, sizePx);
            params.setMargins(0, 0, marginPx, 0);
            colorView.setLayoutParams(params);
            
            GradientDrawable shape = new GradientDrawable();
            shape.setShape(GradientDrawable.OVAL);
            shape.setColor(colorInt);
            shape.setStroke(2, Color.WHITE);
            colorView.setBackground(shape);
            
            colorView.setOnClickListener(v -> {
                // Deselect others
                for (int j = 0; j < colorContainer.getChildCount(); j++) {
                    View cv = colorContainer.getChildAt(j);
                    cv.setScaleX(1.0f);
                    cv.setScaleY(1.0f);
                    ((GradientDrawable)cv.getBackground()).setStroke(2, Color.WHITE);
                }
                // Select current
                v.setScaleX(1.25f);
                v.setScaleY(1.25f);
                ((GradientDrawable)v.getBackground()).setStroke(4, Color.parseColor("#CCFF00"));
                selectedColor = colorName;
            });
            
            colorContainer.addView(colorView);
            
            // Select first by default
            if (i == 0) colorView.performClick();
        }
    }

    private void setupSizeSelection(String category) {
        TextView tvSizeLabel = findViewById(R.id.tvSizeLabel);
        View sizeScrollView = findViewById(R.id.sizeScrollView);

        if ("ACCESSORIES".equals(category)) {
            if (tvSizeLabel != null) tvSizeLabel.setVisibility(View.GONE);
            if (sizeScrollView != null) sizeScrollView.setVisibility(View.GONE);
            selectedSize = getString(R.string.size_standard);
            return;
        }

        TextView[] sizeViews = {
            findViewById(R.id.size1), findViewById(R.id.size2), findViewById(R.id.size3),
            findViewById(R.id.size4), findViewById(R.id.size5), findViewById(R.id.size6)
        };

        if ("SHOES".equals(category)) {
            if (sizeViews[0] != null) sizeViews[0].setText(R.string.size_38);
            if (sizeViews[1] != null) sizeViews[1].setText(R.string.size_39);
            if (sizeViews[2] != null) sizeViews[2].setText(R.string.size_40);
            if (sizeViews[3] != null) sizeViews[3].setText(R.string.size_41);
            if (sizeViews[4] != null) sizeViews[4].setText(R.string.size_42);
            if (sizeViews[5] != null) sizeViews[5].setText(R.string.size_43);
        } else {
            if (sizeViews[0] != null) sizeViews[0].setText(R.string.size_s);
            if (sizeViews[1] != null) sizeViews[1].setText(R.string.size_m);
            if (sizeViews[2] != null) sizeViews[2].setText(R.string.size_l);
            if (sizeViews[3] != null) sizeViews[3].setText(R.string.size_xl);
            if (sizeViews[4] != null) sizeViews[4].setText(R.string.size_xxl);
            if (sizeViews[5] != null) sizeViews[5].setText(R.string.size_3xl);
        }

        for (TextView sv : sizeViews) {
            if (sv != null) {
                sv.setOnClickListener(v -> {
                    for (TextView other : sizeViews) {
                        if (other != null) {
                            other.setBackgroundColor(Color.parseColor("#121212"));
                            other.setTextColor(Color.WHITE);
                        }
                    }
                    v.setBackgroundColor(Color.parseColor("#CCFF00"));
                    ((TextView)v).setTextColor(Color.BLACK);
                    selectedSize = ((TextView)v).getText().toString();
                });
            }
        }
        
        if (sizeViews[1] != null) sizeViews[1].performClick();
    }
}
