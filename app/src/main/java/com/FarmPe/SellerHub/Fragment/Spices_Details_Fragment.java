package com.FarmPe.SellerHub.Fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.FarmPe.SellerHub.Adapter.QuantityPrice_Adapter2;
import com.FarmPe.SellerHub.Adapter.Quantity_Adapter2;
import com.FarmPe.SellerHub.Adapter.Spices_Category_Adapter;
import com.FarmPe.SellerHub.Adapter.Variety_Adapter2;
import com.FarmPe.SellerHub.Bean.SelectLanguageBean;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class Spices_Details_Fragment extends Fragment {

    TextView norecords,toolbar_title,sell_name,a_grade,b_grade,c_grade,hybrid,jawari,desi;
    LinearLayout back_feed, continuebtn, main_layout, quality_layout;
    Fragment selectedFragment;
    public static String imageUri;
    JSONObject lngObject;
    public static EditText variety, quality, quantity, unit_of_measurement, price;
    SessionManager sessionManager;
    JSONArray jsonArray;
    public static Dialog dialog;
    public static String navigation, Sell_Image,SellDetails_id,Sell_MlistId,SellCategort_id,sellinglist_id,selllistname,selling_list_Id1,sell_editid,selling_detailsid;
    RecyclerView recyclerView;
   public static EditText min_quantity,min_price,max_price;
    PopupMenu quantity_popup,unit_of_price_pop_up,unit_of_price_pop_up1,quantity_popup1;
    String Quality_id,Variety_id;
    EditText search;
    int mrp_int,amount_int;
    public static DrawerLayout drawer;
    public static String search_status="status",SellingListMasterId;
    // ArrayList<String> newOrderBeansList2 = new ArrayList<String>();



    public static ArrayList<SelectLanguageBean> newOrderBeansList = new ArrayList<>();
    public static ArrayList<SelectLanguageBean> newOrderBeansList1 = new ArrayList<>();
    public static ArrayList<SelectLanguageBean> newOrderBeansList2 = new ArrayList<>();
    public static ArrayList<SelectLanguageBean> newOrderBeansList3 = new ArrayList<>();
    private List<SelectLanguageBean> searchresultAraaylist = new ArrayList<>();
    public static Variety_Adapter2 livestock_types_adapter;
   // public static Quality_Adapter2 livestock_types_adapter1;
    public static Quantity_Adapter2 livestock_types_adapter2;
    public static QuantityPrice_Adapter2 livestock_types_adapter3;
    SelectLanguageBean selectLanguageBean;

    public static Spices_Details_Fragment newInstance() {
        Spices_Details_Fragment fragment = new Spices_Details_Fragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.select_your_region_sell_details, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        back_feed = view.findViewById(R.id.back_feed);
        main_layout = view.findViewById(R.id.linearLayout);
        variety = view.findViewById(R.id.variety);
        quality = view.findViewById(R.id.quality);
        quality_layout = view.findViewById(R.id.quality_lay);
        quantity = view.findViewById(R.id.quantity);
        unit_of_measurement = view.findViewById(R.id.select_uom);
        price = view.findViewById(R.id.price);
        continuebtn = view.findViewById(R.id.continuebtn);
        sell_name = view.findViewById(R.id.sellnamee);

        a_grade = view.findViewById(R.id.agrade_btn);
        b_grade = view.findViewById(R.id.bgrade_btn);
        c_grade = view.findViewById(R.id.cgrade_btn);
        min_price = view.findViewById(R.id.min_priceee);
        max_price = view.findViewById(R.id.max_priceee);
        min_quantity = view.findViewById(R.id.min_quantity);
        hybrid = view.findViewById(R.id.hybrid_btn);
        jawari = view.findViewById(R.id.jwari_btn);
        desi = view.findViewById(R.id.desi_btn);
        search = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recycler_view);
        norecords = view.findViewById(R.id.norecords);
        norecords.setVisibility(View.GONE);
        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout_op);



        sessionManager = new SessionManager(getActivity());

        String id = (getArguments().getString("sellname"));
        imageUri = id;

        Bundle bundle= getArguments();
        if (bundle!=null){
            SellDetails_id = (getArguments().getString("Edit_Id"));
            Sell_MlistId = getArguments().getString("EditMaster_Id");
            SellCategort_id = getArguments().getString("EditCategory_Id");
            Sell_Image = getArguments().getString("EditImage");

            System.out.println("Image111111"+Sell_Image);
        }
        if (getArguments() .getString("navg_from")!= null) {
            navigation = String.valueOf(getArguments().getString("navg_from").equals("invtry_details"));
        }
       /* sell_editid=What_Areu_Selling_Fragment.sellingdetailsid;

        if(sell_editid!= null){
            selling_detailsid=sell_editid;
        }else {
            selling_detailsid="0";
        }

*/
       /* if(getArguments().getString("status").equals("home_search")){





        }*/




       setupUI(drawer);

        sellinglist_id = getArguments().getString("sellinglistM_id");

        if(sellinglist_id!= null){
            selling_list_Id1=sellinglist_id;
        }else {
            selling_list_Id1="0";
        }

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sorting(s.toString());
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        unit_of_measurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                drawer.openDrawer(GravityCompat.END);
                search_status="state";
                search.setText("");


                newOrderBeansList.clear();
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                livestock_types_adapter3 = new QuantityPrice_Adapter2(getActivity(),newOrderBeansList);

                recyclerView.setAdapter(livestock_types_adapter3);

                PrepareUOM();


            }
        });







        a_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Quality_id = "1";
                a_grade.setTextColor(Color.parseColor("#FFFFFF"));
                b_grade.setTextColor(Color.parseColor("#000000"));
                c_grade.setTextColor(Color.parseColor("#000000"));



                a_grade.setBackgroundResource(R.drawable.black_border_blue_filled);
                b_grade.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                c_grade.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });


        b_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Quality_id = "2";
                b_grade.setTextColor(Color.parseColor("#FFFFFF"));
                a_grade.setTextColor(Color.parseColor("#000000"));
                c_grade.setTextColor(Color.parseColor("#000000"));



                b_grade.setBackgroundResource(R.drawable.black_border_blue_filled);
                a_grade.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                c_grade.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });




        c_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Quality_id = "3";
                c_grade.setTextColor(Color.parseColor("#FFFFFF"));
                b_grade.setTextColor(Color.parseColor("#000000"));
                a_grade.setTextColor(Color.parseColor("#000000"));



                c_grade.setBackgroundResource(R.drawable.black_border_blue_filled);
                b_grade.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                a_grade.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });



        hybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Variety_id = "1";
                hybrid.setTextColor(Color.parseColor("#FFFFFF"));
                jawari.setTextColor(Color.parseColor("#000000"));
                desi.setTextColor(Color.parseColor("#000000"));



                hybrid.setBackgroundResource(R.drawable.black_border_blue_filled);
                jawari.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                desi.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });


        jawari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Variety_id = "2";
                jawari.setTextColor(Color.parseColor("#FFFFFF"));
                hybrid.setTextColor(Color.parseColor("#000000"));
                desi.setTextColor(Color.parseColor("#000000"));



                jawari.setBackgroundResource(R.drawable.black_border_blue_filled);
                hybrid.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                desi.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });


        desi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Variety_id = "3";
                desi.setTextColor(Color.parseColor("#FFFFFF"));
                jawari.setTextColor(Color.parseColor("#000000"));
                hybrid.setTextColor(Color.parseColor("#000000"));



                desi.setBackgroundResource(R.drawable.black_border_blue_filled);
                jawari.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                hybrid.setBackgroundResource(R.drawable.black_bordr_white_filled1);



            }
        });




        /*quality_layout.setVisibility(View.GONE);
        quality.setVisibility(View.GONE);*/

       /* DecimalFormat formatter = new DecimalFormat("#,###,###");
        String yourFormattedString = formatter.format(price);
        price.setText(yourFormattedString);*/
       // afterTextChanged(price);

        //quantity_popup = new PopupMenu(getActivity(), quantity,Gravity.RIGHT);
        unit_of_price_pop_up = new PopupMenu(getActivity(), unit_of_measurement,Gravity.RIGHT);

        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.dark_green));





      //  selllistname = getArguments().getString("sellinglistM_name");

       // sell_name.setText(selllistname);

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("camerasell", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("camerasell", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }

                return false;
            }
        });


        max_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {

                max_price.removeTextChangedListener(this);
                try {
                    String originalString = editable.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,##,##,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    max_price.setText(formattedString);
                    max_price.setSelection(max_price.getText().length());
                    mrp_int=Integer.parseInt(max_price.getText().toString());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                max_price.addTextChangedListener(this);
            }
        });

        min_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                min_price.removeTextChangedListener(this);
                try {
                    String originalString = editable.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,##,##,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    min_price.setText(formattedString);
                    min_price.setSelection(min_price.getText().length());
                    amount_int=Integer.parseInt(min_price.getText().toString());
                    mrp_int=Integer.parseInt(max_price.getText().toString());

                    if (amount_int>mrp_int){
                        Toast toast = Toast.makeText(getActivity(), "Min Price should be less than Max Price", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                        toast.show();
                        min_price.getText().clear();
                    }

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                min_price.addTextChangedListener(this);
            }
        });










        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Quality_id == null) {


                    Toast toast = Toast.makeText(getActivity(), "Please select quality", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                }else  if(Variety_id == null) {


                    Toast toast = Toast.makeText(getActivity(), "Please select variety", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                }else  if(min_quantity.getText().toString().equals("")) {


                    Toast toast = Toast.makeText(getActivity(), "Please enter min quantity", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                }else  if(min_price.getText().toString().equals("")) {


                    Toast toast = Toast.makeText(getActivity(), "Please enter min price", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                }else  if(max_price.getText().toString().equals("")) {


                    Toast toast = Toast.makeText(getActivity(), "Please enter max price", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                }else  if(unit_of_measurement.getText().toString().equals("")) {


                    Toast toast = Toast.makeText(getActivity(), "Please select UOM", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                }else {

                        nextpage();
                   // uploadImage(getResizedBitmap(Spices_CameraFragment.selectedImage, 100, 100));
                }



            }
        });


       /* quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                try{

                    JSONObject jsonObject = new JSONObject();

                    Crop_Post.crop_posting(getActivity(), Urls.GetSellingQuantityList, jsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {

                            System.out.println("GetCropQuantityListeeeeeee"+result);


                            try{

                                jsonArray = result.getJSONArray("SellingQuantityList");
                                newOrderBeansList2.clear();

                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                                    quantity_popup.getMenu().add(jsonObject1.getString("SellingQuantity"));
                                    quantity_popup.getMenu().add(jsonObject1.getString("SellingQuantityId"));

                                }

                                quantity_popup.show();



                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }



                quantity_popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        String quantity_string = item.getTitle().toString().trim();
                        quantity.setText(quantity_string);



                        return true;
                    }
                });


            }
        });



        unit_of_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    newOrderBeansList3.clear();
                    JSONObject jsonObject = new JSONObject();


                    Crop_Post.crop_posting(getActivity(), Urls.GetUnitOfPriceList, jsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {

                            System.out.println("GetCropQuantityListeeeeeee"+result);


                            try{

                                jsonArray = result.getJSONArray("SellingUnitOfPrice");

                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);



                                    unit_of_price_pop_up.getMenu().add(jsonObject1.getString("UnitOfPrice"));
                                    unit_of_price_pop_up.getMenu().add(jsonObject1.getString("UnitOfPriceId"));


                                }

                                unit_of_price_pop_up.show();


                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });


                }catch (Exception e){
                    e.printStackTrace();
                }


                unit_of_price_pop_up.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        String unit_of_price_string = item.getTitle().toString().trim();
                        unit_of_price.setText(unit_of_price_string);



                        return true;
                    }
                });

            }
        });

*/



//    }else
//
//    {
//        Toast.makeText(getActivity(), "No Internet Access!", Toast.LENGTH_LONG).show();
//
//    }



/*

        quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.quality_recy);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                recyclerView=dialog.findViewById(R.id.quality_recyy);
                TextView popup_heading;
                ImageView cross;
                popup_heading=dialog.findViewById(R.id.popup_heading);
                cross=dialog.findViewById(R.id.cross);
                popup_heading.setText("Sell Quantity");
                newOrderBeansList2.clear();
                // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
                GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(mLayoutManager_farm);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                livestock_types_adapter2=new Quantity_Adapter2(getActivity(),newOrderBeansList2);
                recyclerView.setAdapter(livestock_types_adapter2);

                try{

                    newOrderBeansList2.clear();
                    JSONObject jsonObject = new JSONObject();


                    Crop_Post.crop_posting(getActivity(), Urls.GetSellingQuantityList, jsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {

                            System.out.println("GetCropQuantityListeeeeeee"+result);


                            try{

                                jsonArray = result.getJSONArray("SellingQuantityList");

                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    selectLanguageBean = new SelectLanguageBean(jsonObject1.getString("SellingQuantity"),jsonObject1.getInt("SellingQuantityId"),"");

                                    newOrderBeansList2.add(selectLanguageBean);
                                }
                                livestock_types_adapter2.notifyDataSetChanged();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }

                cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
*/

       /* unit_of_measurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.quality_recy);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                recyclerView=dialog.findViewById(R.id.quality_recyy);
                TextView popup_heading;
                ImageView cross;
                popup_heading=dialog.findViewById(R.id.popup_heading);
                cross=dialog.findViewById(R.id.cross);
                popup_heading.setText("Unit of Measurement");
                newOrderBeansList3.clear();
                // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
                GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(mLayoutManager_farm);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                livestock_types_adapter3=new QuantityPrice_Adapter2(getActivity(),newOrderBeansList3);
                recyclerView.setAdapter(livestock_types_adapter3);

                try{

                    newOrderBeansList3.clear();
                    JSONObject jsonObject = new JSONObject();


                    Crop_Post.crop_posting(getActivity(), Urls.GetUnitOfPriceList, jsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {

                            System.out.println("GetCropQuantityListeeeeeee"+result);


                            try{

                                jsonArray = result.getJSONArray("SellingUnitOfPrice");

                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    selectLanguageBean = new SelectLanguageBean(jsonObject1.getString("UnitOfPrice"),jsonObject1.getInt("UnitOfPriceId"),"");

                                    newOrderBeansList3.add(selectLanguageBean);
                                }
                                livestock_types_adapter3.notifyDataSetChanged();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }

                cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
*/
       /* variety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.quality_recy);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                recyclerView=dialog.findViewById(R.id.quality_recyy);
                TextView popup_heading;
                ImageView cross;
                popup_heading=dialog.findViewById(R.id.popup_heading);
                cross=dialog.findViewById(R.id.cross);
                popup_heading.setText("Variety");
                newOrderBeansList.clear();
                // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
                GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(mLayoutManager_farm);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                livestock_types_adapter=new Variety_Adapter2(getActivity(),newOrderBeansList);
                recyclerView.setAdapter(livestock_types_adapter);

                try{

                    newOrderBeansList.clear();
                    JSONObject jsonObject = new JSONObject();


                    Crop_Post.crop_posting(getActivity(), Urls.GetSellingVarietyList, jsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {

                            System.out.println("GetCropQuantityListeeeeeee"+result);


                            try{

                                jsonArray = result.getJSONArray("SpiceVarietyList");

                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    selectLanguageBean = new SelectLanguageBean(jsonObject1.getString("SellingVariety"),jsonObject1.getInt("SellingVarietyId"),"");

                                    newOrderBeansList.add(selectLanguageBean);
                                }
                                livestock_types_adapter.notifyDataSetChanged();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }

                cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
*/








/*

        variety.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(variety,quality,quantity,unit_of_price,price);
                return false;
            }
        });

        quality.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(quality,variety,quantity,unit_of_price,price);
                return false;
            }
        });
*/

        /*quantity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(quantity,unit_of_price,price);
                return false;
            }
        });

        unit_of_price.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                linear_layout_selection(unit_of_price,quantity,price);
                return false;
            }
        });

        price.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(price,quantity,unit_of_price);
                return false;
            }
        });
*//**/

        return view;



    }

    private void nextpage() {

        Bundle bundle= new Bundle();
        bundle.putString("NAVGI",navigation);
        bundle.putString("selling_Mlist_id", selling_list_Id1);
        bundle.putString("variety_id", Variety_id);
        bundle.putString("quality_id", Quality_id);
        bundle.putString("Image", Sell_Image);

        selectedFragment = Cropdetails_Second_Fragment.newInstance();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout1, selectedFragment);
        selectedFragment.setArguments(bundle);
        transaction.addToBackStack("spicesdetails");
        transaction.commit();
    }

    private void PrepareUOM() {


        recyclerView.setVisibility(View.VISIBLE);

        try{

            newOrderBeansList.clear();
            JSONObject jsonObject = new JSONObject();


            Crop_Post.crop_posting(getActivity(), Urls.GetUnitOfPriceList, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("GetCropQuantityListeeeeeee"+result);


                    try{

                        jsonArray = result.getJSONArray("SellingUnitOfPrice");
                        if (jsonArray != null && jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                selectLanguageBean = new SelectLanguageBean(jsonObject1.getString("UnitOfPrice"), jsonObject1.getInt("UnitOfPriceId"), "");

                                newOrderBeansList.add(selectLanguageBean);
                            }
                            sorting(newOrderBeansList);
                            livestock_types_adapter3.notifyDataSetChanged();
                        }else{
                            recyclerView.setVisibility(View.GONE);
                            norecords.setVisibility(View.VISIBLE);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

      /*
                        if (state_array != null && state_array.length() > 0) {
                            for (int i = 0; i < state_array.length(); i++) {
                                JSONObject jsonObject1 = state_array.getJSONObject(i);

                                stateBean = new StateBean(jsonObject1.getString("State").trim().replace("&amp;", "&"), jsonObject1.getString("StateId"));

                                stateBeanList.add(stateBean);
                            }
                            sorting(stateBeanList);

                            stateApdater.notifyDataSetChanged();
                            // grade_dialog.show();

                        }else{
                            recyclerView.setVisibility(View.GONE);
                            norecords.setVisibility(View.VISIBLE);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
    public void sorting(List<SelectLanguageBean> arrayList){

        Collections.sort(arrayList, new Comparator() {
            @Override
            public int compare(Object state_name1, Object state_name2) {
                //use instanceof to verify the references are indeed of the type in question
                return ((SelectLanguageBean)state_name1).getVendor()
                        .compareTo(((SelectLanguageBean)state_name2).getVendor());
            }
        });
    }

    private void sorting(String filter_text) {

        final String text = filter_text.toLowerCase();



        if (search_status.equals("state")) {
            searchresultAraaylist.clear();
            for (int i = 0; i < newOrderBeansList.size(); i++) {

                if (newOrderBeansList.get(i).getVendor().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(newOrderBeansList.get(i));

                }
            }
            if (searchresultAraaylist.size() == 0) {
                recyclerView.setVisibility(View.GONE);
                norecords.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                norecords.setVisibility(View.GONE);
                livestock_types_adapter3 = new QuantityPrice_Adapter2( getActivity(),searchresultAraaylist);
                recyclerView.setAdapter(livestock_types_adapter3);
            }

        }






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

                        Toast toast = Toast.makeText(getActivity(),"Inventory added successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                        toast.show();

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

                        selectedFragment = Cropdetails_Second_Fragment.newInstance();
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
                if (bitmap!=null) {
                    params.put("SellingImage", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                }
                Log.e(TAG,"Im here " + params);
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId", sessionManager.getRegId("userId"));
                params.put("CreatedBy", sessionManager.getRegId("userId"));
                params.put("SellingDetailsId", "0");
                System.out.println("addsekkdetailssssid" + selling_detailsid);
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


                // params.put("SellingDetailsId",selling_detailsid);
                System.out.println("sellingdetailsiddddbbbb" + selling_detailsid);


                if (getArguments() .getString("navg_from")!= null) {

                    if (getArguments().getString("navg_from").equals("invtry_details")) {
                        params.put("UserId", sessionManager.getRegId("userId"));
                        params.put("CreatedBy", sessionManager.getRegId("userId"));
                        params.put("SellingDetailsId", Inventory_Details_Fragment.sellingedit_id);
                        System.out.println("editiddddd" + Inventory_Details_Fragment.sellingedit_id);
                        params.put("SellingListMasterId", Inventory_Details_Fragment.selling_masterid);
                        params.put("SellingVarietyId", Variety_id);
                        params.put("SellingQualityId", Quality_id);
                        params.put("SellingQuantity", min_quantity.getText().toString());
                        params.put("UnitOfPriceId", String.valueOf(QuantityPrice_Adapter2.quantityprice_id));
                        params.put("Price", "0");
                        params.put("SellingCategoryId", Inventory_Details_Fragment.selling_categoryid);
                        params.put("MinPrice", min_price.getText().toString());
                        params.put("MaxPrice", max_price.getText().toString());


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

    public void linear_layout_selection(EditText selectdl1, EditText l2,EditText l3){
        selectdl1.setBackgroundResource(R.drawable.border_1_layout);
        l2.setBackgroundResource(R.drawable.request_price_white_border2);
        l3.setBackgroundResource(R.drawable.request_price_white_border2);

    }

   /* public void afterTextChanged(EditText s) {
        double doubleValue = 0;
        if (s != null) {
            try {
                doubleValue = Double.parseDouble(s.toString().replace(',', ','));
            } catch (NumberFormatException e) {
                //Error
            }
        }
        //Do something with doubleValuese
    }*/

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }

            });
        }
/////kkkkkkkkkkkk
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

            try{
                assert inputManager != null;
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }catch(AssertionError e){
                e.printStackTrace();
            }
        }
    }
}
