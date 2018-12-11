package com.olabode33.android.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.model.Recipe;
import com.olabode33.android.bakingapp.utils.OnRecipeSelectedListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by obello004 on 12/6/2018.
 */

public class RecipeMasterAdapter extends RecyclerView.Adapter<RecipeMasterAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipeList;
    private Context mContext;
    private OnRecipeSelectedListener mOnRecipeSelectedListener;

    public RecipeMasterAdapter(Context context, List<Recipe> mRecipeList, OnRecipeSelectedListener recipe) {
        this.mContext = context;
        this.mRecipeList = mRecipeList;
        this.mOnRecipeSelectedListener = recipe;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context)
                    .inflate(R.layout.item_recipe, parent, false);



        return new RecipeViewHolder(v, mOnRecipeSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {
        Recipe recipe = mRecipeList.get(position);

        holder.recipeNameTextView.setText(recipe.get_name());
        if(recipe.get_image().equals(null) || recipe.get_image().equals("")){
            Picasso.get()
                    .load(R.mipmap.ic_recipe_book)
                    .placeholder(R.mipmap.ic_recipe_book)
                    .error(R.mipmap.ic_recipe_book_round)
                    .into(holder.recipeImageImageView);
        } else {
            Picasso.get()
                    .load(recipe.get_image())
                    .placeholder(R.mipmap.ic_recipe_step)
                    .error(R.mipmap.ic_recipe_step_round)
                    .into(holder.recipeImageImageView);
        }

        holder.recipeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe recipe = mRecipeList.get(position);
                mOnRecipeSelectedListener.onRecipeSelected(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recipe_name)
        TextView recipeNameTextView;
        @BindView(R.id.ll_recipe_item)
        LinearLayout recipeItem;
        @BindView(R.id.iv_recipe_image)
        ImageView recipeImageImageView;

        public RecipeViewHolder(View itemView, OnRecipeSelectedListener recipe) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
