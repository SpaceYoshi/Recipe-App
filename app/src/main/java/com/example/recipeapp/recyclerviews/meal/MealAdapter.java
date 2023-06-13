package com.example.recipeapp.recyclerviews.meal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealViewHolder> {
    Context context;
    List<Meal> mealList;

    public MealAdapter(Context context, List<Meal> mealList) {
        this.context = context;
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealViewHolder(LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        holder.name.setText(mealList.get(position).getName());
        holder.category.setText(mealList.get(position).getCategory());
        holder.area.setText(mealList.get(position).getArea());
        // Image
    }

    @Override
    public int getItemCount() {
        try {
            return mealList.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
