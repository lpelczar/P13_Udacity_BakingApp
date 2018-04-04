package com.example.lpelczar.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lpelczar.bakingapp.RecipeFragment.OnListFragmentInteractionListener;
import com.example.lpelczar.bakingapp.models.Recipe;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Recipe} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class RecipeRecyclerViewAdapter extends
        RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private final List<Recipe> recipes;
    private final OnListFragmentInteractionListener listener;

    public RecipeRecyclerViewAdapter(List<Recipe> recipes,
                                     OnListFragmentInteractionListener listener) {
        this.recipes = recipes;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.recipe = recipes.get(position);
        holder.name.setText(recipes.get(position).getName());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onListFragmentInteraction(holder.recipe);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView name;
        public Recipe recipe;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            name = view.findViewById(R.id.recipe_name_tv);
        }
    }
}
