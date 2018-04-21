package com.example.lpelczar.bakingapp.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.example.lpelczar.bakingapp.models.RecipeStep;


public class RecipeStepFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_RECIPE_STEP = "recipe-step";

    private int columnCount = 1;
    private RecipeStep recipeStep;

    private OnRecipeStepFragmentInteractionListener listener;

    public RecipeStepFragment() {
    }

    @SuppressWarnings("unused")
    public static RecipeStepFragment newInstance(int columnCount, RecipeStep recipeStep) {
        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putParcelable(ARG_RECIPE_STEP, recipeStep);
        fragment.setArguments(args);
        return fragment;
    }
}
