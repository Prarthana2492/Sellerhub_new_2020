package com.FarmPe.SellerHub.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;


public class MapFragment extends Fragment implements OnMapReadyCallback,

        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    Fragment selectedFragment;
    private LocationRequest mLocationRequest;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    GoogleApiClient mGoogleApiClient;

    Location mLastLocation;
    SessionManager sessionManager;
    String pickUPFrom;
    public static JSONArray get_address_array;
    Button confirm_loc;
    LinearLayout main_layout,back_feed;
    Marker mCurrLocationMarker;
    private TextView resutText,currentaddress,addressbook;
    String address_txt;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    String state;



    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.get_lat_long_map_layout, container, false);

        Status_bar_change_singleton.getInstance().color_change(getActivity());


        displayLocationSettingsRequest(getActivity());
        resutText = (TextView) view.findViewById(R.id.curr_address);
        back_feed = view.findViewById(R.id.back_feed);
        confirm_loc = view.findViewById(R.id.confirm_loc);
        currentaddress = view.findViewById(R.id.curr_address);
        addressbook = view.findViewById(R.id.addressbook);
        sessionManager = new SessionManager(getActivity());

        main_layout = view.findViewById(R.id.main_layout);
        //getSupportActionBar().setTitle("Map Location Activity");
        mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        final LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);





        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("farms_sucess", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }
                return false;
            }
        });


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* selectedFragment = Farms_Ownership_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();*/
            }
        });





        address_page();

        addressbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // address_page();
                if(get_address_array.length()== 0){

                    Bundle bundle = new Bundle();
                    bundle.putString("navigation_from","MAP_FRAGMENT");
                  /*  selectedFragment = Add_New_Address_Fragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    selectedFragment.setArguments(bundle);
                    transaction.addToBackStack("map_location");
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();*/



                }else {

                   /* selectedFragment = List_Address_Book_Fragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.addToBackStack("currentlocation");
                    transaction.commit();*/
                }

            }
        });

        //linear layout
        // A layout that arranges its children in a single column or a single row.
        //arranges elements side by side either horizontally or vertically.


        //relative layout
      //  lps you arrange your UI elements based on specific rules. You can specify rules like: align this to parent’s left edge, place this to the left/right of this elements etc.




        confirm_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mLastLocation == null) {
                    displayLocationSettingsRequest(getActivity());
                    int duration =1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Select Your Current Location", duration);
                    View snackbarView = snackbar.getView();

                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();


                } else {
                    /*System.out.println("hrrrjjkj");
                    Bundle bundle = new Bundle();
                    bundle.putString("request_navigation","MAP_FRAGMENT");
                    bundle.putString("add_id","1");
                    selectedFragment = Request_Details_New_Fragment .newInstance();
                    FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_menu, selectedFragment);
                    transaction.addToBackStack("map_fragment");
                    selectedFragment.setArguments(bundle);
                    transaction.commit();*/

                   /* Intent intent=new Intent(getActivity(), CameraActivity.class);
                    intent.putExtra("street_adress",state_string + district_string);

                    startActivity(intent);
*/

                    Toast.makeText(getActivity(),"Farms uploaded sucessfuly.",Toast.LENGTH_LONG).show();

                   /* HomePage_With_Bottom_Navigation.linear_bottonsheet.setVisibility(View.VISIBLE);
                    HomePage_With_Bottom_Navigation.text_home.setTextColor(Color.parseColor("#18a360"));
                    HomePage_With_Bottom_Navigation.home_icon.setImageResource(R.drawable.ic_home_green);
                    HomePage_With_Bottom_Navigation.asknandi_text.setTextColor(Color.parseColor("#595959"));
                    HomePage_With_Bottom_Navigation.asknandi_icon.setImageResource(R.drawable.ic_agronomy);
                    selectedFragment = Home_Menu_Fragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();*/

                   /* selectedFragment = Farms_Ownership_Fragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    transaction.addToBackStack("dssdsdsdds");*/
                }

            }

        });

        return view;
    }

    private void address_page() {


        try{
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));
            jsonObject.put("PickUpFrom",pickUPFrom);
            System.out.println("aaaaaaaaaaaaadddd" + sessionManager.getRegId("userId"));
            System.out.println("ggggggggggaaaaaaa"+jsonObject);


            Crop_Post.crop_posting(getActivity(), Urls.Get_New_Address, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("ggggggggggaaaaaaa"+result);

                    try{
                        get_address_array = result.getJSONArray("UserAddressDetails");

                        if(get_address_array.length()== 0){

                            addressbook.setText("Add New Address");



                        }else {

                            addressbook.setText("Select From Address Book");

                        }




                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mapFrag.getMapAsync(this);
            //configureCameraIdle();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap=googleMap;

        configureCameraIdle();
        mGoogleMap.setOnCameraIdleListener(onCameraIdleListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }


    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }


    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());


        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {

                final Status status = result.getStatus();
                switch (status.getStatusCode()) {

                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;


                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;


                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });

    }


    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {}
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}
    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18));
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();
            } else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    private void configureCameraIdle() {

        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng latLng = mGoogleMap.getCameraPosition().target;
                Geocoder geocoder = new Geocoder(getActivity());
                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        String locality = addressList.get(0).getAddressLine(0);
                        String country = addressList.get(0).getCountryName();

                        // Toast.makeText(getActivity(), state, Toast.LENGTH_LONG).show();
                        if (!locality.isEmpty() && !country.isEmpty())
                            currentaddress.setText(locality + "  " + country);
                        state= addressList.get(0).getLocality()+","+addressList.get(0).getAdminArea();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                } else {

                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}





