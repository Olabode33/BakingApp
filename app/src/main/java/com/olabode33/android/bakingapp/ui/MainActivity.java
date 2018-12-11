package com.olabode33.android.bakingapp.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.model.Recipe;
import com.olabode33.android.bakingapp.utils.Constants;
import com.olabode33.android.bakingapp.utils.NetworkUtils;
import com.olabode33.android.bakingapp.utils.OnRecipeSelectedListener;

import org.parceler.Parcels;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnRecipeSelectedListener {

    //private int mPosition;
    //private List<Recipe> mRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        RecipeMasterFragment recipeDetailsFragment = new RecipeMasterFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_list_fragment, recipeDetailsFragment)
                    .commit();
        }
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        //Toast toast = Toast.makeText(mContext, "Click on Recipe " + recipe.get_name(), Toast.LENGTH_SHORT);
        //toast.show();
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(Constants.EXTRA_RECIPE_KEY, Parcels.wrap(recipe));
        startActivity(intent);
    }


}
