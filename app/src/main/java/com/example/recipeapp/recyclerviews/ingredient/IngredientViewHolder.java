package com.example.recipeapp.recyclerviews.ingredient;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;

public class IngredientViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView description;
    ImageView image;

    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.ingredient_name);
        description = itemView.findViewById(R.id.ingredient_description);
        image = itemView.findViewById(R.id.ingredient_image);
    }

}
