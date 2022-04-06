package com.gofly.myaccount.giftcard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gofly.R;

import com.gofly.main.activity.BaseActivity;

import butterknife.BindView;

public class GiftCardPaymentActivity extends BaseActivity {

    String webUrl,walletAmount;

    @BindView(R.id.giftcard_webview)
    WebView webView;
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_gift_card_payment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_gift_card_payment);
        walletAmount=getIntent().getExtras().getString("walletamount");
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
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if ("www.gofly.com".equals(Uri.parse(url).getHost())) {
                // This is my website, so do not override; let my WebView load the page
                return false;
            }
            Intent intent = new Intent(getApplicationContext(), GiftPurchaseSuccessfulActivity.class);
            intent.putExtra("weburl", url);
            intent.putExtra("walletamount", walletAmount);
            startActivity(intent);
            finish();

            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
          /*  giftPurchaseSuccessfulFragment=new GiftPurchaseSuccessfulFragment(url);
            intentAndFragmentService.fragmentDisplay(getActivity(), R.id.fragment_container,
                    giftPurchaseSuccessfulFragment,null, false);*/
            return true;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
}
