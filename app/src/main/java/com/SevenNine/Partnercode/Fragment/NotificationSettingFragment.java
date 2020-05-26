package com.SevenNine.Partnercode.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.SevenNine.Partnercode.Activity.Status_bar_change_singleton;
import com.SevenNine.Partnercode.Bean.FarmsImageBean;
import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationSettingFragment extends Fragment {

    public static List<FarmsImageBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    LinearLayout back_feed,logout_layout;
    Fragment selectedFragment;
    SessionManager sessionManager;
    TextView notificatn_set,accountinfo,accountinfo1,accountinfo2,accountinfo3;
    JSONObject lngObject;


    public static NotificationSettingFragment newInstance() {
        NotificationSettingFragment fragment = new NotificationSettingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.noti_setting_layout, container, false);
        back_feed=view.findViewById(R.id.back_feed);
      //  logout_layout=view.findViewById(R.id.logout_layout);
      //  accountinfo=view.findViewById(R.id.actninfo);

       // notificatn_set=view.findViewById(R.id.toolbar_title);

        sessionManager = new SessionManager(getActivity());
        Status_bar_change_singleton.getInstance().color_change(getActivity());

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
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("settingg", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    return true;
                }
                return false;
            }
        });


     /*   try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));
            notificatn_set.setText(lngObject.getString("NotificationSetting"));
            accountinfo.setText(lngObject.getString("AccountInfo"));
            accountinfo1.setText(lngObject.getString("AccountInfo"));
            accountinfo2.setText(lngObject.getString("AccountInfo"));
            accountinfo3.setText(lngObject.getString("AccountInfo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/



        return view;
    }



}
