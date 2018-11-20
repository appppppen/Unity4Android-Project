package com.apen.unity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.unity3d.player.UnityPlayer;
import com.apen.unitylib.UnityPlayerActivity;


public class UnityActivity extends WebActivity {
    RelativeLayout mParent;
    public ViewGroup mViewParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unity);

        mParent=findViewById(R.id.UnityView);
        webglContainer=findViewById(R.id.webglContainer);
        mViewParent=findViewById(R.id.webViewFragment);

        setView(mParent);
        WebViewInit(mViewParent);
        findViewById(R.id.androidclick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnityPlayer.UnitySendMessage("TestAndroid", "Toast", "Android中点击了按钮");
            }
        });
    }
    public void unityToast(final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UnityActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }
}
