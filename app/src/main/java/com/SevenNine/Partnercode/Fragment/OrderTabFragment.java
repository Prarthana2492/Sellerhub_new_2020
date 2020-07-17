package com.SevenNine.Partnercode.Fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.TabLayoutAdapter.PagerOrder;


import org.json.JSONObject;

public class OrderTabFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    Fragment selectedFragment;
    FloatingActionButton compose_msg;

    JSONObject jsonObject;
    String bmmvendorstoreid;
    public static TabLayout tabLayout;
    public static TextView last_month_text;
    private ViewPager viewPager;

    public static OrderTabFragment newInstance() {
        OrderTabFragment fragment = new OrderTabFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_tab_lay, container, false);
     //  compose_msg=view.findViewById(R.id.fab__compose);
        tabLayout = view.findViewById(R.id.simpleTabLayout_land);
        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.addTab(tabLayout.newTab().setText("Accepted"));
        tabLayout.addTab(tabLayout.newTab().setText("Dispatched"));
        tabLayout.addTab(tabLayout.newTab().setText("Cancelled"));


        viewPager = view.findViewById(R.id.simpleViewPager_tab);

        PagerOrder adapter = new PagerOrder(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);

        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
