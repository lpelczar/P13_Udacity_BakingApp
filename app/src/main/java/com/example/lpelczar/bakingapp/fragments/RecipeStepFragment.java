package com.example.lpelczar.bakingapp.fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpelczar.bakingapp.R;
import com.example.lpelczar.bakingapp.models.RecipeStep;
import com.google.android.exoplayer2.C;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class RecipeStepFragment extends Fragment {

    private static final String ARG_IS_LANDSCAPE = "is-landscape";
    private static final String ARG_RECIPE_STEP = "recipe-step";
    private static final String ARG_RECIPE_STEPS = "recipe-steps";
    private static final String ARG_POSITION = "position";
    private static final String ARG_PLAY_WHEN_READY = "play-when-ready";

    private boolean isLandscape = false;
    private RecipeStep recipeStep;
    private List<RecipeStep> recipeSteps;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView playerView;
    private static MediaSessionCompat mediaSession;
    private long position;
    private Uri videoUri;
    private boolean playWhenReady = true;

    public RecipeStepFragment() {
    }

    @SuppressWarnings("unused")
    public static RecipeStepFragment newInstance(RecipeStep recipeStep, List<RecipeStep> recipeSteps,
                                                 boolean isLandscape) {
        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_LANDSCAPE, isLandscape);
        args.putParcelableArrayList(ARG_RECIPE_STEPS, new ArrayList<>(recipeSteps));
        args.putParcelable(ARG_RECIPE_STEP, recipeStep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = C.TIME_UNSET;
        if (savedInstanceState != null) {
            isLandscape = savedInstanceState.getBoolean(ARG_IS_LANDSCAPE);
            recipeStep = savedInstanceState.getParcelable(ARG_RECIPE_STEP);
            recipeSteps = savedInstanceState.getParcelableArrayList(ARG_RECIPE_STEPS);
            position = savedInstanceState.getLong(ARG_POSITION);
            playWhenReady = savedInstanceState.getBoolean(ARG_PLAY_WHEN_READY);
        }

        if (getArguments() != null) {
            isLandscape = getArguments().getBoolean(ARG_IS_LANDSCAPE);
            recipeStep = getArguments().getParcelable(ARG_RECIPE_STEP);
            recipeSteps = getArguments().getParcelableArrayList(ARG_RECIPE_STEPS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        if (view.findViewById(R.id.step_description_tv) != null) {
            TextView descriptionTextView = view.findViewById(R.id.step_description_tv);
            descriptionTextView.setText(recipeStep.getDescription());
        }

        playerView = view.findViewById(R.id.playerView);
        if (isCorrectUrl(recipeStep.getVideoURL())) {
            initializeMediaSession();
            videoUri = Uri.parse(recipeStep.getVideoURL());
            initializePlayer(videoUri);
        } else {
            playerView.setVisibility(View.INVISIBLE);
            ImageView video = view.findViewById(R.id.no_video_iv);
            if (recipeStep.getThumbnailURL() != null && !recipeStep.getThumbnailURL().isEmpty()) {
                loadImage(video, recipeStep.getThumbnailURL());
            } else {
                video.setImageDrawable(getResources().getDrawable(R.drawable.novideo));
                TextView videoNotAvailable = view.findViewById(R.id.no_video_tv);
                videoNotAvailable.setVisibility(View.VISIBLE);
            }
        }
        handlePreviousAndNextStepButtons(view);
        return view;
    }

    private void loadImage(final ImageView videoImage, String path) {
        Picasso.with(videoImage.getContext()).load(path).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                videoImage.setImageDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {}

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}
        });
    }

    private void handlePreviousAndNextStepButtons(View view) {

        if (view.findViewById(R.id.previous_step_button) != null) {
            Button previous = view.findViewById(R.id.previous_step_button);
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentStepId = recipeStep.getId();
                    if (getStepById(currentStepId - 1) != null) {
                        replaceStepFragment(getStepById(currentStepId - 1));
                    } else if (getStepById(currentStepId - 2) != null) {
                        replaceStepFragment(getStepById(currentStepId - 2));
                    } else {
                        Toast.makeText(getContext(), "This is the first step!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (view.findViewById(R.id.next_step_button) != null) {
            Button next = view.findViewById(R.id.next_step_button);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentStepId = recipeStep.getId();
                    if (getStepById(currentStepId + 1) != null) {
                        replaceStepFragment(getStepById(currentStepId + 1));
                    } else if (getStepById(currentStepId + 2) != null) {
                        replaceStepFragment(getStepById(currentStepId + 2));
                    } else {
                        Toast.makeText(getContext(), "This is the last step!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void replaceStepFragment(RecipeStep nextStep) {
        RecipeStepFragment fragment = RecipeStepFragment.newInstance(nextStep,
                recipeSteps, false);
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();
        }
    }

    private RecipeStep getStepById(int id) {
        for (RecipeStep recipeStep : recipeSteps) {
            if (recipeStep.getId() == id) {
                return recipeStep;
            }
        }
        return null;
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
            if (position != C.TIME_UNSET) exoPlayer.seekTo(position);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(playWhenReady);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoUri != null)
            initializePlayer(videoUri);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            playWhenReady = exoPlayer.getPlayWhenReady();
            position = exoPlayer.getCurrentPosition();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
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
        outState.putParcelableArrayList(ARG_RECIPE_STEPS, new ArrayList<>(recipeSteps));
        outState.putLong(ARG_POSITION, position);
        outState.putBoolean(ARG_PLAY_WHEN_READY, playWhenReady);
        super.onSaveInstanceState(outState);
    }
}
