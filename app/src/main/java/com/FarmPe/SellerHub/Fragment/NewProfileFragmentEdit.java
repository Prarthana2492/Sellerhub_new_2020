package com.FarmPe.SellerHub.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.FarmPe.SellerHub.Activity.LandingPageActivity;
import com.FarmPe.SellerHub.Activity.R_U_Farmer_Activity;
import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;
import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.android.volley.VolleyLog.TAG;

public class NewProfileFragmentEdit extends Fragment {
    Fragment selectedFragment;
   public LinearLayout backfeed1;
   EditText phone_no,name;
   SessionManager sessionManager;
   public  static CircleImageView image;
   JSONArray get_cimage_array;
   public static String image_id;
   ImageView edit_name;
    String ID,ProfileName,UserType;
    Bitmap bitmap;
    BottomSheetDialog mBottomSheetDialog;
    View sheetView;
    TextView usertype;


    public static NewProfileFragmentEdit newInstance() {
        NewProfileFragmentEdit fragment = new NewProfileFragmentEdit();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_a_profile_layout, container, false);
        backfeed1= view.findViewById(R.id.back_feed);
        phone_no= view.findViewById(R.id.phone_text);
        image= view.findViewById(R.id.prod_imgg);
        name= view.findViewById(R.id.prof_name);
        edit_name= view.findViewById(R.id.name_edit);
        usertype= view.findViewById(R.id.user_text);



        Status_bar_change_singleton.getInstance().color_change(getActivity());


        sessionManager = new SessionManager(getActivity());

        phone_no.setText(sessionManager.getRegId("phone"));

        backfeed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), LandingPageActivity.class);
                startActivity(i);


            }
        });
/////////// Get Name //////////////////


        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject post_object = new JSONObject();

            jsonObject.put("Id",sessionManager.getRegId("userId"));
            post_object.put("objUser",jsonObject);


            Crop_Post.crop_posting(getActivity(), Urls.Get_Profile_Details, post_object, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("ggpgpgpg" + result);

                    try{

                        JSONObject jsonObject1 = result.getJSONObject("user");
                         ProfileName = jsonObject1.getString("UserName");
                         UserType = jsonObject1.getString("UserType");
                        //  ID = jsonObject1.getString("id");
                        System.out.println("nameeeeeeeeeeeeeeeeeeeeeeeeeee"+ProfileName);
                        name.setText(ProfileName);
                        usertype.setText(UserType);



                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
            edit_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent i = new Intent(getActivity(), R_U_Farmer_Activity.class);
                    i.putExtra("navi_from", "profile");
                    i.putExtra("name",ProfileName );
                    i.putExtra("type", UserType);
                    i.putExtra("id",ID );
                    startActivity(i);


                }
            });


            image .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mBottomSheetDialog = new BottomSheetDialog(getActivity());
                    sheetView = getActivity().getLayoutInflater().inflate(R.layout.profile_bottom_sheet, null);

                    LinearLayout gallery = sheetView.findViewById(R.id.gallery);
                    LinearLayout camera = sheetView.findViewById(R.id.camera);

                    gallery.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // to go to gallery
                            startActivityForResult(i, 100);
                            mBottomSheetDialog.hide();
                        }
                    });



                    camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mBottomSheetDialog.hide();
                            selectedFragment = Profile_CameraFragment.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, selectedFragment);
                            transaction.addToBackStack("newprofile");
                            transaction.commit();
                        }
                    });

                   /* imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            camera.takePicture(null, null, jpegCallback);
                        }
                    });
                    */


                    mBottomSheetDialog.setContentView(sheetView);
                    mBottomSheetDialog.show();


                    /**/
                }
            });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    Intent i = new Intent(getActivity(), LandingPageActivity.class);
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId", sessionManager.getRegId("userId"));

            Crop_Post.crop_posting(getActivity(), Urls.GetCImagelist, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("dhfjfjd" + result);

                    try {
                        get_cimage_array = result.getJSONArray("captureImagelist");
                        for (int i = 0; i < get_cimage_array.length(); i++) {

                            JSONObject jsonObject1 = get_cimage_array.getJSONObject(i);
                            image_id = jsonObject1.getString("CImageId");
                            String image_view = jsonObject1.getString("Image1");

                            Glide.with(getActivity()).load(image_view)
                                    .thumbnail(0.5f)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_gallery__default)
                                    .into(image);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }




        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {


            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //   g_vision_controller = G_Vision_Controller.getInstance( );
//getting the image Uri


                final InputStream imageStream;

                imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(imageStream);
                //   g_vision_controller.callCloudVision(bitmap,getActivity(),"profile");
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                image.setImageBitmap(bitmap);
                uploadImage(getResizedBitmap(bitmap,100,100));

                Toast toast = Toast.makeText(getActivity(),"Your Changed Your Profile Photo", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                toast.show();
                //  Toast.makeText(getActivity(),"Your Changed Your Profile Photo", Toast.LENGTH_SHORT).show();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
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

                        Log.e(TAG,"afaeftagsbillvalue"+response);
                        Log.e(TAG,"afaeftagsbillvalue"+response);

                        progressDialog.dismiss();

                        image.setImageBitmap(bitmap);

                        /*Toast toast = Toast.makeText(getActivity(),"Profile Details Updated Successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                        toast.show();*/

/*
                        int duration = 1000;
                        Snackbar snackbar = Snackbar
                                .make(lnr, "Profile Details Updated Successfully", duration);
                        View snackbarView = snackbar.getView();
                        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                        tv.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        } else {
                            tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        }
                        snackbar.show();*/

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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId",sessionManager.getRegId("userId"));
                params.put("CImageId",image_id);
                // params.put("FullName",sessionManager.getRegId("name"));
                //  params.put("PhoneNo",sessionManager.getRegId("phone"));
                //  params.put("EmailId","abcd@gmail.com");
                //    params.put("Password",profile_passwrd.getText().toString());
                Log.e(TAG,"afaeftagsparams"+params);
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                Log.e(TAG,"Im here " + params);
                if (bitmap!=null) {
                    params.put("Image1", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                }
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(1000 * 60, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        if (bm == null) {

            return null;

        } else {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            Bitmap resizedBitmap = Bitmap.createBitmap(
                    bm, 0, 0, width, height, matrix, false);
            bm.recycle();
            return resizedBitmap;
        }
    }












}

