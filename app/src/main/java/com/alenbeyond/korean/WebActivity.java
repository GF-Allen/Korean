package com.alenbeyond.korean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {

    LinearLayout mNetNotAvailable;
    WebView mWebView;
    ProgressBar progressBarWeb;
    TextView tvNetInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initData();
    }

    private void initView() {
        mNetNotAvailable = (LinearLayout) findViewById(R.id.ll_web_net_not_available);
        mWebView = (WebView) findViewById(R.id.webView);
        progressBarWeb = (ProgressBar) findViewById(R.id.progressBar_web);
        tvNetInfo = (TextView) findViewById(R.id.tv_net_info);

        mWebView.setWebChromeClient(new MyWebChromeClient());
        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);

        mNetNotAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
    }

    private void initData() {
        String url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url);
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                if (mWebView.getTitle() != null) {
                    getSupportActionBar().setTitle(mWebView.getTitle());
                }
                progressBarWeb.setVisibility(View.GONE);
            } else {
                if (progressBarWeb.getVisibility() == View.GONE) {
                    progressBarWeb.setVisibility(View.VISIBLE);
                }
            }
            progressBarWeb.setProgress(newProgress);
            progressBarWeb.postInvalidate();
            super.onProgressChanged(view, newProgress);
        }
    }
}
