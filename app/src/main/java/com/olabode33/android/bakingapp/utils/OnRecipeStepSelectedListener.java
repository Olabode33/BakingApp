package com.olabode33.android.bakingapp.utils;

import com.olabode33.android.bakingapp.model.RecipeStep;

import java.util.List;

/**
 * Created by obello004 on 12/8/2018.
 */

public interface OnRecipeStepSelectedListener {
    void onRecipeStepSelected(int position, List<RecipeStep> recipes);
}
