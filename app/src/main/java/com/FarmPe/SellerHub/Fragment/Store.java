package com.FarmPe.SellerHub.Fragment;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
import com.FarmPe.SellerHub.Adapter.StoreAdapter;
import com.FarmPe.SellerHub.Bean.StoreListBean;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Store extends Fragment {


    public static List<StoreListBean> modelBeanArrayList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static StoreAdapter farmadapter;
    JSONArray storelist;

     StoreListBean storeListBean;
     Fragment selectedFragment = null;
     TextView toolbar_title,continue_button,sub_label,toolbar_title1;
     LinearLayout back_feed,add_store_lay;
     SessionManager sessionManager;
     public static LinearLayout linearLayout;
     ImageView filter_text;
     public static String store;



    public static Store newInstance() {
        Store fragment = new Store();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_model_recy, container, false);


       // Status_bar_change_singleton.getInstance().color_change(getActivity());
        recyclerView=view.findViewById(R.id.recycler_what_looking);
        toolbar_title=view.findViewById(R.id.toolbar_title);
        back_feed=view.findViewById(R.id.back_feed);
       // continue_button=view.findViewById(R.id.continue_button);
        filter_text=view.findViewById(R.id.filter_text);
        linearLayout=view.findViewById(R.id.linearLayout);
        add_store_lay=view.findViewById(R.id.add_store_lay);
      //  sub_label=view.findViewById(R.id.sub_label);
        sessionManager = new SessionManager(getActivity());
        Status_bar_change_singleton.getInstance().color_change(getActivity());

     //   text_box = getArguments().getString("status_home");



     //   b_arrow=view.findViewById(R.id.b_arrow);




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

        add_store_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store="Store";
                selectedFragment = FirmShopDetailsFragment.newInstance();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_layout1,selectedFragment);
                ft.commit();
            }
        });

        filter_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.layout_filterpopup);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final TextView rec = (TextView) dialog.findViewById(R.id.credited_to);
                final TextView asce = (TextView)dialog.findViewById(R.id.sort_ascendi) ;
                final TextView desc = (TextView)dialog.findViewById(R.id.sort_desendi) ;
                //   final TextView popuptxt = (TextView)dialog.findViewById(R.id.popup_heading) ;
                LinearLayout image = (LinearLayout) dialog.findViewById(R.id.close_popup);


                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });




       // ModelList();
       // newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        modelBeanArrayList.clear();
        try{
            // newOrderBeansList.clear();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));
            // jsonObject.put("SellingTypeId", What_Areu_Looking_Adapter.sellingtypeid);

            System.out.println("jhfdfdjceeee"+jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.GetStoreDetails, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("GetSellingTypeeeeeeeegggggggstoreeeeeeeeeeeeeeeeeeeee"+result);


                    try{
                        storelist = result.getJSONArray("getfirmdetails");
                        System.out.println("idddddddddddddddddddddddddddddddddddddddddnext"+storelist);

                        for(int i=0;i<storelist.length();i++){
                            JSONObject jsonObject1 = storelist.getJSONObject(i);
                            StoreListBean sellbean = new StoreListBean(jsonObject1.getString("FirmShopName"),jsonObject1.getString("AddressLine1"),jsonObject1.getString("GST"),"560098",jsonObject1.getString("AddressLine2"),jsonObject1.getString("Image1"));
                            modelBeanArrayList.add(sellbean);
                        }
                        farmadapter = new StoreAdapter(getActivity(),modelBeanArrayList);
                        recyclerView.setAdapter(farmadapter);
                        farmadapter.notifyDataSetChanged();


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }



       /* StoreListBean bean=new StoreListBean("Raja Rajeshwari Nagar","GSTIN 22AAAAAAA0000A1Z5","560098","Bangalore","");
        modelBeanArrayList.add(bean);
        modelBeanArrayList.add(bean);
        modelBeanArrayList.add(bean);
        modelBeanArrayList.add(bean);*/



        return view;
    }


}
