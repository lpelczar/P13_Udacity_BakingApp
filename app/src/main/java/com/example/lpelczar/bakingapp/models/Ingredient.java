package com.example.lpelczar.bakingapp.models;

import com.google.gson.annotations.SerializedName;


public class Ingredient implements RecipeDetail {

    private double quantity;
    private String measure;
    @SerializedName("ingredient")
    private String name;

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Quantity: " + quantity + " Measure: " + measure + " Name: " + name;
    }
}
