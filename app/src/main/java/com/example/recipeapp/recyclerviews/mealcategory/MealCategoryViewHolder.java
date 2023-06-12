package com.example.recipeapp.recyclerviews.mealcategory;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;

public class MealCategoryViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView description;
    ImageView image;

    public MealCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.meal_category_name);
        image = itemView.findViewById(R.id.meal_category_image);
        description = itemView.findViewById(R.id.meal_category_description);
    }

}
