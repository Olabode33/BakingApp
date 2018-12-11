package com.olabode33.android.bakingapp.model;

import org.parceler.Parcel;

/**
 * Created by obello004 on 12/6/2018.
 */

@Parcel
public class RecipeIngredient {
    double _quantity;
    String _measure;
    String _ingredient;

    public RecipeIngredient() {
    }

    public RecipeIngredient(double quantity, String measure, String ingredient){
        this._quantity = quantity;
        this._measure = measure;
        this._ingredient = ingredient;
    }

    public double get_quantity() {
        return _quantity;
    }

    public void set_quantity(float _quantity) {
        this._quantity = _quantity;
    }

    public String get_measure() {
        return _measure;
    }

    public void set_measure(String _measure) {
        this._measure = _measure;
    }

    public String get_ingredient() {
        return _ingredient;
    }

    public void set_ingredient(String _ingredient) {
        this._ingredient = _ingredient;
    }
}
