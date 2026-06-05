package com.example.stylenest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;

public class CartFragment extends Fragment implements CartAdapter.OnCartChangeListener {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private TextView emptyText, totalValue;
    private MaterialButton checkoutBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.cartRecyclerView);
        emptyText = view.findViewById(R.id.emptyCartText);
        totalValue = view.findViewById(R.id.cartTotalValue);
        checkoutBtn = view.findViewById(R.id.checkoutButton);

        setupRecyclerView();
        updateUI();

        checkoutBtn.setOnClickListener(v -> {
            if (!CartManager.getInstance().getCartItems().isEmpty()) {
                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(CartManager.getInstance().getCartItems(), this);
        recyclerView.setAdapter(adapter);
    }

    private void updateUI() {
        boolean isEmpty = CartManager.getInstance().getCartItems().isEmpty();
        if (isEmpty) {
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            totalValue.setText("KES 0");
            checkoutBtn.setEnabled(false);
            checkoutBtn.setAlpha(0.5f);
        } else {
            emptyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            totalValue.setText(CartManager.getInstance().getTotal());
            checkoutBtn.setEnabled(true);
            checkoutBtn.setAlpha(1.0f);
        }
    }

    @Override
    public void onCartChanged() {
        updateUI();
    }
}