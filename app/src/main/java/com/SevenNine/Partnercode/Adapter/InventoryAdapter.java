package com.SevenNine.Partnercode.Adapter;

import android.app.Activity;
import android.graphics.Paint;
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

import com.SevenNine.Partnercode.Bean.InventoryBean;
import com.SevenNine.Partnercode.Bean.Sellbean;
import com.SevenNine.Partnercode.Fragment.InventoryList;
import com.SevenNine.Partnercode.Fragment.What_Are_looking;
import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONObject;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder> {

    private List<InventoryBean> productList;
    Activity activity;
    Fragment selectedFragment;
    public static String prod_name,mrp,brand,prod_id,amount,quantity,status;
    SessionManager sessionManager;
    LinearLayout linear_layout;


    public InventoryAdapter(Activity activity, List<InventoryBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image,prod_img_fix;
        public LinearLayout item;
        public TextView name,weight,price,actual_price,edit;


        public MyViewHolder(View view) {
            super(view);

            prod_img_fix=view.findViewById(R.id.prod_img_fix);
            item=view.findViewById(R.id.item);
            name=view.findViewById(R.id.f_name);
            weight=view.findViewById(R.id.weight);
            price=view.findViewById(R.id.price);
            actual_price=view.findViewById(R.id.actual_price);
            edit=view.findViewById(R.id.edit);
            linear_layout=view.findViewById(R.id.linear_layout);

            sessionManager=new SessionManager(activity);

        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())


                .inflate(R.layout.cat_prod_detai_item, parent, false);
        return new MyViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
     final InventoryBean products = productList.get(position);

        holder.name.setText(products.getProd_name());
        holder.weight.setText(products.getQuantity()+" Kg");
        holder.price.setText("Rs "+products.getAmount());
        holder.actual_price.setText("₹"+products.getMrp());
        holder.actual_price.setPaintFlags(holder.actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        Glide.with(activity).load(products.getProd_icon())
                .thumbnail(0.5f)
                //.crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_gallery__default))
                .into(holder.prod_img_fix);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prod_name=products.getProd_name();
                brand=products.getBrand();
                quantity=products.getQuantity();
                amount=products.getAmount();
                mrp=products.getMrp();
                prod_id=products.getProd_desc();

                selectedFragment = What_Are_looking.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("spicescateorye");
                transaction.commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
