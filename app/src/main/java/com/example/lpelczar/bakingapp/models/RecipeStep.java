package com.example.lpelczar.bakingapp.models;


public class RecipeStep implements RecipeDetail {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Short Description: " + shortDescription + " Description: " +
                description + " Video URL " + videoURL + " Thumbnail URL: " + thumbnailURL;
    }
}
