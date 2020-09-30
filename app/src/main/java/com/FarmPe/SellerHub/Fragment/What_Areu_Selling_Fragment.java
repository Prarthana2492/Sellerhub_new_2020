package com.FarmPe.SellerHub.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
import com.FarmPe.SellerHub.Adapter.What_Areu_Selling_Adapter;
import com.FarmPe.SellerHub.Bean.Sellbean;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.Urls;
import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class What_Areu_Selling_Fragment extends Fragment {

    public static ArrayList<Sellbean> newOrderBeansList = new ArrayList<>();
    private List<Sellbean> searchresultAraaylist = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static What_Areu_Selling_Adapter livestock_types_adapter;
    Fragment selectedFragment = null;
    TextView toolbar_title;
    public static String livestock_status;
    LinearLayout back_feed,linearLayout;
    JSONArray get_categorylist_array;
    JSONArray get_soiltype;
    Sellbean addTractorBean3;
    EditText search;
   public static String sellingdetailsid,sellnavigation;

    public static What_Areu_Selling_Fragment newInstance() {
        What_Areu_Selling_Fragment fragment = new What_Areu_Selling_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.livestock_recy_layout, container, false);


        Status_bar_change_singleton.getInstance().color_change(getActivity());
       // HomePage_With_Bottom_Navigation.linear_bottonsheet.setVisibility(View.GONE);
       // HomePage_With_Bottom_Navigation.view.setVisibility(View.GONE);

        recyclerView=view.findViewById(R.id.recycler_what_looking);
        toolbar_title=view.findViewById(R.id.toolbar_title);
        back_feed=view.findViewById(R.id.back_feed);
        linearLayout = view.findViewById(R.id.linearLayout);
        search=view.findViewById(R.id.search);

        sellingdetailsid=Inventory_Details_Fragment.SId;
        System.out.println("selleditiddd"+sellingdetailsid);

        if (getArguments() != null) {
            sellnavigation= String.valueOf(getArguments().getString("navg_from").equals("invtry_details"));
        }


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = InventoryList.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.commit();

            }
        });

                 /*
                //   System.out.println("hfsdgfhagfdashf"+ getArguments().getString("status"));
                if (getArguments().getString("status").equals("home_page")) {

            */



               /* FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("homepage", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/

               /* selectedFragment = Home_Menu_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();*/
/*
                }else {

//                    FragmentManager fm = getActivity().getSupportFragmentManager();
//                    fm.popBackStack("settingss", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    selectedFragment = Setting_List_Fragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                }*/




        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                 /*   if (getArguments().getString("status").equals("home_page")) {
                 */
                  selectedFragment = InventoryList.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.commit();
                  /*  }else {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("settingss", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }*/
                    return true;
                }

                return false;
            }
        });



        newOrderBeansList.clear();
        // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        livestock_types_adapter=new What_Areu_Selling_Adapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(livestock_types_adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sorting(s.toString());
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                search.requestFocus();
                search.setCursorVisible(true);

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

    try{

            newOrderBeansList.clear();
            JSONObject jsonObject = new JSONObject();


            Crop_Post.crop_posting(getActivity(), Urls.GetSellingType, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("GetSellingTypeeeeeeee"+result);


                    try{

                        get_soiltype = result.getJSONArray("SellingTypeList");

                        for(int i=0;i<get_soiltype.length();i++){

                            JSONObject jsonObject1 = get_soiltype.getJSONObject(i);
                            addTractorBean3 = new Sellbean(jsonObject1.getString("SellingTypeName"),jsonObject1.getString("SellingTypeId"),jsonObject1.getString("SellingTypeIcon"));

                            newOrderBeansList.add(addTractorBean3);
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

        return view;
    }

    public  void sorting(String filter_text){

        final String text = filter_text.toLowerCase();

        searchresultAraaylist.clear();
        for (int i = 0; i < newOrderBeansList.size(); i++) {

            if (newOrderBeansList.get(i).getName().toLowerCase().contains(text)) {
                searchresultAraaylist.add(newOrderBeansList.get(i));

            }
        }
        livestock_types_adapter=new What_Areu_Selling_Adapter(getActivity(),searchresultAraaylist);
        recyclerView.setAdapter(livestock_types_adapter);

    }

}
