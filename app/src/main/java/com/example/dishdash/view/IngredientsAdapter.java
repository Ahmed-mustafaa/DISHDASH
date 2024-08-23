package com.example.dishdash.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dishdash.R;


import java.util.List;
import java.util.Map;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<Map<String, String>>  ingredients;
    private Context context;

    public IngredientsAdapter(Context context,List<Map<String, String>>  ingredients) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
Map<String, String> ingredient = ingredients.get(position);
        holder.nameTextView.setText(ingredient.get("name"));
        holder.ingredientMeasure.setText(ingredient.get("measure"));
        // Load image using a library like Glide or Picasso
        Glide.with(holder.itemView.getContext())
                .load(ingredient.get("imageUrl"))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView ingredientMeasure;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.ingredientName);
            ingredientMeasure = itemView.findViewById(R.id.ingredientMeasure);
            imageView = itemView.findViewById(R.id.IngredientImage);
        }
    }
}