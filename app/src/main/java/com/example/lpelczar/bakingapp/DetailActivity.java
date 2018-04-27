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

    public static final String ARG_RECIPE = "extra-recipe";
    public static final String ARG_TWO_PANE = "two-pane";
    public static final String ARG_RECIPE_DETAILS_FRAGMENT = "recipe-details-fragment";
    public static final String ARG_RECIPE_STEP_FRAGMENT = "recipe-step-fragment";

    private Recipe recipe;
    private boolean twoPaneMode;
    private RecipeDetailsFragment recipeDetailsFragment;
    private RecipeStepFragment recipeStepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(ARG_RECIPE);
            twoPaneMode = savedInstanceState.getBoolean(ARG_TWO_PANE);
            recipeDetailsFragment = (RecipeDetailsFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, ARG_RECIPE_DETAILS_FRAGMENT);
            recipeStepFragment = (RecipeStepFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, ARG_RECIPE_STEP_FRAGMENT);
        } else {
            if (getIntent() == null) closeOnError();
            Bundle data = getIntent().getExtras();
            if (data != null) {
                recipe = data.getParcelable(ARG_RECIPE);
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

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            twoPaneMode = true;

            RecipeDetailsFragment recipeDetailsFragment = RecipeDetailsFragment.newInstance(
                    1, recipeDetails);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_details_fragment, recipeDetailsFragment)
                    .commit();

            RecipeStep recipeStep = recipe.getSteps().get(0);
            RecipeStepFragment recipeStepFragment = RecipeStepFragment.newInstance(recipeStep, false);

            FragmentManager stepFragmentManager = getSupportFragmentManager();
            stepFragmentManager.beginTransaction()
                    .add(R.id.recipe_step_fragment, recipeStepFragment)
                    .commit();
        } else {
            RecipeDetailsFragment recipeDetailsFragment = RecipeDetailsFragment.newInstance(
                    1, recipeDetails);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, recipeDetailsFragment)
                    .commit();
        }
    }

    @Override
    public void onRecipeDetailItemInteraction(RecipeDetail item) {

        if (item instanceof RecipeStep) {

            RecipeStep recipeStep = (RecipeStep) item;
            RecipeStepFragment recipeStepFragment = RecipeStepFragment.newInstance(recipeStep,
                    false);

            FragmentManager stepFragmentManager = getSupportFragmentManager();

            if (twoPaneMode) {
                stepFragmentManager.beginTransaction()
                        .replace(R.id.recipe_step_fragment, recipeStepFragment)
                        .commit();
            } else {
                stepFragmentManager.beginTransaction()
                        .replace(R.id.fragment, recipeStepFragment)
                        .commit();
            }
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.recipe_not_available, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ARG_RECIPE, recipe);
        outState.putBoolean(ARG_TWO_PANE, twoPaneMode);
        getSupportFragmentManager().putFragment(outState, ARG_RECIPE_DETAILS_FRAGMENT, recipeDetailsFragment);
        getSupportFragmentManager().putFragment(outState, ARG_RECIPE_STEP_FRAGMENT, recipeStepFragment);
        super.onSaveInstanceState(outState);
    }
}
