package com.example.recipeapp.views.area;

import androidx.annotation.NonNull;

public class Area {
    private final String name;
    private final int resourceID;

    public Area(String name, int resourceID) {
        this.name = name;
        this.resourceID = resourceID;
    }

    public String getName() {
        return name;
    }

    public int getResourceID() {
        return resourceID;
    }

    @NonNull
    @Override
    public String toString() {
        return "Area{" +
                "name='" + name + '\'' +
                ", resourceID=" + resourceID +
                '}';
    }

}
