package com.depromeet.tmj.im_off;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.depromeet.tmj.im_off.utils.datastore.AppPreferencesDataStore;

public class SplashActivity extends AppCompatActivity {
    private LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initBinding();
        initUi();
    }

    private void initBinding() {
        lottie = findViewById(R.id.lottie);
    }

    private void initUi() {
        lottie.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isFirstLaunch()) {
                    goToSettingActivity();
                } else {
                    goToMainActivity();
                }
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void goToMainActivity() {
        startActivity(MainActivity.getCallingIntent(getApplicationContext(), false));
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    private void goToSettingActivity() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    private boolean isFirstLaunch() {
        AppPreferencesDataStore dataStore = AppPreferencesDataStore.getInstance();
        return dataStore.getFirstLaunch();
    }
}
