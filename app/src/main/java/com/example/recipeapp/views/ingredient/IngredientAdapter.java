package com.example.recipeapp.views.ingredient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeapp.R;
import com.example.recipeapp.utility.OnItemClickListener;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private static final String LOG_TAG = IngredientAdapter.class.getName();
    private final Context context;
    private final List<Ingredient> ingredientList;
    private final OnItemClickListener clickListener;

    public IngredientAdapter(Context context, List<Ingredient> ingredientList, OnItemClickListener clickListener) {
        this.context = context;
        this.ingredientList = ingredientList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.name.setText(ingredientList.get(position).getName());
        holder.description.setText(ingredientList.get(position).getDescription());
        Glide.with(context).load(ingredientList.get(position).getImageURI()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView description;
        ImageView image;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ingredient_name);
            description = itemView.findViewById(R.id.ingredient_description);
            image = itemView.findViewById(R.id.ingredient_image);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Log.i(LOG_TAG, "Item " + clickedPosition + " was clicked.");
            clickListener.onItemClick(clickedPosition);
        }
    }

}
