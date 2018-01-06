package com.kartum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.kartum.utils.Debug;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsnCondActivity extends BaseActivity {

    @BindView(R.id.mWebView)
    WebView mWebView;

//    @BindView(R.id.tvLegalDisclaime)
//    TextView tvLegalDisclaime;
    @BindView(R.id.tvBackErrow)
    TextView tvBackErrow;
    @BindView(R.id.btnAccept)
    Button btnAccept;

//    BusList.Bu busParams;
//    SearchBusActivity.SearchParams searchParams;

    String url;
    String title = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_cond);
        ButterKnife.bind(this);

//        initDrawer(true);
        initIntentParams();
        init();
    }

    private void initIntentParams() {

        try {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().containsKey("url")) {
                    url = getIntent().getStringExtra("url");
                }

                if (getIntent().getExtras().containsKey("title")) {
                    title = getIntent().getStringExtra("title");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        setTitleText(title);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra("msg", "1");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tvBackErrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TermsnCondActivity.super.onBackPressed();
            }
        });

        android.webkit.CookieManager.getInstance().removeAllCookie();
        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
//        mWebView.getSettings().setLoadWithOverviewMode(true);
//        mWebView.getSettings().setUseWideViewPort(true);

        showDialog("");
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Debug.e("onPageStarted", "" + url);
                super.onPageStarted(view, url, favicon);
            }

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return false;
//            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                try {
                    dismissDialog();


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

//            @Override
//            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setMessage("Invalid");
//                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        handler.proceed();
//                    }
//                });
//                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        handler.cancel();
//                    }
//                });
//                final AlertDialog dialog = builder.create();
//                dialog.show();
//            }
        });

//        mWebView.loadUrl(url);
        mWebView.loadUrl("file:///android_asset/terms_of_use.html");

    }

}
