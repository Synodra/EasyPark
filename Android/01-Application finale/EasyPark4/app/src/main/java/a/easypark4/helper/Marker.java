package a.easypark4.helper;

import android.location.Location;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by seynabou.ba on 22/12/2016.
 */


public class Marker {

    private GoogleMap mGoogleMap;
    private double latitude;
    private double longitude;
    private LatLng latlng;
    private MarkerOptions markerOptions;
    private com.google.android.gms.maps.model.Marker marker;

    /**
     * Instanciation objet marker
     * @author : Seynabou
     */
    public Marker(Location location){
        setLocation(location);
    }

    /**
     * Getter Latitude du marker
     * @return
     */
    public double getLatitude(){

        return latitude;
    }

    /**
     * Getter Longitude du marker
     * @return
     */
    public double getLongitude(){
        return longitude;

    }

    /**
     * Getter LATLNG du marker
     */
    public LatLng getLatlng() {
        return latlng;
    }

    /**
     * Setteur location du marker
     * @param location
     */
    public void setLocation(Location location){
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        latlng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    /**
     * Update location du marker
     * @param location
     */
    public void updateLocation(Location location) {
        setLocation(location);
        marker.setPosition(latlng);
    }

    /**
     *  Afficher marker sur la carte
     */
    public void afficherMarker(GoogleMap map){
        markerOptions = new MarkerOptions().position(latlng);       // Initialisation de l'objet markerOption
        marker = map.addMarker(markerOptions);               // Ajout du marker à la carte
    }

}