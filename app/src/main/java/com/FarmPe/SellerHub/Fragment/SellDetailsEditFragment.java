package com.FarmPe.SellerHub.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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


import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
import com.FarmPe.SellerHub.Adapter.QuantityPrice_Adapter3;
import com.FarmPe.SellerHub.Bean.SelectLanguageBean;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;
import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SellDetailsEditFragment extends Fragment {
    Fragment selectedFragment;
    public LinearLayout backfeed1;
    TextView norecords,toolbar_title,sell_name,a_grade,b_grade,c_grade,hybrid,jawari,desi;
    TextView yes,no,cartons,plastic_box,petjar,otheroption1,fivekg,tenkg,twentykg,fiftykg,hundredkg,otherkg_option;
    String Organicproduce_Id;
    String PackagingtypeId;
    String PackagingsizeId;
    public static DrawerLayout drawer;
    public static String search_status="status";
    public static EditText min_quantity,min_price,max_price,unit_of_measurement;
    RecyclerView recyclerView;
    EditText search;
    SessionManager sessionManager;
    JSONArray jsonArray;
    String Quality_id,Variety_id,quality_str;
    PopupMenu quantity_popup,unit_of_price_pop_up;
    LinearLayout list_fr_selling;
    public static  String SellDetails_id,Sell_MlistId,SellCategort_id;
    String status;

    public static ArrayList<SelectLanguageBean> newOrderBeansList = new ArrayList<>();
    public static QuantityPrice_Adapter3 livestock_types_adapter3;
    private List<SelectLanguageBean> searchresultAraaylist = new ArrayList<>();
    SelectLanguageBean selectLanguageBean;

    public static SellDetailsEditFragment newInstance() {
        SellDetailsEditFragment fragment = new SellDetailsEditFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sell_details_edit, container, false);
        backfeed1= view.findViewById(R.id.back_feed);


        Status_bar_change_singleton.getInstance().color_change(getActivity());
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.dark_green));


        unit_of_price_pop_up = new PopupMenu(getActivity(), unit_of_measurement, Gravity.RIGHT);


        sessionManager = new SessionManager(getActivity());
        unit_of_measurement = view.findViewById(R.id.select_uom);
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
        list_fr_selling = view.findViewById(R.id.continuebtn);
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
        norecords.setVisibility(View.GONE);
        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout_op);

        setupUI(drawer);

//bundle.putString("Quality",SQuality);
//                bundle.putString("Variety",SVariety);
//                bundle.putString("Minquantity",Quantity);
//                bundle.putString("Minprice",SMinPrice);
//                bundle.putString("Maxprice",SMaxPrice);


        Bundle bundle= getArguments();
        if (bundle!=null){
            SellDetails_id = (getArguments().getString("Edit_Id"));
            Sell_MlistId = getArguments().getString("EditMaster_Id");
            SellCategort_id = getArguments().getString("EditCategory_Id");
            min_price.setText(getArguments().getString("Minprice"));
            max_price.setText(getArguments().getString("Maxprice"));
            min_quantity.setText(getArguments().getString("Minquantity"));
            unit_of_measurement.setText(getArguments().getString("UOM"));

            if (getArguments().getString("Quality").equals("A Grade")) {

                Quality_id = "1";

                a_grade.setTextColor(Color.parseColor("#FFFFFF"));
                b_grade.setTextColor(Color.parseColor("#000000"));
                c_grade.setTextColor(Color.parseColor("#000000"));

                a_grade.setBackgroundResource(R.drawable.black_border_blue_filled);
                b_grade.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                c_grade.setBackgroundResource(R.drawable.black_bordr_white_filled1);

            }else if(getArguments().getString("Quality").equals("B Grade")){
                Quality_id = "2";

                b_grade.setTextColor(Color.parseColor("#FFFFFF"));
                a_grade.setTextColor(Color.parseColor("#000000"));
                c_grade.setTextColor(Color.parseColor("#000000"));

                b_grade.setBackgroundResource(R.drawable.black_border_blue_filled);
                a_grade.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                c_grade.setBackgroundResource(R.drawable.black_bordr_white_filled1);
            }else{

                Quality_id = "3";

                c_grade.setTextColor(Color.parseColor("#FFFFFF"));
                a_grade.setTextColor(Color.parseColor("#000000"));
                b_grade.setTextColor(Color.parseColor("#000000"));

                c_grade.setBackgroundResource(R.drawable.black_border_blue_filled);
                a_grade.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                a_grade.setBackgroundResource(R.drawable.black_bordr_white_filled1);

            }


            if (getArguments().getString("Variety").equals("Hybrid")) {

                Variety_id = "1";

                hybrid.setTextColor(Color.parseColor("#FFFFFF"));
                jawari.setTextColor(Color.parseColor("#000000"));
                desi.setTextColor(Color.parseColor("#000000"));

                hybrid.setBackgroundResource(R.drawable.black_border_blue_filled);
                jawari.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                desi.setBackgroundResource(R.drawable.black_bordr_white_filled1);

            }else if(getArguments().getString("Quality").equals("Jawari")){
                Variety_id = "2";

                jawari.setTextColor(Color.parseColor("#FFFFFF"));
                hybrid.setTextColor(Color.parseColor("#000000"));
                desi.setTextColor(Color.parseColor("#000000"));

                jawari.setBackgroundResource(R.drawable.black_border_blue_filled);
                hybrid.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                desi.setBackgroundResource(R.drawable.black_bordr_white_filled1);
            }else{

                Variety_id = "3";

                desi.setTextColor(Color.parseColor("#FFFFFF"));
                hybrid.setTextColor(Color.parseColor("#000000"));
                jawari.setTextColor(Color.parseColor("#000000"));

                desi.setBackgroundResource(R.drawable.black_border_blue_filled);
                hybrid.setBackgroundResource(R.drawable.black_bordr_white_filled1);
                jawari.setBackgroundResource(R.drawable.black_bordr_white_filled1);

            }



            System.out.println("Image111111"+SellDetails_id);
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

                livestock_types_adapter3 = new QuantityPrice_Adapter3(getActivity(),newOrderBeansList);

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


        backfeed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("inventoryd", FragmentManager.POP_BACK_STACK_INCLUSIVE);



            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                 // HomeMenuFragment.onBack_status = "farms";

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("inventoryd", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    return true;
                }
                return false;
            }
        });



        list_fr_selling.setOnClickListener(new View.OnClickListener() {
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
                }else  if(Organicproduce_Id == null) {


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
                         SellEdit();
                  //  nextpage();
                    // uploadImage(getResizedBitmap(Spices_CameraFragment.selectedImage, 100, 100));
                }



            }
        });







        return view;
    }

    private void SellEdit() {

        try{

            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));
            jsonObject.put("SellingDetailsId", SellDetails_id);
            jsonObject.put("SellingListMasterId",Sell_MlistId );
            jsonObject.put("SellingCategoryId",SellCategort_id );
            jsonObject.put("SellingVarietyId",Variety_id);
            jsonObject.put("SellingQualityId", Quality_id);
            jsonObject.put("SellingQuantity", min_quantity.getText().toString());
            jsonObject.put("UnitOfPriceId",String.valueOf(QuantityPrice_Adapter3.quantityprice_id));
            jsonObject.put("Price","0");

            jsonObject.put("MinPrice",min_price.getText().toString() );
            jsonObject.put("MaxPrice",max_price.getText().toString() );
            jsonObject.put("OrganicProduceId",Organicproduce_Id);
            jsonObject.put("PackagingTypeId",PackagingtypeId);
            jsonObject.put("PackagingSizeId",PackagingsizeId);

     Crop_Post.crop_posting(getActivity(), Urls.EditSellDetails, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    Bundle bundle=new Bundle();

                    System.out.println("EditSellDetailslllllllllllllllllllll"+result);


                    try{

                        status= result.getString("Status");
                      //  message = result.getString("Message");


                        if((status.equals("Success"))){


                            Toast toast = Toast.makeText(getActivity(), "Sell details edited", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                            toast.show();
                           selectedFragment = InventoryList.newInstance();
                            FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout1, selectedFragment);
                            transaction.commit();


                        }else{


                            Toast toast = Toast.makeText(getActivity(), "Sell details not edited", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                            toast.show();

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
                livestock_types_adapter3 = new QuantityPrice_Adapter3( getActivity(),searchresultAraaylist);
                recyclerView.setAdapter(livestock_types_adapter3);
            }

        }

    }

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

