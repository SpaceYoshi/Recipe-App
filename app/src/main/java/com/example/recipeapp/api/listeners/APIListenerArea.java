package com.example.recipeapp.api.listeners;

import com.example.recipeapp.views.area.Area;

public interface APIListenerArea {
    void onAreaAvailable(Area area);
    void onAreaError(Error error);
}
