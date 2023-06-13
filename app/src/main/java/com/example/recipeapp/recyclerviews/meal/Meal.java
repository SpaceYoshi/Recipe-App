package com.example.recipeapp.recyclerviews.meal;

import androidx.annotation.NonNull;

import java.util.List;

public class Meal {
    private final int id;
    private final String name;
    private final String category;
    private final String area;
    private final String instructions;
    private final String imageURI;
    private final List<String> tagList;
    private final String linkYouTube;
    private final List<String> ingredientList; // main ingredient is first item
    private final List<String> measureList;

    public Meal(int id, String name, String category, String area, String instructions,
                String imageURI, List<String> tagList, String linkYouTube, List<String> ingredientList, List<String> measureList) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.imageURI = imageURI;
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

    public String getImageURI() {
        return imageURI;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public String getLinkYouTube() {
        return linkYouTube;
    }

    public List<String> getIngredientList() {
        return ingredientList;
    }

    public List<String> getMeasureList() {
        return measureList;
    }

    @NonNull
    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", area='" + area + '\'' +
                ", instructions='" + instructions + '\'' +
                ", imageURI='" + imageURI + '\'' +
                ", tagList=" + tagList +
                ", linkYouTube='" + linkYouTube + '\'' +
                ", ingredientList=" + ingredientList +
                ", measureList=" + measureList +
                '}';
    }

}
