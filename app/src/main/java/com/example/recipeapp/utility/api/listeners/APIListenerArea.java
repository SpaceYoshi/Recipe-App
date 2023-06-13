package com.example.recipeapp.utility.api.listeners;

import com.example.recipeapp.utility.Area;

public interface APIListenerArea {
    void onAreaAvailable(Area area);
    void onAreaError(Error error);
}
