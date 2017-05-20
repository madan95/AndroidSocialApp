package com.location.goblin.myapplicationtestgps;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderApi;

/**
 * Created by Madan on 25/04/2017.
 */

public class LocationService extends IntentService {
    public static BackgroundConnection globalAsyn = null;
    public LocationService() {
        super("com.location.goblin.myapplicationtestgps.LocationService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        final Location location = intent.getParcelableExtra(FusedLocationProviderApi.KEY_LOCATION_CHANGED);
        if(location!=null){
            Log.d("LocationService", "Location to Send : "+location.getLatitude());
            send(String.valueOf(location.getLatitude()),
                   String.valueOf(location.getLongitude()));
         }
    }


    public void send(String lat, String log){
          if(isConnectingToInternet(getApplicationContext())){
            try{
                SharedPreferences pref = getSharedPreferences("Regi", 0);
                SharedPreferences.Editor editor = pref.edit();
                String id = pref.getString("id", "deafult");
                String token = pref.getString("token", "default");
                globalAsyn = new BackgroundConnection();
                globalAsyn.execute(new String[]{
                        id, token, lat, log});
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnectingToInternet(Context _context) {
        System.out.println(" is Connected");
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}
