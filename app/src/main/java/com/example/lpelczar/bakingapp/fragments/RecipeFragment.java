package com.example.lpelczar.bakingapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lpelczar.bakingapp.R;
import com.example.lpelczar.bakingapp.adapters.RecipeRecyclerViewAdapter;
import com.example.lpelczar.bakingapp.models.Recipe;
import com.example.lpelczar.bakingapp.utils.RecipesVideoFrameAsync;

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_RECIPES = "recipes";
    private static final String ARG_RECYCLER_VIEW_STATE = "recycler-state";

    private int columnCount = 1;
    private List<Recipe> recipes;
    private RecyclerView recyclerView;
    private Parcelable recyclerViewState;

    private OnRecipeFragmentInteractionListener listener;

    public RecipeFragment() {
    }

    @SuppressWarnings("unused")
    public static RecipeFragment newInstance(int columnCount, List<Recipe> recipes) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putParcelableArrayList(ARG_RECIPES, new ArrayList<>(recipes));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            columnCount = savedInstanceState.getInt(ARG_COLUMN_COUNT);
            recipes = savedInstanceState.getParcelableArrayList(ARG_RECIPES);
            recyclerViewState = savedInstanceState.getParcelable(ARG_RECYCLER_VIEW_STATE);
        }

        if (getArguments() != null) {
            columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            recipes = getArguments().getParcelableArrayList(ARG_RECIPES);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }
            RecipeRecyclerViewAdapter recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(recipes, listener);
            recyclerView.setAdapter(recipeRecyclerViewAdapter);
            startVideoFramesAsyncTask(recipeRecyclerViewAdapter);
        }
        return view;
    }

    private void startVideoFramesAsyncTask(RecipeRecyclerViewAdapter recipeRecyclerViewAdapter) {
        RecipesVideoFrameAsync recipesVideoFrameAsync = new RecipesVideoFrameAsync();
        recipesVideoFrameAsync.execute(recipeRecyclerViewAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeFragmentInteractionListener) {
            listener = (OnRecipeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (recyclerViewState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    public interface OnRecipeFragmentInteractionListener {
        void onRecipeItemInteraction(Recipe recipe);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(ARG_COLUMN_COUNT, columnCount);
        outState.putParcelableArrayList(ARG_RECIPES, new ArrayList<>(recipes));
        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(ARG_RECYCLER_VIEW_STATE, recyclerViewState);
        super.onSaveInstanceState(outState);
    }
}
