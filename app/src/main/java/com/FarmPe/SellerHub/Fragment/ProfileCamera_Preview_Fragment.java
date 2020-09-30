package com.FarmPe.SellerHub.Fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;
import com.FarmPe.SellerHub.volleypost.VolleyMultipartRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class ProfileCamera_Preview_Fragment extends Fragment {

    Fragment selectedFragment;
    ImageView b_arrow,cancel,right;
    public static  String imageUri;
    ImageView imageView;
    LinearLayout back_arrow,main_layout;


    SessionManager sessionManager;


    public static ProfileCamera_Preview_Fragment newInstance() {
        ProfileCamera_Preview_Fragment fragment = new ProfileCamera_Preview_Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profileimage_background_layout, container, false);


        getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        Status_bar_change_singleton.getInstance().color_change(getActivity());
        cancel= view.findViewById(R.id.cancel);

        right=view.findViewById(R.id.right);
        imageView=view.findViewById(R.id.capt_img);
        main_layout=view.findViewById(R.id.linearLayout);
        sessionManager = new SessionManager(getActivity());


        String id= (getArguments().getString("sellname"));
        imageUri = id;


        Glide.with(getActivity()).load("file://" + id)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)
                .skipMemoryCache(true))
                .into(imageView);






        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                   /* selectedFragment = Spices_CameraFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();*/

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("camerac", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }

                return false;

            }
        });






        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("camerac", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(getResizedBitmap(Profile_CameraFragment.selectedImage, 100, 100));
            }
        });



        return view;
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap1) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



    private void uploadImage(final Bitmap bitmap){
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "",
                "Loading....Please wait.");
        progressDialog.show();




        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.AddUpdateShopCImageDetails,
                new Response.Listener<NetworkResponse>(){

                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e(TAG,"afaeftagsbillvalue"+response.data);
                        Log.e(TAG,"afaeftagsbillvalue"+response);
                        progressDialog.dismiss();

            /*            Bundle bundle = new Bundle();
                        bundle.putString("status","shopcamera");
                        selectedFragment = Shop_LocationEdit_Fragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        selectedFragment.setArguments(bundle);
                        transaction.commit();*/

                        Toast toast = Toast.makeText(getActivity(),"Profile Details Updated Successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                        toast.show();

                        selectedFragment = NewProfileFragmentEdit.newInstance();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_layout,selectedFragment);
                        ft.commit();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {


            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError){
                if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));

                    //Toast.makeText(getActivity(),volleyError.getMessage(), Toast.LENGTH_SHORT).show();

                    int duration=1000;
                    Snackbar snackbar = Snackbar
                            .make(main_layout, volleyError.getMessage(),duration);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                    tv.setTextColor(Color.WHITE);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    } else {

                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }

                    snackbar.show();


                    volleyError = error;
                }

                return volleyError;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                Log.e(TAG,"Im here " + params);

                if (bitmap!=null) {

                    params.put("Image1", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                }

                Log.e(TAG,"Im here " + params);
                return params;


            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId", sessionManager.getRegId("userId"));
                params.put("CImageId",NewProfileFragmentEdit.image_id);

                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaparams"+params);
                //  params.put("FarmDescription", description.getText().toString());
                Log.e(TAG,"afaeftagsparams"+params);
                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(1000 * 60, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the request to volley

        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


    public Bitmap getResizedBitmap(Bitmap bm1, int newWidth, int newHeight) {

        if (bm1==null){

            return null;

        }else {
            System.out.println("llllllllllllllllllllllllllllll"+newHeight);
            int width = bm1.getWidth();
            int height = bm1.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);
            // "RECREATE" THE NEW BITMAP

            Bitmap resizedBitmap = Bitmap.createBitmap(
                    bm1, 0, 0, width, height, matrix, false);
            //  bm1.recycle();
            return resizedBitmap;
        }
    }
}
