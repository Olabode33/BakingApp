package com.olabode33.android.bakingapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.olabode33.android.bakingapp.R;
import com.olabode33.android.bakingapp.model.RecipeStep;
import com.olabode33.android.bakingapp.utils.Constants;
import com.olabode33.android.bakingapp.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by obello004 on 12/9/2018.
 */

public class RecipeStepFragment extends Fragment {

    private List<RecipeStep> mRecipeStepList;
    private int mPosition;
    private RecipeStep mRecipeStep;
    private SimpleExoPlayer mPlayer;
    private Context mContext;
    private final String TAG = "RecipeStepFragment";
    private int mCurrentWindow = 0;
    private long mPlaybackPosition = 0;

    @BindView(R.id.ep_recipe_step_video)
    SimpleExoPlayerView mPlayView;
    @BindView(R.id.iv_recipe_step_image)
    ImageView mRecipeStepImageView;
    @BindView(R.id.tv_recipe_step_desc_long)
    TextView mRecipeDescriptionTextView;
    @BindView(R.id.fl_exoplayer_frame)
    FrameLayout mVideoPlayerFrameLayout;
    @BindView(R.id.pb_loading_video)
    ProgressBar mLoadingVideoProgressBar;

    private Dialog mVideoFullScreenDialog;
    private boolean mExoPlayerIsFullScreen = false;
    private int mOrientation;

    public RecipeStepFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null){
            mPosition = savedInstanceState.getInt(Constants.EXTRA_RECIPE_STEP_POSITION_KEY);
            mRecipeStepList = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_RECIPE_STEP_KEY));
            mPlaybackPosition = savedInstanceState.getLong(Constants.EXTRA_VIDEO_PLAYBACK_POSITION_KEY);
            mCurrentWindow = savedInstanceState.getInt(Constants.EXTRA_VIDEO_CURRENT_WINDOW_KEY);
        }

        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        ButterKnife.bind(this, rootView);
        mContext = rootView.getContext();

        mOrientation = getResources().getConfiguration().orientation;

        if(mRecipeStep != null){
            Log.d(TAG, "Recipe Step: " + mPosition);
            populateUI();
            if(mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                initVideoFullScreen();
                openFullScreenDialog();
            }
        }

        //return super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.EXTRA_RECIPE_STEP_POSITION_KEY, mPosition);
        outState.putParcelable(Constants.EXTRA_RECIPE_STEP_KEY, Parcels.wrap(mRecipeStepList));
        outState.putInt(Constants.EXTRA_VIDEO_CURRENT_WINDOW_KEY, mCurrentWindow);
        outState.putLong(Constants.EXTRA_VIDEO_PLAYBACK_POSITION_KEY, mPlaybackPosition);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            mPosition = savedInstanceState.getInt(Constants.EXTRA_RECIPE_STEP_POSITION_KEY);
            mPlaybackPosition = savedInstanceState.getLong(Constants.EXTRA_VIDEO_PLAYBACK_POSITION_KEY);
            mCurrentWindow = savedInstanceState.getInt(Constants.EXTRA_VIDEO_CURRENT_WINDOW_KEY);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        displayVideoPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPlayer == null){
            displayVideoPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
        if(mVideoFullScreenDialog != null){
            mVideoFullScreenDialog.dismiss();
            mVideoFullScreenDialog = null;
        }
    }

    public void setRecipeStep(List<RecipeStep> recipeSteps, int position) {
        this.mRecipeStep = recipeSteps.get(position);
        this.mRecipeStepList = recipeSteps;
        this.mPosition = position;
    }

    private void populateUI() {

        if (mRecipeStep.get_videoURL().equals(null) || mRecipeStep.get_videoURL().equals("")){
            mVideoPlayerFrameLayout.setVisibility(View.GONE);

        } else {
            displayVideoPlayer();
        }
        if (mRecipeStep.get_thumbnailURL().equals(null) || mRecipeStep.get_thumbnailURL().equals("") || mRecipeStep.get_thumbnailURL().equals(" ")) {
            mRecipeStepImageView.setVisibility(View.GONE);
        } else {//Display image
            if(mRecipeStepImageView.getVisibility() == View.GONE){
                mRecipeStepImageView.setVisibility(View.VISIBLE);
            }
            Picasso.get()
                    .load(mRecipeStep.get_thumbnailURL())
                    .placeholder(R.mipmap.ic_recipe_step)
                    .error(R.mipmap.ic_recipe_step_round)
                    .into(mRecipeStepImageView);
        }
        mRecipeDescriptionTextView.setText(mRecipeStep.get_description());
    }

    private void initializePlayer(Uri mediaUrl) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
        mPlayView.setPlayer(mPlayer);

        String userAgent = Util.getUserAgent(mContext, "BakingApp");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUrl, new DefaultDataSourceFactory(mContext, userAgent), new DefaultExtractorsFactory(), null, null);
        mPlayer.prepare(mediaSource);
        mPlayer.setPlayWhenReady(true);
        mPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
        mPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if(playbackState == ExoPlayer.STATE_BUFFERING){
                    mLoadingVideoProgressBar.setVisibility(View.VISIBLE);
                } else {
                    mLoadingVideoProgressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity() {

            }
        });
    }

    private void displayVideoPlayer() {
        //Display video using Exoplayer
        if(mPlayView.getVisibility() == View.GONE){
            mVideoPlayerFrameLayout.setVisibility(View.VISIBLE);
        }
        mPlayView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_recipe_step));

        if(NetworkUtils.isOnline(mContext)) {
            if(mPlayer == null) {
                initializePlayer(Uri.parse(mRecipeStep.get_videoURL()));
            }
        } else {
            Toast toast = Toast.makeText(mContext, getString(R.string.internet_connection_required_msg), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void releasePlayer() {
        if(mPlayer != null) {
            mPlaybackPosition = mPlayer.getCurrentPosition();
            mCurrentWindow = mPlayer.getCurrentWindowIndex();
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void initVideoFullScreen() {
        mVideoFullScreenDialog = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            @Override
            public void onBackPressed() {
                if(mExoPlayerIsFullScreen) {
                    closeFullScreenDialog();
                }
                super.onBackPressed();
            }
        };
    }

    private void openFullScreenDialog() {
        ((ViewGroup) mPlayView.getParent()).removeView(mPlayView);
        mVideoFullScreenDialog.addContentView(mPlayView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExoPlayerIsFullScreen = true;
        mVideoFullScreenDialog.show();
    }

    private void closeFullScreenDialog() {
        ((ViewGroup) mPlayView.getParent()).removeView(mPlayView);
        mVideoPlayerFrameLayout.addView(mPlayView);
        mExoPlayerIsFullScreen = false;
        mVideoFullScreenDialog.dismiss();
        mVideoFullScreenDialog = null;
        releasePlayer();
    }
}
