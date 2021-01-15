package com.FarmPe.SellerHub.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
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

import com.FarmPe.SellerHub.Bean.Inventory_Static_Bean;
import com.FarmPe.SellerHub.Fragment.AddProductFragment;
import com.FarmPe.SellerHub.Fragment.SellDetailsEditFragment;
import com.FarmPe.SellerHub.Fragment.Spices_Details_Fragment;
import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
import com.FarmPe.SellerHub.Bean.InventoryBean;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONObject;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder> {

    private List<Inventory_Static_Bean> productList;
    Activity activity;
    Fragment selectedFragment;
    public static String prod_name,mrp,brand,prod_id,amount,quantity,status,ProductId,offer_price,deliver_charges,isofferactive,exp_date,prod_img;
    SessionManager sessionManager;
    LinearLayout linear_layout;
    public static String sellingedit_id,selling_masterid,selling_categoryid,edit_image;

    public InventoryAdapter(Activity activity, List<Inventory_Static_Bean> moviesList) {
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
     final Inventory_Static_Bean products = productList.get(position);

        holder.name.setText(products.getProduct_name());
//
//     if(products.getProd_name().equals("")){
//         holder.name.setText(products.getBrand());
//     }else {
//         holder.name.setText(products.getProd_name());
//     }

        holder.weight.setText(products.getQuantity());
       // holder.price.setText("Rs "+products.getAmount());
//        if (products.getMrp().equals(products.getAmount){
//            holder.actual_price.setVisibility(View.INVISIBLE);
//            holder.mrp_text.setVisibility(View.INVISIBLE);
//        }else{
//            holder.actual_price.setText("₹"+products.getMrp());
//            holder.actual_price.setBackground(activity.getResources().getDrawable(R.drawable.line));
//          //  holder.actual_price.setPaintFlags(holder.actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        }

        holder.price.setText("Rs "+products.getPrice());
        holder.actual_price.setText("₹ "+products.getMrp());
        holder.actual_price.setBackground(activity.getResources().getDrawable(R.drawable.line));


      /*  if (products.getOffer_price().equals("0")){
            holder.off_text.setVisibility(View.GONE);
            holder.price.setText("Rs "+products.getAmount());

        }else{
            holder.off_text.setVisibility(View.VISIBLE);
           // holder.off_text.setText(products.getOffer_price()+"%"+"\n off");
            holder.price.setText("Rs "+products.getOffer_price());

            double off_price_calcu=(((Double.parseDouble(products.getMrp())-Double.parseDouble(products.getOffer_price()))/(Double.parseDouble(products.getMrp())))*100);
            System.out.println("jhfdiueshfr"+off_price_calcu);
            int offer_per_int=(int)off_price_calcu;
            String off_price_text=String.valueOf(offer_per_int);
            holder.off_text.setText(off_price_text+"%");

        }*/

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   ProductId=products.getSelling_cat_id();

                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.delete_details_popup);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                TextView ok=dialog.findViewById(R.id.ok);
                TextView cancel=dialog.findViewById(R.id.cancel);



                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });


//                ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        try{
//                            JSONObject jsonObject  = new JSONObject();
//                            jsonObject.put("SellingDetailsId",ProductId);
//                            jsonObject.put("UserId",sessionManager.getRegId("userId"));
//
//                            System.out.println("bank_dvvvvetails_iddd"+jsonObject);
//
//                            Crop_Post.crop_posting(activity, Urls.DeleteProductDetails, jsonObject, new VoleyJsonObjectCallback() {
//                                @Override
//                                public void onSuccessResponse(JSONObject result) {
//                                    System.out.println("111111dddd" + result);
//
//                                    try{
//
//                                        status = result.getString("Status");
//
//                                        if(status.equals("1")){
//
//                                            productList.remove(position);
//                                            notifyDataSetChanged();
//                                            System.out.println("jdhjahdjkah"+productList.size());
//
//                                        }
//
//                                    }catch (Exception e){
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//
//
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//
//                        dialog.dismiss();
//
//                    }
//                });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

        Glide.with(activity).load(products.getProduct_icon())
                .thumbnail(0.5f)
                //.crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                        .error(R.drawable.veg))
                .into(holder.prod_img_fix);



        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Bundle bundle = new Bundle();
//                bundle.putString("navg_from","invtry_details");
//                bundle.putString("Edit_Id",sellingedit_id);
//                bundle.putString("EditMaster_Id",selling_masterid);
//                bundle.putString("EditCategory_Id",selling_categoryid);
//                bundle.putString("Quality",edit_quality.getText().toString());
//                bundle.putString("Variety",edit_variety.getText().toString());
//                bundle.putString("Minquantity",edit_quantity.getText().toString());
//                bundle.putString("Minprice",SMinPrice);
//                bundle.putString("Maxprice",SMaxPrice);
//                bundle.putString("UOM",UOM);


               /* prod_name=products.getProd_name();
                brand=products.getBrand();
                offer_price=products.getOffer_price();
                quantity=products.getQuantity();
                amount=products.getAmount();
                deliver_charges=products.getDelivery_charge();
                exp_date=products.getExp_date();
                isofferactive=products.getIsOfferActive();
                mrp=products.getMrp();
                prod_id=products.getProd_desc();
                prod_img=products.getProd_icon();
                Bundle bundle=new Bundle();
                bundle.putString("selling_catid",products.getSelling_cat_id());
                bundle.putString("productlistid",products.getProductlistId());
                bundle.putString("sellingmasterid",products.getSelling_master_id());
                bundle.putString("sellingtypeid",products.getSellingTypeId());*/
              //  bundle.putString("prod_img",products.getProd_icon());
             //   bundle.putString("prod_name",products.getProd_name());

//                Bundle bundle = new Bundle();
//                bundle.putString("navg_from","invtry_details");
//                bundle.putString("Edit_Id",products.getSelling_cat_id());
//                bundle.putString("EditMaster_Id",products.getSelling_master_id());
//                bundle.putString("EditCategory_Id",products.getExp_date());
//                bundle.putString("Quality",products.getDelivery_charge());
//                bundle.putString("Variety",products.getOffer_price());
//                bundle.putString("Minquantity",products.getQuantity());
//                bundle.putString("Minprice",products.getAmount());
//                bundle.putString("Maxprice",products.getMrp());
//                bundle.putString("UOM",products.getProd_desc());


                selectedFragment = SellDetailsEditFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("confirm");
//                selectedFragment.setArguments(bundle);
                transaction.commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
