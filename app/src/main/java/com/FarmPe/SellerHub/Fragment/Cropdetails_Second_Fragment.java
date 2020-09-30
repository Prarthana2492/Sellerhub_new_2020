package com.FarmPe.SellerHub.Fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
import com.FarmPe.SellerHub.Adapter.QuantityPrice_Adapter2;
import com.FarmPe.SellerHub.Adapter.Spices_Category_Adapter;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class Cropdetails_Second_Fragment extends Fragment {
    Fragment selectedFragment;
   public LinearLayout backfeed1,continue_btn;
   TextView yes,no,cartons,plastic_box,petjar,otheroption1,fivekg,tenkg,twentykg,fiftykg,hundredkg,otherkg_option;
   SessionManager sessionManager;
   String sell_MlistId;
    String variety_Id;
    String Get_image;
    String quality_Id;
    String Organicproduce_Id;
    String PackagingtypeId;
    String PackagingsizeId;
    Bitmap GETIMAGE;

    public static Cropdetails_Second_Fragment newInstance() {
        Cropdetails_Second_Fragment fragment = new Cropdetails_Second_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cropdetails2_layout, container, false);
        backfeed1= view.findViewById(R.id.back_feed);
        yes= view.findViewById(R.id.yes);
        no= view.findViewById(R.id.no);
        cartons= view.findViewById(R.id.cartons_btn);
        plastic_box= view.findViewById(R.id.plastic_btn);
        petjar= view.findViewById(R.id.pet_btn);
        otheroption1= view.findViewById(R.id.other_btn);
        fivekg= view.findViewById(R.id.fivekg_btn);
        tenkg= view.findViewById(R.id.tenkg_btn);
        twentykg= view.findViewById(R.id.twentykg_btn);
        fiftykg= view.findViewById(R.id.fiftykg_btn);
        hundredkg= view.findViewById(R.id.hundrdkg_btn);
        otherkg_option= view.findViewById(R.id.otherkg_btn);
        continue_btn= view.findViewById(R.id.continuebtn);


        Status_bar_change_singleton.getInstance().color_change(getActivity());
        sessionManager = new SessionManager(getActivity());

        Bundle bundle= getArguments();
        if (bundle!=null){
            sell_MlistId = (getArguments().getString("selling_Mlist_id"));
            variety_Id = getArguments().getString("variety_id");
            quality_Id = getArguments().getString("quality_id");
            Get_image = getArguments().getString("Image");
            System.out.println("image2222222"+Get_image);
        }


        //GETIMAGE = BitmapFactory.decodeFile(String.valueOf(Get_image));

      /*  byte[] decodedString = Base64.decode(Get_image,Base64.NO_WRAP);
        InputStream inputStream  = new ByteArrayInputStream(decodedString);
        GETIMAGE  = BitmapFactory.decodeStream(inputStream);
*/
       //public byte[] getFileDataFromDrawable(Bitmap bitmap1) {
        //        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //        bitmap1.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //        return byteArrayOutputStream.toByteArray();
        //    }


        System.out.println("convetedimage"+GETIMAGE);








        backfeed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("spicesdetails", FragmentManager.POP_BACK_STACK_INCLUSIVE);


            }
        });

//spicesdetails
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("spicesdetails", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }
                return false;
            }
        });



        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Organicproduce_Id = "1";
                yes.setTextColor(Color.parseColor("#FFFFFF"));
                no.setTextColor(Color.parseColor("#000000"));

                yes.setBackgroundResource(R.drawable.black_border_blue_filled);
                no.setBackgroundResource(R.drawable.black_bordr_white_filled1);

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Organicproduce_Id = "2";
                no.setTextColor(Color.parseColor("#FFFFFF"));
                yes.setTextColor(Color.parseColor("#000000"));

                no.setBackgroundResource(R.drawable.black_border_blue_filled);
                yes.setBackgroundResource(R.drawable.black_bordr_white_filled1);

            }
        });


        cartons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               PackagingtypeId ="1";
                cartons.setTextColor(Color.parseColor("#FFFFFF"));
                plastic_box.setTextColor(Color.parseColor("#000000"));
                petjar.setTextColor(Color.parseColor("#000000"));
                otheroption1.setTextColor(Color.parseColor("#000000"));



                cartons.setBackgroundResource(R.drawable.black_border_blue_filled);
                plastic_box.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                petjar.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                otheroption1.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });

        plastic_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PackagingtypeId ="2";
                plastic_box.setTextColor(Color.parseColor("#FFFFFF"));
                cartons.setTextColor(Color.parseColor("#000000"));
                petjar.setTextColor(Color.parseColor("#000000"));
                otheroption1.setTextColor(Color.parseColor("#000000"));



                plastic_box.setBackgroundResource(R.drawable.black_border_blue_filled);
                cartons.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                petjar.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                otheroption1.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });


        petjar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PackagingtypeId ="3";
                petjar.setTextColor(Color.parseColor("#FFFFFF"));
                plastic_box.setTextColor(Color.parseColor("#000000"));
                cartons.setTextColor(Color.parseColor("#000000"));
                otheroption1.setTextColor(Color.parseColor("#000000"));



                petjar.setBackgroundResource(R.drawable.black_border_blue_filled);
                plastic_box.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                cartons.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                otheroption1.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });

        otheroption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PackagingtypeId ="4";
                otheroption1.setTextColor(Color.parseColor("#FFFFFF"));
                plastic_box.setTextColor(Color.parseColor("#000000"));
                petjar.setTextColor(Color.parseColor("#000000"));
                cartons.setTextColor(Color.parseColor("#000000"));



                otheroption1.setBackgroundResource(R.drawable.black_border_blue_filled);
                plastic_box.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                petjar.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                cartons.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });


        fivekg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PackagingsizeId ="1";
                fivekg.setTextColor(Color.parseColor("#FFFFFF"));
                tenkg.setTextColor(Color.parseColor("#000000"));
                twentykg.setTextColor(Color.parseColor("#000000"));
                fiftykg.setTextColor(Color.parseColor("#000000"));
                hundredkg.setTextColor(Color.parseColor("#000000"));
                otherkg_option.setTextColor(Color.parseColor("#000000"));



                fivekg.setBackgroundResource(R.drawable.black_border_blue_filled);
                tenkg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                twentykg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                fiftykg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                hundredkg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                otherkg_option.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });


        tenkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PackagingsizeId ="2";
                tenkg.setTextColor(Color.parseColor("#FFFFFF"));
                fivekg.setTextColor(Color.parseColor("#000000"));
                twentykg.setTextColor(Color.parseColor("#000000"));
                fiftykg.setTextColor(Color.parseColor("#000000"));
                hundredkg.setTextColor(Color.parseColor("#000000"));
                otherkg_option.setTextColor(Color.parseColor("#000000"));


                tenkg.setBackgroundResource(R.drawable.black_border_blue_filled);
                fivekg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                twentykg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                fiftykg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                hundredkg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                otherkg_option.setBackgroundResource(R.drawable.black_bordr_white_filled1);

            }
        });

        twentykg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PackagingsizeId ="3";
                twentykg.setTextColor(Color.parseColor("#FFFFFF"));
                tenkg.setTextColor(Color.parseColor("#000000"));
                fivekg.setTextColor(Color.parseColor("#000000"));
                fiftykg.setTextColor(Color.parseColor("#000000"));
                hundredkg.setTextColor(Color.parseColor("#000000"));
                otherkg_option.setTextColor(Color.parseColor("#000000"));

                twentykg.setBackgroundResource(R.drawable.black_border_blue_filled);
                tenkg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                fivekg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                fiftykg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                hundredkg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                otherkg_option.setBackgroundResource(R.drawable.black_bordr_white_filled1);


            }
        });

        fiftykg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PackagingsizeId ="4";
                fiftykg.setTextColor(Color.parseColor("#FFFFFF"));
                tenkg.setTextColor(Color.parseColor("#000000"));
                twentykg.setTextColor(Color.parseColor("#000000"));
                fivekg.setTextColor(Color.parseColor("#000000"));
                hundredkg.setTextColor(Color.parseColor("#000000"));
                otherkg_option.setTextColor(Color.parseColor("#000000"));



                fiftykg.setBackgroundResource(R.drawable.black_border_blue_filled);
                tenkg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                twentykg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                fivekg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                hundredkg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                otherkg_option.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });

        hundredkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PackagingsizeId ="5";
                hundredkg.setTextColor(Color.parseColor("#FFFFFF"));
                tenkg.setTextColor(Color.parseColor("#000000"));
                twentykg.setTextColor(Color.parseColor("#000000"));
                fiftykg.setTextColor(Color.parseColor("#000000"));
                fivekg.setTextColor(Color.parseColor("#000000"));
                otherkg_option.setTextColor(Color.parseColor("#000000"));



                hundredkg.setBackgroundResource(R.drawable.black_border_blue_filled);
                tenkg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                twentykg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                fiftykg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                fivekg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                otherkg_option.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });


        otherkg_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PackagingsizeId ="6";
                otherkg_option.setTextColor(Color.parseColor("#FFFFFF"));
                tenkg.setTextColor(Color.parseColor("#000000"));
                twentykg.setTextColor(Color.parseColor("#000000"));
                fiftykg.setTextColor(Color.parseColor("#000000"));
                hundredkg.setTextColor(Color.parseColor("#000000"));
                fivekg.setTextColor(Color.parseColor("#000000"));



                otherkg_option.setBackgroundResource(R.drawable.black_border_blue_filled);
                tenkg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                twentykg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                fiftykg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                hundredkg.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                fivekg.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });

  continue_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

          if(Organicproduce_Id == null) {


              Toast toast = Toast.makeText(getActivity(), "Please select organic produce", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
              toast.show();
          }else  if(PackagingtypeId == null) {


              Toast toast = Toast.makeText(getActivity(), "Please select package type", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
              toast.show();
          }else  if(PackagingsizeId==null) {


              Toast toast = Toast.makeText(getActivity(), "Please select package size", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
              toast.show();
          }else {
              uploadImage(getResizedBitmap(Spices_CameraFragment.selectedImage, 100, 100));
          }
         /* if (getArguments() .getString("navg_from")!= null) {
              if (getArguments().getString("NAVGI").equals(Spices_Details_Fragment.navigation)) {

                  uploadImage(getResizedBitmap(GETIMAGE, 100, 100));

                System.out.println("bitmapimageddf"+GETIMAGE);
              }
          }*/
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



        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.AddUpdateSelling,
                new Response.Listener<NetworkResponse>(){
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e(TAG,"afaeftagsbillvalue"+response.data);
                        Log.e(TAG,"afaeftagsbillvalue"+response);
                        progressDialog.dismiss();

                       /* Toast toast = Toast.makeText(getActivity(),"Inventory added successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                        toast.show();*/

                       /* int duration=1000;
                       Snackbar snackbar = Snackbar
                                .make(main_layout, "Selllist Added Successfully",duration);
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

                        selectedFragment = ProductConfirmationFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.addToBackStack("spicesdetails");
                        transaction.commit();
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



                    Toast toast = Toast.makeText(getActivity(),volleyError.getMessage(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    toast.show();
                 /*   int duration=1000;
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
                    snackbar.show();*/

                    volleyError = error;
                }

                return volleyError;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                Log.e(TAG,"Im here " + params);
               // if (bitmap!=null) {
                    params.put("SellingImage", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

              //  }
                Log.e(TAG,"Im here " + params);
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId", sessionManager.getRegId("userId"));
                params.put("CreatedBy", sessionManager.getRegId("userId"));
                params.put("SellingDetailsId", "0");
                params.put("SellingListMasterId", sell_MlistId);

                params.put("SellingVarietyId", variety_Id);
                params.put("SellingQualityId", quality_Id);
                params.put("SellingQuantity", Spices_Details_Fragment.min_quantity.getText().toString());
                params.put("UnitOfPriceId", String.valueOf(QuantityPrice_Adapter2.quantityprice_id));
                params.put("Price", "0");
                params.put("SellingCategoryId", Spices_Category_Adapter.selling_category_id);
                params.put("MinPrice", Spices_Details_Fragment.min_price.getText().toString());
                params.put("MaxPrice", Spices_Details_Fragment.max_price.getText().toString());
                params.put("OrganicProduceId",Organicproduce_Id );
                params.put("PackagingTypeId", PackagingtypeId);
                params.put("PackagingSizeId", PackagingsizeId);




                // params.put("SellingDetailsId",selling_detailsid);



                if (getArguments() .getString("NAVGI")!= null) {

                    if (getArguments().getString("NAVGI").equals(Spices_Details_Fragment.navigation)) {
                        params.put("UserId", sessionManager.getRegId("userId"));
                        params.put("CreatedBy", sessionManager.getRegId("userId"));
                        params.put("SellingDetailsId", Spices_Details_Fragment.SellDetails_id);
                        System.out.println("editiddddd" + Spices_Details_Fragment.SellDetails_id);
                        params.put("SellingListMasterId", Spices_Details_Fragment.Sell_MlistId);
                        System.out.println("Masterid" + Spices_Details_Fragment.Sell_MlistId);
                        params.put("SellingVarietyId", variety_Id);
                        params.put("SellingQualityId", quality_Id);
                        params.put("SellingQuantity", Spices_Details_Fragment.min_quantity.getText().toString());
                        params.put("UnitOfPriceId", String.valueOf(QuantityPrice_Adapter2.quantityprice_id));
                        params.put("Price", "0");
                        params.put("SellingCategoryId", Spices_Details_Fragment.SellCategort_id);
                        System.out.println("categoryid" + Spices_Details_Fragment.SellCategort_id);
                        params.put("MinPrice", Spices_Details_Fragment.min_price.getText().toString());
                        params.put("MaxPrice", Spices_Details_Fragment.max_price.getText().toString());
                        params.put("OrganicProduceId",Organicproduce_Id );
                        params.put("PackagingTypeId", PackagingtypeId);
                        params.put("PackagingSizeId", PackagingsizeId);


                    } else {


                    }
                }

              /*  if (getArguments() .getString("navg_from5")!= null) {

                if (getArguments().getString("navg_from5").equals("true")) {

                    params.put("UserId", sessionManager.getRegId("userId"));
                    params.put("CreatedBy", sessionManager.getRegId("userId"));
                    params.put("SellingDetailsId", selling_detailsid);
                    params.put("SellingListMasterId", selling_list_Id1);
                    params.put("SellingVarietyId", Variety_id);
                    params.put("SellingQualityId", Quality_id);
                    params.put("SellingQuantity", min_quantity.getText().toString());
                    params.put("UnitOfPriceId", String.valueOf(QuantityPrice_Adapter2.quantityprice_id));
                    params.put("Price", "0");
                    params.put("SellingCategoryId", Spices_Category_Adapter.selling_category_id);
                    params.put("MinPrice", min_price.getText().toString());
                    params.put("MaxPrice", max_price.getText().toString());


                    System.out.println("bbb" + selling_list_Id1);


                    System.out.println("iddssssssaannbcvbnm" + params);

                }
            }*/



                System.out.println("idsssss"+Spices_Category_Adapter.selling_category_id);
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

           /* if (resizedBitmap != null && !resizedBitmap.isRecycled()) {
                resizedBitmap.recycle();
                resizedBitmap = null;
            }*/
            bm1.recycle();
            return resizedBitmap;
        }
    }
}

