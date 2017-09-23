package com.example.bohdan.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.logger.IronSourceLogger;
import com.ironsource.mediationsdk.logger.LogListener;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();

    Button showVideoButton;
    Button showInterstitialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showVideoButton = (Button) findViewById(R.id.show_video_button);
        showInterstitialButton = (Button) findViewById(R.id.show_interstitial_button);

        showVideoButton.setOnClickListener(this);
        showInterstitialButton.setOnClickListener(this);

        IronSource.init(this, "67341a5d");

        IronSource.setRewardedVideoListener(new RewardedVideoListener() {
            @Override
            public void onRewardedVideoAdOpened() {
                Log.d(TAG, "onRewardedVideoAdOpened: ");
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Log.d(TAG, "onRewardedVideoAdClosed: ");
            }

            @Override
            public void onRewardedVideoAvailabilityChanged(boolean b) {
                Log.d(TAG, "onRewardedVideoAvailabilityChanged: avalible " + b);
            }

            @Override
            public void onRewardedVideoAdStarted() {
                Log.d(TAG, "onRewardedVideoAdStarted: ");
            }

            @Override
            public void onRewardedVideoAdEnded() {
                Log.d(TAG, "onRewardedVideoAdEnded: ");
            }

            @Override
            public void onRewardedVideoAdRewarded(Placement placement) {
                Log.d(TAG, "onRewardedVideoAdRewarded: ");
                if (placement != null) {
                    String rewardName = placement.getRewardName();
                    int rewardAmount = placement.getRewardAmount();
                    Log.d(TAG, "onRewardedVideoAdRewarded: Name = " + rewardName + " Reward Amount = " + rewardAmount);
                }
            }

            @Override
            public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {
                Log.d(TAG, "onRewardedVideoAdShowFailed: ");
            }
        });
        IronSource.setInterstitialListener(new InterstitialListener() {
            @Override
            public void onInterstitialAdReady() {
                Log.d(TAG, "onInterstitialAdReady: ");
                IronSource.showInterstitial();
            }

            @Override
            public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
                Log.d(TAG, "onInterstitialAdLoadFailed: ");
            }

            @Override
            public void onInterstitialAdOpened() {
                Log.d(TAG, "onInterstitialAdOpened: ");
            }

            @Override
            public void onInterstitialAdClosed() {
                Log.d(TAG, "onInterstitialAdClosed: ");
            }

            @Override
            public void onInterstitialAdShowSucceeded() {
                Log.d(TAG, "onInterstitialAdShowSucceeded: ");
            }

            @Override
            public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
                Log.d(TAG, "onInterstitialAdShowFailed: ");
            }

            @Override
            public void onInterstitialAdClicked() {
                Log.d(TAG, "onInterstitialAdClicked: ");
            }
        });
        IronSource.setLogListener(new LogListener() {
            @Override
            public void onLog(IronSourceLogger.IronSourceTag ironSourceTag, String s, int i) {
                Log.d(TAG, "onLog: " + s + " int " + i);
            }
        });
        IntegrationHelper.validateIntegration(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IronSource.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        IronSource.onPause(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.show_video_button:
                IronSource.showRewardedVideo();
                break;
            case R.id.show_interstitial_button:
                IronSource.loadInterstitial();
                break;
        }
    }
}