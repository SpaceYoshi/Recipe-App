package com.example.recipeapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;
import com.example.recipeapp.api.APIManager;
import com.example.recipeapp.api.listeners.APIListenerArea;
import com.example.recipeapp.api.listeners.APIListenerCategory;
import com.example.recipeapp.api.listeners.APIListenerMeal;
import com.example.recipeapp.utility.OnItemClickListener;
import com.example.recipeapp.views.area.Area;
import com.example.recipeapp.views.area.AreaSpinnerAdapter;
import com.example.recipeapp.views.category.Category;
import com.example.recipeapp.views.category.CategorySpinnerAdapter;
import com.example.recipeapp.views.meal.Meal;
import com.example.recipeapp.views.meal.MealAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements OnItemClickListener, APIListenerArea, APIListenerCategory, APIListenerMeal {
    private static final String LOG_TAG = SearchFragment.class.getName();
    private static final List<Category> CATEGORY_LIST = new ArrayList<>(20);
    private static final List<Area> AREA_LIST = new ArrayList<>(40);
    private static final List<Meal> MEAL_LIST = new ArrayList<>();
    private final APIManager apiManager = new APIManager(getContext(), this, this, this);
    private static boolean firstStart = true;

    // Adapters
    private MealAdapter mealAdapter;
    private CategorySpinnerAdapter categorySpinnerAdapter;
    private AreaSpinnerAdapter areaSpinnerAdapter;

    // Views
    private SearchView searchView;
    private CheckBox checkBoxIngredient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (firstStart) {
            apiManager.getCategories();
            apiManager.getAreas();

            firstStart = false;
        }

        // RecyclerView
        RecyclerView recyclerView = requireView().findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mealAdapter = new MealAdapter(getContext(), MEAL_LIST, this);
        recyclerView.setAdapter(mealAdapter);

        // SearchView
        searchView = requireView().findViewById(R.id.search_view);
        searchView.setOnClickListener(v -> searchView.setIconified(false));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onSearch(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Checkbox
        checkBoxIngredient = requireView().findViewById(R.id.search_checkBox_ingredient);

        // Buttons
        Button searchButton = requireView().findViewById(R.id.search_search_button);
        Button searchRandomButton = recyclerView.findViewById(R.id.search_random_button);
        searchButton.setOnClickListener(this::onSearchButton);
        searchRandomButton.setOnClickListener(this::onRandomButton);

        // Spinners
        Spinner spinnerCategory = requireView().findViewById(R.id.search_spinner_category);
        categorySpinnerAdapter = new CategorySpinnerAdapter(requireContext(), CATEGORY_LIST);
        spinnerCategory.setAdapter(categorySpinnerAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resetMealList();
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                apiManager.getMealsCategory(selectedCategory.getName());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Spinner spinnerArea = requireView().findViewById(R.id.search_spinner_area);
        areaSpinnerAdapter = new AreaSpinnerAdapter(requireContext(), AREA_LIST);
        spinnerArea.setAdapter(areaSpinnerAdapter);
        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resetMealList();
                Area selectedArea = (Area) parent.getItemAtPosition(position);
                apiManager.getMealsArea(selectedArea.getName());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onSearchButton(View view) {
        onSearch(searchView.getQuery().toString());
    }

    public void onRandomButton(View view) {
        resetMealList();
        apiManager.getMealRandom();
    }

    private void onSearch(String query) {
        resetMealList();
        if (checkBoxIngredient.isChecked()) apiManager.getMealsPrimaryIngredient(query);
        else apiManager.getMealsName(query);
    }

    private void resetMealList() {
        MEAL_LIST.clear();
    }

    @Override
    public void onAreaAvailable(Area area) {
        AREA_LIST.add(area);
        areaSpinnerAdapter.notifyDataSetChanged(); // TODO check if this is sufficient to refresh
    }

    @Override
    public void onAreaError(Error error) {
        Log.e(LOG_TAG, error.toString());
    }

    @Override
    public void onCategoryAvailable(Category category) {
        CATEGORY_LIST.add(category);
        categorySpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryError(Error error) {
        Log.e(LOG_TAG, error.toString());
    }

    @SuppressLint("NotifyDataSetChanged") // could be improved
    @Override
    public void onMealAvailable(Meal meal) {
        MEAL_LIST.add(meal);
        mealAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMealError(Error error) {
        Log.e(LOG_TAG, error.toString());
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Log.i(LOG_TAG, "onItemClick called for position " + clickedPosition);

        // TODO
    }

}