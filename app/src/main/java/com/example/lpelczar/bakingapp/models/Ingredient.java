package com.example.lpelczar.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Ingredient implements RecipeDetail, Parcelable {

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

    //Parcelable
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    private Ingredient(Parcel in){
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.name =  in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.name);
    }
}
