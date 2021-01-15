package com.FarmPe.SellerHub.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Shop_LocationEdit_Fragment extends Fragment {
    Fragment selectedFragment;
    LinearLayout Continue, back_feed, location_edit, camera_edit, location;
    ImageView select_location, capture_image,image_athcc;
    SessionManager sessionManager;
    JSONArray get_cimage_array, get_loctn_array;
    String image, lat, lang, capt_location,loc_captd,sect_loc,camera_txt;
    public static String id, loctn_id;
    TextView captured_location, photo_txt, continue_txt, lcn_edt_txt, camera_edit_text,toolbar_title,loc_heading,photo_heading,img_txt;
    JSONObject lngObject;


    public static Shop_LocationEdit_Fragment newInstance() {
        Shop_LocationEdit_Fragment fragment = new Shop_LocationEdit_Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selectcurrentlocation2, container, false);

        //  Status_bar_change_singleton.getInstance().color_change(getActivity());

        Continue = view.findViewById(R.id.conti);
        continue_txt = view.findViewById(R.id.apply_loan);
        back_feed = view.findViewById(R.id.back_feed);
        select_location = view.findViewById(R.id.select_location);
        capture_image = view.findViewById(R.id.image_acc);
        location_edit = view.findViewById(R.id.location_edit);
        camera_edit = view.findViewById(R.id.camera_edit);
        captured_location = view.findViewById(R.id.capt_lcn);
        location = view.findViewById(R.id.lnr_lctn);
        photo_txt = view.findViewById(R.id.img_txt);
        camera_edit_text = view.findViewById(R.id.c_edit_txt);
        lcn_edt_txt = view.findViewById(R.id.l_edit_txt);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        loc_heading = view.findViewById(R.id.heading);
        photo_heading = view.findViewById(R.id.heading2);
        image_athcc = view.findViewById(R.id.image_athcc);
        img_txt = view.findViewById(R.id.img_txt);


        sessionManager = new SessionManager(getActivity());

        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));

            continue_txt.setText(lngObject.getString("PROCEED").replace("\n",""));
            camera_edit_text.setText(lngObject.getString("Edit"));
            lcn_edt_txt.setText(lngObject.getString("Edit"));
            toolbar_title.setText(lngObject.getString("LocationandImage"));
            loc_heading.setText(lngObject.getString("SelectShopLocation"));
            photo_heading.setText(lngObject.getString("CaptureFirmShopPhoto"));
            loc_captd=(lngObject.getString("LocationCaptured"));
            sect_loc =(lngObject.getString("SelectLocation"));
            camera_txt =(lngObject.getString("CapturePhoto"));
            // camera_txt.setText(lngObject.getString("DidntreceiveOTP"));
            // enter_otp_here.setText(lngObject.getString("EnterOTPhere"));
            //  didnt_receive_otp.setText(lngObject.getString("Enteryourphonenumbertogetstarted"));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getArguments().getString("status").equals("firmdetails")) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("firmdetails1", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                } else {
                    selectedFragment = Shop_Location_Fragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.addToBackStack("shop_locatn");
                    transaction.commit();

                }

            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    if (getArguments().getString("status").equals("firmdetails")) {

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("firmdetails1", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    } else {
                        selectedFragment = Shop_Location_Fragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.addToBackStack("shop_locatn");
                        transaction.commit();

                    }


                    return true;
                }
                return false;
            }
        });

        capture_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Bundle bundle = new Bundle();
                bundle.putString("status","shop_camera");*/
//                selectedFragment = Shop_Camera_Fragment.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout1, selectedFragment);
//                //selectedFragment.setArguments(bundle);
//                transaction.addToBackStack("shop_cameraa");
//                transaction.commit();


            }
        });


        camera_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Bundle bundle = new Bundle();*/
                //  bundle.putString("status","shopl_edit");
//                selectedFragment = Shop_Camera_Fragment.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout1, selectedFragment);
//                // selectedFragment.setArguments(bundle);
//                transaction.addToBackStack("shop_cameraa");
//                transaction.commit();

            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Edit_Fragment", "curr_loc_edit");
                // bundle.putString("loctn_id",loctn_id);
                System.out.println("aaaaaaaaaid" + loctn_id);
                selectedFragment = Shop_Current_Location_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.addToBackStack("map_locatn");
                transaction.commit();

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
                            String image_id = jsonObject1.getString("CImageId");
                            String image_view = jsonObject1.getString("Image1");

                            capture_image.setMinimumHeight(0);
                            capture_image.setMinimumWidth(0);
                            capture_image.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
                            capture_image.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;
                            capture_image.setPadding(40,0,40,0);

                            Glide.with(getActivity()).load(image_view)
                                    .thumbnail(0.5f)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_gallery__default)
                                    .into(capture_image);
                        }

                        if (get_cimage_array.length() == 0) {
                            camera_edit.setVisibility(View.GONE);
                            photo_txt.setVisibility(View.VISIBLE);
                            photo_txt.setText(camera_txt);

                        } else {
                            camera_edit.setVisibility(View.VISIBLE);
                            image_athcc.setVisibility(View.GONE);
                            img_txt.setVisibility(View.GONE);

                        }

                        if (get_loctn_array.length() == 0 || get_cimage_array.length() == 0) {
                            Continue.setBackgroundResource(R.drawable.grey_curved_border);
                        } else {
                            Continue.setBackgroundResource(R.drawable.border_filled_red_not_curved);
                            Continue.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertMessage();
                                }
                            });
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


////////////// get Location details



      /*  try{


            JSONObject jsonObject = new JSONObject();
           // jsonObject.put("UserId", "1");
           jsonObject.put("UserId", sessionManager.getRegId("userId"));


            Crop_Post.crop_posting(getActivity(), Urls.GetCLocationList, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("GetCLocationListttttttttt"+result);

                    try{
                        get_loctn_array = result.getJSONArray("clocationList");

                        for(int i=0;i<get_loctn_array.length();i++) {

                            JSONObject jsonObject1 = get_loctn_array.getJSONObject(i);
                            capt_location = jsonObject1.getString("CapturedLocation");
                            loctn_id = jsonObject1.getString("CLocationId");

                            //   captured_location.setText(capt_location);

                        }

                            if(get_loctn_array.length()==0){
                                location_edit.setVisibility(View.GONE);

                            }else{
                                location_edit.setVisibility(View.VISIBLE);
                            }

                            if(get_loctn_array.length() == 0 || get_cimage_array.length()== 0){

                                Continue.setBackgroundResource(R.drawable.grey_curved_border);
                            }else {

                                Continue.setBackgroundResource(R.drawable.border_filled_red_not_curved);
                                Continue.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AlertMessage();
                                    }
                                });



                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }*/


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId", sessionManager.getRegId("userId"));

            Crop_Post.crop_posting(getActivity(), Urls.GetCLocationList, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("dhfjfjd" + result);
                    try {
                        get_loctn_array = result.getJSONArray("clocationList");
                        for (int i = 0; i < get_loctn_array.length(); i++) {
                            JSONObject jsonObject1 = get_loctn_array.getJSONObject(i);

                            loctn_id = jsonObject1.getString("CLocationId");
                            String location_lat = jsonObject1.getString("Latitude");
                            String location_long = jsonObject1.getString("Longitude");
                            String location_captured_image = jsonObject1.getString("CapturedLocation");

                            sessionManager.savelocation(location_captured_image);



                        }
                        if (get_loctn_array.length() == 0) {
                            location_edit.setVisibility(View.GONE);
                            captured_location.setText("Select Location");

                        } else {
                            location_edit.setVisibility(View.VISIBLE);
                            captured_location.setText("Location Captured");
                        }

                        if (get_loctn_array.length() == 0 || get_cimage_array.length() == 0) {

                            Continue.setBackgroundResource(R.drawable.grey_curved_border);
                        } else {

                            Continue.setBackgroundResource(R.drawable.border_filled_red_not_curved);
                            Continue.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertMessage();
                                }
                            });
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
//
        } catch (Exception e) {
            e.printStackTrace();
        }



       /* if((get_cimage_array.length()>0)&&(get_loctn_array.length()>0)){
            Continue.setBackgroundResource(R.drawable.border_filled_red_not_curved);

        }else{
            Continue.setBackgroundResource(R.drawable.grey_curved_border);
        }


*/


        location_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle bundle = new Bundle();
                bundle.putString("Edit_Fragment", "curr_loc_edit");
                bundle.putString("loctn_id", loctn_id);
                System.out.println("aaaaaaaaaid" + loctn_id);
                selectedFragment = Shop_Current_Location_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.addToBackStack("map_locatn");
                transaction.commit();

            }
        });


        return view;
    }

    private void AlertMessage() { // alert dialog box

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.verification_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView cancel, ok,text_desc;
        cancel = dialog.findViewById(R.id.cancel_btn);
        ok = dialog.findViewById(R.id.ok_btn);
        text_desc = dialog.findViewById(R.id.text_desc);



      /*  try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));

            ok.setText(lngObject.getString("OK").replace("\n",""));
            text_desc.setText(lngObject.getString("DoyouwanttosubmitthedetailsforVerification"));
            cancel.setText(lngObject.getString("CANCEL"));



        } catch (JSONException e) {
            e.printStackTrace();
        }

*/





        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (Store.store!=null){
                    selectedFragment = Store.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.addToBackStack("add_edit");
                    transaction.commit();
                    dialog.dismiss();
               /* }else{
                    selectedFragment = VerificationFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.addToBackStack("add_edit");
                    transaction.commit();
                    dialog.dismiss();
                }*/

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


       /* final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);

        alertDialogBuilder.setMessage("Do You want to submit the details for verification?");
        //alertDialogBuilder.setMessage(Html.fromHtml("<font size = '18dp'>Do You want to submit the details for verification?</font>"));
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                selectedFragment = VerificationFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("add_edit");
                transaction.commit();



            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.show();*/

}