package com.wahanaartha.supervisionline.Utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationUtils implements LocationListener {

    Context context;
    private String provider;
    private LocationManager locationManager;
    private String latitude = "no value";
    private String longitude = "no value";

    public LocationUtils(Context context) {
        this.context = context;
        // Get the location manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        latitude = "no value";
        longitude = "no value";
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            /*Toast.makeText(context, "Provider " + provider + " has been selected.",
                    Toast.LENGTH_SHORT).show();*/
            // System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            /*Toast.makeText(context, "Location not available",
                    Toast.LENGTH_SHORT).show();*/
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        latitude = lat + "";
        longitude = lng + "";
   /* Toast.makeText(context, " lat: "+lat +"  Long:"+lng,
            Toast.LENGTH_SHORT).show(); */
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Toast.makeText(context, "Disabled provider " + provider,
        //        Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        //Toast.makeText(context, "Enabled new provider " + provider,
        //      Toast.LENGTH_SHORT).show();
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }


}
