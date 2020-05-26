package com.SevenNine.Partnercode.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.SevenNine.Partnercode.Activity.Status_bar_change_singleton;
import com.SevenNine.Partnercode.R;

public class Crops_Uploaded_Sucess_Fragment extends Fragment {
    Fragment selectedFragment;
    LinearLayout backfeed;
    TextView next,sucess_txt;

    public static Crops_Uploaded_Sucess_Fragment newInstance() {
        Crops_Uploaded_Sucess_Fragment fragment = new Crops_Uploaded_Sucess_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farms_sucess_layout, container, false);
        backfeed= view.findViewById(R.id.back_feed1);
        next= view.findViewById(R.id.go_to_home);
        sucess_txt= view.findViewById(R.id.sucess_txt);
        sucess_txt.setText("Crops Added Sucessfully");



        Status_bar_change_singleton.getInstance().color_change(getActivity());

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

/*
                    selectedFragment = Home_Menu_Fragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    return true;*/
                }
                return false;
            }
        });


        backfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* selectedFragment = Home_Menu_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();*/

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  selectedFragment = Home_Menu_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();*/


            }
        });

        return view;
    }
}

