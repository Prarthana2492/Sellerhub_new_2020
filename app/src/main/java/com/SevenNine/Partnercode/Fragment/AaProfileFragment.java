package com.SevenNine.Partnercode.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SevenNine.Partnercode.Activity.Status_bar_change_singleton;
import com.SevenNine.Partnercode.G_Vision_Controller;
import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;
import com.SevenNine.Partnercode.Urls;
import com.SevenNine.Partnercode.Volly_class.Crop_Post;
import com.SevenNine.Partnercode.Volly_class.VoleyJsonObjectCallback;
import com.SevenNine.Partnercode.volleypost.VolleyMultipartRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.SevenNine.Partnercode.Fragment.Shop_Camera_Fragment.selectedImage;
import static com.SevenNine.Partnercode.Fragment.Shop_Camera_Preview_Fragment.EMOJI_FILTER;
import static com.android.volley.VolleyLog.TAG;


public class AaProfileFragment extends Fragment {


    BottomSheetDialog mBottomSheetDialog;
    View sheetView;
    public  static  CircleImageView prod_img;
    Fragment selectedFragment;
    EditText abt_text,userInput;
    LinearLayout backfeed,acc_info_lay,linearLayout,save_lay;
    TextView notificatn,change_language,your_addresss,acc_info1,refer_ern,feedbk,help_1,abt_frmpe,polic_1,logot,setting_tittle,aboutText;
    SessionManager sessionManager;
    public static EditText profile_phone,profname;
    JSONObject lngObject;
    Bitmap bitmap;
    G_Vision_Controller g_vision_controller;
    public  static String ProfilePhone;

    public static AaProfileFragment newInstance() {
        AaProfileFragment fragment = new AaProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_a_profile_layout, container, false);
        Status_bar_change_singleton.getInstance().color_change(getActivity());

        backfeed=view.findViewById(R.id.back_feed);
        acc_info_lay=view.findViewById(R.id.acc_info_lay);
        linearLayout=view.findViewById(R.id.main_layout);
        profname = view.findViewById(R.id.prof_name);
        profile_phone = view.findViewById(R.id.phone_text);
        save_lay = view.findViewById(R.id.save_lay);

        prod_img = view.findViewById(R.id.prod_imgg);
        sessionManager = new SessionManager(getActivity());
        setupUI(linearLayout);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        backfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (getArguments().getString("status").equals("HOME_IMG")){

                    selectedFragment = HomeMenuFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    // transaction.addToBackStack("looking");
                    transaction.commit();

                }else*/



       /* FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack ("aaAccount", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        */
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //    getFragmentManager().popBackStack("home_menu", android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);

                   /* if (getArguments().getString("status").equals("HOME_IMG")){
                        selectedFragment = HomeMenuFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        // transaction.addToBackStack("looking");
                        transaction.commit();
                    }else */


                    return true;
                }
                return false;
            }
        });

        linearLayout.setBackgroundColor(Color.parseColor("#f5f5f5"));
        //  mBottomSheetBehavior6.setState(BottomSheetBehavior.STATE_COLLAPSED);
        prod_img.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // to go to gallery
                startActivityForResult(i, 100); // on activity method will execute*/

            }
        });

        save_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(bitmap);
            }
        });


        try{
            JSONObject jsonObject = new JSONObject();
            JSONObject post_object = new JSONObject();

            jsonObject.put("Id",sessionManager.getRegId("userId"));
            post_object.put("objUser",jsonObject);


            Crop_Post.crop_posting(getActivity(), Urls.Get_Profile_Details1, post_object, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("ggpgpgpg" + result);

                    try{

                        JSONObject jsonObject1 = result.getJSONObject("user");
                        String ProfileName1 = jsonObject1.getString("UserName");
                        System.out.println("11111" + jsonObject1.getString("FullName"));
                        String ProfilePhone = jsonObject1.getString("PhoneNo");
                        String ProfileEmail = jsonObject1.getString("EmailId");
                        String ProfileImage = jsonObject1.getString("ProfilePic");
                        System.out.println("11111" + ProfileName1);



                        //  name.setText(ProfileName1);
                        profile_phone.setText(ProfilePhone);
                        profname.setText(ProfileName1);

                        profname.setFilters(new InputFilter[]{EMOJI_FILTER});
                        profile_phone.setFilters(new InputFilter[]{EMOJI_FILTER});
                        //profile_mail.setFilters(new InputFilter[]{EMOJI_FILTER});


                        if (!(ProfileImage.equals(""))){
                            Glide.with(getActivity()).load(ProfileImage)

                                    .thumbnail(0.5f)
                                    //  .crossFade()
                                    .error(R.drawable.avatarmale)
                                    .into(prod_img);
                        }else{
                            try {

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("UserId", sessionManager.getRegId("userId"));


                                Crop_Post.crop_posting(getActivity(), Urls.GetCImagelist, jsonObject, new VoleyJsonObjectCallback() {
                                    @Override
                                    public void onSuccessResponse(JSONObject result) {
                                        System.out.println("dhfjfjd" + result);


                                        try {

                                            JSONArray imagelist_array = result.getJSONArray("captureImagelist");

                                            for (int i = 0; i < imagelist_array.length(); i++) {


                                                JSONObject jsonObject1 = imagelist_array.getJSONObject(i);
                                                String image_view = jsonObject1.getString("Image1");



                                                Glide.with(getActivity()).load(image_view)
                                                        .thumbnail(0.5f)
                                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                        .error(R.drawable.avatarmale)
                                                        .into(prod_img);
                                            }




                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                       /* Glide.with(getActivity()).load(ProfileImage)

                                .thumbnail(0.5f)
                                //  .crossFade()
                                .error(R.drawable.avatarmale)
                                .into(prod_img);*/


                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }







   /* private void save_description() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.Update_Profile_Details,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int duration = 1000;
                        Snackbar snackbar = Snackbar
                                .make(linearLayout, "Profile Details Updated Successfully", duration);
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
                        mBottomSheetDialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("status","HOME_IMG");
                        selectedFragment = AaProfileFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        selectedFragment.setArguments(bundle);
                        transaction.commit();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("UserId",sessionManager.getRegId("userId"));
                params.put("FullName",profname.getText().toString());
                params.put("PhoneNo",sessionManager.getRegId("phone"));
                params.put("About",abt_text.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }
*/


    public static InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        if (dstart == 0)
                            return "";
                    }
                }
                return null;
          /*  char c = source.charAt(index);
            if (isCharAllowed(c))
                sb.append(c);
            else
                keepOriginal = false;*/
            }

            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };


    public static InputFilter EMOJI_FILTER1 = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        if (dstart == 0)
                            return "";
                    }
                }

                return null;
          /*  char c = source.charAt(index);
            if (isCharAllowed(c))
                sb.append(c);
            else
                keepOriginal = false;*/
            }

            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };




    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            final InputStream imageStream;
            try {
                imageStream = getActivity().getContentResolver().openInputStream(imageUri);

                bitmap = BitmapFactory.decodeStream(imageStream);
                System.out.println("bittttttttt"+bitmap);
                prod_img.setImageBitmap(bitmap);
                uploadImage(bitmap);
                //uploadImage(getResizedBitmap(bitmap,100,100));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }else {
            //Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadImage(final Bitmap bitmap){
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.Update_Profile_Details,
                new Response.Listener<NetworkResponse>(){
                    @Override
                    public void onResponse(NetworkResponse response) {
                        //Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        //  String resultResponse = new String(response.data);
                        Log.e(TAG,"afaeftagsbillvaluegf"+response);
                        /*if (update!=null){
                          //  mBottomSheetDialog.dismiss();
                            name.setText(name_str.length()+"7");
                            System.out.println("pppphhhoonnee_numbrr"+name_str+phone_str);
                            phone_no.setText(phone_str.length()+"7");
                        }*/
                        //  sessionManager.save_name(name.getText().toString(),phone_no.getText().toString(),"");



                        //Toast.makeText(getActivity(),"uploaded", Toast.LENGTH_SHORT).show();
                       /* selectedFragment = SettingFragment.newInstance();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_layout,selectedFragment);
                        ft.commit();*/

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            /*
             *pass files using below method
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId", sessionManager.getRegId("userId"));
                 // params.put("UserName", profname.getText().toString());
                params.put("PhoneNo", profile_phone.getText().toString());
                System.out.println("parametrsss" + params);

               /* if (update != null) {

                    params.put("PhoneNo", phone_str);

                    System.out.println("parametrsss1111" + params);
                } else {

                    params.put("PhoneNo", phone_no.getText().toString());

                    System.out.println("parametrsss" + params);
                    // return params;
                }*/
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                // params.put("File", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                Log.e(TAG,"Imhereafaeftagsparams Imhereafaeftagsparams "+bitmap);

                if (bitmap!=null){
                    params.put("ProfilePic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                  //  sessionManager.savebitmap(bitmap);

                }else {
                    params.put("ProfilePic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                }
                Log.e(TAG,"Imhereafaeftagsparams "+params);
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

    public void setupUI(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        /*InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);*/

        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();

        if (focusedView != null) {

            try {
                assert inputManager != null;
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (AssertionError e) {
                e.printStackTrace();
            }
        }
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
