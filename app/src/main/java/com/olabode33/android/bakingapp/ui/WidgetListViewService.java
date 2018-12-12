package com.olabode33.android.bakingapp.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.model.Recipe;
import com.olabode33.android.bakingapp.model.RecipeIngredient;
import com.olabode33.android.bakingapp.utils.JsonUtil;
import com.olabode33.android.bakingapp.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by obello004 on 12/12/2018.
 */

public class WidgetListViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    Cursor mCursor;
    List<Recipe> mRecipes;
    List<RecipeIngredient> mRecipeIngredients;
    String TAG = "WidgetListRemoteViewFactory";

    public ListRemoteViewsFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        //Log.d(TAG, "onCreate() " + mRecipes.get(0).get_name());
        if(mRecipes == null) {
            initData();
        } else {
            mRecipeIngredients = mRecipes.get(0).get_ingredents();
        }
    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged()");
        if(mRecipes == null) {
            initData();
        } else {
            mRecipeIngredients = mRecipes.get(0).get_ingredents();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mRecipeIngredients == null || mRecipeIngredients.size() == 0) return 0;

        return mRecipeIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RecipeIngredient recipeIngredient = mRecipeIngredients.get(i);

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredients_list_item);
        remoteViews.setTextViewText(R.id.widget_tv_list_items, recipeIngredient.get_ingredient());

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public void initData() {
        if(NetworkUtils.isOnline(mContext)){
            AppWidgetManager awm = AppWidgetManager.getInstance(mContext);
            URL url = NetworkUtils.getRecipeJsonListUrl();
            new WidgetAPICall(awm).execute(url);
        } else {
            mRecipes = JsonUtil.parseRecipeListJson(JsonUtil.readJSONRaw(mContext));
        }
    }

    private class WidgetAPICall extends AsyncTask<URL, Void, String> {

        private RemoteViews views;
        private int widgetId;
        private AppWidgetManager appWidgetManager;
        private List<Recipe> recipes;
        private Context context;

        public WidgetAPICall(AppWidgetManager appWidgetManager) {
            this.appWidgetManager = appWidgetManager;
        }


        @Override
        protected String doInBackground(URL... urls) {

            Log.d("WidgetAPIAsync", "doInBackground");
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
            mRecipes = JsonUtil.parseRecipeListJson(s);
            //Log.d("WidgetAPIAsync", "onPostExecute" + s);
            //views.setTextViewText(R.id.widget_tv_title, mRecipes.get(0).get_name());

            int[] appWidgetId = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, RecipeIngredientsWidget.class.getName()));
            //initData();
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_lv_recipe_ingredients);
        }
    }
}
