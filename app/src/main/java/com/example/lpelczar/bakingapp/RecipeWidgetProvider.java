package com.example.lpelczar.bakingapp;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RemoteViews;

import com.example.lpelczar.bakingapp.models.Ingredient;
import com.example.lpelczar.bakingapp.models.Recipe;
import com.google.gson.Gson;

import java.util.List;

public class RecipeWidgetProvider extends AppWidgetProvider {

    public static String SHARED_PREFERENCES = "RecipeWidgetData";
    public static String KEY_RECIPE = "Recipe";

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, String recipeName, List<Ingredient> ingredients) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);
        views.setTextViewText(R.id.widget_recipe_name, recipeName);
        views.removeAllViews(R.id.widget_container);
        views.setViewVisibility(R.id.widget_starter_text, View.GONE);
        views.setViewVisibility(R.id.widget_starter_image, View.GONE);

        for (Ingredient ingredient : ingredients) {
            RemoteViews ingredientView = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_item);
            ingredientView.setTextViewText(R.id.ingredient_name_tv, ingredient.getName());
            ingredientView.setTextViewText(R.id.ingredient_quantity_tv,
                    String.format("%s %s", ingredient.getQuantity(), ingredient.getMeasure()));
            views.addView(R.id.widget_container, ingredientView);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String recipeString = sharedPreferences.getString(KEY_RECIPE, null);
        Recipe recipe = new Gson().fromJson(recipeString, Recipe.class);
        if (recipe != null) {
            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId, recipe.getName(), recipe.getIngredients());
            }
        }
    }
}

