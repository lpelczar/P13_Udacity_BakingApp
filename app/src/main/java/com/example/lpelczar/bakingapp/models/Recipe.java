package com.example.lpelczar.bakingapp.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

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

    //Parcelable
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    private Recipe(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.servings = in.readInt();
        this.image =  in.readString();
        this.ingredients = new ArrayList<>();
        in.readTypedList(ingredients, Ingredient.CREATOR);
        this.steps = new ArrayList<>();
        in.readTypedList(steps, RecipeStep.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.servings);
        dest.writeString(this.image);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
    }
}
