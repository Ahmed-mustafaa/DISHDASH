package com.example.dishdash.view.Favorites;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.dishdash.model.Meal;
import com.example.dishdash.R;

import com.example.dishdash.NetworkCall.NetworkCallBack;

import java.util.ArrayList;
import java.util.List;

// gotta use an interface just to pass the object of Meal needs to be handled
public class favAdapter extends RecyclerView.Adapter<favAdapter.ViewHolder> {

    private List<Meal> meals;
    private onRClickListener listener;

    private NetworkCallBack networkCallBacks; // Listenerrrrrr ( da el interface that has
    private static final String TAG = "RecyclerView";

    public favAdapter(Context context, onRClickListener listener) {
        this.listener = listener;
        meals = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recycler, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recycler.getContext());
        View v = inflater.inflate(R.layout.row,recycler,false);

        Log.i(TAG, "_______________ onCreateViewHoler ______________ ");
        return new ViewHolder(v);
    }
    public void setList(List<Meal> meals){
        this.meals = meals;
       notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,   int position) {
        Meal meal = meals.get(position);
        holder.Name.setText(meal.getStrMeal());
        holder.Category.setText(meal.getStrCategory());
        holder.Country.setText(meal.getStrArea());
        holder.Add.setVisibility(View.GONE);
        Glide.with(holder.thumb.getContext())
                .load(meal.getStrMealThumb())
        .apply(new RequestOptions().override(Target.SIZE_ORIGINAL))
                        .into(holder.thumb);
        holder.DeleteFromFav.setOnClickListener(v -> {
            meal.setFavorite(false);
            listener.onRemove(meal);
        });

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
       public  TextView Name;
       public  TextView Category;
       public  TextView Country;
        public ImageView thumb;
        public View layout;
        Button Add;
        Button DeleteFromFav;
        public CardView card;

        public ViewHolder(View convertView) {
            super(convertView);
            Name = convertView.findViewById(R.id.Name);
            Country = convertView.findViewById(R.id.Country);
            Category = convertView.findViewById(R.id.Category);
            thumb = convertView.findViewById(R.id.productImage);
            DeleteFromFav = convertView.findViewById(R.id.RemoveButton);
            Add = convertView.findViewById(R.id.Add);
            card = convertView.findViewById(R.id.card);

        }

    }



}
