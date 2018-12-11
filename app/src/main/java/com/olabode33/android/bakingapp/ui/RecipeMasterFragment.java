package com.olabode33.android.bakingapp.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.adapters.RecipeMasterAdapter;
import com.olabode33.android.bakingapp.model.Recipe;
import com.olabode33.android.bakingapp.utils.JsonUtil;
import com.olabode33.android.bakingapp.utils.NetworkUtils;
import com.olabode33.android.bakingapp.utils.OnRecipeSelectedListener;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by obello004 on 12/6/2018.
 */

public class RecipeMasterFragment extends Fragment {

    View mView;
    private List<Recipe> mRecipeList;
    @BindView(R.id.rv_recipe_master) RecyclerView mRecipeRecyclerView;

    OnRecipeSelectedListener mCallback;

    private int mOrientation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallback = (OnRecipeSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRecipeClickListener");
        }
    }

    public RecipeMasterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        //Log.i("mRecipe", mRecipeList.toString());

        mView = inflater.inflate(R.layout.fragment_recipe_master_list, container, false);
        ButterKnife.bind(this, mView);
        makeAPICall();

        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void makeAPICall() {
        URL url = NetworkUtils.getRecipeJsonListUrl();

        if(NetworkUtils.isOnline(mView.getContext())){
            new RecipeJSONListApiTask().execute(url);
        }
        else{
            Toast.makeText(mView.getContext(), getString(R.string.error_no_network), Toast.LENGTH_LONG).show();
            mRecipeList = JsonUtil.parseRecipeListJson(JsonUtil.readJSONRaw(getContext()));
            populateUI();
        }
    }

    private void populateUI() {
        mOrientation = getResources().getConfiguration().orientation;

        RecipeMasterAdapter recipeMasterAdapter = new RecipeMasterAdapter(mView.getContext(), mRecipeList, mCallback);

        if(mOrientation == Configuration.ORIENTATION_LANDSCAPE || mView.findViewById(R.id.recipe_details_detail_fragment) != null) {
            mRecipeRecyclerView.setLayoutManager(new GridLayoutManager(mView.getContext(), 4));
            mRecipeRecyclerView.setAdapter(recipeMasterAdapter);
        } else {
            mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
            mRecipeRecyclerView.setAdapter(recipeMasterAdapter);
        }
    }

    private class RecipeJSONListApiTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String jsonResult = null;

            try {
                jsonResult = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e){
                e.printStackTrace();
            }

            return  jsonResult;
        }

        @Override
        protected void onPostExecute(String s) {
            mRecipeList = JsonUtil.parseRecipeListJson(s);
            populateUI();
        }
    }
}
