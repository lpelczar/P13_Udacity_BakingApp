package com.example.lpelczar.bakingapp;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.lpelczar.bakingapp.fragments.RecipeDetailsFragment;
import com.example.lpelczar.bakingapp.fragments.RecipeFragment;
import com.example.lpelczar.bakingapp.models.Recipe;
import com.example.lpelczar.bakingapp.models.RecipeDetail;
import com.example.lpelczar.bakingapp.models.RecipeStep;
import com.example.lpelczar.bakingapp.services.RecipeAPIService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements
        RecipeFragment.OnRecipeFragmentInteractionListener {

    public static final String ARG_RECIPE_FRAGMENT = "recipe-fragment";
    private RecipeFragment recipeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            recipeFragment = (RecipeFragment) getSupportFragmentManager().getFragment(
                    savedInstanceState, ARG_RECIPE_FRAGMENT);
        }

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
                startRecipesFragment(recipeResult);
            }
            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void startRecipesFragment(List<Recipe> recipes) {

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            startFragment(recipes, 3);
        } else {
            startFragment(recipes, 1);
        }
    }

    private void startFragment(List<Recipe> recipes, int columnCount) {
        RecipeFragment recipeFragment = RecipeFragment.newInstance(columnCount, recipes);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment, recipeFragment)
                .commit();
    }

    @Override
    public void onRecipeItemInteraction(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_RECIPE, recipe);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, ARG_RECIPE_FRAGMENT, recipeFragment);
        super.onSaveInstanceState(outState);
    }
}
