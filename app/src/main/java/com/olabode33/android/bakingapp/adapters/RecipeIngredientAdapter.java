package com.olabode33.android.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.model.RecipeIngredient;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by obello004 on 12/10/2018.
 */

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.RecipeIngredientViewHolder> {
    List<RecipeIngredient> mRecipeIngredients;
    Context mContext;

    public RecipeIngredientAdapter(List<RecipeIngredient> mRecipeIngredients, Context mContext) {
        this.mRecipeIngredients = mRecipeIngredients;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_recipe_ingredient, parent, false);

        return new RecipeIngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientViewHolder holder, int position) {
        RecipeIngredient recipeIngredient = mRecipeIngredients.get(position);

        holder.mIngredientNameTextView.setText(recipeIngredient.get_ingredient());
        holder.mIngredientQuantityTextView.setText(String.valueOf(recipeIngredient.get_quantity()));
        holder.mIngredientMeasureTextView.setText(recipeIngredient.get_measure());
    }

    @Override
    public int getItemCount() {
        return mRecipeIngredients.size();
    }

    public static class RecipeIngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ingredient_name) TextView mIngredientNameTextView;
        @BindView(R.id.tv_ingredient_quantity) TextView mIngredientQuantityTextView;
        @BindView(R.id.tv_ingredient_measure) TextView mIngredientMeasureTextView;

        public RecipeIngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
