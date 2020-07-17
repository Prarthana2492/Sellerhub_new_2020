package com.SevenNine.Partnercode.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
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

import com.SevenNine.Partnercode.Bean.InventoryBean;
import com.SevenNine.Partnercode.Bean.Sellbean;
import com.SevenNine.Partnercode.Fragment.AddProductFragment;
import com.SevenNine.Partnercode.Fragment.InventoryList;
import com.SevenNine.Partnercode.Fragment.What_Are_looking;
import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;
import com.SevenNine.Partnercode.Urls;
import com.SevenNine.Partnercode.Volly_class.Crop_Post;
import com.SevenNine.Partnercode.Volly_class.VoleyJsonObjectCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONObject;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder> {

    private List<InventoryBean> productList;
    Activity activity;
    Fragment selectedFragment;
    public static String prod_name,mrp,brand,prod_id,amount,quantity,status,ProductId,offer_price,deliver_charges,isofferactive,exp_date;
    SessionManager sessionManager;
    LinearLayout linear_layout;


    public InventoryAdapter(Activity activity, List<InventoryBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image,prod_img_fix;
        public LinearLayout item;
        public TextView name,weight,price,actual_price,edit,delete,off_text,mrp_text;


        public MyViewHolder(View view) {
            super(view);

            prod_img_fix=view.findViewById(R.id.prod_img_fix);
            item=view.findViewById(R.id.item);
            name=view.findViewById(R.id.f_name);
            weight=view.findViewById(R.id.weight);
            price=view.findViewById(R.id.price);
            actual_price=view.findViewById(R.id.actual_price);
            edit=view.findViewById(R.id.edit);
            delete=view.findViewById(R.id.delete);
            off_text=view.findViewById(R.id.off_text);
            mrp_text=view.findViewById(R.id.mrp_text);
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
        if (products.getMrp().equals(products.getAmount())){
            holder.actual_price.setVisibility(View.INVISIBLE);
            holder.mrp_text.setVisibility(View.INVISIBLE);
        }else{
            holder.actual_price.setText("₹"+products.getMrp());
            holder.actual_price.setBackground(activity.getResources().getDrawable(R.drawable.line));
          //  holder.actual_price.setPaintFlags(holder.actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (products.getOffer_price().equals("0")){
            holder.off_text.setVisibility(View.GONE);
        }else{
            holder.off_text.setVisibility(View.VISIBLE);
            holder.off_text.setText(products.getOffer_price()+"%"+"\n off");

        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductId=products.getProd_desc();

                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.delete_details_popup);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                TextView ok=dialog.findViewById(R.id.ok);
                TextView cancel=dialog.findViewById(R.id.cancel);


                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            JSONObject jsonObject  = new JSONObject();
                            jsonObject.put("ProductId",ProductId);
                            jsonObject.put("UserId",sessionManager.getRegId("userId"));

                            System.out.println("bank_dvvvvetails_iddd"+jsonObject);

                            Crop_Post.crop_posting(activity, Urls.DeleteProductDetails, jsonObject, new VoleyJsonObjectCallback() {
                                @Override
                                public void onSuccessResponse(JSONObject result) {
                                    System.out.println("111111dddd" + result);

                                    try{

                                        status = result.getString("Status");

                                        if(status.equals("1")){

                                            productList.remove(position);
                                            notifyDataSetChanged();
                                            System.out.println("jdhjahdjkah"+productList.size());

                                        }

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });


                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        dialog.dismiss();

                    }
                });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

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
                offer_price=products.getOffer_price();
                quantity=products.getQuantity();
                amount=products.getAmount();
                deliver_charges=products.getDelivery_charge();
                exp_date=products.getExp_date();
                isofferactive=products.getIsOfferActive();
                mrp=products.getMrp();
                prod_id=products.getProd_desc();
                Bundle bundle=new Bundle();
                bundle.putString("selling_catid",products.getSelling_cat_id());
                bundle.putString("productlistid",products.getProductlistId());
                bundle.putString("sellingmasterid",products.getSelling_master_id());
                bundle.putString("sellingtypeid",products.getSellingTypeId());

                selectedFragment = AddProductFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("spicescateorye");
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
