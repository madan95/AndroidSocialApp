package com.location.goblin.myapplicationtestgps.notused;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.location.goblin.myapplicationtestgps.LocationService;
import com.location.goblin.myapplicationtestgps.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleApiClient mGoogleClient;
    private String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
    private int requestCodeForFineLocation = 2;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        Log.d("Main", "onCreate before ");
        mGoogleClient = new GoogleApiClient.Builder(this, this, this)
                .addApi(LocationServices.API)
                .build();

        //      this.startService(new Intent(this.getBaseContext(), Stacked.class));
        Log.d("Main", "onCreate after ");
    }
    public void stopPendingIntent(View view){
    Log.d("Main", "Stop PENDING Intenet");
        PendingIntent.getService(this, 0,  new Intent(this, LocationService.class),
                PendingIntent.FLAG_UPDATE_CURRENT).cancel();
        Log.d("Main", "aFTER stop pending intent");

    }
    public void startPendingIntent(View view){
        Log.d("Main", "Stop PENDING Intenet");
        mGoogleClient.connect();
        Log.d("Main", "aFTER stop pending intent");

    }
    @Override
    protected void onStart() {
        super.onStart();

        mGoogleClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleClient.disconnect();

        super.onStop();
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("Main", "onCreate after ");
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, requestCodeForFineLocation);

            return;
        }
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(2000L)
                .setInterval(5000L)
                .setSmallestDisplacement(0F);

        pendingIntent = PendingIntent.getService(this, 0,
                new Intent(this, LocationService.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleClient, locationRequest, pendingIntent);

    }
    @SuppressWarnings("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationRequest locationRequest = LocationRequest.create()
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setFastestInterval(2000L)
                            .setInterval(5000L)
                            .setSmallestDisplacement(0F);

                     pendingIntent = PendingIntent.getService(this, 0,
                            new Intent(this, LocationService.class),
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    LocationServices.FusedLocationApi.requestLocationUpdates(
                            mGoogleClient, locationRequest, pendingIntent);
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