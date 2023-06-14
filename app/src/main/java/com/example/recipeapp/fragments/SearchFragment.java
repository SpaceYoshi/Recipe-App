package com.example.recipeapp.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeapp.R;
import com.example.recipeapp.api.APIManager;
import com.example.recipeapp.api.MealSearchType;
import com.example.recipeapp.api.listeners.APIListenerArea;
import com.example.recipeapp.api.listeners.APIListenerCategory;
import com.example.recipeapp.api.listeners.APIListenerMeal;
import com.example.recipeapp.utility.OnItemClickListener;
import com.example.recipeapp.views.area.Area;
import com.example.recipeapp.views.area.AreaSpinnerAdapter;
import com.example.recipeapp.views.category.Category;
import com.example.recipeapp.views.category.CategorySpinnerAdapter;
import com.example.recipeapp.views.ingredient.IngredientAdapter;
import com.example.recipeapp.views.meal.Meal;
import com.example.recipeapp.views.meal.MealAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements OnItemClickListener, APIListenerArea, APIListenerCategory, APIListenerMeal {
    private static final String LOG_TAG = SearchFragment.class.getName();
    private static final List<Category> CATEGORY_LIST = new ArrayList<>(20);
    private static final List<Area> AREA_LIST = new ArrayList<>(40);
    private static final List<Meal> MEAL_LIST = new ArrayList<>();
    private APIManager apiManager;
    private static boolean firstStart = true;
    private LayoutInflater inflater;
    private ViewGroup container;

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
        this.inflater = inflater;
        this.container = container;
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        apiManager = new APIManager(getContext(), this, this, this);
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

        // Search & Random button
        Button searchButton = requireView().findViewById(R.id.search_search_button);
        Button searchRandomButton = requireView().findViewById(R.id.search_random_button);
        searchButton.setOnClickListener(this::onSearchButton);
        searchRandomButton.setOnClickListener(this::onRandomButton);

        // Spinners TODO: find a way to stop the spinners from activating on init
        Spinner spinnerCategory = requireView().findViewById(R.id.search_spinner_category);
        categorySpinnerAdapter = new CategorySpinnerAdapter(requireContext(), CATEGORY_LIST);
        spinnerCategory.setAdapter(categorySpinnerAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resetMealList();
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                if (selectedCategory == null) return;
                selectCategory(selectedCategory);
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
                if (selectedArea == null) return;
                apiManager.getMeals(MealSearchType.SHORT_AREA, selectedArea.getName());
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
        apiManager.getMeals(MealSearchType.LONG_RANDOM);
    }

    private void onSearch(String query) {
        resetMealList();
        if (checkBoxIngredient.isChecked()) apiManager.getMeals(MealSearchType.SHORT_PRIMARY_INGREDIENT, query);
        else apiManager.getMeals(MealSearchType.LONG_NAME, query);
    }

    private void resetMealList() {
        MEAL_LIST.clear();
        Log.d(LOG_TAG, "Meal list reset.");
    }

    public void selectCategory(Category category) {
        apiManager.getMeals(MealSearchType.SHORT_CATEGORY, category.getName());
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

    @SuppressLint("NotifyDataSetChanged")
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

        Meal selectedMeal = MEAL_LIST.get(clickedPosition);
        View popupView = inflater.inflate(R.layout.detail_meal, container, false);

        // Find views
        ImageView image = popupView.findViewById(R.id.detail_meal_image);
        Glide.with(popupView).load(selectedMeal.getImageURI()).into(image);

        TextView area = popupView.findViewById(R.id.detail_meal_name);
        TextView category = popupView.findViewById(R.id.detail_meal_category);
        TextView description = popupView.findViewById(R.id.detail_meal_description);

        area.setText(selectedMeal.getArea());
        category.setText(selectedMeal.getCategory());
        description.setText(selectedMeal.getInstructions());

        // YouTube button
        Button buttonYouTube = popupView.findViewById(R.id.detail_meal_youtube);
        buttonYouTube.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(selectedMeal.getLinkYouTube()))));

        // Ingredient RecyclerView
        RecyclerView recyclerViewIngredient = popupView.findViewById(R.id.detail_meal_ingredients);
        recyclerViewIngredient.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewIngredient.setAdapter(new IngredientAdapter(getContext(), apiManager.getMealIngredients(selectedMeal), this));

        // Define PopupWindow
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(getView(), Gravity.TOP | Gravity.CENTER, 0, 0);
        popupView.setOnClickListener(v -> popupWindow.dismiss());
    }

}