package com.example.recipeapp.api.listeners;

import com.example.recipeapp.views.meal.Meal;

public interface APIListenerMeal {
    void onMealAvailable(Meal meal);
    void onMealError(Error error);
}
