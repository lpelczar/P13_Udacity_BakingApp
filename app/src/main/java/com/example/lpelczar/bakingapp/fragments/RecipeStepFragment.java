package com.example.lpelczar.bakingapp.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lpelczar.bakingapp.R;
import com.example.lpelczar.bakingapp.models.RecipeStep;


public class RecipeStepFragment extends Fragment {

    private static final String ARG_IS_LANDSCAPE = "is-landscape";
    private static final String ARG_RECIPE_STEP = "recipe-step";
    private RecipeStep recipeStep;
    private boolean isLandscape = false;

    public RecipeStepFragment() {
    }

    @SuppressWarnings("unused")
    public static RecipeStepFragment newInstance(RecipeStep recipeStep,
                                                 boolean isLandscape) {
        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_LANDSCAPE, isLandscape);
        args.putParcelable(ARG_RECIPE_STEP, recipeStep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            isLandscape = getArguments().getBoolean(ARG_IS_LANDSCAPE);
            recipeStep = getArguments().getParcelable(ARG_RECIPE_STEP);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        TextView descriptionTextView = view.findViewById(R.id.step_description_tv);
        descriptionTextView.setText(recipeStep.getDescription());

        return view;
    }
}
