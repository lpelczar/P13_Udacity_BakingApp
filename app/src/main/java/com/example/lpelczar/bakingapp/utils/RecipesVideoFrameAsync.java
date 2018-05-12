package com.example.lpelczar.bakingapp.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;

import com.example.lpelczar.bakingapp.adapters.RecipeRecyclerViewAdapter;
import com.example.lpelczar.bakingapp.models.Recipe;
import com.example.lpelczar.bakingapp.models.RecipeStep;

import java.util.HashMap;

public class RecipesVideoFrameAsync extends
        AsyncTask<RecipeRecyclerViewAdapter, Void, RecipeRecyclerViewAdapter> {

    @Override
    protected RecipeRecyclerViewAdapter doInBackground(RecipeRecyclerViewAdapter... adapters) {
        RecipeRecyclerViewAdapter adapter = adapters[0];
        for (Recipe recipe : adapter.getRecipes()) {
            RecipeStep lastStep = recipe.getSteps().get(recipe.getSteps().size() - 1);
            if (lastStep.getVideoURL() != null && !lastStep.getVideoURL().isEmpty()) {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(lastStep.getVideoURL(),
                        new HashMap<String, String>());
                Bitmap frame = mediaMetadataRetriever.getFrameAtTime(1000);
                recipe.setVideoFrame(frame);
            }
        }
        return adapter;
    }

    @Override
    protected void onPostExecute(RecipeRecyclerViewAdapter recipeRecyclerViewAdapter) {
        recipeRecyclerViewAdapter.notifyDataSetChanged();
    }
}
