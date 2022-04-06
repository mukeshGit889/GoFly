package com.gofly.myaccount.wallet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gofly.R;
import com.gofly.main.activity.BaseActivity;

import butterknife.BindView;

public class WalletWebviewActivity extends BaseActivity {
    String webUrl;

    @BindView(R.id.wallet_webview)
    WebView webView;
    private Object MyWebViewClient;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_wallet_webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webUrl=getIntent().getExtras().getString("weburl");
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        if(webUrl != null){
           // webView.postUrl(webUrl, null);
            webView.loadUrl(webUrl);
        }
        webView.setWebViewClient(new MyBrowser());

       /* webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                Intent intent = new Intent(getApplicationContext(), RechargeSuccessfulActivity.class);
                intent.putExtra("weburl", url);
                startActivity(intent);
                finish();
               *//* intentAndFragmentService.intentDisplay(WalletWebviewActivity.this, RechargeSuccessfulActivity.class, null);
                finish();*//*
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);


            }
        });
*/
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if ("www.gofly.com".equals(Uri.parse(url).getHost())) {
                // This is my website, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(getApplicationContext(), RechargeSuccessfulActivity.class);
            intent.putExtra("weburl", url);
            startActivity(intent);
            finish();
            return true;
        }
    }
}
