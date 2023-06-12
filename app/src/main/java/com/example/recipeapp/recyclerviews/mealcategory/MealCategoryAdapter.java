package com.example.recipeapp.recyclerviews.mealcategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.R;

import java.util.List;

public class MealCategoryAdapter extends RecyclerView.Adapter<MealCategoryViewHolder> {
    Context context;
    List<MealCategory> mealCategoryList;

    public MealCategoryAdapter(Context context, List<MealCategory> mealCategoryList) {
        this.context = context;
        this.mealCategoryList = mealCategoryList;
    }

    @NonNull
    @Override
    public MealCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealCategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MealCategoryViewHolder holder, int position) {
        holder.name.setText(mealCategoryList.get(position).getName());
        holder.description.setText(mealCategoryList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        try {
            return mealCategoryList.size();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return 0;
        }
    }

}
