package com.SevenNine.Partnercode.Adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SevenNine.Partnercode.Bean.NewOrderBean;
import com.SevenNine.Partnercode.Fragment.OrderDetailsFragment;
import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;
import com.SevenNine.Partnercode.Urls;
import com.SevenNine.Partnercode.Volly_class.Login_post;
import com.SevenNine.Partnercode.Volly_class.VoleyJsonObjectCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class AcceptedOrderAdapter extends RecyclerView.Adapter<AcceptedOrderAdapter.MyViewHolder>  {
    private List<NewOrderBean> productList;
    Activity activity;
    Fragment selectedFragment;
    String name;
    SessionManager sessionManager;

    public static CardView cardView;
    public AcceptedOrderAdapter(Activity activity, List<NewOrderBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView prod_name,dispatched_date,next,accept,quantity,mrp;
        public ImageView image;


        public MyViewHolder(View view) {
            super(view);
            image=view.findViewById(R.id.image);
            prod_name=view.findViewById(R.id.prod_name);
            dispatched_date=view.findViewById(R.id.dispatched);
            next=view.findViewById(R.id.arrow);
            accept=view.findViewById(R.id.accept);
            quantity=view.findViewById(R.id.quantity);
            mrp=view.findViewById(R.id.mrp);
           sessionManager=new SessionManager(activity);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.accept_order_item, parent, false);
        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NewOrderBean products1 = productList.get(position);

        System.out.println("ordreadapterrrr" + products1.getUom());
        if (products1.getProd_desc().equals("")){
            holder.prod_name.setText(products1.getProd_name()+", "+products1.getBrand()+", "+products1.getSellingCategoryName());

        }else{
            holder.prod_name.setText(products1.getProd_name()+", "+products1.getBrand()+", "+products1.getProd_desc());

        }
        holder.dispatched_date.setText("₹"+(products1.getAmount()));
        holder.quantity.setText(products1.getQuantity()+" Kg");
        holder.mrp.setText("₹"+products1.getFirstname());
        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        Glide.with(activity).load(products1.getProducts_Icon())
                .thumbnail(0.5f)
                // .crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                        .error(R.drawable.veg))
                .into(holder.image);


        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("createdon", products1.getCreatedOn());
                bundle.putString("Amount", products1.getAmount());
                bundle.putString("ProdName", products1.getProd_name());
                bundle.putString("quantity", products1.getQuantity());
                bundle.putString("product_info", products1.getProductInfo());
                bundle.putString("prod_img", products1.getProd_img());
                if (products1.getSellingListName()==null){
                    bundle.putString("pay_mode", "COD");
                }else{
                    bundle.putString("pay_mode", "Online Payment");

                }
                bundle.putString("uom", products1.getUom());
                selectedFragment = OrderDetailsFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("track2");
                selectedFragment.setArguments(bundle);
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}