package com.FarmPe.SellerHub.Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.SellerHub.Bean.Sellbean;
import com.FarmPe.SellerHub.Fragment.Spices_CameraFragment;
import com.FarmPe.SellerHub.Fragment.Spices_Fragment;
import com.FarmPe.SellerHub.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class Spices_Adapter extends RecyclerView.Adapter<Spices_Adapter.MyViewHolder> {

    private List<Sellbean> productList;
    Activity activity;
    Fragment selectedFragment;
    public static String sellinglistid,sellinglistname;


    public Spices_Adapter(Activity activity, List<Sellbean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public LinearLayout item;
        public TextView name;


        public MyViewHolder(View view) {
            super(view);

            image=view.findViewById(R.id.prod_img);
            item=view.findViewById(R.id.item);
            name=view.findViewById(R.id.f_name);

        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())


                .inflate(R.layout.homepage_adapter_layout, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Sellbean products = productList.get(position);



        holder.name.setText(products.getName());

        Glide.with(activity).load(products.getImage())
                .thumbnail(0.5f)
                //.crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_gallery__default))
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellinglistid=products.getId();
                sellinglistname=products.getName();
                Bundle bundle = new Bundle();
                bundle.putString("status","spices_adapter");
                bundle.putString("status1",sellinglistid);
                System.out.println("a99999999999999999999999999"+sellinglistid);

              bundle.putString("navg_from3", Spices_Fragment.sell_navigation2);
                selectedFragment = Spices_CameraFragment.newInstance();
                FragmentTransaction transaction =  ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.addToBackStack("sellinglistiddetails");
                transaction.commit();
            }
        });
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

}
