package com.example.lpelczar.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lpelczar.bakingapp.RecipeDetailsFragment.OnListFragmentInteractionListener;
import com.example.lpelczar.bakingapp.models.Ingredient;
import com.example.lpelczar.bakingapp.models.RecipeDetail;
import com.example.lpelczar.bakingapp.models.RecipeStep;

import java.util.List;
import java.util.Locale;

public class HeterogeneousRecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final List<RecipeDetail> recipeDetails;
    private final int INGREDIENT = 0, STEP = 1;
    private final OnListFragmentInteractionListener listener;

    HeterogeneousRecyclerViewAdapter(List<RecipeDetail> recipeDetails, OnListFragmentInteractionListener listener) {
        this.recipeDetails = recipeDetails;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case INGREDIENT:
                View ingredientView = inflater.inflate(R.layout.ingredient_item, viewGroup, false);
                viewHolder = new IngredientViewHolder(ingredientView);
                break;
            case STEP:
                View recipeStepView = inflater.inflate(R.layout.recipe_step_item, viewGroup, false);
                viewHolder = new RecipeStepViewHolder(recipeStepView);
                break;
            default:
                throw new IllegalArgumentException("Wrong element type!");
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case INGREDIENT:
                IngredientViewHolder ingredientViewHolder = (IngredientViewHolder) viewHolder;
                configureIngredientViewHolder(ingredientViewHolder, position);
                break;
            case STEP:
                RecipeStepViewHolder recipeStepViewHolder = (RecipeStepViewHolder) viewHolder;
                configureRecipeStepViewHolder(recipeStepViewHolder, position);
                break;
        }
    }

    private void configureIngredientViewHolder(IngredientViewHolder ingredientViewHolder, int position) {

        final Ingredient ingredient = (Ingredient) recipeDetails.get(position);
        ingredientViewHolder.ingredientName.setText(ingredient.getName());
        ingredientViewHolder.ingredientMeasure.setText(ingredient.getMeasure());
        ingredientViewHolder.ingredientQuantity.setText(String.format(Locale.getDefault() ,"%.2f",
                ingredient.getQuantity()));

        ingredientViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onListFragmentInteraction(ingredient);
                }
            }
        });
    }

    private void configureRecipeStepViewHolder(RecipeStepViewHolder recipeStepViewHolder, int position) {

        final RecipeStep recipeStep = (RecipeStep) recipeDetails.get(position);
        recipeStepViewHolder.stepId.setText(recipeStep.getId());
        recipeStepViewHolder.stepShortDescription.setText(recipeStep.getShortDescription());

        recipeStepViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onListFragmentInteraction(recipeStep);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return recipeDetails.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (recipeDetails.get(position) instanceof Ingredient) {
            return INGREDIENT;
        } else if (recipeDetails.get(position) instanceof RecipeStep) {
            return STEP;
        }
        return -1;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        final TextView ingredientName;
        final TextView ingredientQuantity;
        final TextView ingredientMeasure;

        IngredientViewHolder(View view) {
            super(view);
            this.view = view;
            ingredientName = view.findViewById(R.id.ingredient_name_tv);
            ingredientQuantity = view.findViewById(R.id.ingredient_quantity_tv);
            ingredientMeasure = view.findViewById(R.id.ingredient_measure_tv);
        }
    }

    public class RecipeStepViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        final TextView stepId;
        final TextView stepShortDescription;

        RecipeStepViewHolder(View view) {
            super(view);
            this.view = view;
            stepId = view.findViewById(R.id.step_id_tv);
            stepShortDescription = view.findViewById(R.id.step_short_description_tv);
        }
    }
}
