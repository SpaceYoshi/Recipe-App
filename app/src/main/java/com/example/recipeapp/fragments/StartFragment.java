package com.example.recipeapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;
import com.example.recipeapp.recyclerviews.mealcategory.MealCategory;
import com.example.recipeapp.recyclerviews.mealcategory.MealCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class StartFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = requireView().findViewById(R.id.recycler_view_category);
        recyclerView.setHasFixedSize(false);

        List<MealCategory> mealCategoryList = new ArrayList<>();
        addTestData(mealCategoryList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MealCategoryAdapter(getContext(), mealCategoryList));
    }

    private void addTestData(List<MealCategory> mealCategoryList) {
        mealCategoryList.add(new MealCategory("Lekker", "Enorm lekker gerecht."));
        mealCategoryList.add(new MealCategory("Ook lekker", "Enorm lekker gerecht. AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
        mealCategoryList.add(new MealCategory("Baopaotisme", "Enorm lekker gerecht. Enorm lekker gerecht. Enorm lekker gerecht. Enorm lekker gerecht."));
        mealCategoryList.add(new MealCategory("Yooooooooooooooooooooooooooooooooo", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer elit odio, faucibus ut condimentum nec, consectetur sit amet dui. Vivamus elementum nunc ac tortor venenatis malesuada. Ut convallis tortor nec malesuada consectetur. Vivamus purus est, gravida vitae magna at, tempus ullamcorper purus. Curabitur sed erat eget urna accumsan volutpat. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vivamus ut tellus et nisl dictum gravida semper at est. In sodales lacus velit, ut efficitur tellus molestie nec. Aliquam mauris est, convallis at posuere at, porttitor non nulla. Pellentesque aliquam commodo lobortis. Etiam pellentesque lacus in ligula luctus facilisis. Integer vestibulum, dolor eget tristique dignissim, sem est ultricies lectus, a laoreet leo libero quis risus. Cras id condimentum mi, non blandit dolor."));
        mealCategoryList.add(new MealCategory("Nog veel gekker", "Enorm lekker gerecht. Enorm lekker gerecht."));
    }

}