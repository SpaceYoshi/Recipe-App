package com.example.recipeapp.utility.api.listeners;

import com.example.recipeapp.recyclerviews.category.Category;

public interface APIListenerCategory {
    void onCategoryAvailable(Category category);
    void onCategoryError(Error error);
}
