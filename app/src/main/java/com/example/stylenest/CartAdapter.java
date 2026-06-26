package com.example.stylenest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartManager.Product> cartItems;
    private OnCartChangeListener listener;

    public interface OnCartChangeListener {
        void onCartChanged();
    }

    public CartAdapter(List<CartManager.Product> cartItems, OnCartChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartManager.Product product = cartItems.get(position);
        holder.name.setText(product.name);
        holder.price.setText(product.price);
        holder.size.setText("Size: " + product.size);
        holder.color.setText("Color: " + product.color);
        
        // Professional Touch: Using centerCrop for uniform cart item appearance
        Glide.with(holder.itemView.getContext())
             .load(product.imageSource)
             .placeholder(product.placeholderRes)
             .error(product.placeholderRes)
             .centerCrop()
             .into(holder.image);

        holder.removeBtn.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (currentPos != RecyclerView.NO_POSITION) {
                cartItems.remove(currentPos);
                notifyItemRemoved(currentPos);
                notifyItemRangeChanged(currentPos, cartItems.size());
                if (listener != null) listener.onCartChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView image, removeBtn;
        TextView name, price, size, color;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cartProductImage);
            name = itemView.findViewById(R.id.cartProductName);
            price = itemView.findViewById(R.id.cartProductPrice);
            size = itemView.findViewById(R.id.cartProductSize);
            color = itemView.findViewById(R.id.cartProductColor);
            removeBtn = itemView.findViewById(R.id.removeButton);
        }
    }
}