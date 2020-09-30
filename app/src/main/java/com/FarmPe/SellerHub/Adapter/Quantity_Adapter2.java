package com.FarmPe.SellerHub.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.FarmPe.SellerHub.Bean.SelectLanguageBean;
import com.FarmPe.SellerHub.Fragment.Spices_Details_Fragment;
import com.FarmPe.SellerHub.R;

import java.util.List;

public class Quantity_Adapter2 extends RecyclerView.Adapter<Quantity_Adapter2.MyViewHolder> {

    private List<SelectLanguageBean> productList;
    Activity activity;
    Fragment selectedFragment;
    public static int quantity_id;



    public Quantity_Adapter2(Activity activity, List<SelectLanguageBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RadioGroup variety;
        public RadioButton variety_txt;


        public MyViewHolder(View view) {
            super(view);


            variety=view.findViewById(R.id.radioGroup);
            variety_txt=view.findViewById(R.id.radioButton1);

        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())


                .inflate(R.layout.quqlity_adapter, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SelectLanguageBean products = productList.get(position);
        quantity_id= products.getLanguageid();


        holder.variety_txt.setText(products.getVendor());

        holder.variety_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spices_Details_Fragment.quantity.setText(products.getVendor());
                Spices_Details_Fragment.dialog.dismiss();
            }
        });

    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
}
