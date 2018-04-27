package com.example.lpelczar.bakingapp.fragments;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_RECIPES = "recipes";

    private int columnCount = 1;
    private List<Recipe> recipes;

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
            RecyclerView recyclerView = (RecyclerView) view;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }
            recyclerView.setAdapter(new RecipeRecyclerViewAdapter(recipes, listener));
        }
        return view;
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

    public interface OnRecipeFragmentInteractionListener {
        void onRecipeItemInteraction(Recipe recipe);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(ARG_COLUMN_COUNT, columnCount);
        outState.putParcelableArrayList(ARG_RECIPES, new ArrayList<>(recipes));
        super.onSaveInstanceState(outState);
    }
}
