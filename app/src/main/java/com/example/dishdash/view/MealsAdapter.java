package com.example.dishdash.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dishdash.R;
import com.example.dishdash.model.Meal;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {
    private Context mContext;

    private List<Meal> meals;
    boolean isCategoryMeals;

    private static final String TAG = "CountriesAdapter";

    public MealsAdapter(Context mContext, List<Meal> meals, boolean isCategoryMeals) {
        this.mContext = mContext;
        this.meals = meals;
        this.isCategoryMeals = isCategoryMeals;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recycler, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recycler.getContext());
        View vh;
        if (isCategoryMeals) {

            vh= inflater.inflate(R.layout.countries_layout, recycler, false);
        } else {
            vh = inflater.inflate(R.layout.categories_items, recycler, false);
        }
        return new ViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsAdapter.ViewHolder holder, int position) {
        Meal meal = meals.get(position); // Get Meal object at the current position

                holder.MealName.setText(meal.getStrMeal());


                Glide.with(holder.itemView.getContext())
                        .load(meal.getStrMealThumb())
                        .into(holder.category_Img);


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
        public View layout;
        public ImageView category_Img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            if (isCategoryMeals) {
                MealName = itemView.findViewById(R.id.country_name);
            } else {
                MealName = itemView.findViewById(R.id.category_name);
                category_Img = itemView.findViewById(R.id.categoryI_Img);
            }

        }
    }
}
