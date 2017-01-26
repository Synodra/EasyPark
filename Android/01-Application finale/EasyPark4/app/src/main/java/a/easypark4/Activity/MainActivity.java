package a.easypark4.Activity;

// Importation des librairies
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
//import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import a.easypark4.LoginActivity;
import a.easypark4.R;
import a.easypark4.app.AppConfig;
import a.easypark4.app.AppController;
import a.easypark4.helper.Marker;
import a.easypark4.helper.SQLiteHandler;
import a.easypark4.helper.SessionManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ProfileFragment.OnFragmentInteractionListener {

    private SQLiteHandler db;           // Déclaration de la base de données local
    private SessionManager session;     // Déclaration de la session utilisateur
    private TextView txtName;           // Déclaration de la zone de text name dans le header
    private TextView txtEmail;          // Déclaration de la zone de text email dans le header
    private View navHeaderView;         // Déclaration du View pour la NavBar
    private TextView txtHome;           // Déclaration de la zone de texte de bienvenue
    private ProgressDialog pDialog;

    // Map variables
    private static int PERMISSION_GPS = 100;
    private SupportMapFragment sMapFragment;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;
    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private Marker maPosition;

    private FloatingActionButton balise;
    private GeoJsonLayer beaconLayer;

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);


        //region DB_SESSION

        //SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        // deconnexion de l'utilisateur au démmarage
        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //endregion

        //region HOME
        txtHome = (TextView) findViewById(R.id.homeMessage);
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        String s = (f.format(d)).substring(9);
        int time = Integer.parseInt(s);

        if(time<120000)
        {
            txtHome.setText("Good morning "+user.get("name"));
        }
        else if (time >120000 && time<190000)
        {
            txtHome.setText("Good afternoon "+user.get("name"));
        }
        else if (time >190000)
        {
            txtHome.setText("Good evening "+user.get("name"));
        }




        //endregion
        //region NAVBAR

        // création de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Création de la bar de navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Permet l'activation des boutons du menu de navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Gestion des éléments du layout nav bar header
        navHeaderView = navigationView.getHeaderView(0);

        // section gestion de la connexion de l'utilisateur
        txtName = (TextView) navHeaderView.findViewById(R.id.headerName);
        txtEmail = (TextView) navHeaderView.findViewById(R.id.headerEmail);

        // Displaying the user details on the screen
        txtName.setText(user.get("firstname") + " " + user.get("name"));
        txtEmail.setText(user.get("email"));

        //endregion

        //region CREATION_MAP

        // Création de l'API Client pour google
        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API).build();

        sMapFragment = SupportMapFragment.newInstance();

        sMapFragment.getMapAsync(this);

        //endregion

        //region SERVICE_LOCALISATION

        // Provide access to the system location services
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Register for location updates using the named provider, and a pending intent
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(20 * 1000)        // 60 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        // Check if the given provider is enabled
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            createGpsDisabledAlert();
        }

        // Check app location permission


        //endregion

        balise = (FloatingActionButton) findViewById(R.id.btnfloatLocalisation);
        balise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Activation des balises", Toast.LENGTH_LONG)
                        .show();

                VisibleRegion visibleregion = mGoogleMap.getProjection().getVisibleRegion();
                getBeacon(visibleregion, mGoogleMap);

            }
        });
        balise.setVisibility(View.INVISIBLE);

    }

    //region EVENT_INTERFACE

    /**
     * Si bouton retour activé
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Création du menu paramètre si le bouton sur la bar d'action est activé
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Action quand un bouton du menu paramètre est activé
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // Launching the login activity
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Action quand un bouton de la NavBar est activé
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        ProfileFragment profileFragment = new ProfileFragment();
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();


        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(sMapFragment.isAdded())
        {
            fragmentManager.beginTransaction().hide(sMapFragment).commit();
            balise.setVisibility(View.VISIBLE);
        }
        if(profileFragment.isAdded())
            fragmentManager.beginTransaction().hide(profileFragment).commit();
        if (id == R.id.btnMenuHome) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, homeFragment).commit();
           // txtHome.setVisibility(View.VISIBLE);
            balise.setVisibility(View.INVISIBLE);
        } else if (id == R.id.btnMenuMap) {
            if(!sMapFragment.isAdded())
            {
                balise.setVisibility(View.VISIBLE);

                fragmentManager.beginTransaction().replace(R.id.content_frame, sMapFragment).commit();
            }
            else {
                balise.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().show(sMapFragment).commit();
            }
        } else if (id == R.id.btnMenuAccountSetting) {
            if(!profileFragment.isAdded()) {
                balise.setVisibility(View.INVISIBLE);
                txtHome.setVisibility(View.INVISIBLE);
                fragmentManager.beginTransaction().replace(R.id.content_frame, profileFragment).commit();
            }
            else {
                txtHome.setVisibility(View.INVISIBLE);
                balise.setVisibility(View.INVISIBLE);
              fragmentManager.beginTransaction().show(profileFragment).commit();
            }
        } else if (id == R.id.btnMenuLogout) {
            logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    //endregion

    //region EVENT_SESSION

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //endregion

    //region EVENT_GOOGLE_MAP

    /**
     * Action dès que la carte google map est créée
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {

            Toast.makeText(MainActivity.this, "En cours de localisation ... ", Toast.LENGTH_LONG).show();
        }

        // Création du marker de positionnement de l'appareil
    /*    maPosition = new Marker(location);
        maPosition.afficherMarker(mGoogleMap);      // Affichage du curseur sur la carte google map
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(maPosition.getLatlng(), 15));*/
    }

    /**
     * Action dès que la position du périphérique change
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            onHandleMap(location, mGoogleMap);
        } else {
            Toast.makeText(MainActivity.this, "Impossible de générer les coordonnées GPS", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Update des actvités de géolocalisation sur la carte
     * @param location
     * @param googleMap
     */
    public void onHandleMap(Location location, GoogleMap googleMap) {
       mGoogleMap = googleMap;
        maPosition = new Marker(googleMap,location);
        maPosition.setLocation(location);
        maPosition.afficherMarker();
    }

    //endregion

    //region GOOGLE_API

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();
        AppIndex.AppIndexApi.start(mGoogleApiClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mGoogleApiClient, getIndexApiAction());
        mGoogleApiClient.disconnect();
    }

    //endregion

    //region GPS_ACTION

    /**
     * Creates an alert dialog to use when GPS isn't enabled and offers to activate it
     */
    private void createGpsDisabledAlert() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder
                .setMessage("Le GPS est inactif, voulez-vous l'activer ?")
                .setCancelable(false)
                .setPositiveButton("Activer GPS ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                MainActivity.this.showGpsOptions();
                            }
                        }
                );
        localBuilder.setNegativeButton("Ne pas l'activer ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        paramDialogInterface.cancel();
                        MainActivity.this.finish();
                    }
                }
        );
        localBuilder.create().show();
    }

    /**
     * Ouvre les paramètres de la localisation
     */
    private void showGpsOptions() {
        startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    //endregion

    //region LIST_BEACON

    /**
     * Retourne un JSON contenant la position des balise en fonction de la position de la caméra
     * @param visibleregion
     * @return json
     */
    public void getBeacon(final VisibleRegion visibleregion, final GoogleMap gMap) {
        String tag_string_req = "req_list_beacon";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LIST_BEACON, new Response.Listener<String>() {

            @Override
            public void onResponse(String response){
                Log.d(TAG, "Login Response: " + response);
                hideDialog();

                try{
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        beaconLayer = new GeoJsonLayer(mGoogleMap, jObj);
                        if(!beaconLayer.isLayerOnMap()) {
                            beaconLayer.removeLayerFromMap();
                        }
                        beaconLayer.addLayerToMap();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("minLat", String.valueOf(visibleregion.nearRight.latitude));
                params.put("maxLat", String.valueOf(visibleregion.farRight.latitude));
                params.put("minLng", String.valueOf(visibleregion.nearLeft.longitude));
                params.put("maxLng", String.valueOf(visibleregion.nearRight.longitude));
                params.put("other", "test");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    //endregion

    @Override
    public void onRequestPermissionsResult(int requestcode, @NonNull String [] permissions, @NonNull int[] grantResults){
        if(requestcode == PERMISSION_GPS){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,MainActivity.this);
            }
            else {
                Toast.makeText(MainActivity.this,"Permission refusée !",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Location services failed. Please reconnect.");
    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location loc) {
            double latitude = loc.getLatitude();
            double longitude = loc.getLongitude();
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}


    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}