package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.iamport.InicisWebViewClient;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.service.model.Order;
import com.xeed.cheapnsale.service.model.Payment;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    private WebView mainWebView;
    private ArrayList<Menu> menus;
    private String menuName;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final Payment payment = (Payment) getIntent().getExtras().get("payment");
        final Order order = (Order) getIntent().getExtras().get("order");

        menus = order.getMenus();

        menuName = menus.get(0).getMenuName();
        if(menus.size()>1){
            menuName += "외 "+ (menus.size()-1)+"개";
        }

        mainWebView = (WebView) findViewById(R.id.payment_web_view);
        WebSettings settings = mainWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mainWebView.addJavascriptInterface(new AndroidBridge(), "Payment");

        mainWebView.setWebViewClient(new InicisWebViewClient(PaymentActivity.this){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mainWebView.loadUrl("javascript:call_payment('" + payment.getMerchantUid() + "','" + payment.getAmount() +"','"+ menuName +"');");
            }

        });

        mainWebView.loadUrl("file:///android_asset/payment.html");
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void paymentHandler(final String status, final String paymentMessage) { // must be final
            handler.post(new Runnable() {
                public void run() {
                    boolean isPayment = false;
                    if ("paid".equals(status)) {
                        isPayment = true;
                    }
                    if(isPayment){
                        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                        intent.putExtra("isPayment", true);
                        startActivity(intent);
                    }else{
                        Toast.makeText(PaymentActivity.this, paymentMessage, Toast.LENGTH_LONG).show();
                    }
                    finish();
                }
            });
        }
    }

}
