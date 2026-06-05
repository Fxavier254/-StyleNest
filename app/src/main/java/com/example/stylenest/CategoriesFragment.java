package com.example.stylenest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CategoriesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        view.findViewById(R.id.category_mens).setOnClickListener(v -> openProductList("MEN'S STORE"));
        view.findViewById(R.id.category_womens).setOnClickListener(v -> openProductList("WOMEN'S STORE"));
        view.findViewById(R.id.category_shoes).setOnClickListener(v -> openProductList("SHOES"));
        view.findViewById(R.id.category_accessories).setOnClickListener(v -> openProductList("ACCESSORIES"));
        view.findViewById(R.id.category_kids).setOnClickListener(v -> openProductList("KIDS STORE"));

        return view;
    }

    private void openProductList(String category) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ProductListFragment.newInstance(category))
                .addToBackStack(null)
                .commit();
    }
}