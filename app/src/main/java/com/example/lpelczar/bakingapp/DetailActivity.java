package com.example.lpelczar.bakingapp;

import android.content.Intent;
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

        recipeDetailsFragment = RecipeDetailsFragment.newInstance(
                1, recipeDetails);

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            twoPaneMode = true;

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_details_fragment, recipeDetailsFragment)
                    .commit();

            if (recipeStepFragment == null) {
                recipeStepFragment = RecipeStepFragment.newInstance(recipe.getSteps().get(0), false);
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_step_fragment, recipeStepFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, recipeDetailsFragment)
                    .commit();
        }
    }

    @Override
    public void onRecipeDetailItemInteraction(RecipeDetail item) {

        if (item instanceof RecipeStep) {
            recipeStepFragment = RecipeStepFragment.newInstance((RecipeStep) item, false);

            if (twoPaneMode) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_step_fragment, recipeStepFragment)
                        .commit();
            } else {
                Intent intent = new Intent(this, StepActivity.class);
                intent.putExtra(StepActivity.ARG_RECIPE_NAME, recipe.getName());
                intent.putExtra(StepActivity.ARG_RECIPE_STEP, item);
                startActivity(intent);
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
        if (recipeDetailsFragment != null && recipeDetailsFragment.isAdded()) {
            getSupportFragmentManager()
                    .putFragment(outState, ARG_RECIPE_DETAILS_FRAGMENT, recipeDetailsFragment);
        }
        if (recipeStepFragment != null && recipeStepFragment.isAdded()) {
            getSupportFragmentManager()
                    .putFragment(outState, ARG_RECIPE_STEP_FRAGMENT, recipeStepFragment);
        }
        super.onSaveInstanceState(outState);
    }
}
