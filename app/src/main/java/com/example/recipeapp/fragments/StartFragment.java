package com.example.recipeapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;
import com.example.recipeapp.api.APIManager;
import com.example.recipeapp.api.listeners.APIListenerCategory;
import com.example.recipeapp.utility.OnItemClickListener;
import com.example.recipeapp.views.category.Category;
import com.example.recipeapp.views.category.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class StartFragment extends Fragment implements OnItemClickListener, APIListenerCategory {
    private static final String LOG_TAG = StartFragment.class.getName();
    private static final List<Category> CATEGORY_LIST = new ArrayList<>(20);
    private static boolean firstStart = true;
    private CategoryAdapter categoryAdapter;
    private ViewGroup container;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.container = container;
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (firstStart) {
            APIManager apiManager = new APIManager(getContext(), null, this, null);
            apiManager.getCategories();

            firstStart = false;
        }

        // RecyclerView
        RecyclerView recyclerViewCategory = requireView().findViewById(R.id.start_recycler_view);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryAdapter = new CategoryAdapter(getContext(), CATEGORY_LIST, this);
        recyclerViewCategory.setAdapter(categoryAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onCategoryAvailable(Category category) {
        CATEGORY_LIST.add(category);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryError(Error error) {
        Log.e(LOG_TAG, error.toString());
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Log.i(LOG_TAG, "onItemClick called for position " + clickedPosition);

        SearchFragment searchFragment = new SearchFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(container.getId(), searchFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        searchFragment.selectCategory(CATEGORY_LIST.get(clickedPosition));
    }

}