package com.rmart.customer.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.rmart.R;
import com.rmart.baseclass.Constants;
import com.rmart.baseclass.views.BaseActivity;
import com.rmart.utilits.LoggerInfo;

public class PlayVideosView extends BaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener {

    private String videoUrl;
    private YouTubePlayerView youTubeView;
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_videos);

        LoggerInfo.printLog("Activity", "PlayVideosView");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            videoUrl = extras.getString("VideoUrl");
        }

        loadUIComponents();
    }

    @Override
    public void onPermissionsGranted(Integer requestCode) {

    }

    private void loadUIComponents() {
        youTubeView = findViewById(R.id.youtube_view);

        // Initializing video player with developer key
        youTubeView.initialize(Constants.YOU_TUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);

        //This flag tells the player to automatically enter fullscreen when in landscape. Since we don't have
        //landscape layout for this activity, this is a good way to allow the user rotate the video player.
        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);

        //This flag controls the system UI such as the status and navigation bar, hiding and showing them
        //alongside the player UI
        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);

        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

        if (!TextUtils.isEmpty(videoUrl)) {
            try {
                if (wasRestored) {
                    youTubePlayer.play();
                } else {
                    youTubePlayer.setPlayerStateChangeListener(this);
                    youTubePlayer.loadVideo(videoUrl);
                }
            } catch (Exception e) {
                youTubeView.initialize(Constants.YOU_TUBE_API_KEY, this);
            }
        }

        // Hiding player controls
        // youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(getString(R.string.you_tube_error_player), errorReason.toString());
            showCloseAlert(errorMessage);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Constants.YOU_TUBE_API_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    private void showCloseAlert(String pMessage) {
        if (!isFinishing()) {
            showCloseAlert(pMessage);
        }
    }

    @Override
    public void onLoading() {
        LoggerInfo.printLog("Youtube", "onLoading");
    }

    @Override
    public void onLoaded(String s) {
        LoggerInfo.printLog("Youtube", String.format("onLoaded %s", s));
    }

    @Override
    public void onAdStarted() {
        LoggerInfo.printLog("Youtube", "onAdStarted");
    }

    @Override
    public void onVideoStarted() {
        LoggerInfo.printLog("Youtube", "onVideoStarted");
    }

    @Override
    public void onVideoEnded() {
        LoggerInfo.printLog("Youtube", "onVideoEnded");
        finish();
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }

    @Override
    public void showBadge(boolean b) {

    }

    @Override
    public void updateBadgeCount() {

    }

    @Override
    public void hideHamburgerIcon() {

    }

    @Override
    public void showHamburgerIcon() {

    }

    @Override
    public void showCartIcon() {

    }

    @Override
    public void hideCartIcon() {

    }
}
