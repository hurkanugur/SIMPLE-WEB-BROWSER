package com.example.webbrowserwithwebview;

import android.app.Activity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HukoWebViewClient extends WebViewClient {
    private Activity activity = null;
    public HukoWebViewClient(Activity activity) {
        this.activity = activity;
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        return false;
    }
    @Override
    public void onPageFinished(WebView view, String url) {
        MainActivity.pageTitle.setText(view.getTitle());
        MainActivity.tabLayout.getTabAt(MainActivity.currentTabPosition).setText(view.getTitle());
    }
}