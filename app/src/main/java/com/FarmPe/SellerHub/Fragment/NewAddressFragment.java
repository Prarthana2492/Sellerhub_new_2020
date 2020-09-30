package com.FarmPe.SellerHub.Fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
import com.FarmPe.SellerHub.Adapter.Address_Adapter;
import com.FarmPe.SellerHub.Adapter.DistrictAdapter;
import com.FarmPe.SellerHub.Adapter.StateApdater;
import com.FarmPe.SellerHub.Adapter.TalukAdapter;
import com.FarmPe.SellerHub.Adapter.VillageAdapter;
import com.FarmPe.SellerHub.Bean.StateBean;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class NewAddressFragment extends Fragment {
    RecyclerView recycler_brand;
    LinearLayout back_feed,loan_lay,main_layout,continuebtn;
    TextView back_text,next,norecords,toolbar_title;
    Fragment selectedFragment;
    public static EditText full_name,mobile_no,address,landmark,pincode,district,block,nyaypanchayat,grampanchayath,village ;
    Calendar myCalendar;
    public static EditText state;
    public static DrawerLayout drawer;
    RecyclerView recyclerView;
    String account_validate,ifsc_validate,status;
    EditText account_no,ifsc_code;
    EditText search;
    SessionManager sessionManager;


    static List<StateBean> stateBeanList = new ArrayList<>();
    static List<StateBean> districtBeanList = new ArrayList<>();
    static List<StateBean> talukBeanList = new ArrayList<>();
    static List<StateBean> hobliBeanList = new ArrayList<>();
    static List<StateBean> villageBeanList = new ArrayList<>();
    private List<StateBean> searchresultAraaylist = new ArrayList<>();
    StateApdater stateApdater;
    DistrictAdapter districtAdapter;
    TalukAdapter talukAdapter;
    VillageAdapter villageAdapter;


    public static String search_status="status";

    JSONArray state_array,jsonArray,tal_array,village_array;
    StateBean stateBean;


    public static NewAddressFragment newInstance() {
        NewAddressFragment fragment = new NewAddressFragment();
        return fragment;
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_your_region_profilelayout, container, false);


        getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.dark_green));


        back_feed=view.findViewById(R.id.back_feed);
        full_name=view.findViewById(R.id.fulname);
        mobile_no=view.findViewById(R.id.mobno);
        address=view.findViewById(R.id.addressss);
        landmark=view.findViewById(R.id.lnd_mr);
        pincode=view.findViewById(R.id.pincodeee);
        state=view.findViewById(R.id.st);
        district=view.findViewById(R.id.dt);
        block=view.findViewById(R.id.bk);
//        nyaypanchayat=view.findViewById(R.id.nt);
//        grampanchayath=view.findViewById(R.id.gt);
        village=view.findViewById(R.id.ve);
        search = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recycler_view);
        norecords = view.findViewById(R.id.norecords);
        main_layout = view.findViewById(R.id.main_layout);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        norecords.setVisibility(View.GONE);
        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout_op);


        sessionManager = new SessionManager(getActivity());


        continuebtn=view.findViewById(R.id.continuebtn);


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getArguments().getString("prof_add_status").equals("setting")) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("seller_setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                }else  {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("newaddressfragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                }

              /*  FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack ("newaddressfragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/

            }
        });

        full_name.setText(getArguments().getString("addr_name"));
        mobile_no.setText(getArguments().getString("addr_mobile_number"));
        address.setText(getArguments().getString("addr_address"));
        pincode.setText(getArguments().getString("addr_pincode"));
        landmark.setText(getArguments().getString("addr_landmark"));

        state.setText(getArguments().getString("addr_state"));
        district.setText(getArguments().getString("addr_district"));
        block.setText(getArguments().getString("addr_block"));
        /*nyaypanchayat.setText(getArguments().getString("addr_nyay_panchayat"));
        grampanchayath.setText(getArguments().getString("addr_gram_panchayat"));*/
        village.setText(getArguments().getString("addr_village"));


        if(getArguments().getString("prof_add_status").equals("edit_add_addressss")){

            toolbar_title.setText("Edit Address Details");
        }



      continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    JSONObject jsonObject = new JSONObject();
                   // jsonObject.put("UserAddressId", "1");
                    jsonObject.put("FullName",full_name.getText().toString());
                    jsonObject.put("MobileNo", mobile_no.getText().toString());
                    jsonObject.put("Address", address.getText().toString());
                    jsonObject.put("LandMark", landmark.getText().toString());
                    jsonObject.put("StateId", StateApdater.stateid);
                    jsonObject.put("DistrictId", DistrictAdapter.districtid);
                    jsonObject.put("BlockId", TalukAdapter.talukid);
                    jsonObject.put("VillageId", VillageAdapter.villageid);
                    jsonObject.put("PinCode", pincode.getText().toString());
                    jsonObject.put("UserId", sessionManager.getRegId("userId"));
                    System.out.println("add address check"+jsonObject);


                    if(getArguments().getString("prof_add_status").equals("edit_add_addressss")){

                        jsonObject.put("UserId",sessionManager.getRegId("userId"));
                        jsonObject.put("FullName",full_name.getText().toString());
                        jsonObject.put("MobileNo",mobile_no.getText().toString());
                        jsonObject.put("Address",address.getText().toString());
                        jsonObject.put("LandMark",landmark.getText().toString());
                        jsonObject.put("StateId",StateApdater.stateid);
                        jsonObject.put("DistrictId",DistrictAdapter.districtid);
                        jsonObject.put("BlockId",TalukAdapter.talukid);
//                        jsonObject.put("NyayPanchayatId",Nyay_Panchayat_Adapter.nyay_panchayat_id);
//                        jsonObject.put("GramPanchayatId",Gram_Panchayat_Adapter.gram_panchayat_id);
                        jsonObject.put("VillageId",VillageAdapter.villageid);
                        jsonObject.put("PinCode",pincode.getText().toString());
                        jsonObject.put("UserAddressId", Address_Adapter.add_id);
                        System.out.println("edit address check"+jsonObject);

                    }

                    Crop_Post.crop_posting(getActivity(), Urls.AddUserAddressDetails, jsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.println("AddUserAddressDetailssssssssssssssss"+result);
                            try{

                                status= result.getString("Status");

                                if(status.equals("1")){

                                    int duration=1000;
                                    Snackbar snackbar = Snackbar
                                            .make(main_layout, "Address Added Successfully",duration);
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

                                   /* selectedFragment = SellersettingFragment.newInstance();
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frame_layout, selectedFragment);
                                   // transaction.addToBackStack("events");
                                    transaction.commit();*/

                                }else{

                                    int duration=1000;
                                    Snackbar snackbar = Snackbar
                                            .make(main_layout, "Address not Added",duration);
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

                                }


                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                    });


                }catch(Exception e){
                    e.printStackTrace();
                }
               /* selectedFragment = Addnewbankdetailswithifsc.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("add_new");
                transaction.commit();*/
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (getArguments().getString("prof_add_status").equals("setting")) {

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("seller_setting", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    }else  {

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("newaddressfragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    }
                    return true;
                }

                return false;
            }
        });


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


        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                drawer.openDrawer(GravityCompat.END);
                search_status="state";
                search.setText("");


                stateBeanList.clear();
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                stateApdater = new StateApdater(stateBeanList,getActivity());

                recyclerView.setAdapter(stateApdater);

                prepareStateData();


            }
        });


        district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!state.getText().toString().equals("")) {


                    drawer.openDrawer(GravityCompat.END);

                    search_status="district";
                    search.setText("");


                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    districtAdapter= new DistrictAdapter( districtBeanList,getActivity());
                    recyclerView.setAdapter(districtAdapter);



                    prepareDistricData();


                }else{
                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Please Select State", Snackbar.LENGTH_LONG);
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

                }

            }
        });



        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!district.getText().toString().equals("")) {


                    drawer.openDrawer(GravityCompat.END);
                    search_status = "taluk";
                    search.setText("");
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    talukAdapter = new TalukAdapter(talukBeanList, getActivity());
                    recyclerView.setAdapter(talukAdapter);
                    prepareTalukData();


                }else{

                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Please Select District", Snackbar.LENGTH_LONG);
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

                }
            }
        });



        village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!district.getText().toString().equals("")) {


                    drawer.openDrawer(GravityCompat.END);
                    search_status = "village";
                    search.setText("");
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    villageAdapter = new VillageAdapter(villageBeanList, getActivity());
                    recyclerView.setAdapter(villageAdapter);
                    prepareVillageData();


                }else{

                    Snackbar snackbar = Snackbar
                            .make(main_layout, "Please Select Block", Snackbar.LENGTH_LONG);
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

                }
            }
        });


        full_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(full_name,mobile_no,address,landmark,pincode,state,district,block,village);
                return false;
            }
        });
        mobile_no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(mobile_no,full_name,address,landmark,pincode,state,district,block,village);
                return false;
            }
        });
        address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(address,full_name,mobile_no,landmark,pincode,state,district,block,village);
                return false;
            }
        });
        landmark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(landmark,full_name,mobile_no,address,pincode,state,district,block,village);
                return false;
            }
        });
        pincode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(pincode,full_name,mobile_no,address,landmark,state,district,block,village);
                return false;
            }
        });
        state.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(state,full_name,mobile_no,address,landmark,pincode,district,block,village);
                return false;
            }
        });
        district.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(district,full_name,mobile_no,address,landmark,pincode,state,block,village);
                return false;
            }
        });
        block.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(block,full_name,mobile_no,address,landmark,pincode,state,district,village);
                return false;
            }
        });

        village.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linear_layout_selection(village,full_name,mobile_no,address,landmark,pincode,state,district,block);
                return false;
            }
        });





        return view;
    }







    public void prepareStateData() {

        recyclerView.setVisibility(View.VISIBLE);
        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject post_jsonobject = new JSONObject();
            jsonObject.put("CountryId","91");
            post_jsonobject.put("Stateobj",jsonObject);



            Crop_Post.crop_posting(getActivity(), Urls.State, post_jsonobject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("11111ssss" + result);
                    try{
                        stateBeanList.clear();
                        state_array = result.getJSONArray("StateList");

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
        }

    }


    private void prepareDistricData() {

        recyclerView.setVisibility(View.VISIBLE);

        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject post_jsonobject = new JSONObject();
            jsonObject.put("StateId",stateApdater.stateid);
            post_jsonobject.put("Districtobj",jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.Districts, post_jsonobject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("dddddddddddd11111" + result);
                    try{
                        districtBeanList.clear();
                        jsonArray = result.getJSONArray("DistrictList");
                        if (jsonArray != null &&jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                stateBean = new StateBean(jsonObject1.getString("District"), jsonObject1.getString("DistrictId"));
                                districtBeanList.add(stateBean);
                            }

                            sorting(districtBeanList);


                            districtAdapter.notifyDataSetChanged();
                            //grade_dialog.show();
                        }
                        else{
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
    }


    private void prepareTalukData() {
        recyclerView.setVisibility(View.VISIBLE);


        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject jsonpost = new JSONObject();
            jsonObject.put("DistrictId",DistrictAdapter.districtid);
            jsonpost.put("Blockobj",jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.Blocks, jsonpost, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("aaaaaaaaaaaaafffffffffffff"+result);
                    try{
                        talukBeanList.clear();
                        tal_array = result.getJSONArray("BlockList") ;

                        if (tal_array != null &&tal_array.length() > 0){

                            for(int i=0;i<tal_array.length();i++){
                                JSONObject jsonObject1 = tal_array.getJSONObject(i);
                                stateBean = new StateBean(jsonObject1.getString("BlockName"),jsonObject1.getString("BlockId"));
                                talukBeanList.add(stateBean);

                            }
                            sorting(talukBeanList);

                            talukAdapter.notifyDataSetChanged();
                            // grade_dialog.show();


                        }else {

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

    }



    private void prepareVillageData() {
        recyclerView.setVisibility(View.VISIBLE);


        try{

            JSONObject jsonObject = new JSONObject();
            JSONObject jsonpost = new JSONObject();
            jsonObject.put("BlockId",TalukAdapter.talukid);
            jsonpost.put("Villageobj",jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.GetVillagebyBlock, jsonpost, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("aaaaaaaaaaaaafffffffffffff"+result);
                    try{
                        villageBeanList.clear();
                        village_array = result.getJSONArray("VillageByBlockList") ;

                        if (village_array != null &&village_array.length() > 0){

                            for(int i=0;i<village_array.length();i++){
                                JSONObject jsonObject1 = village_array.getJSONObject(i);
                                stateBean = new StateBean(jsonObject1.getString("Village"),jsonObject1.getString("VillagId"));
                                villageBeanList.add(stateBean);

                            }
                            sorting(villageBeanList);

                            villageAdapter.notifyDataSetChanged();
                            // grade_dialog.show();


                        }else {

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

    }

    public void sorting(List<StateBean> arrayList){

        Collections.sort(arrayList, new Comparator() {
            @Override
            public int compare(Object state_name1, Object state_name2) {
                //use instanceof to verify the references are indeed of the type in question
                return ((StateBean)state_name1).getName()
                        .compareTo(((StateBean)state_name2).getName());
            }
        });
    }


    private void sorting(String filter_text) {

        final String text = filter_text.toLowerCase();



        if (search_status.equals("state")) {
            searchresultAraaylist.clear();
            for (int i = 0; i < stateBeanList.size(); i++) {

                if (stateBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(stateBeanList.get(i));

                }
            }
            if (searchresultAraaylist.size() == 0) {
                recyclerView.setVisibility(View.GONE);
                norecords.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                norecords.setVisibility(View.GONE);
                stateApdater = new StateApdater(searchresultAraaylist, getActivity());
                recyclerView.setAdapter(stateApdater);
            }

        }

        else if (search_status.equals("district")) {
            searchresultAraaylist.clear();
            for (int i = 0; i < districtBeanList.size(); i++) {

                if (districtBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(districtBeanList.get(i));

                }
            }
            if (searchresultAraaylist.size()==0){
                recyclerView.setVisibility(View.GONE);
                norecords.setVisibility(View.VISIBLE);
            }else {
                recyclerView.setVisibility(View.VISIBLE);
                norecords.setVisibility(View.GONE);

                districtAdapter = new DistrictAdapter(searchresultAraaylist, getActivity());
                recyclerView.setAdapter(districtAdapter);
            }

        }
        else if (search_status.equals("taluk")) {
            searchresultAraaylist.clear();
            for (int i = 0; i < talukBeanList.size(); i++) {

                if (talukBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(talukBeanList.get(i));

                }
            }
            if (searchresultAraaylist.size() == 0) {
                recyclerView.setVisibility(View.GONE);
                norecords.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                norecords.setVisibility(View.GONE);
                talukAdapter = new TalukAdapter(searchresultAraaylist, getActivity());
                recyclerView.setAdapter(talukAdapter);
            }
        }
       /* else if (search_status.equals("hoblii")) {
            searchresultAraaylist.clear();
            for (int i = 0; i < hobliBeanList.size(); i++) {

                if (hobliBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(hobliBeanList.get(i));

                }
            }
            if (searchresultAraaylist.size() == 0) {
                recyclerView.setVisibility(View.GONE);
                norecords.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                norecords.setVisibility(View.GONE);
                hoblisAdapter = new HoblisAdapter(searchresultAraaylist, getActivity());
                recyclerView.setAdapter(hoblisAdapter);
            }
        }*/
        else if (search_status.equals("village")){
            searchresultAraaylist.clear();
            for (int i = 0; i < villageBeanList.size(); i++) {

                if (villageBeanList.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(villageBeanList.get(i));

                }
            }
            //  villageAdapter = new VillageAdapter(sdearcstateBeanList);
            recyclerView.setAdapter(villageAdapter);
        }

    }



    public void linear_layout_selection(EditText selectdl1,EditText l2,EditText l3,EditText l4, EditText l5 ,EditText l6 , EditText l7,EditText l8,EditText l9){

      selectdl1.setBackgroundResource(R.drawable.border_1_layout);
      l2.setBackgroundResource(R.drawable.request_price_white_border2);
      l3.setBackgroundResource(R.drawable.request_price_white_border2);
      l4.setBackgroundResource(R.drawable.request_price_white_border2);
      l5.setBackgroundResource(R.drawable.request_price_white_border2);
      l6.setBackgroundResource(R.drawable.request_price_white_border2);
      l7.setBackgroundResource(R.drawable.request_price_white_border2);
      l8.setBackgroundResource(R.drawable.request_price_white_border2);
      l9.setBackgroundResource(R.drawable.request_price_white_border2);


  }
}
