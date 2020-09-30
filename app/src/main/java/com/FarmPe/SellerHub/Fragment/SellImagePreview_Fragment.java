package com.FarmPe.SellerHub.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
import com.FarmPe.SellerHub.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


public class SellImagePreview_Fragment extends Fragment {

    Fragment selectedFragment;
    ImageView b_arrow;
    public static  String imageUri;
    ImageView imageView;
    LinearLayout back_arrow,name_layout;
    TextView Continue,take_photo_again;
    public  static  String sellingmaster_id,sellinglistnamee,sell_navigation4;


    public static SellImagePreview_Fragment newInstance() {
        SellImagePreview_Fragment fragment = new SellImagePreview_Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_background_layout, container, false);


        getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);



        Status_bar_change_singleton.getInstance().color_change(getActivity());


        imageView=view.findViewById(R.id.capt_img);
        back_arrow=view.findViewById(R.id.back_feed);
        name_layout=view.findViewById(R.id.fname_layout);
        name_layout.setVisibility(View.GONE);
        Continue=view.findViewById(R.id.continue_4);
        take_photo_again=view.findViewById(R.id.take_photo);
        Continue.setText("CONTINUE");

        String id= (getArguments().getString("sellname"));
        if (getArguments() != null) {


            sell_navigation4 = (getArguments().getString("navg_from4"));
        }
        imageUri = id;


        Glide.with(getActivity()).load("file://" + id)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)
                .skipMemoryCache(true))
                .into(imageView);


        sellingmaster_id = getArguments().getString("sellinglist_id");
        sellinglistnamee = getArguments().getString("sellinglist_name");


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                   /* selectedFragment = Spices_CameraFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();*/

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("camera", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }

                return false;

            }
        });



        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* selectedFragment = Spices_CameraFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
*/

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("camera", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        take_photo_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("camera", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("sellinglistM_id",sellingmaster_id);
                bundle.putString("sellinglistM_name",sellinglistnamee);
                bundle.putString("navg_from5",sell_navigation4);
                selectedFragment = Spices_Details_Fragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("camerasell");
                selectedFragment.setArguments(bundle);
                transaction.commit();
            }
        });

        return view;
    }
}
