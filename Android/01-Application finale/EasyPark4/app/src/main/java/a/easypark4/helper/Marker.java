package a.easypark4.helper;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import a.easypark4.R;

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
    public Marker(GoogleMap googleMap,Location location){
        setLocation(location);
        this.mGoogleMap = googleMap;
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
    public void afficherMarker(){
        mGoogleMap.clear();
        LatLng pointMarker = new LatLng( latitude, longitude );
        MarkerOptions markerOptions = new MarkerOptions().position(pointMarker).title("Ma Position");
        mGoogleMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
        CameraUpdate updateFactory = CameraUpdateFactory.newLatLngZoom(pointMarker, 17);
        mGoogleMap.moveCamera(updateFactory);
    }
    /**
     * Personnaliser la couleur du markeur
     * @param couleur : pour indiquer l'état d'une place ( vert => libre , rouge => prise)
     */

    public MarkerOptions etatMarkeur(MarkerOptions markerOptions, String couleur){

        switch (couleur){
            case "vert" :
               markerOptions=markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                break;
            case "rouge" :
                markerOptions=markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                break;


        }



        return markerOptions;
    }
}