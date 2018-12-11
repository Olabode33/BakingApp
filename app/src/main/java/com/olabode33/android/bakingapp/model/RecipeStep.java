package com.olabode33.android.bakingapp.model;

import org.parceler.Parcel;

/**
 * Created by obello004 on 12/6/2018.
 */

@Parcel
public class RecipeStep {
    int _id;
    String _shortDescription;
    String _description;
    String _videoURL;
    String _thumbnailURL;

    public RecipeStep() {

    }

    public RecipeStep(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
       this. _id = id;
       this._shortDescription = shortDescription;
       this._description = description;
       this._videoURL = videoURL;
       this._thumbnailURL = thumbnailURL;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_shortDescription() {
        return _shortDescription;
    }

    public void set_shortDescription(String _shortDescription) {
        this._shortDescription = _shortDescription;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_thumbnailURL() {
        return _thumbnailURL;
    }

    public void set_thumbnailURL(String _thumbnailURL) {
        this._thumbnailURL = _thumbnailURL;
    }

    public String get_videoURL() {
        return _videoURL;
    }

    public void set_videoURL(String _videoURL) {
        this._videoURL = _videoURL;
    }
}
