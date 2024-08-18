package com.example.dishdash.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dishdash.R;
import com.example.dishdash.model.Meal;

import java.util.List;

public class DailyMealsAdapter extends RecyclerView.Adapter<DailyMealsAdapter.ViewHolder> {
    private Context mContext;

    private static final String TAG = "CustomAdapter";
    private List<Meal> meals;
public DailyMealsAdapter(Context mContext, List<Meal> meals) {
    this.mContext = mContext;
    this.meals = meals;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recycler, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(recycler.getContext());
                View v = inflater.inflate(R.layout.daily_meal_item,recycler,false);
                ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyMealsAdapter.ViewHolder holder, int position) {
    Meal meal = meals.get(position);
    holder.title.setText(meal.strMeal);
  //  holder.desc.setText(meal.getStrInstructions());

        Glide.with(holder.thumb.getContext())
                .load(meal.getStrMealThumb())
                .apply(new RequestOptions().override(150,150))
                .into(holder.thumb);
        holder.constraintLayout.setOnClickListener( v-> {
            Toast.makeText(mContext, meals.get(position).getStrMeal(), Toast.LENGTH_SHORT).show();
        });
    }
    public void setMeals(List<Meal> meals) {
    this.meals = meals;
    notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return meals != null ? meals.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView title;
        public  TextView desc;

        public ImageView thumb;
        public View layout;
        public ConstraintLayout constraintLayout;


        public ViewHolder(View v) {
            super(v);
             layout = v;
            title = v.findViewById(R.id.Title);
            thumb = v.findViewById(R.id.mealImage);
            constraintLayout = v.findViewById(R.id.meal_item_layout);


        }
    }
}
