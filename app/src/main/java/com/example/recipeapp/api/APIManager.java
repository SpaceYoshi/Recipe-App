package com.example.recipeapp.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipeapp.R;
import com.example.recipeapp.api.listeners.APIListenerCategory;
import com.example.recipeapp.views.area.AreaFlagMap;
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
    private static final String JSON_URL_PREFIX = "https://www.themealdb.com/api/json/v1/" + API_KEY;
    private static final Map<String, Ingredient> INGREDIENT_MAP = new HashMap<>(600); // currently 574 different ingredients
    private static boolean ingredientMapFilled = false;
    private final RequestQueue queue;
    private final APIListenerArea listenerArea;
    private final APIListenerCategory listenerCategory;
    private final APIListenerMeal listenerMeal;
    private final Context context;

    public APIManager(Context context, APIListenerArea listenerArea, APIListenerCategory listenerCategory, APIListenerMeal listenerMeal) {
        this.listenerArea = listenerArea;
        this.listenerCategory = listenerCategory;
        this.listenerMeal = listenerMeal;
        this.queue = Volley.newRequestQueue(context);
        this.context = context;
        fillIngredientMap();
    }

    public void getCategories() {
        if (listenerCategory == null) {
            Log.e(LOG_TAG, "Required listener is null.");
            return;
        }
        final String url = JSON_URL_PREFIX + "/categories.php/";
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
        final String url = JSON_URL_PREFIX + "/list.php?a=list";
        final JsonObjectRequest request = new JsonObjectRequest(url, response -> {
//            Log.d(LOG_TAG, "Volley response: " + response.toString());
            try {
                JSONArray jsonArray = response.getJSONArray("meals");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("strArea");

                    Integer resourceID = AreaFlagMap.MAP.get(name);
                    if (resourceID == null) resourceID = R.drawable.ic_launcher_background; // placeholder

                    Area area = new Area(name, resourceID);
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

    public void getMeals(MealSearchType mealSearchType) {
        getMeals(mealSearchType, null);
    }
    public void getMeals(MealSearchType mealSearchType, String input) {
        if (mealSearchType != MealSearchType.LONG_RANDOM) if (StringValidator.isInvalidClean(input)) {
            Log.e(LOG_TAG, "Illegal input, ignoring.");
            return;
        } else if (listenerMeal == null) {
            Log.e(LOG_TAG, "Meal listener is null.");
            return;
        }

        String url = JSON_URL_PREFIX;
        switch (mealSearchType) {
            case LONG_NAME:
                url += "/search.php?s=" + input;
                break;
            case LONG_RANDOM:
                url += "/random.php";
                break;
            case LONG_ID:
                url += "/lookup.php?i=" + input;
                break;
            default:
                handleShortMeals(mealSearchType, input);
                return;
        }
        getMealsByURL(url);
    }

    private void getMealsByURL(String url) {
        final JsonObjectRequest request = new JsonObjectRequest(url, response -> {
//            Log.d(LOG_TAG, "Volley response: " + response.toString());
            try {
                if (response.getString("meals").equals("null")) {
                    Toast toast = Toast.makeText(context, "No results, please try again.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
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

    private void handleShortMeals(MealSearchType mealSearchType, String input) {
        String url = JSON_URL_PREFIX;
        switch (mealSearchType) {
            case SHORT_CATEGORY:
                url += "/filter.php?c=" + input;
                break;
            case SHORT_AREA:
                url += "/filter.php?a=" + input;
                break;
            case SHORT_PRIMARY_INGREDIENT:
                url += "/filter.php?i=" + input;
                break;
        }
        final JsonObjectRequest request = new JsonObjectRequest(url, response -> {
//            Log.d(LOG_TAG, "Volley response: " + response.toString());
            try {
                if (response.getString("meals").equals("null")) {
                    Toast toast = Toast.makeText(context, "No results, please try again.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                List<Integer> idList = new ArrayList<>();
                JSONArray jsonArray = response.getJSONArray("meals");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("idMeal");
                    idList.add(id);
//                    Log.d(LOG_TAG, "Adding " + id);
                }
                for (Integer integer : idList) getMeals(MealSearchType.LONG_ID, integer.toString());
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
        final String url = JSON_URL_PREFIX + "/list.php?i=list";
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
