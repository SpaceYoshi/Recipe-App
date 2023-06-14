package com.example.recipeapp.views.area;

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

public class AreaSpinnerAdapter extends ArrayAdapter<Area> {
    private static final String LOG_TAG = AreaSpinnerAdapter.class.getName();
    private final List<Area> areaList;
    private final Context context;

    public AreaSpinnerAdapter(@NonNull Context context, @NonNull List<Area> areaList) {
        super(context, R.layout.item_spinner, R.id.spinner_text, areaList);
        this.context = context;
        this.areaList = areaList;
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
//        Log.d(LOG_TAG, "getCustomView()");
        View row = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);

        ImageView image = row.findViewById(R.id.spinner_image);
        Glide.with(context).load(areaList.get(position).getResourceID()).into(image);

        TextView name = row.findViewById(R.id.spinner_text);
        name.setText(areaList.get(position).getName());

        return row;
    }

}
