package com.example.lpelczar.bakingapp;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;

import com.example.lpelczar.bakingapp.adapters.HeterogeneousRecyclerViewAdapter;
import com.example.lpelczar.bakingapp.models.RecipeDetail;
import com.example.lpelczar.bakingapp.models.RecipeStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class StepsVideoFrameAsync extends
        AsyncTask<HeterogeneousRecyclerViewAdapter, Void, HeterogeneousRecyclerViewAdapter> {

    @Override
    protected HeterogeneousRecyclerViewAdapter doInBackground(HeterogeneousRecyclerViewAdapter... adapters) {
        HeterogeneousRecyclerViewAdapter adapter = adapters[0];
        List<RecipeStep> recipeSteps = new ArrayList<>();
        for (RecipeDetail recipeDetail : adapter.getRecipeDetails()) {
            if (recipeDetail instanceof RecipeStep) {
                recipeSteps.add((RecipeStep) recipeDetail);
            }
        }
        for (RecipeStep recipeStep : recipeSteps) {
            if (recipeStep.getVideoURL() != null && !recipeStep.getVideoURL().isEmpty()) {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(recipeStep.getVideoURL(),
                        new HashMap<String, String>());
                Bitmap frame = mediaMetadataRetriever.getFrameAtTime(1000);
                recipeStep.setVideoFrame(frame);
            }
        }
        return adapter;
    }

    @Override
    protected void onPostExecute(HeterogeneousRecyclerViewAdapter adapter) {
        adapter.notifyDataSetChanged();
    }
}
