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

    private String selectedSize = "M"; 
    private String selectedColor = ""; 
    private ProductRepository.ProductItem product;
    private ImageView imageIv;
    private TextView stockTv;
    private MaterialButton addToCart;
    private MaterialButton buyNow;
    private LinearLayout imageGalleryContainer;

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

        String productName = getIntent().getStringExtra("product_name");
        
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
        imageIv = findViewById(R.id.detailProductImage);
        stockTv = findViewById(R.id.stockStatusDetail);
        addToCart = findViewById(R.id.addToCartButton);
        buyNow = findViewById(R.id.buyNowButton);
        imageGalleryContainer = findViewById(R.id.imageGalleryContainer);

        nameTv.setText(product.name);
        priceTv.setText(product.price);
        
        setupImageGallery();
        setupSizeSelection(product.category);
        setupColorSelection();

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

    private void setupImageGallery() {
        if (imageGalleryContainer == null || product.allImages == null || product.allImages.size() <= 1) {
            View scroll = findViewById(R.id.imageGalleryScroll);
            if (scroll != null) scroll.setVisibility(View.GONE);
            return;
        }

        imageGalleryContainer.removeAllViews();
        float density = getResources().getDisplayMetrics().density;
        int sizePx = (int) (60 * density);
        int marginPx = (int) (8 * density);

        for (Object imgObj : product.allImages) {
            ImageView thumb = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(sizePx, sizePx);
            lp.setMargins(0, 0, marginPx, 0);
            thumb.setLayoutParams(lp);
            thumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumb.setBackgroundColor(Color.parseColor("#1A1A1A"));
            thumb.setPadding(2, 2, 2, 2);
            
            Glide.with(this).load(imgObj).into(thumb);
            
            thumb.setOnClickListener(v -> {
                Glide.with(this).load(imgObj).placeholder(R.drawable.ic_clothing_placeholder).into(imageIv);
                for (int i = 0; i < imageGalleryContainer.getChildCount(); i++) {
                    imageGalleryContainer.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
                v.setBackgroundColor(Color.parseColor("#CCFF00"));
            });
            
            imageGalleryContainer.addView(thumb);
        }
    }

    private void updateAvailability(boolean isAvailable) {
        if (stockTv != null) {
            stockTv.setVisibility(View.VISIBLE);
            if (!isAvailable) {
                stockTv.setText("OUT OF STOCK");
                stockTv.setTextColor(Color.RED);
                addToCart.setEnabled(false);
                addToCart.setAlpha(0.3f);
                buyNow.setEnabled(false);
                buyNow.setAlpha(0.3f);
                buyNow.setText("UNAVAILABLE");
            } else {
                stockTv.setText("IN STOCK");
                stockTv.setTextColor(Color.parseColor("#CCFF00"));
                addToCart.setEnabled(true);
                addToCart.setAlpha(1.0f);
                buyNow.setEnabled(true);
                buyNow.setAlpha(1.0f);
                buyNow.setText("BUY NOW");
            }
        }
    }

    private void addItemToCart() {
        Object currentImage = product.colorImageMap.get(selectedColor);
        if (currentImage == null) currentImage = product.imageSource;
        CartManager.getInstance().addItem(product.name, product.price, selectedSize, selectedColor, currentImage, R.drawable.ic_clothing_placeholder);
    }

    private void setupColorSelection() {
        LinearLayout colorContainer = findViewById(R.id.colorContainer);
        colorContainer.removeAllViews();

        if (product.colors == null || product.colors.length == 0) {
            findViewById(R.id.tvColorLabel).setVisibility(View.GONE);
            selectedColor = "Default";
            updateAvailability(product.inStock);
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
                for (int j = 0; j < colorContainer.getChildCount(); j++) {
                    View cv = colorContainer.getChildAt(j);
                    cv.setScaleX(1.0f);
                    cv.setScaleY(1.0f);
                    ((GradientDrawable)cv.getBackground()).setStroke(2, Color.WHITE);
                }
                v.setScaleX(1.25f);
                v.setScaleY(1.25f);
                ((GradientDrawable)v.getBackground()).setStroke(4, Color.parseColor("#CCFF00"));
                selectedColor = colorName;

                // Color-specific Image Swap
                Object colorImage = product.colorImageMap.get(colorName);
                if (colorImage != null) {
                    Glide.with(this).load(colorImage).placeholder(R.drawable.ic_clothing_placeholder).into(imageIv);
                }

                // Color-specific Stock logic
                Boolean colorStock = product.colorStockMap.get(colorName);
                updateAvailability(colorStock != null ? (product.inStock && colorStock) : product.inStock);
            });
            
            colorContainer.addView(colorView);
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
