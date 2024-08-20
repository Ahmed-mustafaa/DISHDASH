package com.example.dishdash.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
                holder.countryName.setText(item.getName());
                break;
            case CATEGORY:
                holder.CategoryName.setText(item.getName());
                Glide.with(holder.itemView.getContext())
                        .load(item.getImageUrl())
                        .into(holder.category_Img);
                break;
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
        public ImageView category_Img;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            countryName = v.findViewById(R.id.country_name);
            CategoryName = v.findViewById(R.id.category_name);
            category_Img = v.findViewById(R.id.categoryI_Img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && items != null && position < items.size()) {
                        DisplayItem item = items.get(position);
                        if (item != null) {
                            if (item.type == DisplayItem.ItemType.CATEGORY) {
                                String categoryName = item.name;
                                Intent intent = new Intent(view.getContext(), CategoryActivity.class);
                                intent.putExtra("categoryName", categoryName);
                                view.getContext().startActivity(intent); // Start the activity

                                Toast.makeText(view.getContext(), "Clicked on category: " + categoryName, Toast.LENGTH_SHORT).show();
                            } else if (item.type == DisplayItem.ItemType.COUNTRY) {
                                String countryName = item.name;
                                Intent intent = new Intent(view.getContext(), CategoryActivity.class);
                                intent.putExtra("countryName", countryName);
                                view.getContext().startActivity(intent); // Start the activity
                                Toast.makeText(view.getContext(), "Clicked on country: " + countryName, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }
    }
}