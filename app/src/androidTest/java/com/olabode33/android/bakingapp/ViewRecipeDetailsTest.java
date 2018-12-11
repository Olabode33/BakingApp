package com.olabode33.android.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.olabode33.android.bakingapp.ui.MainActivity;
import com.olabode33.android.bakingapp.ui.RecipeDetailsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by obello004 on 12/11/2018.
 */
@RunWith(AndroidJUnit4.class)
public class ViewRecipeDetailsTest {
    public static final String RECIPE_NAME = "Nutella Pie";
    public static final String INGREDIENTS_LABEL = "Ingredients";

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipeItem_OpensRecipeDetialsActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_recipe_master))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Espresso.onView(withId(R.id.tv_recipe_ingredients)).check(matches(withText(INGREDIENTS_LABEL)));
    }
}
