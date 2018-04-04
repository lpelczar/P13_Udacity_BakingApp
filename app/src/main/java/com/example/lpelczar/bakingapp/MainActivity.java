package com.example.lpelczar.bakingapp;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.lpelczar.bakingapp.models.Recipe;
import com.example.lpelczar.bakingapp.models.RecipeDetail;
import com.example.lpelczar.bakingapp.services.RecipeAPIService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements
        RecipeFragment.OnRecipeFragmentInteractionListener,
        RecipeDetailsFragment.OnRecipeDetailsFragmentInteractionListener {

    List<Recipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getRecipesFromApi();
    }

    private void getRecipesFromApi() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        RecipeAPIService service = restAdapter.create(RecipeAPIService.class);

        service.getRecipes(new Callback<List<Recipe>>() {
            @Override
            public void success(List<Recipe> recipeResult, Response response) {
                recipes.addAll(recipeResult);
                startRecipesFragment();
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void startRecipesFragment() {
        RecipeFragment recipeFragment = RecipeFragment.newInstance(1);
        recipeFragment.setRecipes(recipes);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment, recipeFragment)
                .commit();
    }

    @Override
    public void onRecipeItemInteraction(Recipe recipe) {
        RecipeDetailsFragment recipeDetailsFragment = RecipeDetailsFragment.newInstance(1);
        List<RecipeDetail> recipeDetails = new ArrayList<>();
        recipeDetails.addAll(recipe.getIngredients());
        recipeDetails.addAll(recipe.getSteps());
        recipeDetailsFragment.setRecipeDetails(recipeDetails);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, recipeDetailsFragment)
                .commit();
    }

    @Override
    public void onRecipeDetailItemInteraction(RecipeDetail item) {
        Toast.makeText(getApplicationContext(), item.toString(), Toast.LENGTH_LONG).show();
    }
}
