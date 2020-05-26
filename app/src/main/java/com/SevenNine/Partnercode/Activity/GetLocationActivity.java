package com.SevenNine.Partnercode.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class GetLocationActivity extends AppCompatActivity{
   /* implements
} OnMapReadyCallback {*/

    LocationManager manager;
    boolean permission_navigated = false,gps_navigated = false,got_latlong=false;
    int attempts=0,permission_attempt=0,gps_attempt=0;

    Intent intent;
    public static String livelocation;

    public static double latitude,longitude;

    TextView current_address;

    SessionManager sessionManager;

    boolean valid = true;

    LinearLayout nomap;

    boolean loc_frst_set;

    private LatLng currLatLong=null;

    private GoogleMap currentgoogleMap;

    SupportMapFragment mapFragment;
    String nav;
  @Override
    public void onBackPressed() {
      /*Intent i = new Intent(GetLocationActivity.this, com.SevenNine.Partner.Activity.HomePage_With_Bottom_Navigation.class);
      startActivity(i);*/
    }

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_latitude_longitude);
      /*  intent = getIntent();
        Bundle b = intent.getExtras();
        if (b!=null) {
            Log.d("Navfrom", b.getString("NavigationFrom"));
            nav = b.getString("NavigationFrom");
        }
        */
        //String nav = intent.getExtras().getString("NavigateFrom");
        //nomap = findViewById(R.id.nomap);
        current_address= findViewById(R.id.curr_address);

    /*    if(currLatLong!=null) {
            current_address.setText(getAddressFromLatLong(currLatLong.latitude, currLatLong.longitude));

            // current_address.setText(getAddressFromLatLong(currLatLong.latitude, currLatLong.longitude));
            //currentgoogleMap.addMarker(new MarkerOptions().position(india).title("India"));
            System.out.println("gdghddddd");
            //  currentgoogleMap.animateCamera(CameraUpdateFactory.newLatLng(currLatLong));
            currentgoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currLatLong, 17f));
        }*/
       /* ImageView current = findViewById(R.id.current);
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("gdghd");

            }
        });*/


// fetching places related strats

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyBrYtFN3tRw8ElIi5P8ayOwgNbykiMYcjM");
        }
// Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setCountry("IN");
        autocompleteFragment.setHint("Enter your Location");

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                System.out.println("dddd = "+place.getLatLng().longitude);

                latitude=place.getLatLng().latitude;

                longitude = place.getLatLng().longitude;

                //mapFragment.getMapAsync(GetLocationActivity.this);

                loc_frst_set=false;

                got_latlong=true;

             //   getLatLongfromPlaceId(place.getId()); to get place ID pass "Place.Field.ID" in setPlaceFields

            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getBaseContext(),status.toString(), Toast.LENGTH_SHORT).show();

                Log.i("dddd1", "An error occurred: " + status);
            }
        });

        //fetching places related ends



// map related
     /*   mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
*/

        //Button continue_ = findViewById(R.id.cl);
/*

        continue_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(got_latlong & valid &
                        !current_address.getText().toString().contains("Ocean") &
                        !current_address.getText().toString().contains("Sea")) {

                    sessionManager = new SessionManager(GetLocationActivity.this);
                    sessionManager.storeLatLong(latitude+"",longitude+"");

                    Intent i = new Intent(GetLocationActivity.this, HomePage_With_Bottom_Navigation.class);
                    startActivity(i);
                }
                else {
             *//*       if(attempts%2!=0)
                      //  askTogetHighAccuracy();else
                    checkGPSTurnedOn();*//*

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GetLocationActivity.this);
                    alertDialogBuilder.setTitle("Not getting Location?");
                    alertDialogBuilder.setMessage("Click try again to get Your Location");
                    alertDialogBuilder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            final ProgressDialog progressDialog = ProgressDialog.show(GetLocationActivity.this,"", "Fetching your Current loaction. Please wait...");
                            final int[] count = {5};

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                     final GPSTracker gpsTracker = new GPSTracker(getBaseContext());
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            count[0]--;

                                            if(gpsTracker.longitude>0) {

                                                got_latlong=true;

                                                currLatLong = new LatLng(gpsTracker.latitude,gpsTracker.longitude);
                                                latitude=gpsTracker.latitude;
                                                longitude=gpsTracker.longitude;

                                                loc_frst_set=false;

                                                mapFragment.getMapAsync(GetLocationActivity.this);

                                            }

                                            if(got_latlong |  count[0]==0) {
                                                progressDialog.dismiss();
                                                handler.removeCallbacksAndMessages(null);

                                            }
                                            else
                                                handler.postDelayed(this, 500);
                                        }
                                    }, 1000);  //the time is in miliseconds

                                }
                            });

                        }
                    });
                    alertDialogBuilder.setNegativeButton("Enter Manually", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    });
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.show();

                   // Toast.makeText(getBaseContext(),"Enter Location Manually",Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        manager = (LocationManager) getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);
        checkLocationPermission();
    }
    private void checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(GetLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            //ask for permission pop up
            askForPermission();

        } else {

            if(permission_navigated)permission_navigated=false;

            //check if gps is turned on
           // checkGPSTurnedOn();

        }
    }




/*
    @SuppressLint("MissingPermission")
    private void checkGPSTurnedOn()
    {
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        } else {
            if (gps_navigated) gps_navigated = false;

            final ProgressDialog progressDialog = ProgressDialog.show(GetLocationActivity.this,"", "Fetching your Current loaction. Please wait...");
            final int[] count = {5};

          runOnUiThread(new Runnable() {
    @Override
    public void run() {
        final GPSTracker gpsTracker = new GPSTracker(getBaseContext());
attempts++;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                count[0]--;

                 if(gpsTracker.longitude>0) {

                     got_latlong=true;
                     currLatLong = new LatLng(gpsTracker.latitude,gpsTracker.longitude);
                     latitude=gpsTracker.latitude;
                     longitude=gpsTracker.longitude;
                     loc_frst_set=false;

                   mapFragment.getMapAsync(GetLocationActivity.this);

                 }

                 if(got_latlong |  count[0]==0) {
                     progressDialog.dismiss();
                     handler.removeCallbacksAndMessages(null);
                 }
                 else
                handler.postDelayed(this, 500);
            }
        }, 1000);  //the time is in miliseconds

    }
});

        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        if(permission_navigated & permission_attempt<2)
            checkLocationPermission();

        if(gps_navigated & gps_attempt<2)
            checkGPSTurnedOn();

*//*            if(highAccuracy_navigated)
                checkGPSTurnedOn();*//*
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
// dialogue box event handling

        switch (requestCode) {
            case 99: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //check if gps is turned on
                 //   checkGPSTurnedOn();

                    }
                    else {

                    permission_attempt++;

                    askForPermission();

                    }
                }

            }
        }

        private void askForPermission()
        {

            if(permission_attempt<=2)
            if (ActivityCompat.shouldShowRequestPermissionRationale(GetLocationActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // if location permission is not on will be promfted for first time
                ActivityCompat.requestPermissions(GetLocationActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);

            } else {

                //user pressed dont show again
                permissionDeclined();

            }
            else
                Toast.makeText(getBaseContext(),"Enter Location Manually", Toast.LENGTH_SHORT).show();
        }
        //don't delete this its required

/*    private void askTogetHighAccuracy() {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
       // alertDialogBuilder.setTitle("Turn on GPS?");
        alertDialogBuilder.setMessage("Unable to fetch lat long please reset GPS or allow High Accuracy");
        alertDialogBuilder.setPositiveButton("High Accuracy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //will be navigated to turn on gps
                attempts++;
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                //alertDialogBuilder.show();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.show();

    }*/

    private void buildAlertMessageNoGps() {
        gps_attempt++;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Turn on GPS?");
        alertDialogBuilder.setMessage("Your GPS seems to be disabled, do you want to enable it?");
        alertDialogBuilder.setPositiveButton("Turn On", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                gps_navigated=true;
                //will be navigated to turn on gps
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

                if(gps_attempt<2)
                alertDialogBuilder.show();
                else
                    Toast.makeText(getBaseContext(),"Enter Location Manually", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.show();

    }

    private void permissionDeclined()
    {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Allow Location Access");
        alertDialogBuilder.setMessage("You have declined location access permission please turn it on to navigate into the App.");
        alertDialogBuilder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                permission_navigated=true;
                //will navigate to permission section to check location
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);



            }
        });
        alertDialogBuilder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                permission_attempt++;
                dialogInterface.dismiss();

                if(permission_attempt!=2)
                alertDialogBuilder.show();
                Toast.makeText(getBaseContext(),"Enter Location Manually", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.show();
    }

   /* @Override
    public void onMapReady(final GoogleMap googleMap) {
        currentgoogleMap=googleMap;
        nomap.setVisibility(View.INVISIBLE);

        LatLng india = new LatLng(latitude, longitude);

        current_address.setText(getAddressFromLatLong(latitude,longitude));
        //currentgoogleMap.addMarker(new MarkerOptions().position(india).title("India"));

        currentgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(india));

        // for adding + and - to map to zoom in and out
        currentgoogleMap.getUiSettings().setZoomControlsEnabled(true);

        currentgoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(india, 17f));



        currentgoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                if(loc_frst_set) {

                    LatLng midLatLng = currentgoogleMap.getCameraPosition().target;

                    current_address.setText(getAddressFromLatLong(midLatLng.latitude, midLatLng.longitude));

                    latitude=midLatLng.latitude;
                    longitude=midLatLng.longitude;

                }
                else
                    loc_frst_set=true;

            }
        });


    }*/

/*    private void getLatLongfromPlaceId(String placeid)
    {
        final ProgressDialog  progressDialog = ProgressDialog.show(GetLocationActivity.this, "",
                "Fetching LatLong. Please wait...");

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://maps.googleapis.com/maps/api/place/details/json?placeid="+placeid+"&key=AIzaSyDgQSmB4zuUBFUv4rzBhY_e-ZRygBRVT4U",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.cancel();

                        try {
                            JSONObject main = new JSONObject(response);

                            JSONObject getresult = main.getJSONObject("result");

                            JSONObject getgeometry = getresult.getJSONObject("geometry");

                            JSONObject getlocation = getgeometry.getJSONObject("location");

                            Toast.makeText(GetLocationActivity.this,getlocation.getString("lat")+"  "+getlocation.getString("lng"),Toast.LENGTH_SHORT).show();

                            latitude=getlocation.getDouble("lat");

                            longitude = getlocation.getDouble("lng");

                            mapFragment.getMapAsync(GetLocationActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



System.out.println("lattttt = "+response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.cancel();

                    }
                }

        );

        VolleySingletonQuee.getInstance(getBaseContext()).addToRequestQueue(stringRequest);

    }*/


 private String getAddressFromLatLong(Double Latitude, Double Longitude)
 {
     Geocoder geocoder;
     List<Address> addresses;
     geocoder = new Geocoder(this, Locale.getDefault());
     try {
         addresses = geocoder.getFromLocation(Latitude, Longitude, 1);
         String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
         livelocation = addresses.get(0).getSubLocality();
        /* String city2 = addresses.get(0).getSubAdminArea();
         String state = addresses.get(0).getAdminArea();
         String country = addresses.get(0).getCountryName();
         String postalCode = addresses.get(0).getPostalCode();
         String knownName = addresses.get(0).getFeatureName();*/
         Log.e("tyfshgdsahgsah",livelocation);
         valid=true;
return address;

     } catch (IOException e) {
         valid=false;
    return "Address Not Available for this Location";

     }
     catch (IndexOutOfBoundsException e)
     {
         valid=false;
         return "Please choose a valid location";
     }
 }
}

