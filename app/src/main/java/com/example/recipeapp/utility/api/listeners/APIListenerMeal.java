package com.example.recipeapp.utility.api.listeners;

import com.example.recipeapp.recyclerviews.meal.Meal;

public interface APIListenerMeal {
    void onMealAvailable(Meal meal);
    void onMealError(Error error);
}
