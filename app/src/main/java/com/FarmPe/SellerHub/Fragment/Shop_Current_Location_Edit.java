/*
package com.FarmPe.SellerHub.Fragment;

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



import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.ui.IconGenerator;


public class Shop_Current_Location_Edit extends Fragment {

    Fragment selectedFragment;
    LinearLayout Continue,back_feed,edit_loc;
    ImageView capture_photo;
    SessionManager sessionManager;

    public static ClusterManager<Person> mClusterManager;
    private IconGenerator mIconGenerator;



    public static Shop_Current_Location_Edit newInstance() {
        Shop_Current_Location_Edit fragment = new Shop_Current_Location_Edit();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selectcurrentlocation2, container, false);

        //  Status_bar_change_singleton.getInstance().color_change(getActivity());

        Continue = view.findViewById(R.id.continu_btn);
        back_feed = view.findViewById(R.id.back_feed);
       // select_location = view.findViewById(R.id.select_location);
        capture_photo = view.findViewById(R.id.capture_photo);
        edit_loc = view.findViewById(R.id.edit_loc);

        sessionManager = new SessionManager(getActivity());
        mIconGenerator = new IconGenerator(getActivity().getApplicationContext());




        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("map_loctn", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//                    HomeMenuFragment.onBack_status = "farms";
//
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("map_loctn", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    return true;
                }
                return false;
            }
        });



        edit_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("Edit_Fragment","curr_loc_edit");
                selectedFragment = Shop_Current_Location_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("map_locatn");
                selectedFragment.setArguments(bundle);
                transaction.commit();


            }
        });



        capture_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedFragment = GuidelinesFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("shop_camera");
                transaction.commit();


            }
        });


//        Continue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                selectedFragment = GuidelinesFragment.newInstance();
////                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
////                transaction.replace(R.id.frame_layout1, selectedFragment);
////                transaction.commit();
//
//            }
//        });


        return view;
    }
}

*/
