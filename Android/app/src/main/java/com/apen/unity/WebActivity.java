package com.apen.unity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.apen.unitylib.UnityPlayerActivity;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.unity3d.player.UnityPlayer;

import static android.net.Uri.decode;


public class WebActivity extends UnityPlayerActivity {
    public FrameLayout webglContainer;

    IX5WebChromeClient.CustomViewCallback mCallBack;
    WebView mWebView;
    WebViewClient apenwebviewclient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
            Uri uri = Uri.parse(url);
            if (uri.getScheme().equals("apenUnity")) {
                UnityPlayer.UnitySendMessage("webview", "OnReceivedMessage", url);
                return true;
            } else if (uri.getScheme().equals("apen")) {
                String Authority = uri.getAuthority();
                String decodeURL = decode(uri.toString());
                if (Authority.equals("showShareMenuView")) {
                    String XRurl = WebviewGetValueByKey(decodeURL, "url");
                    String title = WebviewGetValueByKey(decodeURL, "title");
                    String text = WebviewGetValueByKey(decodeURL, "text");
                    String imageurl = WebviewGetValueByKey(decodeURL, "imageurl");

                }
                return true;
            }
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    };


    WebChromeClient apenwebchromeclient = new WebChromeClient(){

        @Override
        public void onHideCustomView() {
            switchFullScreen(false);
            if (mCallBack!=null){
                mCallBack.onCustomViewHidden();
            }
            mWebView.setVisibility(View.VISIBLE);
            webglContainer.removeAllViews();
            webglContainer.setVisibility(View.GONE);
            super.onHideCustomView();
        }

        @Override
        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
            switchFullScreen(true);
            mWebView.setVisibility(View.GONE);
            webglContainer.setVisibility(View.VISIBLE);
            webglContainer.addView(view);
            mCallBack=customViewCallback;
            super.onShowCustomView(view, customViewCallback);
        }

        @Override
        public void onShowCustomView(View view, int i, IX5WebChromeClient.CustomViewCallback customViewCallback) {
            switchFullScreen(true);
            mWebView.setVisibility(View.GONE);
            webglContainer.setVisibility(View.VISIBLE);
            webglContainer.addView(view);
            mCallBack=customViewCallback;
            super.onShowCustomView(view, i, customViewCallback);
        }
    };

    public String WebviewGetValueByKey(String uri, String name) {
        String result = "";
        int index = uri.indexOf("?");
        String temp = uri.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }
    /**
     * 切换全屏,取消全屏
     */
    private void switchFullScreen(boolean isChecked) {
        if (isChecked) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    protected void WebViewInit(ViewGroup mViewParent) {
        mWebView = new WebView(this, null);
        mViewParent.addView(mWebView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
        mWebView.setClickable(true);
        mWebView.setWebViewClient(apenwebviewclient);
        mWebView.setWebChromeClient(apenwebchromeclient);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.setWebContentsDebuggingEnabled(true);
        }
        WebSettings settings = mWebView.getSettings();
        mWebView.getSettings().setJavaScriptEnabled(true);
        String userAgent = settings.getUserAgentString();

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setUserAgentString(userAgent + ";seedweet");
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setAppCacheEnabled(true);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAllowFileAccessFromFileURLs(true);
        webSetting.setAllowUniversalAccessFromFileURLs(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setAllowFileAccess(true);// 设置启用或禁止访问文件数据
        webSetting.setAllowContentAccess(true);
        webSetting.setPluginsEnabled(true);
        webSetting.setJavaScriptEnabled(true); // 设置是否支持JavaScript
        webSetting.setBlockNetworkImage(false);
        webSetting.setDatabaseEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);// 设置允许JS弹窗
        webSetting.setLoadsImagesAutomatically(true);
        webSetting.setLoadWithOverviewMode(true);
        mWebView.loadUrl("http://www.manew.com/");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }


    protected void runwebviewjs(String str) {
        mWebView.loadUrl("javascript:" + str);
    }
}
