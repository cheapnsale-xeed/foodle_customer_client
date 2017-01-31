package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.iamport.InicisWebViewClient;

import java.net.URLEncoder;

public class PaymentActivity extends AppCompatActivity {

    private static final String APP_SCHEME = "iamporttest://";

    private WebView mainWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final String paymentPage = getPaymentPage("inicis");

        mainWebView = (WebView) findViewById(R.id.payment_web_view);
        mainWebView.setWebViewClient(new InicisWebViewClient(this));
        WebSettings settings = mainWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mainWebView.setWebChromeClient(new WebChromeClient());
        mainWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mainWebView.loadUrl("javascript:window.IMP.init('iamport');");
                mainWebView.loadUrl("javascript:" + paymentPage + ";");
            }
        });

        //String paymentUrl = "https://s3.ap-northeast-2.amazonaws.com/cheapnsale-apk-download/payment_test.html"; //WebView 호출 URL
        String paramStr = null;
        try {
            paramStr = "param1=" + URLEncoder.encode("value1", "UTF-8") + "&param2=" + URLEncoder.encode("value2", "UTF-8");
        } catch (Exception e) {
        }




        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(mainWebView, true);
        }

        Intent intent = getIntent();
        Uri intentData = intent.getData();

        if ( intentData == null ) {
            mainWebView.loadUrl("file:///android_asset/payment.html");
        } else {
            //isp 인증 후 복귀했을 때 결제 후속조치
            String url = intentData.toString();
            if ( url.startsWith(APP_SCHEME) ) {
                String redirectURL = url.substring(APP_SCHEME.length()+3);
                mainWebView.loadUrl(redirectURL);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String url = intent.toString();
        if ( url.startsWith(APP_SCHEME) ) {
            String redirectURL = url.substring(APP_SCHEME.length()+3);
            mainWebView.loadUrl(redirectURL);
        }
    }

    private String getPaymentPage(String data1) {


        StringBuffer sb = new StringBuffer();
        //sb.append(" var IMP = window.IMP;\n");
        //sb.append(" window.IMP.init('iamport');\n");
        sb.append(" window.IMP.request_pay({");
        sb.append(" pg : '").append(data1).append("'");
        sb.append(" pay_method : 'card',");
        sb.append(" merchant_uid : 'merchant_' + new Date().getTime(),");
        sb.append(" name : 'Order Name',");
        sb.append(" amount : 14000,");
        sb.append(" buyer_email : 'iamport@siot.do',");
        sb.append(" buyer_name : 'CheanSale',");
        sb.append(" buyer_tel : '010-1234-5678',");
        sb.append(" buyer_addr : 'Seoul',");
        sb.append(" buyer_postcode : '123-456',");
        sb.append(" m_redirect_url : 'https://www.yourdomain.com/payments/complete',");
        sb.append(" app_scheme : 'iamportapp'");
        sb.append(" }, function(rsp) {");
        sb.append("    if ( rsp.success ) {");
        sb.append(" var msg = 'Order finished';");
        sb.append(" msg += 'Unique ID : ' + rsp.imp_uid;");
        sb.append(" msg += 'Store ID : ' + rsp.merchant_uid;");
        sb.append("        msg += 'Amount : ' + rsp.paid_amount;");
        sb.append("        msg += 'Card Number : ' + rsp.apply_num;");
        sb.append("    } else {");
        sb.append("        var msg = 'Payment Failed.';");
        sb.append("        msg += 'Error Content : ' + rsp.error_msg;");
        sb.append("    }");
        sb.append(" alert(msg);");
        sb.append(" });");
        //sb.append(" </script>");


        return sb.toString();
    }
}
