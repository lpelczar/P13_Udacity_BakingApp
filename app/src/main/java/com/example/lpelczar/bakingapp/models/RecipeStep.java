package com.example.lpelczar.bakingapp.models;


public class RecipeStep {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    @Override
    public String toString() {
        return "ID: " + id + " Short Description: " + shortDescription + " Description: " +
                description + " Video URL " + videoURL + " Thumbnail URL: " + thumbnailURL;
    }
}
