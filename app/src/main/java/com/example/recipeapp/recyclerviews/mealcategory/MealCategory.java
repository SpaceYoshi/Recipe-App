package com.example.recipeapp.recyclerviews.mealcategory;

public class MealCategory {
    private String name;
    private String description;
    // image

    public MealCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
