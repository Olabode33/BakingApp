package com.olabode33.android.bakingapp.model;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by obello004 on 12/6/2018.
 */

@Parcel
public class Recipe {
    int _id;
    String _name;
    List<RecipeIngredient> _ingredents;
    List<RecipeStep> _steps;
    int _serving;
    String _image;

    public Recipe () {
    }

    public Recipe(int id, String name, List<RecipeIngredient> ingredents, List<RecipeStep> steps, int serving, String image) {
        this._id = id;
        this._name = name;
        this._ingredents = ingredents;
        this._steps = steps;
        this._serving = serving;
        this._image = image;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public void set_ingredents(List<RecipeIngredient> _ingredents) {
        this._ingredents = _ingredents;
    }

    public List<RecipeIngredient> get_ingredents() {
        return _ingredents;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_name() {
        return _name;
    }

    public void set_image(String _image) {
        this._image = _image;
    }

    public String get_image() {
        return _image;
    }

    public void set_steps(List<RecipeStep> _steps) {
        this._steps = _steps;
    }

    public List<RecipeStep> get_steps() {
        return _steps;
    }

    public void set_serving(int _serving) {
        this._serving = _serving;
    }

    public int get_serving() {
        return _serving;
    }
}
