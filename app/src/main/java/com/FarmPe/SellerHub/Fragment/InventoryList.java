package com.FarmPe.SellerHub.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
import com.FarmPe.SellerHub.Adapter.InventoryAdapter;
import com.FarmPe.SellerHub.Bean.InventoryBean;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class InventoryList extends Fragment {

    public static ArrayList<InventoryBean> newOrderBeansList_subcat = new ArrayList<>();

    public static RecyclerView recyclerView_main;
    public static InventoryAdapter livestock_types_adapter;
    JSONObject jsonObject1;
    Fragment selectedFragment = null;
    public static TextView toolbar_title,name,list_prod_text;
    public static String livestock_status;
    LinearLayout back_feed,linearLayout;
    JSONArray get_categorylist_array;
    JSONArray get_soiltype;
    ImageView list_prod;
    public static String sellingcatId,sellnavigation;
    SessionManager sessionManager;

    public static InventoryList newInstance() {
        InventoryList fragment = new InventoryList();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inventory_prod_recy, container, false);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryDark1));
        Status_bar_change_singleton.getInstance().color_change(getActivity());


        recyclerView_main=view.findViewById(R.id.recycler_cat_detail);
        list_prod_text=view.findViewById(R.id.list_prod_text);
        name=view.findViewById(R.id.name);
        list_prod=view.findViewById(R.id.list_prod);
sessionManager=new SessionManager(getActivity());
        linearLayout = view.findViewById(R.id.linearLayout);
        back_feed = view.findViewById(R.id.back_feed);
//        toolbar_title.setText("Select Category");
        // sellingdetailsid=Inventory_Details_Fragment.SId;
    //    sellingcatId=getArguments().getString("sellingCatId");

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = HomeFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.commit();
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    selectedFragment = HomeFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.commit();
                    return true;
                }

                return false;
            }
        });

        list_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = What_Areu_Selling_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.commit();
            }
        });
        list_prod_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = What_Areu_Selling_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.commit();
            }
        });
        newOrderBeansList_subcat.clear();
        // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView_main.setLayoutManager(mLayoutManager_farm);
        recyclerView_main.setItemAnimator(new DefaultItemAnimator());

        try{

            //  newOrderBeansList_subcat_veg.clear();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));

            System.out.println("jhfdfdjc111"+jsonObject);
            Crop_Post.crop_posting(getActivity(), Urls.GetProductDetails, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("GetSellingTypeeeeeeee"+result);


                    try{

                        get_soiltype = result.getJSONArray("SellDetails");

                        for(int i=0;i<get_soiltype.length();i++){

                            JSONObject jsonObject1 = get_soiltype.getJSONObject(i);
                            InventoryBean sellbean = new InventoryBean(jsonObject1.getString("SellingListName"),jsonObject1.getString("UnitOfPrice"),jsonObject1.getString("SellingCategoryName"),jsonObject1.getString("SellingQuantity"),jsonObject1.getString("MinPrice"),jsonObject1.getString("MaxPrice"),"",jsonObject1.getString("SellingListIcon"),jsonObject1.getString("SellingDetailsId"),"",jsonObject1.getString("SellingListMasterId"),jsonObject1.getString("SellingVariety"),jsonObject1.getString("SellingQuality"),jsonObject1.getString("SellingCategoryId"),"","");
                          //  bean = new Inventorydetailsbean(jsonObject1.getString("SellingDetailsId"),jsonObject1.getString("SellingListName"),jsonObject1.getString("UnitOfPrice"),jsonObject1.getString("Price"),jsonObject1.getString("SellingQuantity"),jsonObject1.getString("SellingListIcon"),jsonObject1.getString("SellingVariety"),jsonObject1.getString("SellingCategoryName"),jsonObject1.getString("SellingQuality"),jsonObject1.getString("MinPrice"),jsonObject1.getString("MaxPrice"));

                            // bean = new Inventorydetailsbean(jsonObject1.getString("SellingDetailsId"),jsonObject1.getString("SellingListName"),jsonObject1.getString("UnitOfPrice"),jsonObject1.getString("Price"),jsonObject1.getString("SellingQuantity"),jsonObject1.getString("SellingListIcon"),jsonObject1.getString("SellingVariety"),jsonObject1.getString("SellingCategoryName"),jsonObject1.getString("SellingQuality"),jsonObject1.getString("MinPrice"),jsonObject1.getString("SellingCategoryId"));

                            newOrderBeansList_subcat.add(sellbean);
                        }
                        livestock_types_adapter=new InventoryAdapter(getActivity(),newOrderBeansList_subcat);
                        recyclerView_main.setAdapter(livestock_types_adapter);
                     //   name.setText(jsonObject1.getString("SellingCategoryName"));

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



}
