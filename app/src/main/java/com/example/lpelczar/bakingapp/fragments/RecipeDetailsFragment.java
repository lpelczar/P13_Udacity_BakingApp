package com.example.lpelczar.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lpelczar.bakingapp.R;
import com.example.lpelczar.bakingapp.StepsVideoFrameAsync;
import com.example.lpelczar.bakingapp.adapters.HeterogeneousRecyclerViewAdapter;
import com.example.lpelczar.bakingapp.models.RecipeDetail;

import java.util.ArrayList;
import java.util.List;


public class RecipeDetailsFragment extends Fragment {

    private static final String ARG_RECIPE_DETAILS = "recipe-details";
    private List<RecipeDetail> recipeDetails;

    private OnRecipeDetailsFragmentInteractionListener listener;

    public RecipeDetailsFragment() {
    }

    @SuppressWarnings("unused")
    public static RecipeDetailsFragment newInstance(int columnCount, List<RecipeDetail> recipeDetails) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_RECIPE_DETAILS, new ArrayList<>(recipeDetails));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            recipeDetails = savedInstanceState.getParcelableArrayList(ARG_RECIPE_DETAILS);
        }

        if (getArguments() != null) {
            recipeDetails = getArguments().getParcelableArrayList(ARG_RECIPE_DETAILS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            HeterogeneousRecyclerViewAdapter adapter = new HeterogeneousRecyclerViewAdapter(recipeDetails, listener);
            recyclerView.setAdapter(adapter);
            startVideoFramesAsyncTask(adapter);
        }
        return view;
    }

    private void startVideoFramesAsyncTask(HeterogeneousRecyclerViewAdapter adapter) {
        StepsVideoFrameAsync stepsVideoFrameAsync = new StepsVideoFrameAsync();
        stepsVideoFrameAsync.execute(adapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeDetailsFragmentInteractionListener) {
            listener = (OnRecipeDetailsFragmentInteractionListener) context;
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

    public interface OnRecipeDetailsFragmentInteractionListener {
        void onRecipeDetailItemInteraction(RecipeDetail item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(ARG_RECIPE_DETAILS, new ArrayList<>(recipeDetails));
        super.onSaveInstanceState(outState);

    }
}
