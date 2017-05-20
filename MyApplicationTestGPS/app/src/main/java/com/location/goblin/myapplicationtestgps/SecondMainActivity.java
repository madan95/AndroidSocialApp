package com.location.goblin.myapplicationtestgps;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static com.location.goblin.myapplicationtestgps.GlobalStrings.insideHomePage;
import static com.location.goblin.myapplicationtestgps.GlobalStrings.startOfApplication;

/**
 * Created by Madan on 06/02/2017.
 */

public class SecondMainActivity extends AppCompatActivity {
    private static final String TAG = "SecondMainActivity";
    private String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
    private int requestCodeForFineLocation = 2;
    private WebView webView;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Log.d(TAG, "The onCreate() event");
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, requestCodeForFineLocation);
            return;
        }
        if(checkAreadyLoggedin()){
            onCreateMethods(insideHomePage);
        }else {
            onCreateMethods(startOfApplication);
        }

    }

    public boolean checkAreadyLoggedin(){
        boolean loggedin = false;
        SharedPreferences pref = getSharedPreferences("Regi", 0);
        SharedPreferences.Editor editor = pref.edit();
        String id = pref.getString("id", null);
        String token = pref.getString("token", null);
        if(id!=null && token != null){
            loggedin = true;
        }else{
            loggedin = false;
        }
        return loggedin;
    }
    public void onCreateMethods(String url){
        webView = (WebView) findViewById(R.id.webView1);
        webView.setWebViewClient(new WebViewClient());
        JavaScriptInterface javaScriptInterface = new JavaScriptInterface(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(javaScriptInterface, "JSInterface");
        webView.loadUrl(url);

           webView.setWebViewClient(new WebViewClient() {
               public void onPageFinished(WebView view, String url) {
                   try { webView.loadUrl("javascript:start()");
                   }catch (Exception e){
                       System.out.println("Hey Madan No start() method Found in JS");
                   }
               }
           });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    onCreateMethods(startOfApplication);
                     // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
