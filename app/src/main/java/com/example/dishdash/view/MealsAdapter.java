package com.example.dishdash.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dishdash.R;
import com.example.dishdash.model.Meal;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {
    private Context mContext;

    private List<Meal> meals;
    boolean isCategoryMeals = false;

    private static final String TAG = "Adapter";

    public MealsAdapter(AppCompatActivity mContext, List<Meal> meals, boolean isCategoryMeals) {
        this.mContext = mContext;
        this.meals = meals;
        this.isCategoryMeals = isCategoryMeals;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recycler, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recycler.getContext());
        View vh;
            vh = inflater.inflate(R.layout.categories_items, recycler, false);

        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsAdapter.ViewHolder holder, int position) {
        Meal meal = meals.get(position); // Get Meal object at the current position
        holder.mealId= Integer.parseInt(meal.getIdMeal());
        holder.MealName.setText(meal.getStrMeal());

        if (meal != null && meal.getStrMealThumb() != null) {
            // Check if ImageView is not null
            if (holder.categoryI_Img != null) {
                Glide.with(holder.itemView.getContext())
                        .load(meal.getStrMealThumb())
                        .into(holder.categoryI_Img);
                Log.i(TAG, "onBindViewHolder: " + meal.getStrMealThumb());

            } else {
                Log.e("MealsAdapter", "ImageView is null in onBindViewHolder");
            }
        } else {
            Log.e("MealsAdapter", "Meal or Image URL is null in onBindViewHolder");

        }

        holder.itemView.setOnClickListener(v -> {

            Log.i(TAG, "Item clicked: " + holder.getAdapterPosition());
            int mealId = Integer.parseInt(meals.get(holder.getAdapterPosition()).getIdMeal());
            Log.i(TAG, "Meal ID : " + mealId);
            Intent intent = new Intent(v.getContext(), MealDetailsActivity.class);
            intent.putExtra("mealId", mealId);
            v.getContext().startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
    public void setMeals(List<Meal> meals ,boolean isCategoryMeals) {
        this.meals = meals;
        this.isCategoryMeals = isCategoryMeals;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView MealName;
        public int mealId;
        public View layout;
        public ImageView categoryI_Img;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                layout = itemView;
                MealName = itemView.findViewById(R.id.category_name);
                categoryI_Img = itemView.findViewById(R.id.categoryI_Img);
                itemView.findViewById(R.id.heart_icon);

            }



    }
}
