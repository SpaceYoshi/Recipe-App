package com.example.recipeapp.api.listeners;

import com.example.recipeapp.views.category.Category;

public interface APIListenerCategory {
    void onCategoryAvailable(Category category);
    void onCategoryError(Error error);
}
