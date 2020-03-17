package com.example.aarcon.Helpers;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

import java.util.Locale;

public class GPSTrackingHelper {

    public double[] getCoordinates(Context context) {
        final double[] coordinates = {-1, -1};
        // Acquire arFragment reference to the system Location Manager
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;


        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {}
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return coordinates;
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            coordinates[0]=lastKnownLocation.getLatitude();
            coordinates[1]=lastKnownLocation.getLongitude();
        }

        return coordinates;
    }

    public double distance(double[] a, double[] b){
        double distance = -1;
        double earthRad = 6371.0;
        if(a.length==2 && b.length==2){
            double underRoot = Math.pow(Math.sin((Math.toRadians(b[0] - a[0])) / 2),2) + Math.cos(a[0]) * Math.cos(b[0]) * Math.pow(Math.sin((Math.toRadians(b[1] - a[1])) / 2),2);
            double haversine = 2 * Math.asin(Math.sqrt(underRoot));
            distance = haversine*earthRad;
        }
        return distance;
    }

    public double getSpeed(Context context){
        int time = 20000;
        double[] p1 = getCoordinates(context);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {}
        double[] p2 = getCoordinates(context);
        return (distance(p1,p2)/time)*1000*60*60;
    }

}
