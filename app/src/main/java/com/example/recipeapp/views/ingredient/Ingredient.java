package com.example.recipeapp.views.ingredient;

import androidx.annotation.NonNull;

public class Ingredient {
    private final String name;
    private final String description;
    private final String imageURI;

    public Ingredient(String name, String description, String imageURI) {
        this.name = name;
        this.description = description;
        this.imageURI = imageURI;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURI() {
        return imageURI;
    }

    @NonNull
    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageURI='" + imageURI + '\'' +
                '}';
    }

}
