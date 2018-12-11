package com.olabode33.android.bakingapp.utils;

import com.olabode33.android.bakingapp.model.RecipeIngredient;

import java.util.List;

/**
 * Created by obello004 on 12/10/2018.
 */

public interface OnRecipeIngredientsSelectedListener {
    void onRecipeIngredientSelected(List<RecipeIngredient> recipeIngredients);
}
