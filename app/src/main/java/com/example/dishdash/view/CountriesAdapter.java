package com.example.dishdash.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishdash.R;
import com.example.dishdash.model.Category;
import com.example.dishdash.model.Country;
import com.example.dishdash.model.DisplayItem;

import java.util.ArrayList;
import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {
    private Context mContext;


    private static final String TAG = "CountriesAdapter";
    private List<DisplayItem> items; // refering to either country or category
public CountriesAdapter(Context mContext, List<DisplayItem> items) {
    this.mContext = mContext;
    this.items = items;
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recycler, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recycler.getContext());

        View vh;
        if (viewType == DisplayItem.ItemType.COUNTRY.ordinal()) {

             vh= inflater.inflate(R.layout.countries_layout, recycler, false);
        } else {
            vh = inflater.inflate(R.layout.categories_items, recycler, false);
        }
        return new ViewHolder(vh);

        // here we give the row ( that will be displayed on the recycle view the structure of what data will be on the recycle view )

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    DisplayItem item = items.get(position); // object from Country to get country name
        switch (item.type){
            case COUNTRY:
                holder.countryName.setText(item.getStrArea());
                break;
            case CATEGORY:
                holder.CategoryName.setText(item.getStrCategory());
        }/*
        Glide.with(holder.thumb.getContext())
                .load(Country.getStrMealThumb())
                .apply(new RequestOptions().override(150,150))
                .into(holder.thumb);
        holder.constraintLayout.setOnClickListener( v-> {
            Toast.makeText(mContext, meals.get(position).getStrMeal(), Toast.LENGTH_SHORT).show();
        });*/
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).type.ordinal();
    }

    public void setItems(List<DisplayItem> items) {
    this.items = items;
    notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView countryName;
        public TextView CategoryName;
        public View layout;


        public ViewHolder(View v) {
            super(v);
             layout = v;
            countryName = v.findViewById(R.id.country_name);
            CategoryName= v.findViewById(R.id.category_name);


        }
    }
}
