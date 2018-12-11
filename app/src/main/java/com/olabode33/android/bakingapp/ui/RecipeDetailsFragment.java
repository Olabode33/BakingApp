package com.olabode33.android.bakingapp.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.adapters.RecipeStepAdapter;
import com.olabode33.android.bakingapp.model.Recipe;
import com.olabode33.android.bakingapp.model.RecipeStep;
import com.olabode33.android.bakingapp.utils.Constants;
import com.olabode33.android.bakingapp.utils.OnRecipeIngredientsSelectedListener;
import com.olabode33.android.bakingapp.utils.OnRecipeStepSelectedListener;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by obello004 on 12/6/2018.
 */

public class RecipeDetailsFragment extends Fragment {
    private Recipe mRecipe;

    @BindView(R.id.tv_recipe_ingredients)
    TextView mRecipeIngredentsLabel;

    @BindView(R.id.rv_recipe_step)
    RecyclerView mRecipeRecyclerView;

    OnRecipeStepSelectedListener mRecipeStepCallback;
    OnRecipeIngredientsSelectedListener mRecipeIngredientCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mRecipeStepCallback = (OnRecipeStepSelectedListener) context;
            mRecipeIngredientCallback = (OnRecipeIngredientsSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRecipeClickListener & OnRecipeIngredientsSelectedListener");
        }
    }

    public RecipeDetailsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            mRecipe = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_RECIPE_STEP_KEY));
        }

        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, rootView);

        if(mRecipe != null){
            mRecipeIngredentsLabel.setText(getString(R.string.recipe_details_ingredient_label));
            mRecipeIngredentsLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecipeIngredientCallback.onRecipeIngredientSelected(mRecipe.get_ingredents());
                }
            });

            RecipeStepAdapter recipeStepAdapter = new RecipeStepAdapter(mRecipe.get_steps(), rootView.getContext(), mRecipeStepCallback);

            mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            mRecipeRecyclerView.setAdapter(recipeStepAdapter);

            DividerItemDecoration decoration = new DividerItemDecoration(rootView.getContext(), DividerItemDecoration.VERTICAL);
            mRecipeRecyclerView.addItemDecoration(decoration);
        }

        //return super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.EXTRA_RECIPE_STEP_KEY, Parcels.wrap(mRecipe));
    }

    public void setRecipe(Recipe mRecipe) {
        this.mRecipe = mRecipe;
    }

    private void populateUI() {
        //mRecipeName.setText(mRecipe.get_name());
    }
}

