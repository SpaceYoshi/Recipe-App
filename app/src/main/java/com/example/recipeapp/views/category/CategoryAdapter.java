package com.example.recipeapp.views.category;

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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private static final String LOG_TAG = CategoryAdapter.class.getName();
    private final Context context;
    private final List<Category> categoryList;
    private final OnItemClickListener clickListener;

    public CategoryAdapter(Context context, List<Category> categoryList, OnItemClickListener clickListener) {
        this.context = context;
        this.categoryList = categoryList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.name.setText(categoryList.get(position).getName());
        holder.description.setText(categoryList.get(position).getDescription());
        Glide.with(context).load(categoryList.get(position).getImageURI()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    // TODO check if still works
//    public interface OnItemClickListener {
//        void onItemClick(int clickedPosition);
//    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView description;
        ImageView image;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.meal_category_name);
            image = itemView.findViewById(R.id.meal_category_image);
            description = itemView.findViewById(R.id.meal_category_description);

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
