package com.example.lpelczar.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lpelczar.bakingapp.R;
import com.example.lpelczar.bakingapp.models.RecipeStep;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;


public class RecipeStepFragment extends Fragment {

    private static final String ARG_IS_LANDSCAPE = "is-landscape";
    private static final String ARG_RECIPE_STEP = "recipe-step";

    private boolean isLandscape = false;
    private RecipeStep recipeStep;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView playerView;
    private static MediaSessionCompat mediaSession;

    public RecipeStepFragment() {
    }

    @SuppressWarnings("unused")
    public static RecipeStepFragment newInstance(RecipeStep recipeStep,
                                                 boolean isLandscape) {
        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_LANDSCAPE, isLandscape);
        args.putParcelable(ARG_RECIPE_STEP, recipeStep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            isLandscape = savedInstanceState.getBoolean(ARG_IS_LANDSCAPE);
            recipeStep = savedInstanceState.getParcelable(ARG_RECIPE_STEP);
        }

        if (getArguments() != null) {
            isLandscape = getArguments().getBoolean(ARG_IS_LANDSCAPE);
            recipeStep = getArguments().getParcelable(ARG_RECIPE_STEP);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        TextView descriptionTextView = view.findViewById(R.id.step_description_tv);
        descriptionTextView.setText(recipeStep.getDescription());

        playerView = view.findViewById(R.id.playerView);
        if (isCorrectUrl(recipeStep.getVideoURL())) {
            initializeMediaSession();
            initializePlayer(Uri.parse(recipeStep.getVideoURL()));
        } else if (isCorrectUrl(recipeStep.getThumbnailURL())) {
            initializeMediaSession();
            initializePlayer(Uri.parse(recipeStep.getThumbnailURL()));
        } else {
            playerView.setVisibility(View.GONE);
            ImageView video = view.findViewById(R.id.no_video_iv);
            video.setImageDrawable(getResources().getDrawable(R.drawable.novideo));
        }

        return view;
    }

    private boolean isCorrectUrl(String url) {
        try {
            URL videoURL = new URL(url);
            videoURL.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    private void initializeMediaSession() {

        mediaSession = new MediaSessionCompat(Objects.requireNonNull(getActivity()), "DetailActivity");
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);

        PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(new MySessionCallback());
        mediaSession.setActive(true);

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            exoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer.seekTo(0);
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");

            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(Objects.requireNonNull(getActivity()), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(ARG_IS_LANDSCAPE, isLandscape);
        outState.putParcelable(ARG_RECIPE_STEP, recipeStep);
        super.onSaveInstanceState(outState);
    }
}
