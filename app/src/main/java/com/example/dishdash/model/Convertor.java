package com.example.dishdash.model;

import androidx.room.TypeConverter;

import com.example.dishdash.model.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Convertor {

    @TypeConverter
    public String fromIngredientList(List<Ingredient> ingredients) {
        if (ingredients == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(ingredients);
    }

    @TypeConverter
    public List<Ingredient> toIngredientList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {}.getType();
        return gson.fromJson(data, type);
    }
}