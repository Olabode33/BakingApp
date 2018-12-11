package com.olabode33.android.bakingapp;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.olabode33.android.bakingapp.model.Recipe;
import com.olabode33.android.bakingapp.model.RecipeStep;
import com.olabode33.android.bakingapp.ui.RecipeStepDetailsActivity;
import com.olabode33.android.bakingapp.utils.Constants;
import com.olabode33.android.bakingapp.utils.JsonUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by obello004 on 12/11/2018.
 */
@RunWith(AndroidJUnit4.class)
public class ViewRecipeStepsTest {
    private int mRecipeStepPosition;
    private List<RecipeStep> mRecipeStepList;

    private static final String RECIPE_INTRO_STEP = "Recipe Introduction";

    @Rule
    public ActivityTestRule<RecipeStepDetailsActivity> recipeStepDetailsActivityActivityTestRule = new ActivityTestRule<>(RecipeStepDetailsActivity.class, false, false);

    @Test
    public void RecipeDetailsStepUp() {
        List<Recipe> recipeList = JsonUtil.parseRecipeListJson(JsonUtil.readJSONRaw(getContext()));
        Recipe recipe_NutellaPie = recipeList.get(0);
        mRecipeStepList = recipe_NutellaPie.get_steps();
        mRecipeStepPosition = 0;

        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_RECIPE_STEP_KEY, Parcels.wrap(mRecipeStepList));
        intent.putExtra(Constants.EXTRA_RECIPE_STEP_POSITION_KEY, mRecipeStepPosition);
        recipeStepDetailsActivityActivityTestRule.launchActivity(intent);

        Espresso.onView(withId(R.id.tv_recipe_step_desc_long)).check(matches(withText(RECIPE_INTRO_STEP)));
    }
}
