package com.example.lpelczar.bakingapp.models;

import com.google.gson.annotations.SerializedName;


public class Ingredient {

    private double quantity;
    private String measure;
    @SerializedName("ingredient")
    private String name;

    @Override
    public String toString() {
        return "Quantity: " + quantity + " Measure: " + measure + " Name: " + name;
    }
}
