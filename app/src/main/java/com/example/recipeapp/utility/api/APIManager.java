package com.example.recipeapp.utility.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipeapp.recyclerviews.category.Category;
import com.example.recipeapp.recyclerviews.ingredient.Ingredient;
import com.example.recipeapp.recyclerviews.meal.Meal;
import com.example.recipeapp.utility.Area;
import com.example.recipeapp.utility.StringValidator;
import com.example.recipeapp.utility.api.listeners.APIListenerArea;
import com.example.recipeapp.utility.api.listeners.APIListenerCategory;
import com.example.recipeapp.utility.api.listeners.APIListenerIngredient;
import com.example.recipeapp.utility.api.listeners.APIListenerMeal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class APIManager {
    private static final String LOG_TAG = APIManager.class.getName();
    private static final String API_KEY = "1";
    private static final String JSON_URL_PREFIX = "https://www.themealdb.com/api/json/v1/";
    private final RequestQueue queue;
    private final APIListenerArea listenerArea;
    private final APIListenerCategory listenerCategory;
    private final APIListenerIngredient listenerIngredient;
    private final APIListenerMeal listenerMeal;

    public APIManager(Context context, APIListenerArea listenerArea, APIListenerCategory listenerCategory,
                      APIListenerIngredient listenerIngredient, APIListenerMeal listenerMeal) {
        this.listenerArea = listenerArea;
        this.listenerCategory = listenerCategory;
        this.listenerIngredient = listenerIngredient;
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

    // TODO, weg van combobox, en checkbox maken? zodat de gebruiker zelf het ingrediënt kan invoeren
    public void getIngredients() {
        if (listenerIngredient == null) {
            Log.e(LOG_TAG, "Required listener is null.");
            return;
        }
        final String url = JSON_URL_PREFIX + API_KEY + "/list.php?i=list";
        final JsonObjectRequest request = new JsonObjectRequest(url, response -> {
//            Log.d(LOG_TAG, "Volley response: " + response.toString());
            try {
                JSONArray jsonArray = response.getJSONArray("meals");
                for (int i = 0; i < 30; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("strIngredient");
                    String description = jsonObject.getString("strDescription");
                    String imageURI = "https://www.themealdb.com/images/ingredients/" + name + ".png";

                    Ingredient ingredient = new Ingredient(name, description, imageURI);
//                    Log.d(LOG_TAG, "Adding " + ingredient);
                    listenerIngredient.onIngredientAvailable(ingredient);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error while parsing JSON data: " + e.getLocalizedMessage());
            }
        }, error -> {
            Log.e(LOG_TAG, error.getLocalizedMessage());
            listenerIngredient.onIngredientError(new Error(error.getLocalizedMessage()));
        });
        queue.add(request);
    }

    public void getMealsSearch(String input) {
        getMeals("/search.php?s=", input);
    }

    public void getMealsCategory(String category) {
        getMeals("/filter.php?c=", category);
    }

    public void getMealsArea(String area) {
        getMeals("/filter.php?a=", area);
    }

    public void getMealsIngredient(String ingredient) {
        getMeals("/filter.php?i=", ingredient);
    }

    private void getMeals(String urlSuffix, String input) {
        if (listenerMeal == null) {
            Log.e(LOG_TAG, "Required listener is null.");
            return;
        } else if (!StringValidator.isValid(input)) return;

        final String url = JSON_URL_PREFIX + API_KEY + urlSuffix + StringValidator.clean(input);
        final JsonObjectRequest request = new JsonObjectRequest(url, response -> {
            Log.d(LOG_TAG, "Volley response: " + response.toString());
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
                    Log.d(LOG_TAG, "Adding " + meal);
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

    private static List<String> commaSeparatedStringToList(String input) {
        return Arrays.asList(input.split(","));
    }

    private static List<String> getStringListFromEntries(String prefix, JSONObject jsonObject) throws JSONException {
        List<String> ingredientList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) ingredientList.add(jsonObject.getString(prefix + i));
        return ingredientList;
    }
    
}
