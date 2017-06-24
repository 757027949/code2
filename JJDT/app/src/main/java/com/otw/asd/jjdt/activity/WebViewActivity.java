package com.otw.asd.jjdt.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.otw.asd.jjdt.R;
import com.otw.asd.jjdt.base.BaseActivity;

import butterknife.Bind;


/**
 * 网页Activity
 *
 * @author 123
 */
public class WebViewActivity extends BaseActivity {

    @Bind(R.id.webview)
    WebView webview;

    final String APP_CACAHE_DIRNAME = "/webcache";

    @Override
    public int bindLayout() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView(View view) {
        String flow = getIntent().getStringExtra("flow");
        if ("register".equals(flow)) {
            setTopTitle("使用条款和隐私政策");
        }
        init();
    }

    public void init() {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // webSettings.setRenderPriority(RenderPriority.HIGH);//提高渲染的优先级
        // webSettings.setBlockNetworkImage(true);//图片加载放在最后来加载渲染
//		if (AppApplicationUtil.getApp().isNetworkConnected()) {

        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }

//		} else {
//			webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//		}
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        // 开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);

        String cacheDirPath = getFilesDir().getAbsolutePath()
                + APP_CACAHE_DIRNAME;
        // 设置数据库缓存路径
        webSettings.setDatabasePath(cacheDirPath);
        // 设置 Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);
        webSettings.setAppCacheEnabled(true);

        webview.requestFocus();
//        Logger.e(getIntent().getStringExtra("url"));
        webview.loadUrl(getIntent().getStringExtra("url"));
//        webview.loadDataWithBaseURL(null, getIntent().getStringExtra("url"), "text/html", "utf-8", null);
        // webView.postUrl(requestUrl, EncodingUtils.getBytes(postData,
        // "base64"));
//        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                getLodingAlertDialog().show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                getLodingAlertDialog().dismiss();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

    }

    @Override
    public void finishSelf() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.finishSelf();
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        return false;
    }
}
