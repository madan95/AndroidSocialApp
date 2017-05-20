package com.location.goblin.myapplicationtestgps;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Madan on 25/04/2017.
 */

public class GpsGoogleApi implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private PendingIntent pendingIntent;
    private GoogleApiClient mGoogleClient;
    private Activity main;
    public GpsGoogleApi(Activity main){
        this.main = main;
        mGoogleClient = new GoogleApiClient.Builder(main.getApplicationContext(), this, this)
                .addApi(LocationServices.API)
                .build();
        mGoogleClient.connect();
    }
    public void connectGoogleApiClient(){
        mGoogleClient.connect();
    }

    public void disconnectGoogleApiClient(){
        mGoogleClient.disconnect();
    }


    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(5000L)
                .setInterval(10000L)
                .setSmallestDisplacement(0F);

        pendingIntent = PendingIntent.getService(main, 0,
                new Intent(main, LocationService.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleClient, locationRequest, pendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Main", "onCreate after ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Main", "onCreate after ");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Main", "onCreate after " + location.getLatitude());
    }
}
