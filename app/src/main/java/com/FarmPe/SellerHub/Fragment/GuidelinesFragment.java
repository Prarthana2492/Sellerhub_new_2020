package com.FarmPe.SellerHub.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
import com.FarmPe.SellerHub.Adapter.Guidelines_Slider_Adapter;
import com.FarmPe.SellerHub.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class GuidelinesFragment extends Fragment {

    Fragment selectedFragment;
    LinearLayout Continue;
    List<Integer> image_arraylist = new ArrayList<Integer>();

    private int[] myImageList = new int[]{R.drawable.guideline1, R.drawable.guideline2,
            R.drawable.guideline3};

    ViewPager slider;
    LinearLayout ll_dots;
    Guidelines_Slider_Adapter guidelinesSliderAdapter;
    Timer timer;

    public static GuidelinesFragment newInstance() {
        GuidelinesFragment fragment = new GuidelinesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guideslider_layout, container, false);

        Status_bar_change_singleton.getInstance().color_change(getActivity());

        Continue = view.findViewById(R.id.continuebtn);
        slider = view.findViewById(R.id.vp_slider);
        ll_dots = view.findViewById(R.id.dots);

        /*image_arraylist.clear();
        image_arraylist.add(R.drawable.guideline1);
        image_arraylist.add(R.drawable.guideline2);
        image_arraylist.add(R.drawable.guideline3);*/

        guidelinesSliderAdapter = new Guidelines_Slider_Adapter(getActivity(), myImageList);
        slider.setAdapter(guidelinesSliderAdapter);


        addBottomDots(0, ll_dots);



        slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("cddsd = "+position);;
                addBottomDots(position,  ll_dots);
                // page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       /* view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    HomeMenuFragment.onBack_status = "farms";

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("your_farm", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    return true;
                }
                return false;
            }
        });*/


        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = VerificationFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("guide");
                transaction.commit();

            }
        });







        return view;
    }

    private void addBottomDots(int currentPage, LinearLayout ll_dots) {
        TextView[] dots = new TextView[myImageList.length];
        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            // System.out.println("dots_lengthhh"+dots.length);
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#999999")); //bg color
            ll_dots.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#EC4848")); // flip color
    }
}

