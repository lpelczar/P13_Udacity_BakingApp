package com.example.lpelczar.bakingapp.adapters;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lpelczar.bakingapp.R;
import com.example.lpelczar.bakingapp.fragments.RecipeDetailsFragment.OnRecipeDetailsFragmentInteractionListener;
import com.example.lpelczar.bakingapp.models.Ingredient;
import com.example.lpelczar.bakingapp.models.RecipeDetail;
import com.example.lpelczar.bakingapp.models.RecipeStep;

import java.util.List;
import java.util.Locale;

public class HeterogeneousRecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final List<RecipeDetail> recipeDetails;
    private final int INGREDIENT = 0, STEP = 1;
    private final OnRecipeDetailsFragmentInteractionListener listener;

    public HeterogeneousRecyclerViewAdapter(List<RecipeDetail> recipeDetails, OnRecipeDetailsFragmentInteractionListener listener) {
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
        ingredientViewHolder.ingredientName.setText(String.format("%s", ingredient.getName()));
        ingredientViewHolder.ingredientMeasure.setText(String.format("%s", ingredient.getMeasure()));
        ingredientViewHolder.ingredientQuantity.setText(String.format(Locale.getDefault() ,"%.1f",
                ingredient.getQuantity()));

        ingredientViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onRecipeDetailItemInteraction(ingredient);
                }
            }
        });
    }

    private void configureRecipeStepViewHolder(RecipeStepViewHolder recipeStepViewHolder, int position) {

        final RecipeStep recipeStep = (RecipeStep) recipeDetails.get(position);
        recipeStepViewHolder.stepShortDescription.setText(recipeStep.getShortDescription());

        if (recipeStep.getVideoFrame() != null) {
            Drawable drawable = new BitmapDrawable(Resources.getSystem(), recipeStep.getVideoFrame());
            recipeStepViewHolder.relativeLayout.setBackground(drawable);
            recipeStepViewHolder.spoonImage.setVisibility(View.INVISIBLE);
        }

        recipeStepViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onRecipeDetailItemInteraction(recipeStep);
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
        final TextView stepShortDescription;
        final RelativeLayout relativeLayout;
        final ImageView spoonImage;

        RecipeStepViewHolder(View view) {
            super(view);
            this.view = view;
            stepShortDescription = view.findViewById(R.id.step_short_description_tv);
            relativeLayout = view.findViewById(R.id.step_rl);
            spoonImage = view.findViewById(R.id.spoon_iv);
        }
    }
}
