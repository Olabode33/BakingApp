package com.olabode33.android.bakingapp.ui;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.model.Recipe;
import com.olabode33.android.bakingapp.model.RecipeStep;
import com.olabode33.android.bakingapp.utils.Constants;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailsActivity extends AppCompatActivity {
    private int mStepPosition;
    private List<RecipeStep> mRecipeStepsList;
    private RecipeStep mRecipeStep;
    private FragmentManager mFragmentManager;
    private RecipeStepFragment mRecipeStepFragment;

    @BindView(R.id.btn_next_recipe_step)
    Button mNextRecipeStepButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_details);
        ButterKnife.bind(this);

        //if(savedInstanceState != null) {
        //    mStepPosition = savedInstanceState.getInt(Constants.EXTRA_RECIPE_STEP_POSITION_KEY);
        //    Log.d("RecipeStepDetails", "Using Saved Instance State" + mStepPosition);
        //    mRecipeStepsList = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_RECIPE_STEP_KEY));
        //} else {
            Log.d("RecipeStepDetails", "Using Intent");
            Intent intent = getIntent();
            if (intent != null && (intent.hasExtra(Constants.EXTRA_RECIPE_STEP_KEY) && intent.hasExtra(Constants.EXTRA_RECIPE_STEP_POSITION_KEY))) {
                mStepPosition = intent.getIntExtra(Constants.EXTRA_RECIPE_STEP_POSITION_KEY, 0);
                mRecipeStepsList = Parcels.unwrap(intent.getParcelableExtra(Constants.EXTRA_RECIPE_STEP_KEY));
        //    }
        //}

        //if (mRecipeStepsList != null) {
            mRecipeStep = mRecipeStepsList.get(mStepPosition);
            mFragmentManager = getSupportFragmentManager();

            Log.d("RecipeStepDetailA", "Position: " + mStepPosition);
            updateStepUI(savedInstanceState);

            mNextRecipeStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextRecipeStep();
                }
            });
            setTitle(mRecipeStep.get_shortDescription());
        } else {
            Log.d("RecipeStepDetailsA", "no step list");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(Constants.EXTRA_RECIPE_STEP_POSITION_KEY, mStepPosition);
        outState.putParcelable(Constants.EXTRA_RECIPE_STEP_KEY, Parcels.wrap(mRecipeStepsList));
        //getSupportFragmentManager().putFragment(outState, Constants.EXTRA_RECIPE_STEP_KEY, mRecipeStepFragment);
    }

    private void nextRecipeStep() {
        if(mRecipeStepsList != null && mStepPosition >= 0) {
            if(mStepPosition + 1 < mRecipeStepsList.size()) {
                mStepPosition = mStepPosition + 1;
                updateStepUI(null);
            }
            else {
                Toast toast = Toast.makeText(this, getString(R.string.last_step_message), Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    private void updateStepUI(Bundle saved) {
        mRecipeStepFragment = new RecipeStepFragment();
        mRecipeStepFragment.setRecipeStep(mRecipeStepsList, mStepPosition);
        if(saved == null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.recipe_step_details_fragment, mRecipeStepFragment)
                    .addToBackStack(null)
                    .commit();
        }
        mRecipeStep = mRecipeStepsList.get(mStepPosition);
        setTitle(mRecipeStep.get_shortDescription());
    }
}
