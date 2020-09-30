/*
package com.FarmPe.SellerHub.Fragment;

import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.SellerHub.Bean.Inventorydetailsbean;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;
import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InventoryFragment extends Fragment {

    public static List<Inventorydetailsbean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static TrackApplicationAdapter farmadapter;
    TextView toolbar_title;
    LinearLayout back_feed;
    Fragment selectedFragment;
    JSONObject lngObject;
    JSONArray jsonArray;
    Inventorydetailsbean bean;
    SessionManager sessionManager;


    public static InventoryFragment newInstance() {
        InventoryFragment fragment = new InventoryFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inventory_prod_recy, container, false);
        recyclerView=view.findViewById(R.id.recycler_bank_off);
        back_feed=view.findViewById(R.id.back_feed);
        toolbar_title=view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Inventory");
        sessionManager = new SessionManager(getActivity());
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.dark_green));


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




              */
/* selectedFragment = Home_Menu_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();*//*



            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                 */
/*   FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("homepage", FragmentManager.POP_BACK_STACK_INCLUSIVE);*//*


                  */
/*  selectedFragment = Home_Menu_Fragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();*//*



                  */
/*  selectedFragment = Home_Menu_Fragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();*//*

                    */
/*FragmentManager fm = getFragmentManager();
                    fm.popBackStack();*//*


                    return true;
                }
                return false;
            }
        });

//

        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        farmadapter=new TrackApplicationAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(farmadapter);


        try{
            newOrderBeansList.clear();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));
            Crop_Post.crop_posting(getActivity(), Urls.GetSellDetails, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("inventorydetails"+result);
                    try{
                        jsonArray = result.getJSONArray("SellDetails");

                        for(int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            bean = new Inventorydetailsbean(jsonObject1.getString("SellingDetailsId"),jsonObject1.getString("SellingListName"),jsonObject1.getString("UnitOfPrice"),jsonObject1.getString("Price"),jsonObject1.getString("SellingQuantity"),jsonObject1.getString("SellingListIcon"),jsonObject1.getString("SellingVariety"),jsonObject1.getString("SellingCategoryName"),jsonObject1.getString("SellingQuality"),jsonObject1.getString("MinPrice"),jsonObject1.getString("MaxPrice"));
                            newOrderBeansList.add(bean);
                        }
                        farmadapter.notifyDataSetChanged();

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
*/
