//package com.FarmPe.SellerHub.Fragment;
//
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.AlertDialog;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.IntentSender;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.design.widget.BottomSheetBehavior;
//import android.support.design.widget.CoordinatorLayout;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.FarmPe.SellerHub.Activity.HomePage_With_Bottom_Navigation;
//import com.FarmPe.SellerHub.Activity.Person;
//import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
//import com.FarmPe.SellerHub.Adapter.AddingFarms_Adapter;
//import com.FarmPe.SellerHub.Bean.AddTractorBean;
//import com.FarmPe.SellerHub.Bean.AddTractorBean3;
//import com.FarmPe.SellerHub.R;
//import com.FarmPe.SellerHub.SessionManager;
//import com.FarmPe.SellerHub.Urls;
//import com.FarmPe.SellerHub.Volly_class.Crop_Post;
//import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.RequestOptions;
//import com.facebook.AccessTokenManager;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.PendingResult;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResult;
//import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.maps.android.clustering.Cluster;
//import com.google.maps.android.clustering.ClusterManager;
//import com.google.maps.android.clustering.view.DefaultClusterRenderer;
//import com.google.maps.android.ui.IconGenerator;
//
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//import static com.android.volley.VolleyLog.TAG;
//import static com.android.volley.VolleyLog.e;
//
//
//public class Farms_MapView_Fragment extends Fragment  implements OnMapReadyCallback,
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        LocationListener {
//
//    Fragment selectedFragment = null;
//    TextView toolbar_title,addnewfarms,set_location,addnewfarm_btn;
//    LinearLayout back_feed,linearLayout,maplinear,no_farms,farms;
//    CircleImageView profile_image;
//    RecyclerView recyclerView,bottomSheetRecyclerview;
//    public static ArrayList<AddTractorBean> newOrderBeansList = new ArrayList<>();
//    AddTractorBean addTractorBean;
//    JSONArray get_array;
//    int mDimension;
//    AddingFarms_Adapter addingFarms_adapter;
//    SessionManager sessionManager;
//    GoogleMap mGoogleMap;
//    ImageView plus;
//    JSONArray get_cimage_array,get_loctn_array;
//
//    SupportMapFragment mapFrag;
//    GoogleApiClient mGoogleApiClient;
//    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
//
//    public static ClusterManager<Person> mClusterManager;
//
//
//    Location mLastLocation;
//    private LocationRequest mLocationRequest;
//    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
//    Marker mCurrLocationMarker;
//    private CoordinatorLayout coordinatorLayout;
//    private BottomSheetBehavior behavior;
//    private View persistentbottomSheet;
//    private IconGenerator mIconGenerator;
//
//    //*********************************
//    LatLngCallback latLngCallback;
//    LatLng latLng;
//
//    public interface LatLngCallback{
//        public void sendLatLng(LatLng latLng);
//    }
//
//
//
//    public static Farms_MapView_Fragment newInstance() {
//
//        Farms_MapView_Fragment fragment = new Farms_MapView_Fragment();
//        return fragment;
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        // This makes sure that the container activity has implemented
//        // the callback interface. If not, it throws an exception
//        try {
//            latLngCallback = (LatLngCallback) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement TextClicked");
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.farms_mapview_layout, container, false);
//
//        Status_bar_change_singleton.getInstance().color_change(getActivity());
//        HomePage_With_Bottom_Navigation.linear_bottonsheet.setVisibility(View.VISIBLE);
//
//        addnewfarms=view.findViewById(R.id.addnewfarms);
//        addnewfarm_btn=view.findViewById(R.id.list_farmmmmm);
//        sessionManager = new SessionManager(getActivity());
//        profile_image = view.findViewById(R.id.prod_imgg);
//        set_location = view.findViewById(R.id.set_lcn);
//        no_farms= view.findViewById(R.id.no_requests);
//        farms= view.findViewById(R.id.farms);
//        plus= view.findViewById(R.id.plus);
//        coordinatorLayout = (CoordinatorLayout)view.findViewById(R.id.coordinator);
//        persistentbottomSheet = coordinatorLayout.findViewById(R.id.bottomsheet);
//        bottomSheetRecyclerview = coordinatorLayout.findViewById(R.id.recyclerview_bottom_sheet);
//        behavior = BottomSheetBehavior.from(persistentbottomSheet);
//        mIconGenerator = new IconGenerator(getActivity().getApplicationContext());
//
//
//
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//            }
//        }, 1000);
//
//
//
//                plus.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        latLngCallback.sendLatLng(latLng);
//                        selectedFragment = Camera_Fragment2.newInstance();
//                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.frame_layout, selectedFragment);
//                        transaction.addToBackStack("about");
//                        transaction.commit();
//
//                    }
//                });
//
//
//
//         //*************** get image ******************//
//
//
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("UserId", sessionManager.getRegId("userId"));
//
//            Crop_Post.crop_posting(getActivity(), Urls.GetCImagelist, jsonObject, new VoleyJsonObjectCallback() {
//                @Override
//                public void onSuccessResponse(JSONObject result) {
//                    System.out.println("dhfjfjd" + result);
//
//                    try {
//                        get_cimage_array = result.getJSONArray("captureImagelist");
//                        for (int i = 0; i < get_cimage_array.length(); i++) {
//
//                            JSONObject jsonObject1 = get_cimage_array.getJSONObject(i);
//                            String image_id = jsonObject1.getString("CImageId");
//                            String image_view = jsonObject1.getString("Image1");
//
//                            Glide.with(getActivity()).load(image_view)
//                                    .thumbnail(0.5f)
//                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                    .error(R.drawable.avatarmale)
//                                    .into(profile_image);
//                        }
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        // ************ get location *******************//
//
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("UserId", sessionManager.getRegId("userId"));
//
//            Crop_Post.crop_posting(getActivity(), Urls.GetCLocationList, jsonObject, new VoleyJsonObjectCallback() {
//                @Override
//                public void onSuccessResponse(JSONObject result) {
//                    System.out.println("dhfjfjd" + result);
//                    try {
//                        get_loctn_array = result.getJSONArray("clocationList");
//                        for (int i = 0; i < get_loctn_array.length(); i++) {
//                            JSONObject jsonObject1 = get_loctn_array.getJSONObject(i);
//
//                            String loctn_id = jsonObject1.getString("CLocationId");
//                            String location_lat = jsonObject1.getString("Latitude");
//                            String location_long = jsonObject1.getString("Longitude");
//                            String location_captured = jsonObject1.getString("CapturedLocation");
//
//
//                            set_location.setText(location_captured);
//                            System.out.println("setlocationnnnnnnnnnn"+location_captured);
//
//
//
//                        }
//
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
////
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//      /*  try{
//            final JSONObject jsonObject = new JSONObject();
//            jsonObject.put("UserId",sessionManager.getRegId("userId"));
//
//            Crop_Post.crop_posting(getActivity(), Urls.GetAddNewFarms, jsonObject, new VoleyJsonObjectCallback() {
//                @Override
//                public void onSuccessResponse(JSONObject result) {
//                    System.out.println("ggggggggggaaaaaaa"+result);
//                    try{
//                        newOrderBeansList.clear();
//                        get_array = result.getJSONArray("FarmNameList");
//
//                        for(int i=0;i<get_array.length();i++){
//                            JSONObject jsonObject1 = get_array.getJSONObject(i);
////
//                            addTractorBean = new AddTractorBean(jsonObject1.getString("FarmPhoto"),jsonObject1.getString("Name"),jsonObject1.getString("Id"));
//                            newOrderBeansList.add(addTractorBean);
//                        }
//
//                        if(get_array.length()==0){
//                            Toast.makeText(getActivity(), "dfgdfgdfg", Toast.LENGTH_SHORT).show();
//                            no_farms.setVisibility(View.VISIBLE);
//                            farms.setVisibility(View.GONE);
//
//
//                        }else {
//                            farms.setVisibility(View.VISIBLE);
//                            no_farms.setVisibility(View.GONE);
//                        }
//
//
//                        addingFarms_adapter.notifyDataSetChanged();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }*/
//
//        mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        mapFrag.getMapAsync(this);
//        final LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
//        displayLocationSettingsRequest(getActivity());
//
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//
//                    HomePage_With_Bottom_Navigation.linear_bottonsheet.setVisibility(View.VISIBLE);
//                    HomePage_With_Bottom_Navigation.text_home.setTextColor(Color.parseColor("#18a360"));
//                    HomePage_With_Bottom_Navigation.home_icon.setImageResource(R.drawable.ic_home_green);
//                    HomePage_With_Bottom_Navigation.mail_text.setTextColor(Color.parseColor("#595959"));
//                    HomePage_With_Bottom_Navigation.mail_icon.setImageResource(R.drawable.ic_agronomy);
//                    HomePage_With_Bottom_Navigation.view.setVisibility(View.VISIBLE);
//
//                    selectedFragment = Home_Menu_Fragment.newInstance();
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.frame_layout, selectedFragment);
//                    transaction.commit();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//
//
// farms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectedFragment = Farms_Photo_Fragment.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout, selectedFragment);
//                transaction.commit();
//            }
//        });
//
//  addnewfarms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                  System.out.println("kmkbmkm"+latLng);
//                latLngCallback.sendLatLng(latLng);
//                selectedFragment = Camera_Fragment2.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout, selectedFragment);
//                transaction.addToBackStack("about");
//                transaction.commit();
//            }
//        });
//
//
//        addnewfarm_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("kmkbmkm"+latLng);
//                latLngCallback.sendLatLng(latLng);
//                selectedFragment = Camera_Fragment2.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout, selectedFragment);
//                transaction.addToBackStack("about");
//                transaction.commit();
//            }
//        });
//
//
//
//
//
//
//
//       /* Glide.with(getActivity()).load(Home_Menu_Fragment.ProfileImage)
//                .thumbnail(0.5f)
//                // .crossFade()
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
//                .error(R.drawable.avatarmale)
//                .into(profile_image);
//*/
//        Glide.with(getActivity()).load(Home_Menu_Fragment.ProfileImage)
//                .thumbnail(0.5f)
//                // .crossFade()
//                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
//                        .error(R.drawable.avatarmale))
//                .into(profile_image);
//
//        return view;
//    }
//
//
//
//
//
//
//
//    public void ExpandBottomSheet(View view){
//        if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
//            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        } else {
//            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        //stop location updates when Activity is no longer active
//        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//            mapFrag.getMapAsync(this);
//            //configureCameraIdle();
//        }
//    }
//
//
//    @Override
//    public void onMapReady(GoogleMap googleMap)
//    {
//        System.out.println("nfkfjnjvnjnvnvnnn");
//        mGoogleMap=googleMap;
//
//        configureCameraIdle();
//        mGoogleMap.setOnCameraIdleListener(onCameraIdleListener);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(getActivity(),
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//                //Location Permission already granted
//                buildGoogleApiClient();
//                mGoogleMap.setMyLocationEnabled(true);
//            } else {
//                //Request Location Permission
//                checkLocationPermission();
//            }
//        }
//        else {
//
//            buildGoogleApiClient();
//            mGoogleMap.setMyLocationEnabled(true);
//        }
//
//
//
//        mClusterManager = new ClusterManager<>(getActivity(), googleMap);
//    //    mClusterManager.addItem("","");
//        googleMap.setOnCameraIdleListener(mClusterManager);
//        googleMap.setOnMarkerClickListener(mClusterManager);
//        googleMap.setOnInfoWindowClickListener(mClusterManager);
//        mClusterManager.cluster();
//        mClusterManager.setRenderer(new Farms_MapView_Fragment.RenderClusterInfoWindow(getActivity(),googleMap, mClusterManager));
//        farms_map_view();
//
//    }
//
//
//
//    private void farms_map_view() {
//
//
//        try{
//            final JSONObject jsonObject = new JSONObject();
//            jsonObject.put("UserId",sessionManager.getRegId("userId"));
//
//            Crop_Post.crop_posting(getActivity(), Urls.GetAddNewFarms, jsonObject, new VoleyJsonObjectCallback() {
//                @Override
//                public void onSuccessResponse(JSONObject result) {
//                    System.out.println("ggggggggggaaaaaaakkkkkkkkkkkkkkk"+result);
//                    try{
//                        newOrderBeansList.clear();
//                        get_array = result.getJSONArray("FarmNameList");
//
//                        for(int i=0;i<get_array.length();i++){
//
//                            JSONObject jsonObject2 = get_array.getJSONObject(i);
//                            double lat,lng;
//
//                            if (jsonObject2.getString("Latitude").equals("")){
//
//                                lat=0.0;
//
//                            }else {
//                                lat= Double.parseDouble(jsonObject2.getString("Latitude"));
//
//                            }
//
//                            if (jsonObject2.getString("Longitude").equals("")){
//
//                                lng=0.0;
//
//                            }else {
//
//                                lng = Double.parseDouble(String.valueOf(jsonObject2.getDouble("Longitude")));
//
//                            }
//
//                            mClusterManager.addItem(new Person(lat, lng, jsonObject2.getString("Name"), jsonObject2.getString("Id")));
//
//                            addTractorBean = new AddTractorBean(jsonObject2.getString("FarmPhoto"),jsonObject2.getString("Name"),jsonObject2.getString("Id"));
//                            newOrderBeansList.add(addTractorBean);
//
//                          }
//
//
//
//                        if(get_array.length()==0){
//
//                            //Toast.makeText(getActivity(), "dfgdfgdfg", Toast.LENGTH_SHORT).show();
//                            no_farms.setVisibility(View.VISIBLE);
//                            farms.setVisibility(View.GONE);
//
//
//                        }else {
//
//                            farms.setVisibility(View.VISIBLE);
//                            no_farms.setVisibility(View.GONE);
//                        }
//
//
//                        addingFarms_adapter = new AddingFarms_Adapter(getActivity(),newOrderBeansList);
//                        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
//                        bottomSheetRecyclerview.setLayoutManager(mLayoutManager_farm);
//                        bottomSheetRecyclerview.setItemAnimator(new DefaultItemAnimator());
//                        bottomSheetRecyclerview.setAdapter(addingFarms_adapter);
//
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//
//                     }
//                      }
//                 });
//
//
//            mClusterManager.cluster();
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//    protected synchronized void buildGoogleApiClient() {
//
//
//        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//
//        mGoogleApiClient.connect();
//
//    }
//
//
//
//
//    private class RenderClusterInfoWindow extends DefaultClusterRenderer<Person> {
//        private final ImageView mClusterImageView;
//        private final ImageView mImageView;
//
//
//        RenderClusterInfoWindow(Context context, GoogleMap map, ClusterManager<Person> clusterManager) {
//            super(context, map, clusterManager);
//
//            View multiProfile = getLayoutInflater().inflate(R.layout.multi_profile, null);
//            mIconGenerator.setContentView(multiProfile);
//            mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);
//
//            mImageView = new ImageView(getActivity().getApplicationContext());
//            mDimension = (int) getResources().getDimension(R.dimen.card_bottom_interval);
//            mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
//            int padding = (int) getResources().getDimension(R.dimen.bottom_navigation_notification_padding);
//            mImageView.setPadding(padding, padding, padding, padding);
//            mIconGenerator.setContentView(mImageView);
//
//
//        }
//
//        @Override
//        protected void onClusterRendered(Cluster<Person> cluster, Marker marker) {
//            super.onClusterRendered(cluster, marker);
//
//        }
//
//        @Override
//        protected void onBeforeClusterItemRendered(Person item, MarkerOptions markerOptions) {
//            markerOptions.title(item.getName());
//            markerOptions.snippet("");
//
//            super.onBeforeClusterItemRendered(item, markerOptions);
//            System.out.println("kkkkkkkkkkkkkkkk" + item.getId());
//
//
//            mImageView.setImageResource(R.drawable.ic_agronomy_white);
//            Bitmap icon = mIconGenerator.makeIcon();
//            mIconGenerator.setBackground(
//                    ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.circle_profile));
//            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
//
//
//        }
//
//        @Override
//        protected void onBeforeClusterRendered(Cluster<Person> cluster, MarkerOptions markerOptions) {
//            mImageView.setImageResource(R.drawable.ic_agronomy_white);
//
//
//            Bitmap icon = mIconGenerator.makeIcon();
//            mIconGenerator.setBackground(
//                    ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.circle_profile));
//            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
//
//
//
//        /*    mIconGenerator1.setBackground(
//                    ContextCompat.getDrawable(getApplicationContext(), R.drawable.border));
//           // mIconGenerator1.setTextAppearance(R.style.MineCustomTabText);
//            final Bitmap icon = mIconGenerator1.makeIcon(String.valueOf(cluster.getSize()));
//            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));*/
///*
//
//            mImageView.setImageResource(R.drawable.ic_psychoanalysis);
//            Bitmap icon = mIconGenerator.makeIcon();
//            mIconGenerator.setBackground(
//                    ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_profile));
//            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
//*/
//
//
//        }
//
//        @Override
//        protected boolean shouldRenderAsCluster(Cluster cluster) {
//            return cluster.getSize() > 10;
//        }
//    }
//
//
//
//
//    private void displayLocationSettingsRequest(Context context) {
//        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
//                .addApi(LocationServices.API).build();
//        googleApiClient.connect();
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(10000);
//        locationRequest.setFastestInterval(10000 / 2);
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
//
//
//
//        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//            @Override
//            public void onResult(LocationSettingsResult result) {
//
//
//                final Status status = result.getStatus();
//
//                switch (status.getStatusCode()) {
//
//                        case LocationSettingsStatusCodes.SUCCESS:
//                        Log.i(AccessTokenManager.TAG, "All location settings are satisfied.");
//                        break;
//
//
//
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        Log.i(AccessTokenManager.TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");
//
//                        try {
//                            // Show the dialog by calling startResolutionForResult(), and check the result
//                            // in onActivityResult().
//                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
//                        } catch (IntentSender.SendIntentException e) {
//                            Log.i(AccessTokenManager.TAG, "PendingIntent unable to execute request.");
//                        }
//
//                        break;
//
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        Log.i(AccessTokenManager.TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
//                        break;
//
//                }
//            }
//        });
//
//    }
//
//
//    @Override
//    public void onConnected(Bundle bundle) {
//        mLocationRequest = new LocationRequest();
//
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        if (ContextCompat.checkSelfPermission(getActivity(),
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {}
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {}
//    @Override
//    public void onLocationChanged(Location location)
//    {
//        mLastLocation = location;
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker.remove();
//        }
//
//         latLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18));
//    }
//
//
//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
//    private void checkLocationPermission() {
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//                new AlertDialog.Builder(getActivity())
//                        .setTitle("Location Permission Needed")
//                        .setMessage("This app needs the Location permission, please accept to use location functionality")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //Prompt the user once explanation has been shown
//                                ActivityCompat.requestPermissions(getActivity(),
//                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                        MY_PERMISSIONS_REQUEST_LOCATION );
//                            }
//                        })
//                        .create()
//                        .show();
//            } else {
//
//                ActivityCompat.requestPermissions(getActivity(),
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION );
//            }
//        }
//    }
//
//    private void configureCameraIdle() {
//        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
//            @Override
//            public void onCameraIdle() {
//                LatLng latLng = mGoogleMap.getCameraPosition().target;
//                Geocoder geocoder = new Geocoder(getActivity());
//                try {
//                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                    if (addressList != null && addressList.size() > 0) {
//                        String locality = addressList.get(0).getAddressLine(0);
//                        String country = addressList.get(0).getCountryName();
//
//                        // Toast.makeText(getActivity(), state, Toast.LENGTH_LONG).show();
//                        // if (!locality.isEmpty() && !country.isEmpty())
//                        //  currentaddress.setText(locality + "  " + country);
//                        // state= addressList.get(0).getLocality()+","+addressList.get(0).getAdminArea();
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_LOCATION: {
//
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    if (ContextCompat.checkSelfPermission(getActivity(),
//                            Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
//
//                        if (mGoogleApiClient == null) {
//                            buildGoogleApiClient();
//                        }
//
//                        mGoogleMap.setMyLocationEnabled(true);
//                    }
//                } else {
//
//                   Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
//                }
//
//
//                return;
//            }
//        }
//    }
//
//
//}