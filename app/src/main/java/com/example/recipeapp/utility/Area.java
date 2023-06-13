package com.example.recipeapp.utility;

import androidx.annotation.NonNull;

public class Area {
    private final String area;

    public Area(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    @NonNull
    @Override
    public String toString() {
        return "Area{" +
                "area='" + area + '\'' +
                '}';
    }

}
