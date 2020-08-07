package com.SevenNine.Partnercode.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.SevenNine.Partnercode.Adapter.OffersAdapter;
import com.SevenNine.Partnercode.Adapter.OrderDetailsAdapter;
import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONObject;


public class PreviewProductDetails extends Fragment {
    public static RecyclerView recyclerView;
    LinearLayout back_feed;
    SessionManager sessionManager;
    JSONObject lngObject;
    Fragment selectedFragment;
    TextView toolbar_title,prod_name,price,actual_price,offer_perc,abt_product;
    ImageView prod_img;

    public static PreviewProductDetails newInstance() {
        PreviewProductDetails fragment = new PreviewProductDetails();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preview_prod_details, container, false);
     //   back_feed=view.findViewById(R.id.back_feed);
        prod_name=view.findViewById(R.id.prod_name);
        price=view.findViewById(R.id.price);
        actual_price=view.findViewById(R.id.actual_price);
        offer_perc=view.findViewById(R.id.offer_perc);
        abt_product=view.findViewById(R.id.abt_product);
        prod_img=view.findViewById(R.id.prod_img);


        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.dark_green));
       /* back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });*/


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                   /* FragmentManager fm = getFragmentManager();
                    fm.popBackStack();*/
                    selectedFragment = OffersListFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.addToBackStack("track24");
                    transaction.commit();
                    return true;
                }
                return false;

            }
        });

      /*  prod_name.setText(getArguments().getString("product_name_st"));
        price.setText(getArguments().getString("product_price_st"));
        actual_price.setText(getArguments().getString("product_mrp_st"));
        abt_product.setText(getArguments().getString("product_name_st")+", "+getArguments().getString("prod_brand_st")+", "+getArguments().getString("prod_quant"));
*/
        prod_name.setText(OffersAdapter.product_name_st);
      //  price.setText("Rs"+OffersAdapter.product_price_st);
        actual_price.setText("₹"+OffersAdapter.product_mrp_st);
        if (OffersAdapter.off_price.equals("")){
            price.setText("Rs "+OffersAdapter.amount);
            double off_price_calcu=(((Double.parseDouble(OffersAdapter.product_mrp_st)-Double.parseDouble(OffersAdapter.product_price_st))/(Double.parseDouble(OffersAdapter.product_mrp_st)))*100);
            System.out.println("jhfdiueshfr"+off_price_calcu);
            int offer_per_int=(int)off_price_calcu;
            String off_price_text=String.valueOf(offer_per_int);
            offer_perc.setText(off_price_text+"%");
        }else{
            price.setText("Rs "+OffersAdapter.off_price);
            double off_price_calcu=(((Double.parseDouble(OffersAdapter.product_mrp_st)-Double.parseDouble(OffersAdapter.off_price))/(Double.parseDouble(OffersAdapter.product_mrp_st)))*100);
            System.out.println("jhfdiueshfr"+off_price_calcu);
            int offer_per_int=(int)off_price_calcu;
            String off_price_text=String.valueOf(offer_per_int);
            offer_perc.setText(off_price_text+"%");
        }
        if (OffersAdapter.prod_brand_st.equals("")){
            abt_product.setText(OffersAdapter.product_name_st+", "+OffersAdapter.prod_quant+" Kg");

        }else{
            abt_product.setText(OffersAdapter.product_name_st+", "+OffersAdapter.prod_brand_st+", "+OffersAdapter.prod_quant+" Kg");

        }

       /* double off_price_calcu=(((Double.parseDouble(OffersAdapter.product_mrp_st)-Double.parseDouble(OffersAdapter.product_price_st))/(Double.parseDouble(OffersAdapter.product_mrp_st)))*100);
        System.out.println("jhfdiueshfr"+off_price_calcu);
        int offer_per_int=(int)off_price_calcu;
        String off_price_text=String.valueOf(offer_per_int);
        offer_perc.setText(off_price_text+"%");*/

        Glide.with(getActivity()).load(OffersAdapter.prod_img_st)
                .thumbnail(0.5f)
                //.crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_gallery__default))
                .into(prod_img);
    //    LoanInformation();


        return view;
    }


}
