package com.example.recipeapp.recyclerviews.category;

import androidx.annotation.NonNull;

public class Category {
    private String name;
    private String description;
    private String imageURI;

    public Category(String name, String description, String imageURI) {
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
        return "Category{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageURI='" + imageURI + '\'' +
                '}';
    }

}
