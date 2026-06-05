package com.example.stylenest;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

public class AdminDeskFragment extends Fragment {

    private List<ProductRepository.ProductItem> productList;
    private AdminProductAdapter adapter;
    private TextView productCountText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_desk, container, false);

        ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        productCountText = view.findViewById(R.id.productCountText);
        RecyclerView recyclerView = view.findViewById(R.id.adminProductRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        // Use central repository so changes affect the whole app
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
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_product, null);
        AlertDialog dialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen).setView(dialogView).create();

        TextInputEditText etName = dialogView.findViewById(R.id.etProductName);
        TextInputEditText etPrice = dialogView.findViewById(R.id.etProductPrice);
        TextInputEditText etCategory = dialogView.findViewById(R.id.etProductCategory);
        MaterialButton btnSave = dialogView.findViewById(R.id.btnSaveProduct);

        btnSave.setOnClickListener(v -> {
            Editable nameEditable = etName.getText();
            Editable priceEditable = etPrice.getText();
            Editable categoryEditable = etCategory.getText();

            String name = nameEditable != null ? nameEditable.toString().trim() : "";
            String price = priceEditable != null ? priceEditable.toString().trim() : "";
            String category = categoryEditable != null ? categoryEditable.toString().trim() : "";

            if (!name.isEmpty() && !price.isEmpty() && !category.isEmpty()) {
                // Default colors for manually added products
                int[] defaultColors = {Color.BLACK, Color.GRAY};
                String[] defaultColorNames = {"Black", "Grey"};
                
                ProductRepository.ProductItem newItem = new ProductRepository.ProductItem(
                        name, 
                        price, 
                        category, 
                        R.drawable.ic_clothing_placeholder, 
                        true,
                        defaultColors,
                        defaultColorNames
                );
                
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
