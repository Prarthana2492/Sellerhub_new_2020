package com.SevenNine.Partnercode.Activity;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.SevenNine.Partnercode.Fragment.FirmShopDetailsFragment;
import com.SevenNine.Partnercode.R;

public class FirmShopDetailsActivity extends AppCompatActivity  {

    Fragment selectedFragment = null;
   public static LinearLayout bottom_linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firm);



        selectedFragment = FirmShopDetailsFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout1, selectedFragment);
        transaction.commit();

    }


}
