package com.example.lpelczar.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.lpelczar.bakingapp.fragments.RecipeDetailsFragment;
import com.example.lpelczar.bakingapp.fragments.RecipeStepFragment;
import com.example.lpelczar.bakingapp.models.Recipe;
import com.example.lpelczar.bakingapp.models.RecipeDetail;
import com.example.lpelczar.bakingapp.models.RecipeStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity implements
        RecipeDetailsFragment.OnRecipeDetailsFragmentInteractionListener {

    public static final String EXTRA_RECIPE = "extra_recipe";
    private final String RECIPE_KEY = "Recipe";
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE_KEY);
        } else {
            if (getIntent() == null) closeOnError();
            Bundle data = getIntent().getExtras();
            if (data != null) {
                recipe = data.getParcelable(EXTRA_RECIPE);
            } else {
                closeOnError();
                return;
            }
        }

        assert recipe != null;
        setTitle(recipe.getName());
        startRecipeDetailsFragment();
    }

    private void startRecipeDetailsFragment() {
        List<RecipeDetail> recipeDetails = new ArrayList<>();
        recipeDetails.addAll(recipe.getIngredients());
        recipeDetails.addAll(recipe.getSteps());
        RecipeDetailsFragment recipeDetailsFragment = RecipeDetailsFragment.newInstance(
                1, recipeDetails);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, recipeDetailsFragment)
                .commit();
    }

    @Override
    public void onRecipeDetailItemInteraction(RecipeDetail item) {
        if (item instanceof RecipeStep) {

            RecipeStep recipeStep = (RecipeStep) item;
            RecipeStepFragment recipeStepFragment = RecipeStepFragment.newInstance(recipeStep, false);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, recipeStepFragment)
                    .commit();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.recipe_not_available, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RECIPE_KEY, recipe);
        super.onSaveInstanceState(outState);
    }
}
