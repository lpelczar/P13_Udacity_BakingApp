package com.example.lpelczar.bakingapp;

import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lpelczar.bakingapp.models.Recipe;
import com.example.lpelczar.bakingapp.services.RecipeAPIService;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        RecipeAPIService service = restAdapter.create(RecipeAPIService.class);

        service.getRecipes(new Callback<List<Recipe>>() {
            @Override
            public void success(List<Recipe> recipeResult, Response response) {
                for (Recipe r : recipeResult) {
                    Log.e("bla", r.toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

    }

}
