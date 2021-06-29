package com.example.postpc_noamk_ex8;

import android.app.Application;

import androidx.work.WorkManager;

public class CalculationsApp extends Application {
    private static CalculationsApp instance;

    public static CalculationsApp getInstance() {
        if (instance == null) {
            instance = new CalculationsApp();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.cancelAllWork();
    }
}