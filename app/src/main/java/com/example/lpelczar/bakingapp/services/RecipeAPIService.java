package com.example.lpelczar.bakingapp.services;

import com.example.lpelczar.bakingapp.models.Recipe;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;


public interface RecipeAPIService {

    @GET("/baking.json")
    void getRecipes(Callback<List<Recipe>> cb);
}
