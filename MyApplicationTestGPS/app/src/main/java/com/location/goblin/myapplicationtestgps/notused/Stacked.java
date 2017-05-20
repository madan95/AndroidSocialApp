package com.location.goblin.myapplicationtestgps.notused;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Madan on 25/04/2017.
 */

public class Stacked extends IntentService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private GoogleApiClient mGoogleClient;

    public Stacked() {
        super("com.location.goblin.myapplicationtestgps.notused.Stacked");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Stacked", "Inside on HandleIntent");
        mGoogleClient = new GoogleApiClient.Builder(this, this, this)
                .addApi(LocationServices.API)
                .build();
        mGoogleClient.connect();

       /* Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            int i = 0;
            @Override
            public void run(){
                Log.d("Stacked", "Timer schedule running " + i);
                i = i +1;
            }
        }, 0, 2000);*/
        Log.d("Stacked", "After schedule Run Thread");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("Stacked", "onConnected");
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setFastestInterval(5000L)
                .setInterval(10000L)
                .setSmallestDisplacement(0F);
        //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Stacked", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Stacked", "onConnectionFailed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Stacked", "onLocationChanged" + location.getLatitude());

    }
}
