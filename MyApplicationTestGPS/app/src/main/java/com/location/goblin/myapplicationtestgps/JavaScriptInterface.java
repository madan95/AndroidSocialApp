package com.location.goblin.myapplicationtestgps;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;

import static com.location.goblin.myapplicationtestgps.LocationService.globalAsyn;

/**
 * Created by Madan on 06/02/2017.
 */

public class JavaScriptInterface {
    private static final String TAG = "JavaScriptInterface";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Activity main;
    private boolean serviceOn = false;
    private boolean locationEnabled = true;
    private  GpsGoogleApi gpsStart = null;

    public JavaScriptInterface(Activity main){
        this.main = main;
    }

    @JavascriptInterface
    public void setData(String id, String token){
        System.out.println("the input from Server " + id + " and " + token);
        sharedPreferences = main.getApplicationContext().getSharedPreferences("Regi", 0);
        editor = sharedPreferences.edit();
        editor.putString("id", id );
        editor.putString("token", token);
        editor.commit();
       if(serviceOn){
           //Do nothing
       }else{
           startLocation();
           serviceOn=true;
       }
        //main.startService(new Intent(main.getBaseContext(), Stacked.class));
        Log.d(TAG, "Finished setData");
    }

    @JavascriptInterface
    public void startLocation(){
        System.out.println("JSInterfaec start location");
        gpsStart = new GpsGoogleApi(main);
         Log.d(TAG, "Service Started Method Started");
    }

    @JavascriptInterface
    public void stopLocation(){
        System.out.println("JSInterface stop location");
        serviceOn=false;
        gpsStart.disconnectGoogleApiClient();
        PendingIntent.getService(main, 0,  new Intent(main, LocationService.class),
                PendingIntent.FLAG_UPDATE_CURRENT).cancel();
             Log.d(TAG, "Service Stoped Method Started");
    }

    @JavascriptInterface
    public void logout(){
        sharedPreferences = main.getSharedPreferences("Regi", 0);
        editor = sharedPreferences.edit();
        System.out.println(" Logout ");
        editor.clear();
        editor.commit();
        if(globalAsyn ==null) {
            Log.d("Logout", "globalAsyn is Null");
        }else{
            globalAsyn.cancel(true);
        }
        stopLocation();
           //  main.stopService(new Intent(main.getApplicationContext(), Stacked.class));
        CookieSyncManager cookieSyncMngr =
                CookieSyncManager.createInstance(main.getApplicationContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

    }
}
