package com.apen.unity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.unity3d.player.UnityPlayer;
import com.apen.unitylib.UnityPlayerActivity;


public class UnityActivity extends UnityPlayerActivity {
    RelativeLayout mParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unity);
        mParent=(RelativeLayout)findViewById(R.id.UnityView);
        setView(mParent);
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
