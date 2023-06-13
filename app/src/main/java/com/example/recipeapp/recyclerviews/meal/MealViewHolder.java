package com.example.recipeapp.recyclerviews.meal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;

public class MealViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView category;
    TextView area;
    ImageView image;

    public MealViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.meal_name);
        category = itemView.findViewById(R.id.meal_category);
        area = itemView.findViewById(R.id.meal_area);
        image = itemView.findViewById(R.id.meal_image);
    }

}
