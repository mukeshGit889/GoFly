package com.gofly.guessit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.gofly.R;
import com.gofly.main.activity.BaseActivity;

import butterknife.BindView;

public class GuessITActivity extends BaseActivity {
    @BindView(R.id.webview)
    WebView webview;
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_guess_itactivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webview.loadUrl("https://guess-it.pro/");

        // this will enable the javascipt.
        webview.getSettings().setJavaScriptEnabled(true);

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webview.setWebViewClient(new WebViewClient());
       // setContentView(R.layout.activity_guess_itactivity);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
}