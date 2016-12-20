package com.alenbeyond.korean;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by alen on 16/12/20.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        QbSdk.initX5Environment(this, null);
    }
}
