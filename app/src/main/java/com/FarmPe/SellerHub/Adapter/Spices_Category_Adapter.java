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

import com.FarmPe.SellerHub.Bean.MainVerticalBean;
import com.FarmPe.SellerHub.Bean.Sellbean;
import com.FarmPe.SellerHub.Fragment.Spices_CameraFragment;
import com.FarmPe.SellerHub.Fragment.Spices_Category_Fragment;
import com.FarmPe.SellerHub.Fragment.Spices_Fragment;
import com.FarmPe.SellerHub.Fragment.What_Areu_Selling_Fragment;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.Urls;
import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class Spices_Category_Adapter extends RecyclerView.Adapter<Spices_Category_Adapter.MyViewHolder> {

    private List<MainVerticalBean> productList;
    Activity activity;
    Fragment selectedFragment;
    JSONArray get_soiltype;
    public  static String selling_category_id,sellingname;


    private String[] neededPermissions = new String[]{CAMERA, WRITE_EXTERNAL_STORAGE};
    public Spices_Category_Adapter(Activity activity, List<MainVerticalBean> moviesList) {
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
        final MainVerticalBean products = productList.get(position);

        selling_category_id=products.getId();
        sellingname=products.getName();

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


                selectedFragment = Spices_CameraFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("spices_category_adapter");
                transaction.commit();

//
//                selling_category_id=products.getId();
//                sellingname=products.getName();
//                try{
//
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("SellingCategoryId",selling_category_id);
//
//
//
//                    Crop_Post.crop_posting(activity, Urls.GetSellingList, jsonObject, new VoleyJsonObjectCallback() {
//                        @Override
//                        public void onSuccessResponse(JSONObject result) {
//                            System.out.println("GetSellingTypeeeeeeee"+result);
//
//
//                            try{
//                                get_soiltype = result.getJSONArray("SellingList");
//
//                                if(get_soiltype.length()== 0) {
//                                    System.out.println("iddddddddddddddddddddddddddddddddddddddddd"+get_soiltype);
//
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("status",selling_category_id);
//                                    bundle.putString("status","scatgry_adapter");
//
//                                    bundle.putString("navg_from2", Spices_Category_Fragment.sell_navigation1);
//                                    if (What_Areu_Selling_Fragment.sellnavigation!=null){
//                                        bundle.putString("navg_from2", "true");
//
//
//                                    }
//                                    sellingname=products.getName();
//                                    selectedFragment = Spices_CameraFragment.newInstance();
//                                    FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
//                                    transaction.replace(R.id.frame_layout1, selectedFragment);
//                                    transaction.addToBackStack("spices_category_adapter");
//                                    selectedFragment.setArguments(bundle);
//                                    transaction.commit();
//
//                                }else
//                                {
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("status",selling_category_id);
//                                    bundle.putString("navg_from2", Spices_Category_Fragment.sell_navigation1);
//                                    System.out.println("spicescategoryId"+selling_category_id);
//                                    selectedFragment = Spices_Fragment.newInstance();
//                                    FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
//                                    transaction.replace(R.id.frame_layout1, selectedFragment);
//                                    transaction.addToBackStack("spicesfragment");
//                                    selectedFragment.setArguments(bundle);
//                                    transaction.commit();
//                                }
//
//
//
//
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
//                    });

//                }catch (Exception e){
//                    e.printStackTrace();
//                }



            }
        });



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }



}
