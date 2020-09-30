package com.FarmPe.SellerHub.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.FarmPe.SellerHub.Bean.TodayPaymentBean;
import com.FarmPe.SellerHub.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class TodayPaymentAdapter extends RecyclerView.Adapter<TodayPaymentAdapter.MyViewHolder>  {
    private List<TodayPaymentBean> productList;
    Activity activity;
    Fragment selectedFragment;
    String name;


    public static CardView cardView;
    public TodayPaymentAdapter(Activity activity, List<TodayPaymentBean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,order_no,credited_to,date,amount;
        public ImageView image,next;


        public MyViewHolder(View view) {
            super(view);
            image=view.findViewById(R.id.image);
          //  name=view.findViewById(R.id.name);
            order_no=view.findViewById(R.id.order_no);
            credited_to=view.findViewById(R.id.credited_to);
            date=view.findViewById(R.id.date);
            amount=view.findViewById(R.id.amount);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todat_payment_item, parent, false);
        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TodayPaymentBean products1 = productList.get(position);

    //  holder.name.setText(products1.getName()+", "+products1.getMob_no());
        holder.order_no.setText("Order # : "+products1.getOrder_no());
        holder.credited_to.setText("Credited To : "+products1.getCredited_to());
        holder.date.setText(products1.getDate().substring(0,10));
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);;
        formatter .applyPattern("##,##,##,###");
      //  double rate_double1= (Double.parseDouble(products1.getMob_no()));
        holder.amount.setText("Rs."+products1.getMob_no());
        Glide.with(activity).load(R.drawable.received_from)
                .thumbnail(0.5f)
                // .crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_gallery__default))
                .into(holder.image);


      /* holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFragment = OrderDetailsFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("track1");
                transaction.commit();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}