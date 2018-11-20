package com.apen.unity;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by APEN on 2018/11/20 17:18
 * Email: appppppen@gmail.com
 */
public class App extends Application {
    private static final String TAG = "App";
    @Override
    public void onCreate() {
        super.onCreate();
        initX5();
    }
    private void initX5() {
        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                Log.i(TAG, "x5内核 onViewInitFinished is " + arg0);
            }
            @Override
            public void onCoreInitFinished() {
                Log.i(TAG, "x5内核 onCoreInitFinished is ");
            }
        });
    }
}
