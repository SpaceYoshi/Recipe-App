package com.example.recipeapp.utility.api.listeners;

import com.example.recipeapp.recyclerviews.ingredient.Ingredient;

public interface APIListenerIngredient {
    void onIngredientAvailable(Ingredient ingredient);
    void onIngredientError(Error error);
}
