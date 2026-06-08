package com.example.stylenest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class AdminDeskFragment extends Fragment {

    private List<ProductRepository.ProductItem> productList;
    private AdminProductAdapter adapter;
    private TextView productCountText;
    private List<Object> selectedImages = new ArrayList<>();
    private LinearLayout imagePreviewContainer;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        addImageToPreview(imageUri);
                    }
                }
            }
    );

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                    if (photo != null) {
                        addImageToPreview(photo);
                    }
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_desk, container, false);

        ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        productCountText = view.findViewById(R.id.productCountText);
        RecyclerView recyclerView = view.findViewById(R.id.adminProductRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        productList = ProductRepository.getInstance().getAllProducts();
        updateCount();
        
        adapter = new AdminProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.addProductButton).setOnClickListener(v -> showAddProductDialog());

        return view;
    }

    private void updateCount() {
        if (productCountText != null && productList != null) {
            productCountText.setText(getString(R.string.product_count, productList.size()));
        }
    }

    private void showAddProductDialog() {
        selectedImages.clear();
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_product, null);
        AlertDialog dialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen).setView(dialogView).create();

        TextInputEditText etName = dialogView.findViewById(R.id.etProductName);
        TextInputEditText etPrice = dialogView.findViewById(R.id.etProductPrice);
        TextInputEditText etCategory = dialogView.findViewById(R.id.etProductCategory);
        imagePreviewContainer = dialogView.findViewById(R.id.imagePreviewContainer);
        MaterialButton btnGallery = dialogView.findViewById(R.id.btnGallery);
        MaterialButton btnCamera = dialogView.findViewById(R.id.btnCamera);
        MaterialButton btnSave = dialogView.findViewById(R.id.btnSaveProduct);

        btnGallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intent);
        });

        btnCamera.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> {
            Editable nameEdit = etName.getText();
            Editable priceEdit = etPrice.getText();
            Editable catEdit = etCategory.getText();

            String name = (nameEdit != null) ? nameEdit.toString().trim() : "";
            String price = (priceEdit != null) ? priceEdit.toString().trim() : "";
            String category = (catEdit != null) ? catEdit.toString().trim() : "";

            if (!name.isEmpty() && !price.isEmpty() && !category.isEmpty()) {
                Object mainImage = selectedImages.isEmpty() ? R.drawable.ic_clothing_placeholder : selectedImages.get(0);
                
                // Default colors for manually added products
                int[] defaultColors = {Color.BLACK, Color.GRAY, Color.WHITE, Color.BLUE};
                String[] defaultColorNames = {"Black", "Grey", "White", "Blue"};
                
                // FIXED: Use the correct 7-argument constructor
                ProductRepository.ProductItem newItem = new ProductRepository.ProductItem(
                        name, price, category, mainImage, true, defaultColors, defaultColorNames
                );
                
                // Add all selected images to the gallery
                if (!selectedImages.isEmpty()) {
                    newItem.allImages.clear();
                    newItem.allImages.addAll(selectedImages);
                    // Map first few images to default colors
                    for (int i = 0; i < selectedImages.size() && i < defaultColorNames.length; i++) {
                        newItem.colorImageMap.put(defaultColorNames[i], selectedImages.get(i));
                    }
                }
                
                ProductRepository.getInstance().getAllProducts().add(0, newItem);
                adapter.notifyItemInserted(0);
                updateCount();
                dialog.dismiss();
                Toast.makeText(getContext(), "Product Added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void addImageToPreview(Object image) {
        selectedImages.add(image);
        if (imagePreviewContainer != null) {
            ImageView iv = new ImageView(getContext());
            int size = (int) (80 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
            lp.setMargins(0, 0, (int) (8 * getResources().getDisplayMetrics().density), 0);
            iv.setLayoutParams(lp);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(image).into(iv);
            imagePreviewContainer.addView(iv);
        }
    }

    class AdminProductAdapter extends RecyclerView.Adapter<AdminProductAdapter.ViewHolder> {
        List<ProductRepository.ProductItem> products;

        AdminProductAdapter(List<ProductRepository.ProductItem> products) {
            this.products = products;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_product, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProductRepository.ProductItem p = products.get(position);
            holder.name.setText(p.name);
            holder.price.setText(p.price);
            holder.category.setText(p.category);
            holder.stockSwitch.setOnCheckedChangeListener(null); 
            holder.stockSwitch.setChecked(p.inStock);
            holder.stockStatus.setText(p.inStock ? "IN STOCK" : "OUT OF STOCK");
            holder.stockStatus.setTextColor(p.inStock ? 0xFFCCFF00 : 0xFFFF4444);

            Glide.with(holder.itemView.getContext())
                 .load(p.imageSource)
                 .placeholder(R.drawable.ic_clothing_placeholder)
                 .centerCrop()
                 .into(holder.image);

            holder.stockSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                p.inStock = isChecked;
                if (p.colorStockMap != null) {
                    for (String color : p.colorNames) {
                        p.colorStockMap.put(color, isChecked);
                    }
                }
                holder.stockStatus.setText(isChecked ? "IN STOCK" : "OUT OF STOCK");
                holder.stockStatus.setTextColor(isChecked ? 0xFFCCFF00 : 0xFFFF4444);
                Toast.makeText(getContext(), p.name + " is now " + (isChecked ? "In Stock" : "Out of Stock"), Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount() {
            return products != null ? products.size() : 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView name, price, category, stockStatus;
            SwitchMaterial stockSwitch;

            ViewHolder(View v) {
                super(v);
                image = v.findViewById(R.id.adminProductImage);
                name = v.findViewById(R.id.adminProductName);
                price = v.findViewById(R.id.adminProductPrice);
                category = v.findViewById(R.id.adminProductCategory);
                stockStatus = v.findViewById(R.id.stockStatusText);
                stockSwitch = v.findViewById(R.id.stockSwitch);
            }
        }
    }
}
