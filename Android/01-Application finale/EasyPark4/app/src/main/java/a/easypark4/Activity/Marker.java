package a.easypark4.Activity;

import android.location.Location;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by seynabou.ba on 22/12/2016.
 */


class Marker  {

    private GoogleMap mGoogleMap;
    private double latitude;
    private double longitude;

    public Marker(GoogleMap googleMap, Location location){
        this.mGoogleMap = googleMap;
        this.latitude=location.getLatitude();
        this.longitude=location.getLongitude();
    }

    public double getLatitude(){

        return latitude;
    }

    public double getLongitude(){
        return longitude;

    }
    public void setLocation(Location location){
        this.latitude=location.getLatitude();
        this.longitude = location.getLongitude();
    }


    public void afficherMarker(){
        mGoogleMap.clear(); //ERROR mGoogleMap.clear(); invoke method on a null object reference
        LatLng pointMarker = new LatLng(longitude,  latitude);
        mGoogleMap.addMarker(new MarkerOptions().position(pointMarker).title("Ma Position"));
        CameraUpdate updateFactory = CameraUpdateFactory.newLatLngZoom(pointMarker, 13);
        mGoogleMap.moveCamera(updateFactory);
    }

}
