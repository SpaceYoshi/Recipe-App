package com.example.recipeapp.views.meal;

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
import com.example.recipeapp.views.category.CategoryAdapter;
import com.example.recipeapp.utility.OnItemClickListener;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private static final String LOG_TAG = CategoryAdapter.class.getName();
    private final Context context;
    private final List<Meal> mealList;
    private final OnItemClickListener clickListener;

    public MealAdapter(Context context, List<Meal> mealList, OnItemClickListener clickListener) {
        this.context = context;
        this.mealList = mealList;
        this.clickListener = clickListener;
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
        Glide.with(context).load(mealList.get(position).getImageURI()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Log.i(LOG_TAG, "Item " + clickedPosition + " was clicked.");
            clickListener.onItemClick(clickedPosition);
        }
    }

}
