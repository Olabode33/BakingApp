package com.olabode33.android.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.model.RecipeIngredient;
import com.olabode33.android.bakingapp.utils.Constants;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;

public class RecipeIngredientsActivity extends AppCompatActivity {
    List<RecipeIngredient> mRecipeIngredients;
    String mRecipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients);

        Intent intent = getIntent();
        if(intent != null && (intent.hasExtra(Constants.EXTRA_RECIPE_INGREDIENT_KEY) && intent.hasExtra(Constants.EXTRA_RECIPE_NAME_KEY))) {
            mRecipeName = intent.getStringExtra(Constants.EXTRA_RECIPE_NAME_KEY);
            mRecipeIngredients = Parcels.unwrap(intent.getParcelableExtra(Constants.EXTRA_RECIPE_INGREDIENT_KEY));

            RecipeIngredientFragment recipeIngredientFragment = new RecipeIngredientFragment();

            recipeIngredientFragment.setRecipeIngredients(mRecipeIngredients);

            FragmentManager fragmentManager = getSupportFragmentManager();
            if(savedInstanceState == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_ingredients_fragment, recipeIngredientFragment)
                        .commit();
            }
            setTitle(mRecipeName);
        }
    }
}
