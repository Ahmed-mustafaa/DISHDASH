package com.example.dishdash.view.SearchAvtivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dishdash.R;
import com.example.dishdash.model.Category;
import com.example.dishdash.model.Country;
import com.example.dishdash.model.DisplayItem;
import com.example.dishdash.model.Meal;
import com.example.dishdash.view.CountriesAdapter;
import com.example.dishdash.view.MealsAdapter;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchActivity extends AppCompatActivity implements SearchView {
private static final String TAG = "SearchActivity";
    private Chip countryChip;
    private Chip categoryChip;
    private SearchPresenter searchPresenter;
    private List<DisplayItem> countryitems = new ArrayList<>();
    private List<DisplayItem> categoryitems = new ArrayList<>();
    private CountriesAdapter dAdapter;
    private CountriesAdapter CategoriesAdapter;
    LottieAnimationView progress;
    EditText searchEditText;
    private MealsAdapter mealsAdapter;
    boolean IsCountryClicked = false;
    boolean IsCategoryClicked = false;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
searchEditText = findViewById(R.id.searchBar);
        countryChip = findViewById(R.id.countryChip);
        categoryChip = findViewById(R.id.categoryChip);

        recyclerView1 = findViewById(R.id.countryRecyclerView);
        recyclerView2 = findViewById(R.id.categoryRecyclerView);
        recyclerView3 = findViewById(R.id.mealsRecyclerView);

        recyclerView1.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView3.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView1.setVisibility(View.GONE);
        recyclerView2.setVisibility(View.GONE);
        recyclerView3.setVisibility(View.GONE);
        recyclerView1.setAdapter(dAdapter);
        recyclerView2.setAdapter(CategoriesAdapter);
        recyclerView3.setAdapter(mealsAdapter);
        mealsAdapter = new MealsAdapter(SearchActivity.this, new ArrayList<>(), true);
        dAdapter = new CountriesAdapter(SearchActivity.this, countryitems);
        CategoriesAdapter = new CountriesAdapter(SearchActivity.this, categoryitems);

        progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
        searchPresenter = new SearchPresenter(this);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Handle text changes and search for meals
                String query = s.toString().trim();
                if (!query.isEmpty()) {

                    searchPresenter.SearcMealByletter(query);
                    recyclerView3.setVisibility(View.VISIBLE);
                } else {
                    // Optionally clear results or handle empty input
                    mealsAdapter.setMeals(new ArrayList<>(), true);
                    mealsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        // Set onClick listeners for chips
        countryChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsCountryClicked) {
                    // If country RecyclerView is already visible, toggle visibility off
                    recyclerView1.setVisibility(View.GONE);
                    IsCountryClicked = false;
                } else {
                    // Toggle visibility on
                    recyclerView1.setVisibility(View.VISIBLE);
                    IsCountryClicked = true;

                    // Hide category RecyclerView if visible
                    if (IsCategoryClicked) {
                        recyclerView2.setVisibility(View.GONE);
                        IsCategoryClicked = false;
                    }

                    // Load countries
                    searchPresenter.loadCountries();
                }
            }
        });

        categoryChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsCategoryClicked) {
                    // If category RecyclerView is already visible, toggle visibility off
                    recyclerView2.setVisibility(View.GONE);
                    IsCategoryClicked = false;
                } else {
                    // Toggle visibility on
                    recyclerView2.setVisibility(View.VISIBLE);
                    IsCategoryClicked = true;

                    // Hide country RecyclerView if visible
                    if (IsCountryClicked) {
                        recyclerView1.setVisibility(View.GONE);
                        IsCountryClicked = false;
                    }

                    // Load categories
                    searchPresenter.loadCategories();
                }
            }
        });
    }

    @Override
    public void showAllCountries(List<Country> countries) {
        countryitems.clear();
        countryitems.addAll(countries.stream()
                .map(country -> new DisplayItem(DisplayItem.ItemType.COUNTRY, country.getStrArea(), null))
                .collect(Collectors.toList()));
        dAdapter.setItems(countryitems);
        recyclerView1.setAdapter(dAdapter);
    }

    @Override
    public void showAllCategories(List<Category> categories) {
        categoryitems.clear();
        categoryitems.addAll(categories.stream()
                .map(category -> new DisplayItem(DisplayItem.ItemType.CATEGORY,
                        category.getStrCategory(), category.getStrCategoryThumb()))
                .collect(Collectors.toList()));
        CategoriesAdapter.setItems(categoryitems);
        recyclerView2.setAdapter(CategoriesAdapter);
    }

    @Override
    public void showMealsByLetter(List<Meal> meals) {
        mealsAdapter.setMeals(meals, true);  // Set the filtered meals to the adapter
        recyclerView3.setAdapter(mealsAdapter);
        mealsAdapter.notifyDataSetChanged();      }

    @Override
    public void showMealsByCategory(List<Meal> meals) {
        CategoriesAdapter.setItems(categoryitems);
        recyclerView2.setAdapter(CategoriesAdapter);
        CategoriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMealsByCountry(List<Meal> meals) {
        dAdapter.setItems(countryitems);
        recyclerView1.setAdapter(dAdapter);
        dAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    public void showLoading() {
progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
progress.setVisibility(View.GONE);
     }
}