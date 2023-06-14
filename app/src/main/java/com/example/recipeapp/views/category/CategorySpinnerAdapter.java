package com.example.recipeapp.views.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.recipeapp.R;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {
    private static final String LOG_TAG = CategorySpinnerAdapter.class.getName();
    private final List<Category> categoryList;
    private final Context context;

    public CategorySpinnerAdapter(@NonNull Context context, @NonNull List<Category> categoryList) {
        super(context, R.layout.item_spinner, R.id.spinner_text, categoryList);
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, parent);
    }

    private View getCustomView(int position, ViewGroup parent) {
        View row = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);

        ImageView image = row.findViewById(R.id.spinner_image);
        Glide.with(context).load(categoryList.get(position).getImageURI()).into(image);

        TextView name = row.findViewById(R.id.spinner_text);
        name.setText(categoryList.get(position).getName());

        return row;
    }

}
