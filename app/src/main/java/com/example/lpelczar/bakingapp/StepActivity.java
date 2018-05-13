package com.example.lpelczar.bakingapp;

import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.lpelczar.bakingapp.fragments.RecipeStepFragment;
import com.example.lpelczar.bakingapp.models.RecipeStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StepActivity extends AppCompatActivity {

    public static final String ARG_RECIPE_STEP_FRAGMENT = "recipe-step-fragment";
    public static final String ARG_RECIPE_STEP = "recipe-step";
    public static final String ARG_RECIPE_NAME = "recipe-name";
    public static final String ARG_RECIPE_STEPS = "recipe-steps";

    private RecipeStepFragment recipeStepFragment;
    private RecipeStep recipeStep;
    private String recipeName;
    private List<RecipeStep> recipeSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }

        setContentView(R.layout.activity_step);

        if (savedInstanceState != null) {
            recipeStep = savedInstanceState.getParcelable(ARG_RECIPE_STEP);
            recipeStepFragment = (RecipeStepFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, ARG_RECIPE_STEP_FRAGMENT);
            recipeName = savedInstanceState.getString(ARG_RECIPE_NAME);
            recipeSteps = savedInstanceState.getParcelableArrayList(ARG_RECIPE_STEPS);
        } else {
            if (getIntent() == null) closeOnError();
            Bundle data = getIntent().getExtras();
            if (data != null) {
                recipeStep = data.getParcelable(ARG_RECIPE_STEP);
                recipeName = data.getString(ARG_RECIPE_NAME);
                recipeSteps = data.getParcelableArrayList(ARG_RECIPE_STEPS);
            } else {
                closeOnError();
                return;
            }
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(recipeName);
        startRecipeStepFragment();
    }

    private void startRecipeStepFragment() {
        if (recipeStepFragment == null) {
            recipeStepFragment = RecipeStepFragment.newInstance(recipeStep,
                    recipeSteps, false);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, recipeStepFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, recipeStepFragment)
                    .commit();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Step not available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ARG_RECIPE_STEP, recipeStep);
        outState.putString(ARG_RECIPE_NAME, recipeName);
        if (recipeStepFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, ARG_RECIPE_STEP_FRAGMENT, recipeStepFragment);
        }
        outState.putParcelableArrayList(ARG_RECIPE_STEPS, new ArrayList<>(recipeSteps));
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
