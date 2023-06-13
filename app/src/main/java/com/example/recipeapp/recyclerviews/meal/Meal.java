package com.example.recipeapp.recyclerviews.meal;

import com.example.recipeapp.recyclerviews.ingredient.Ingredient;

import java.util.List;

public class Meal {
    private int id;
    private String name;
    private String category;
    private String area;
    private String instructions;
    // private image image;
    private List<String> tagList;
    private String linkYouTube;
    private List<Ingredient> ingredientList; // main ingredient is first item
    private List<String> measureList;

    public Meal(int id, String name, String category, String area, String instructions,
                List<String> tagList, String linkYouTube, List<Ingredient> ingredientList, List<String> measureList) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.tagList = tagList;
        this.linkYouTube = linkYouTube;
        this.ingredientList = ingredientList;
        this.measureList = measureList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public String getInstructions() {
        return instructions;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public String getLinkYouTube() {
        return linkYouTube;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public List<String> getMeasureList() {
        return measureList;
    }

}
