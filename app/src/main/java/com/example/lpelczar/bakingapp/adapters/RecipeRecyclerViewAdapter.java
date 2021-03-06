package com.example.lpelczar.bakingapp.adapters;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lpelczar.bakingapp.R;
import com.example.lpelczar.bakingapp.fragments.RecipeFragment.OnRecipeFragmentInteractionListener;
import com.example.lpelczar.bakingapp.models.Recipe;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;
import java.util.Locale;

public class RecipeRecyclerViewAdapter extends
        RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private final List<Recipe> recipes;
    private final OnRecipeFragmentInteractionListener listener;

    public RecipeRecyclerViewAdapter(List<Recipe> recipes,
                              OnRecipeFragmentInteractionListener listener) {
        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);
        holder.name.setText(recipe.getName());
        holder.servings.setText(String.format(Locale.getDefault(), "Servings: %s",
                recipe.getServings()));

        if (recipe.getImage() != null && !recipe.getImage().isEmpty()) {
            loadImage(holder, recipe.getImage());
        } else if (recipe.getVideoFrame() != null) {
            Drawable drawable = new BitmapDrawable(Resources.getSystem(), recipe.getVideoFrame());
            holder.relativeLayout.setBackground(drawable);
            holder.spoonImage.setVisibility(View.INVISIBLE);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onRecipeItemInteraction(recipe);
                }
            }
        });
    }

    private void loadImage(@NonNull final ViewHolder holder, String path) {
        Picasso.with(holder.relativeLayout.getContext()).load(path).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.relativeLayout.setBackground(new BitmapDrawable(Resources.getSystem(), bitmap));
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {}

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView name;
        final TextView servings;
        final RelativeLayout relativeLayout;
        final ImageView spoonImage;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            name = view.findViewById(R.id.recipe_name_tv);
            servings = view.findViewById(R.id.recipe_servings_tv);
            relativeLayout = view.findViewById(R.id.recipe_rl);
            spoonImage = view.findViewById(R.id.spoon_iv);
        }
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
