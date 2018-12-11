package com.olabode33.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.upstream.ContentDataSource;
import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.adapters.RecipeIngredientAdapter;
import com.olabode33.android.bakingapp.model.RecipeIngredient;
import com.olabode33.android.bakingapp.utils.Constants;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by obello004 on 12/10/2018.
 */

public class RecipeIngredientFragment extends Fragment {

    View mView;
    @BindView(R.id.rv_recipe_ingredient) RecyclerView mIngredientsRecyclerView;
    List<RecipeIngredient> mRecipeIngredients;

    public RecipeIngredientFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        if(savedInstanceState != null) {
            mRecipeIngredients = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_RECIPE_INGREDIENT_KEY));
        }

        mView = inflater.inflate(R.layout.fragment_recipe_ingredient, container, false);
        ButterKnife.bind(this, mView);

        if (mRecipeIngredients != null) {
            RecipeIngredientAdapter recipeIngredientAdapter = new RecipeIngredientAdapter(mRecipeIngredients, mView.getContext());
            mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
            mIngredientsRecyclerView.setAdapter(recipeIngredientAdapter);

            DividerItemDecoration decoration = new DividerItemDecoration(mView.getContext(), DividerItemDecoration.VERTICAL);
            mIngredientsRecyclerView.addItemDecoration(decoration);
        }

        return  mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setRecipeIngredients(List<RecipeIngredient> mRecipeIngredients) {
        this.mRecipeIngredients = mRecipeIngredients;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.EXTRA_RECIPE_INGREDIENT_KEY, Parcels.wrap(mRecipeIngredients));
    }
}
