package com.olabode33.android.bakingapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.model.Recipe;
import com.olabode33.android.bakingapp.model.RecipeIngredient;
import com.olabode33.android.bakingapp.model.RecipeStep;
import com.olabode33.android.bakingapp.utils.Constants;
import com.olabode33.android.bakingapp.utils.OnRecipeIngredientsSelectedListener;
import com.olabode33.android.bakingapp.utils.OnRecipeStepSelectedListener;

import org.parceler.Parcels;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements OnRecipeStepSelectedListener, OnRecipeIngredientsSelectedListener {
    private Recipe mRecipe;
    private Boolean isDualPane = false;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if(savedInstanceState != null) {
            mRecipe = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_RECIPE_KEY));
        } else {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra(Constants.EXTRA_RECIPE_KEY)) {
                mRecipe = Parcels.unwrap(intent.getParcelableExtra(Constants.EXTRA_RECIPE_KEY));
            }
        }

        if(mRecipe != null){
            RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();

            recipeDetailsFragment.setRecipe(mRecipe);

            mFragmentManager = getSupportFragmentManager();

            if(findViewById(R.id.recipe_details_detail_fragment) != null) {
                isDualPane = true;
            } else {
                isDualPane = false;
            }

            if(savedInstanceState == null) {
                mFragmentManager.beginTransaction()
                        .add(R.id.recipe_details_fragment, recipeDetailsFragment)
                        .commit();
            }
            if(isDualPane){
                RecipeIngredientFragment recipeIngredientFragment = new RecipeIngredientFragment();
                recipeIngredientFragment.setRecipeIngredients(mRecipe.get_ingredents());
                mFragmentManager.beginTransaction()
                        .replace(R.id.recipe_details_detail_fragment, recipeIngredientFragment)
                        .commit();
            }

            setTitle(mRecipe.get_name());
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable(Constants.EXTRA_RECIPE_KEY, Parcels.wrap(mRecipe));
    }

    @Override
    public void onRecipeStepSelected(int stepPosition, List<RecipeStep> recipeStepList) {
        if(isDualPane) {
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            recipeStepFragment.setRecipeStep(recipeStepList, stepPosition);
            mFragmentManager.beginTransaction()
                    .replace(R.id.recipe_details_detail_fragment, recipeStepFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeStepDetailsActivity.class);
            intent.putExtra(Constants.EXTRA_RECIPE_STEP_KEY, Parcels.wrap(recipeStepList));
            intent.putExtra(Constants.EXTRA_RECIPE_STEP_POSITION_KEY, stepPosition);
            startActivity(intent);
        }
    }


    @Override
    public void onRecipeIngredientSelected(List<RecipeIngredient> recipeIngredients) {
        if(isDualPane){
            RecipeIngredientFragment recipeIngredientFragment = new RecipeIngredientFragment();
            recipeIngredientFragment.setRecipeIngredients(recipeIngredients);
            mFragmentManager.beginTransaction()
                    .replace(R.id.recipe_details_detail_fragment, recipeIngredientFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeIngredientsActivity.class);
            intent.putExtra(Constants.EXTRA_RECIPE_INGREDIENT_KEY, Parcels.wrap(recipeIngredients));
            intent.putExtra(Constants.EXTRA_RECIPE_NAME_KEY, mRecipe.get_name());
            startActivity(intent);
        }
    }
}
