package com.example.recipeapp.views.area;

import androidx.annotation.NonNull;

public class Area {
    private final String name;

    public Area(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Area{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getImageURI() {
        return null;
    }

}
