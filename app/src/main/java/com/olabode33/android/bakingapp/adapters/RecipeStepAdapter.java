package com.olabode33.android.bakingapp.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.model.RecipeStep;
import com.olabode33.android.bakingapp.utils.OnRecipeStepSelectedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by obello004 on 12/8/2018.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepViewHolder> {

    private List<RecipeStep> mRecipeStepList;
    private Context mContext;
    private OnRecipeStepSelectedListener mOnRecipeStepSelectedListener;

    public RecipeStepAdapter(List<RecipeStep> mRecipeStepList, Context mContext, OnRecipeStepSelectedListener onRecipeStepSelectedListener) {
        this.mRecipeStepList = mRecipeStepList;
        this.mContext = mContext;
        this.mOnRecipeStepSelectedListener = onRecipeStepSelectedListener;
    }

    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context)
                    .inflate(R.layout.item_recipe_step, parent, false);

        return new RecipeStepViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder holder, final int position) {
        RecipeStep recipeStep = mRecipeStepList.get(position);

        holder.mRecipeStepTextView.setText(recipeStep.get_shortDescription());
        holder.mRecipeStepItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RecipeStep recipeStep = mRecipeStepList.get(position);
                mOnRecipeStepSelectedListener.onRecipeStepSelected(position, mRecipeStepList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeStepList.size();
    }

    public static class RecipeStepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recipe_step_desc)
        TextView mRecipeStepTextView;
        @BindView(R.id.ll_recipe_step_item)
        LinearLayout mRecipeStepItem;

        public RecipeStepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
