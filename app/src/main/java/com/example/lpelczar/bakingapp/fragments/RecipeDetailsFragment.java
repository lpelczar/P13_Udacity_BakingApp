package com.example.lpelczar.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lpelczar.bakingapp.adapters.HeterogeneousRecyclerViewAdapter;
import com.example.lpelczar.bakingapp.R;
import com.example.lpelczar.bakingapp.models.RecipeDetail;

import java.util.List;


public class RecipeDetailsFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int columnCount = 1;
    private OnRecipeDetailsFragmentInteractionListener listener;

    private List<RecipeDetail> recipeDetails;

    public void setRecipeDetails(List<RecipeDetail> recipeDetails) {
        this.recipeDetails = recipeDetails;
    }

    public RecipeDetailsFragment() {
    }

    @SuppressWarnings("unused")
    public static RecipeDetailsFragment newInstance(int columnCount) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }
            recyclerView.setAdapter(new HeterogeneousRecyclerViewAdapter(recipeDetails, listener));
        }
        return view;
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
}
