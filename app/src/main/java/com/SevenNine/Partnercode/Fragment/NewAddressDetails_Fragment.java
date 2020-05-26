package com.SevenNine.Partnercode.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.SevenNine.Partnercode.Activity.Status_bar_change_singleton;
import com.SevenNine.Partnercode.Adapter.Address_Adapter;
import com.SevenNine.Partnercode.Bean.Add_New_Address_Bean;
import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;
import com.SevenNine.Partnercode.Urls;
import com.SevenNine.Partnercode.Volly_class.Crop_Post;
import com.SevenNine.Partnercode.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class NewAddressDetails_Fragment extends Fragment {


    public static ArrayList<Add_New_Address_Bean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static Address_Adapter livestock_types_adapter;
    Fragment selectedFragment = null;
    TextView toolbar_title,Continue_txt;
    public static String livestock_status;
    LinearLayout back_feed,linearLayout,Continue;
    JSONArray get_address_array;
    Add_New_Address_Bean add_new_address_bean;

    SessionManager sessionManager;




    public static NewAddressDetails_Fragment newInstance() {
        NewAddressDetails_Fragment fragment = new NewAddressDetails_Fragment();
        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.soil_details_recy_layout, container, false);


       Status_bar_change_singleton.getInstance().color_change(getActivity());

        recyclerView=view.findViewById(R.id.recycler_what_looking);
        toolbar_title=view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("My Addresses");
        back_feed=view.findViewById(R.id.back_feed);
        linearLayout = view.findViewById(R.id.linearLayout);
        Continue = view.findViewById(R.id.continuebtn);
        Continue_txt = view.findViewById(R.id.text);
        Continue_txt.setText("ADD NEW ADDRESS");

        sessionManager = new SessionManager(getActivity());


       Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle bundle = new Bundle();
                bundle.putString("prof_add_status","get_address");
                selectedFragment = NewAddressFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.addToBackStack("newaddressfragment");
                transaction.commit();
            }
        });



        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("settingg", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("settingg", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });



        newOrderBeansList.clear();
        // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        try{
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));


            Crop_Post.crop_posting(getActivity(), Urls.GetUserAddress, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("ggggggggggaaaaaaa"+result);
                    try{
                        newOrderBeansList.clear();


                        get_address_array = result.getJSONArray("UserAddressList");

                        for(int i=0;i<get_address_array.length();i++){
                            JSONObject jsonObject1 = get_address_array.getJSONObject(i);

                            add_new_address_bean = new Add_New_Address_Bean(jsonObject1.getString("FullName"),"",jsonObject1.getString("Address"),jsonObject1.getString("LandMark"),"",jsonObject1.getString("Pincode"),jsonObject1.getString("MobileNo"),"",
                                    jsonObject1.getString("State"),jsonObject1.getString("District"),jsonObject1.getString("Taluk"),jsonObject1.getString("Hoblie"),jsonObject1.getString("Village"),jsonObject1.getString("UserAddressId"),jsonObject1.getBoolean("IsDefaultAddress"),jsonObject1.getString("StateId"),jsonObject1.getString("DistrictId"),jsonObject1.getString("BlockId"),"");


                            newOrderBeansList.add(add_new_address_bean);
                            livestock_types_adapter=new Address_Adapter(getActivity(),newOrderBeansList);
                            recyclerView.setAdapter(livestock_types_adapter);

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


       /* Add_New_Address_Bean img1=new Add_New_Address_Bean("Jagdish","102","RR Nagar","","Bengaluru","560098","","","Karnataka",
                "","","","","", true,"","","","");
        newOrderBeansList.add(img1);

        livestock_types_adapter=new Address_Adapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(livestock_types_adapter);*/


        return view;
    }



}
