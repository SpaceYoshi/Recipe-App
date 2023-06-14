package com.example.recipeapp.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipeapp.api.listeners.APIListenerCategory;
import com.example.recipeapp.views.category.Category;
import com.example.recipeapp.views.ingredient.Ingredient;
import com.example.recipeapp.views.meal.Meal;
import com.example.recipeapp.views.area.Area;
import com.example.recipeapp.utility.StringValidator;
import com.example.recipeapp.api.listeners.APIListenerArea;
import com.example.recipeapp.api.listeners.APIListenerMeal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIManager {
    private static final String LOG_TAG = APIManager.class.getName();
    private static final String API_KEY = "1";
    private static final String JSON_URL_PREFIX = "https://www.themealdb.com/api/json/v1/";
    private static final Map<String, Ingredient> INGREDIENT_MAP = new HashMap<>(600); // currently 574 different ingredients
    private static boolean ingredientMapFilled = false;
    private final RequestQueue queue;
    private final APIListenerArea listenerArea;
    private final APIListenerCategory listenerCategory;
    private final APIListenerMeal listenerMeal;

    public APIManager(Context context, APIListenerArea listenerArea, APIListenerCategory listenerCategory, APIListenerMeal listenerMeal) {
        this.listenerArea = listenerArea;
        this.listenerCategory = listenerCategory;
        this.listenerMeal = listenerMeal;
        this.queue = Volley.newRequestQueue(context);
    }

    public void getCategories() {
        if (listenerCategory == null) {
            Log.e(LOG_TAG, "Required listener is null.");
            return;
        }
        final String url = JSON_URL_PREFIX + API_KEY + "/categories.php/";
        final JsonObjectRequest request = new JsonObjectRequest(url, response -> {
//            Log.d(LOG_TAG, "Volley response: " + response.toString());
            try {
                JSONArray jsonArray = response.getJSONArray("categories");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("strCategory");
                    String imageURI = jsonObject.getString("strCategoryThumb");
                    String description = jsonObject.getString("strCategoryDescription");

                    Category category = new Category(name, description, imageURI);
//                    Log.d(LOG_TAG, "Adding " + mealCategory);
                    listenerCategory.onCategoryAvailable(category);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error while parsing JSON data: " + e.getLocalizedMessage());
            }
        }, error -> {
            Log.e(LOG_TAG, error.getLocalizedMessage());
            listenerCategory.onCategoryError(new Error(error.getLocalizedMessage()));
        });
        queue.add(request);
    }

    public void getAreas() {
        if (listenerArea == null) {
            Log.e(LOG_TAG, "Required listener is null.");
            return;
        }
        final String url = JSON_URL_PREFIX + API_KEY + "/list.php?a=list";
        final JsonObjectRequest request = new JsonObjectRequest(url, response -> {
//            Log.d(LOG_TAG, "Volley response: " + response.toString());
            try {
                JSONArray jsonArray = response.getJSONArray("meals");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("strArea");

                    Area area = new Area(name);
//                    Log.d(LOG_TAG, "Adding " + area);
                    listenerArea.onAreaAvailable(area);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error while parsing JSON data: " + e.getLocalizedMessage());
            }
        }, error -> {
            Log.e(LOG_TAG, error.getLocalizedMessage());
            listenerArea.onAreaError(new Error(error.getLocalizedMessage()));
        });
        queue.add(request);
    }

    private enum MealSearchType {NAME, CATEGORY, AREA, PRIMARY_INGREDIENT, RANDOM}

    public void getMealsName(String input) {
        getMeals(MealSearchType.NAME, input);
    }

    public void getMealsCategory(String category) {
        getMeals(MealSearchType.CATEGORY, category);
    }

    public void getMealsArea(String area) {
        getMeals(MealSearchType.AREA, area);
    }

    public void getMealsPrimaryIngredient(String ingredient) {
        getMeals(MealSearchType.PRIMARY_INGREDIENT, ingredient);
    }

    public void getMealRandom() {
        getMeals(MealSearchType.RANDOM, null);
    }

    private void getMeals(MealSearchType mealSearchType, String input) {
        if (listenerMeal == null) {
            Log.e(LOG_TAG, "Required listener is null.");
            return;
        } else if (!ingredientMapFilled) fillIngredientMap(); // If meals are requested, we should start filling up the ingredient list.

        String url = JSON_URL_PREFIX + API_KEY;
        switch (mealSearchType) {
            case NAME:
                url += "/search.php?s=" + StringValidator.clean(input);
                break;
            case CATEGORY:
                url += "/filter.php?c=" + StringValidator.clean(input);
                break;
            case AREA:
                url += "/filter.php?a=" + StringValidator.clean(input);
                break;
            case PRIMARY_INGREDIENT:
                url += "/filter.php?i=" + StringValidator.clean(input);
                break;
            case RANDOM:
                url += "/random.php";
                break;
        }
        if (mealSearchType != MealSearchType.RANDOM) if (!StringValidator.isValid(input)) return;

        final JsonObjectRequest request = new JsonObjectRequest(url, response -> {
//            Log.d(LOG_TAG, "Volley response: " + response.toString());
            try {
                JSONArray jsonArray = response.getJSONArray("meals");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("idMeal");
                    String name = jsonObject.getString("strMeal");
                    String category = jsonObject.getString("strCategory");
                    String area = jsonObject.getString("strArea");
                    String instructions = jsonObject.getString("strInstructions");
                    String imageURI = jsonObject.getString("strMealThumb");
                    List<String> tagList = commaSeparatedStringToList(jsonObject.getString("strTags"));
                    String linkYouTube = jsonObject.getString("strYoutube");
                    List<String> ingredientList = getStringListFromEntries("strIngredient", jsonObject);
                    List<String> measureList = getStringListFromEntries("strMeasure", jsonObject);

                    Meal meal = new Meal(id, name, category, area, instructions, imageURI, tagList, linkYouTube, ingredientList, measureList);
//                    Log.d(LOG_TAG, "Adding " + meal);
                    listenerMeal.onMealAvailable(meal);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error while parsing JSON data: " + e.getLocalizedMessage());
            }
        }, error -> {
            Log.e(LOG_TAG, error.getLocalizedMessage());
            listenerMeal.onMealError(new Error(error.getLocalizedMessage()));
        });
        queue.add(request);
    }

    public List<Ingredient> getMealIngredients(Meal meal) {
        if (!ingredientMapFilled) {
            Log.e(LOG_TAG, "Ingredient list requested but not ready!");
            return Collections.emptyList();
        }
        List<Ingredient> ingredientList = new ArrayList<>();
        for (String ingredientName : meal.getIngredientList()) ingredientList.add(INGREDIENT_MAP.get(ingredientName));
        return ingredientList;
    }

    private void fillIngredientMap() {
        final String url = JSON_URL_PREFIX + API_KEY + "/list.php?i=list";
        final JsonObjectRequest request = new JsonObjectRequest(url, response -> {
//            Log.d(LOG_TAG, "Volley response: " + response.toString());
            try {
                JSONArray jsonArray = response.getJSONArray("meals");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("strIngredient");
                    String description = jsonObject.getString("strDescription");
                    String imageURI = "https://www.themealdb.com/images/ingredients/" + name + ".png";

                    Ingredient ingredient = new Ingredient(name, description, imageURI);
//                    Log.d(LOG_TAG, "Putting " + name + " and " + ingredient);
                    INGREDIENT_MAP.put(name.toLowerCase(), ingredient);
                }
                ingredientMapFilled = true;
                Log.d(LOG_TAG, "Ingredient map filled. Amount: " + INGREDIENT_MAP.size());
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error while parsing JSON data: " + e.getLocalizedMessage());
            }
        }, error -> Log.e(LOG_TAG, error.getLocalizedMessage()));
        queue.add(request);
    }

    private static List<String> commaSeparatedStringToList(String input) {
        return Arrays.asList(input.split(","));
    }

    private static List<String> getStringListFromEntries(String prefix, JSONObject jsonObject) throws JSONException {
        List<String> ingredientList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) ingredientList.add(jsonObject.getString(prefix + i));
        return ingredientList;
    }
    
}
