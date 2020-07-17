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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.MyViewHolder>  {
    private List<NewOrderBean> productList;
    Activity activity;
    Fragment selectedFragment;
    String name;
    SessionManager sessionManager;

    public static CardView cardView;
    public NewOrderAdapter(Activity activity, List<NewOrderBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView prod_name,dispatched_date,next,accept,quantity,mrp,mrp_txt,off_text;
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
            mrp_txt=view.findViewById(R.id.mrp_txt);
            off_text=view.findViewById(R.id.off_text);
           sessionManager=new SessionManager(activity);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_new_item, parent, false);
        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NewOrderBean products1 = productList.get(position);

        System.out.println("ordreadapterrrr"+products1.getUom());
      holder.prod_name.setText(products1.getProd_name()+", "+products1.getBrand()+", "+products1.getProd_desc());
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);;
        formatter .applyPattern("##,##,##,###");
        double rate_double1= (Double.parseDouble(products1.getAmount()));
      holder.dispatched_date.setText("₹"+formatter.format(rate_double1));
      holder.quantity.setText(products1.getQuantity()+" Kg");
        holder.mrp.setText("₹"+products1.getFirstname());
        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (products1.getFirstname().equals(products1.getAmount())){
            holder.mrp.setVisibility(View.INVISIBLE);
            holder.mrp_txt.setVisibility(View.INVISIBLE);
        }else{
            holder.mrp.setText("₹"+products1.getFirstname());
            holder.mrp.setBackground(activity.getResources().getDrawable(R.drawable.line));
            //  holder.actual_price.setPaintFlags(holder.actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if (products1.getUom().equals("0")){
            holder.off_text.setVisibility(View.GONE);
        }else{
            holder.off_text.setVisibility(View.VISIBLE);
            holder.off_text.setText(products1.getUom()+"%"+"\n off");

        }
        Glide.with(activity).load(products1.getProducts_Icon())
                .thumbnail(0.5f)
                // .crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_gallery__default))
                .into(holder.image);


       holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("createdon",products1.getCreatedOn());
                bundle.putString("Amount",products1.getAmount());
                bundle.putString("ProdName",products1.getProd_name());
                bundle.putString("quantity",products1.getQuantity());
                bundle.putString("product_info",products1.getProductInfo());
                bundle.putString("prod_img",products1.getProducts_Icon());
                bundle.putString("pay_mode",products1.getMode());
                bundle.putString("uom",products1.getUom());
                bundle.putString("delivery_charges",products1.getSellingCategoryName());
                selectedFragment = OrderDetailsFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("track2");
                selectedFragment.setArguments(bundle);
                transaction.commit();
            }
        });

       holder.accept.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               JSONObject params = new JSONObject();
               try {
                   params.put("Amount",products1.getAmount());
                   params.put("PayUTransactionId",products1.getTxnId());  // amount
                   params.put("ProductInfo",products1.getProductInfo());  // amount
                   params.put("ProductId","0");  //transaction fees
                   params.put("UserId",sessionManager.getRegId("userId"));
                   params.put("SellingListName",products1.getSellingListName());
                   params.put("CategoryName",products1.getSellingCategoryName());
                   params.put("Quantity",products1.getQuantity()); //using status
                   params.put("SelectedQuantity",products1.getQuantity()); //using status
                   params.put("UnitOfPrice","Kilograms");
                   params.put("MRP",products1.getFirstname());
                   params.put("Brand",products1.getBrand());
                   params.put("SellingListIcon",products1.getProd_img());
                   params.put("ProductIcon",products1.getProducts_Icon());
                   params.put("ProductName",products1.getProd_name());
                   params.put("ProductDescription",products1.getProd_desc());
                   params.put("CustomerName","Priya");  //tarnsaction id
                   params.put("CreatedBy",sessionManager.getRegId("userId"));

                   System.out.println("RESPMsgdsfadf"+params);
                   Login_post.login_posting(activity, Urls.AddAcceptOrdersFrom7NineDetails, params, new VoleyJsonObjectCallback() {
                       @Override
                       public void onSuccessResponse(JSONObject result) {
                           System.out.println("llllllllllllllllllllllllllll"+result);
                           try {
                               System.out.println("nnnnnmnm" + result.toString());
                               String status=result.getString("Status");
                               //  if(status.equals("Success")){
                               if(status.equals("Success")){
                                   Toast toast = Toast.makeText(activity,"Order has accepted successfully", Toast.LENGTH_LONG);
                                   toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                                   toast.show();
                                   productList.remove(position);
                                   notifyDataSetChanged();
                                   //   Toast.makeText(getActivity(),"Transaction Successfully Completed",Toast.LENGTH_LONG).show();

                               }

                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }
                   });
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}