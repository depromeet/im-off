package com.depromeet.tmj.im_off;

import android.app.Application;

public class ImOffApplication extends Application {
    private static ImOffApplication INSTANCE = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if(INSTANCE == null) {
            INSTANCE = this;
        }
    }

    public static ImOffApplication getApplication() {
        if(INSTANCE == null) {
            throw new IllegalStateException("INSTANCE is null. This application is in illegal state.");
        } else {
            return INSTANCE;
        }
    }
}
