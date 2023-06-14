package com.example.recipeapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;
import com.example.recipeapp.views.meal.Meal;
import com.example.recipeapp.views.meal.MealAdapter;
import com.example.recipeapp.utility.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment implements OnItemClickListener {
    private static final String LOG_TAG = FavouritesFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = requireView().findViewById(R.id.favourites_recycler_view);

        List<Meal> mealList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MealAdapter(getContext(), mealList, this));
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Log.i(LOG_TAG, "onItemClick called for position " + clickedPosition);

    }

}