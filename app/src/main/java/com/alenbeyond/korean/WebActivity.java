package com.alenbeyond.korean;

import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alenbeyond.korean.crawler.Hanjucc;
//import com.tencent.smtt.sdk.WebChromeClient;
//import com.tencent.smtt.sdk.WebSettings;
//import com.tencent.smtt.sdk.WebView;
//import com.tencent.smtt.sdk.WebViewClient;

public class WebActivity extends AppCompatActivity {

    private static final String TAG = "WebActivity";
    WebView mWebView;
    ProgressBar progressBarWeb;
    ProgressBar progressBar;
    TextView tvNetInfo;
    private Handler handler;
    private String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //设置全屏模式
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //去标题
        setContentView(R.layout.activity_web);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 222) {

                    if (videoUrl == null) {
                        Snackbar.make(mWebView, "加载失败啦", Snackbar.LENGTH_INDEFINITE)
                                .setAction("雪微点这重试", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        initData();
                                    }
                                });
                    } else {
                        mWebView.loadUrl(videoUrl);
                    }
                }
            }
        };
        initView();
        initData();
    }

    private void initView() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mWebView = (WebView) findViewById(R.id.webView);
        progressBarWeb = (ProgressBar) findViewById(R.id.progressBar_web);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvNetInfo = (TextView) findViewById(R.id.tv_net_info);

        mWebView.setWebChromeClient(new MyWebChromeClient());
        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView webView, String s) {
                progressBar.setVisibility(View.INVISIBLE);
                mWebView.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void initData() {
        progressBar.setVisibility(View.VISIBLE);
        mWebView.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "雪微稍微的等一会哈", Toast.LENGTH_LONG).show();
        String url = getIntent().getStringExtra("url");
        Log.d(TAG, url);
        mWebView.loadUrl(url);
//        new Thread() {
//
//            @Override
//            public void run() {
//                videoUrl = Hanjucc.getVideoUrl(url);
//                if (handler != null) {
//                    handler.sendEmptyMessage(222);
//                }
//            }
//        }.start();
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }
}
