package com.example.leesanghyuk.abandon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sf.PoetryInfo.PoetryList;
import com.example.sf.amap3d.R;


public class PoetryBrownser extends AppCompatActivity {
    private WebView myWebView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poetry_brownser);
        Intent intent=getIntent();
        String poetryName=intent.getStringExtra(PoetryList.POETRY_NAME);
        myWebView = (WebView) findViewById(R.id.webview);
        //传入诗人名字即可
        init(poetryName);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        //  return true;
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    public void init(String name){
        final String finalName = "http://www.baidu.com/s?ie=utf8&oe=utf8&wd="+name;

        //加载服务器上的页面
        myWebView.loadUrl(finalName);
        //加载本地中的html
        //myWebView.loadUrl("file:///android_asset/www/test2.html");
        //加上下面这段代码可以使网页中的链接不以浏览器的方式打开
        myWebView.setWebViewClient(new WebViewClient());
        //得到webview设置
        WebSettings webSettings = myWebView.getSettings();
        //允许使用javascript
        webSettings.setJavaScriptEnabled(true);
        //将WebAppInterface于javascript绑定
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
    }
}
