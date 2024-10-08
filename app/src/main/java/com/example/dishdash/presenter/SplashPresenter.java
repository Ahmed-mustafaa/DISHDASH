package com.example.dishdash.presenter;

import android.os.Handler;

import com.example.dishdash.view.Splash.SplashScreenView;

public class SplashPresenter implements SplashScreenView.Presenter {

    private SplashScreenView.View view;
    private static final int SPLASH_DELAY = 3500; // 3.5 seconds

    public SplashPresenter(SplashScreenView.View view) {
        this.view = view;
    }

    @Override
    public void startSplashScreen() {
        new Handler().postDelayed(() -> view.startLoginActivity(), SPLASH_DELAY);
    }
}