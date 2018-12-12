package com.olabode33.android.bakingapp.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = getIngredientsListRemoteView(context);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //setListRemoteView(context, views);
        views.setTextViewText(R.id.widget_tv_title, "Nutella Pie Ingredients");
        views.setOnClickPendingIntent(R.id.widget_tv_title, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static RemoteViews getIngredientsListRemoteView(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients_widget);

        Intent intent = new Intent(context, WidgetListViewService.class);
        remoteViews.setRemoteAdapter(R.id.widget_lv_recipe_ingredients, intent);


        return remoteViews;
    }

}

