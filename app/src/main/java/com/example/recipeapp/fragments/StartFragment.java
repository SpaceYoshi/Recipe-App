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
import com.example.recipeapp.recyclerviews.category.Category;
import com.example.recipeapp.recyclerviews.category.CategoryAdapter;
import com.example.recipeapp.utility.api.listeners.APIListenerCategory;
import com.example.recipeapp.utility.api.APIManager;

import java.util.ArrayList;
import java.util.List;

public class StartFragment extends Fragment implements CategoryAdapter.OnItemClickListener, APIListenerCategory {
    private static final String LOG_TAG = StartFragment.class.getName();
    private static List<Category> categoryList = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private static boolean firstStart = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (firstStart) {
//            addTestData(mealCategoryList);
            APIManager apiManager = new APIManager(getContext(), null, this, null, null);
            apiManager.getCategories();

            firstStart = false;
        }
        RecyclerView recyclerView = requireView().findViewById(R.id.start_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryAdapter = new CategoryAdapter(getContext(), categoryList, this);
        recyclerView.setAdapter(categoryAdapter);
    }

//    private void addTestData(List<Category> categoryList) {
//        categoryList.add(new Category("Lekker", "Enorm lekker gerecht.", "https://www.themealdb.com/images/ingredients/Lime-Small.png"));
//        categoryList.add(new Category("Ook lekker", "Enorm lekker gerecht. AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "https://www.themealdb.com/images/ingredients/Lime-Small.png"));
//        categoryList.add(new Category("Baopaotisme", "Enorm lekker gerecht. Enorm lekker gerecht. Enorm lekker gerecht. Enorm lekker gerecht.", "https://www.themealdb.com/images/ingredients/Lime-Small.png"));
//        categoryList.add(new Category("Yooooooooooooooooooooooooooooooooo", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer elit odio, faucibus ut condimentum nec, consectetur sit amet dui. Vivamus elementum nunc ac tortor venenatis malesuada. Ut convallis tortor nec malesuada consectetur. Vivamus purus est, gravida vitae magna at, tempus ullamcorper purus. Curabitur sed erat eget urna accumsan volutpat. Interdum et malesuada fames ac ante ipsum primis in faucibus. Vivamus ut tellus et nisl dictum gravida semper at est. In sodales lacus velit, ut efficitur tellus molestie nec. Aliquam mauris est, convallis at posuere at, porttitor non nulla. Pellentesque aliquam commodo lobortis. Etiam pellentesque lacus in ligula luctus facilisis. Integer vestibulum, dolor eget tristique dignissim, sem est ultricies lectus, a laoreet leo libero quis risus. Cras id condimentum mi, non blandit dolor.",
//                "https://www.themealdb.com/images/ingredients/Lime-Small.png"));
//        categoryList.add(new Category("Nog veel gekker", "Enorm lekker gerecht. Enorm lekker gerecht.", "https://www.themealdb.com/images/ingredients/Lime-Small.png"));
//    }

    @Override
    public void onItemClick(int clickedPosition) {
        Log.i(LOG_TAG, "onItemClick called for position " + clickedPosition);
        // TODO Create an explicit intent to open the detail activity

        // TODO Pass the photo that was selected on to the detail activity by putting it inside the intent

        // TODO Start the activity using the intent
    }

    @Override
    public void onCategoryAvailable(Category category) {
        categoryList.add(category);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryError(Error error) {
        Log.e(LOG_TAG, error.toString());
    }

}