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
import android.widget.TextView;

import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;


public class Shop_Location_Fragment extends Fragment {
    Fragment selectedFragment;
    LinearLayout Continue,back_feed;
    ImageView select_location,capture_image;
    TextView select_lcn_text,camera_txt,continue_txt,toolbar_title,shop_heading,photo_heading;
    JSONObject lngObject;
    SessionManager sessionManager;


    public static Shop_Location_Fragment newInstance() {
        Shop_Location_Fragment fragment = new Shop_Location_Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selectcurrentlocation, container, false);

        sessionManager = new SessionManager(getActivity());

        //  Status_bar_change_singleton.getInstance().color_change(getActivity());

        Continue = view.findViewById(R.id.continu_btn);
        back_feed = view.findViewById(R.id.back_feed);
        select_location = view.findViewById(R.id.select_location);
        capture_image = view.findViewById(R.id.capture_image);
        select_lcn_text = view.findViewById(R.id.txt);
        camera_txt = view.findViewById(R.id.txhgt);
        continue_txt = view.findViewById(R.id.apply_loan);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        shop_heading = view.findViewById(R.id.lctn_heading);
        photo_heading = view.findViewById(R.id.photo_heading);



        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));

            continue_txt.setText(lngObject.getString("PROCEED".replace("\n","")));
            select_lcn_text.setText(lngObject.getString("SelectLocation".replace("\n","")));
            toolbar_title.setText(lngObject.getString("LocationandImage"));
            shop_heading.setText(lngObject.getString("SelectShopLocation"));
            photo_heading.setText(lngObject.getString("CaptureFirmShopPhoto"));
            camera_txt.setText(lngObject.getString("CapturePhoto"));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
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
                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
                    return true;
                }
                return false;
            }
        });


        select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("Edit_Fragment", "shoplctn_frag");
                selectedFragment = Shop_Current_Location_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.addToBackStack("shop_locatn");
                transaction.commit();

            }
        });


        capture_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Bundle bundle= new Bundle();
                bundle.putString("status", "shop");*/
                selectedFragment = Shop_Camera_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                // selectedFragment.setArguments(bundle);
                transaction.addToBackStack("shop_cameraa");
                transaction.commit();


            }
        });

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                selectedFragment = GuidelinesFragment.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout1, selectedFragment);
//                transaction.commit();

            }
        });





        return view;
    }
}

