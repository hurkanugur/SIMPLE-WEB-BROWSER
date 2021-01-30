package com.example.webbrowserwithwebview;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private WebView webView = null;
    public static TabLayout tabLayout;
    private EditText textBox;
    public static TextView pageTitle;
    private ArrayList<String> hukoPageURLs;
    public static int currentTabPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //WEBVIEW SETTINGS
        webView = (WebView)findViewById(R.id.hukoWebView);
        HukoWebViewClient webViewClient = new HukoWebViewClient(this);
        webView.setWebViewClient(webViewClient);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.google.com");

        //XML DEFINITIONS
        textBox = (EditText)findViewById(R.id.hukoTextBar);
        pageTitle = (TextView)findViewById(R.id.hukoPageTitle);
        tabLayout = (TabLayout)findViewById(R.id.hukoTabLayout);

        //ACCORDING TO THEIR TAB NUMBERS, SAVE EVERY TAB'S LATEST URL
        hukoPageURLs = new ArrayList<String>();
        hukoPageURLs.add(0,"https://www.google.com");

        //TAB SELECTING EVENT
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                hukoPageURLs.set(currentTabPosition, webView.getUrl());
                currentTabPosition = tab.getPosition();
                webView.loadUrl(hukoPageURLs.get(tab.getPosition()));
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
    public void HukoButtonClickEvent(View view)
    {
        switch (view.getId())
        {
            case R.id.hukoSearchButton:
                webView.loadUrl("https://www.google.com/search?q=" + textBox.getText().toString());
                textBox.setText("");
                break;
            case R.id.hukoNewTabButton:
                tabLayout.addTab(tabLayout.newTab().setText("Google"));
                hukoPageURLs.add(tabLayout.getTabCount()-1, "https://www.google.com");
                tabLayout.selectTab(tabLayout.getTabAt(tabLayout.getTabCount()-1));
                currentTabPosition = tabLayout.getTabCount()-1;
                break;
        }
    }
    //ENABLES PHONE'S "BACK BUTTON" TO WORK
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack())
        {
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}