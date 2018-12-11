package com.olabode33.android.bakingapp.utils;

import android.content.Context;
import android.graphics.Movie;
import android.util.Log;

import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.model.Recipe;
import com.olabode33.android.bakingapp.model.RecipeIngredient;
import com.olabode33.android.bakingapp.model.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by obello004 on 12/6/2018.
 */

public class JsonUtil {
    //Recipe JSON Key
    public static final String JSON_RECIPE_KEY_ID = "id";
    public static final String JSON_RECIPE_KEY_NAME = "name";
    public static final String JSON_RECIPE_KEY_INGREDIENTS = "ingredients";
    public static final String JSON_RECIPE_KEY_STEPS = "steps";
    public static final String JSON_RECIPE_KEY_SERVINGS = "servings";
    public static final String JSON_RECIPE_KEY_IMAGE = "image";

    //Recipe Ingredients
    public static final String JSON_RECIPE_INGREDIENT_KEY_QUANTITY = "quantity";
    public static final String JSON_RECIPE_INGREDIENT_KEY_MEASURE = "measure";
    public static final String JSON_RECIPE_INGREDIENT_KEY_INGREDIENT = "ingredient";

    //Recipe Steps
    public static final String JSON_RECIPE_STEP_KEY_ID = "id";
    public static final String JSON_RECIPE_STEP_KEY_SHORTDESCRIPTION = "shortDescription";
    public static final String JSON_RECIPE_STEP_KEY_DESCRIPTION = "description";
    public static final String JSON_RECIPE_STEP_KEY_VIDEOURL = "videoURL";
    public static final String JSON_RECIPE_STEP_KEY_THUMBNAILURL = "thumbnailURL";

    public static Recipe parseRecipeJson(JSONObject jsonRecipe){
        int recipe_id = jsonRecipe.optInt(JSON_RECIPE_KEY_ID);
        String recipe_name = jsonRecipe.optString(JSON_RECIPE_KEY_NAME);
        List<RecipeIngredient> ingredients = parseRecipeIngredientListJson(jsonRecipe.optJSONArray(JSON_RECIPE_KEY_INGREDIENTS));
        List<RecipeStep> steps = parseRecipeStepListJson(jsonRecipe.optJSONArray(JSON_RECIPE_KEY_STEPS));
        int serving = jsonRecipe.optInt(JSON_RECIPE_KEY_SERVINGS);
        String image = jsonRecipe.optString(JSON_RECIPE_KEY_IMAGE);

        //Create new recipe object
        Recipe recipe = new Recipe(recipe_id, recipe_name, ingredients, steps, serving, image);

        return recipe;
    }

    public static RecipeIngredient parseRecipeIngredientJson(JSONObject jsonRecipeIngredient){
        Double quantity = jsonRecipeIngredient.optDouble(JSON_RECIPE_INGREDIENT_KEY_QUANTITY);
        String measure = jsonRecipeIngredient.optString(JSON_RECIPE_INGREDIENT_KEY_MEASURE);
        String ingredient = jsonRecipeIngredient.optString(JSON_RECIPE_INGREDIENT_KEY_INGREDIENT);

        //Create new Recipe ingredient object
        RecipeIngredient recipeIngredient = new RecipeIngredient(quantity, measure, ingredient);
        return  recipeIngredient;
    }

    public static RecipeStep parseRecipeStepJson(JSONObject jsonRecipeStep){
        int step_id = jsonRecipeStep.optInt(JSON_RECIPE_STEP_KEY_ID);
        String shortDescription = jsonRecipeStep.optString(JSON_RECIPE_STEP_KEY_SHORTDESCRIPTION);
        String description = jsonRecipeStep.optString(JSON_RECIPE_STEP_KEY_DESCRIPTION);
        String videoURL = jsonRecipeStep.optString(JSON_RECIPE_STEP_KEY_VIDEOURL);
        String thumbnailURL = jsonRecipeStep.optString(JSON_RECIPE_STEP_KEY_THUMBNAILURL);

        //Create new Recipe Step object
        RecipeStep recipeStep = new RecipeStep(step_id, shortDescription, description, videoURL, thumbnailURL);
        return  recipeStep;
    }

    public static List<Recipe> parseRecipeListJson(String json) {
        List<Recipe> recipeList = new ArrayList<>();

        try {
            //JSONObject jsonRecipeList = new JSONObject(json);
            JSONArray jsonRecipeArray = new JSONArray(json);

            if(jsonRecipeArray != null){
                for (int i = 0; i < jsonRecipeArray.length(); i++){
                    JSONObject jsonRecipeObj = jsonRecipeArray.getJSONObject(i);
                    Recipe recipeObj = parseRecipeJson(jsonRecipeObj);

                    recipeList.add(recipeObj);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }

    public static List<RecipeIngredient> parseRecipeIngredientListJson(JSONArray jsonArray){
        List<RecipeIngredient> jsonList = new ArrayList<>();

        if(jsonArray != null){
            for(int i = 0; i < jsonArray.length(); i++){
                try {
                    JSONObject jsonRecipeObj = jsonArray.getJSONObject(i);
                    RecipeIngredient recipeObj = parseRecipeIngredientJson(jsonRecipeObj);

                    jsonList.add(recipeObj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonList;
    }

    private static List<RecipeStep> parseRecipeStepListJson(JSONArray jsonArray){
        List<RecipeStep> jsonList = new ArrayList<>();

        if(jsonArray != null){
            for(int i = 0; i < jsonArray.length(); i++){
                try {
                    JSONObject jsonRecipeObj = jsonArray.getJSONObject(i);
                    RecipeStep recipeObj = parseRecipeStepJson(jsonRecipeObj);

                    jsonList.add(recipeObj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonList;
    }

    public static String readJSONRaw(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.recipes);
        Writer writer = new StringWriter();
        char[] buffer = new char[2024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

        } catch(Exception e) {
            //
        }

        //Log.w("ReadJSONRaw", writer.toString());
        return writer.toString();
    }

}
