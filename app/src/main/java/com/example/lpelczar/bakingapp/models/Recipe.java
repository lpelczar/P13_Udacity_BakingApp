package com.example.lpelczar.bakingapp.models;


import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private int servings;
    private String image;
    private List<Ingredient> ingredients;
    private List<RecipeStep> steps;

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getServings() {
        return servings;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Name: " + name + "\n Ingredients: " + ingredients +
                "\n Steps: " + steps;
    }
}
