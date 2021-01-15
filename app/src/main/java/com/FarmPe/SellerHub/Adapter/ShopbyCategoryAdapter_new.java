package com.FarmPe.SellerHub.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.SellerHub.Bean.MainAdapterBean1;
import com.FarmPe.SellerHub.Bean.MainVerticalBean;
import com.FarmPe.SellerHub.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ShopbyCategoryAdapter_new extends RecyclerView.Adapter<ShopbyCategoryAdapter_new.MyViewHolder> {

    private List<MainAdapterBean1> productList;
    Activity activity;
    Fragment selectedFragment;
    public static String sellingtypeid,itemid;
    public int mSelectedItem = -1;
    public static ArrayList<MainVerticalBean> newOrderBeansList = new ArrayList<>();
    JSONArray get_soiltype;
    public static ShopByCategoryAdapterNext livestock_types_adapter;
    private static int currentPosition = 0;

    public ShopbyCategoryAdapter_new(Activity activity, List<MainAdapterBean1> moviesList) {
        this.productList = moviesList;
        this.activity=activity;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView main_img,more_img;
        public LinearLayout item;
        public TextView name;
        public RecyclerView recyclerView;


        public MyViewHolder(View view) {
            super(view);

            main_img=view.findViewById(R.id.prod_img);
            item=view.findViewById(R.id.item);
            name=view.findViewById(R.id.f_name);
            more_img=view.findViewById(R.id.more_img);
            recyclerView=view.findViewById(R.id.recycler_what_looking);

        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())


                .inflate(R.layout.select_main_layout_shop, parent, false);
        return new MyViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
     final MainAdapterBean1 products = productList.get(position);
      sellingtypeid=products.getId();
        holder.name.setText(products.getName());
        holder.recyclerView.setVisibility(View.GONE);
        holder.more_img.setImageResource(R.drawable.ic_more);

        Glide.with(activity).load(products.getImage())
                .thumbnail(0.5f)
                //.crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                        .error(R.drawable.fruit))
                .into(holder.main_img);

        if (currentPosition==position){
            holder.more_img.setImageResource(R.drawable.ic_minus);
            newOrderBeansList.clear();
            //  livestock_types_adapter.notifyDataSetChanged();
          holder.recyclerView.setVisibility(View.VISIBLE);
            // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
            GridLayoutManager mLayoutManager_farm = new GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false);
            holder.recyclerView.setLayoutManager(mLayoutManager_farm);


            holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
            MainVerticalBean bean = new MainVerticalBean("Biscuits","1","",R.drawable.biscuits);
         newOrderBeansList.add(bean);
            MainVerticalBean bean1 = new MainVerticalBean("Chips","1","",R.drawable.chips);
        newOrderBeansList.add(bean1);
            MainVerticalBean bean2 = new MainVerticalBean("Namkeen","1","",R.drawable.namkeen);
        newOrderBeansList.add(bean2);
            MainVerticalBean bean3 = new MainVerticalBean("Snacks","1","",R.drawable.snacks_category);
        newOrderBeansList.add(bean3);


        
            livestock_types_adapter=new ShopByCategoryAdapterNext(activity,newOrderBeansList);
            holder.recyclerView.setAdapter(livestock_types_adapter);
            livestock_types_adapter.notifyDataSetChanged();

//            try{
//
//                //  newOrderBeansList.clear();
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("SellingTypeId",products.getId());
//
//                System.out.println("jhfdfdjc111"+jsonObject);
//                Crop_Post.lang_posting(activity, Urls.GetSellingCategoryList, jsonObject, new VoleyJsonObjectCallback() {
//                    @Override
//                    public void onSuccessResponse(JSONObject result) {
//
//                        System.out.println("GetSellingTypeeeeeeee"+result);
//
//
//                        try{
//
//                            get_soiltype = result.getJSONArray("SellingCategoryList");
//
//                            for(int i=0;i<get_soiltype.length();i++){
//
////                                JSONObject jsonObject1 = get_soiltype.getJSONObject(i);
////                                MainVerticalBean sellbean = new MainVerticalBean(jsonObject1.getString("SellingCategoryName"),jsonObject1.getString("SellingCategoryId"),jsonObject1.getString("SellingCategoryIcon"));
//
//                             //   newOrderBeansList.add(sellbean);
//                            }
////                            livestock_types_adapter=new ShopByCategoryAdapterNext(activity,newOrderBeansList);
////                            holder.recyclerView.setAdapter(livestock_types_adapter);
////                            livestock_types_adapter.notifyDataSetChanged();
//
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                });
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }

        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // itemid=products.getName();
                if(holder.recyclerView.getVisibility()== View.VISIBLE){
                    //   System.out.println("jdshfkk "+holder.more_img.getDrawable());
                    holder.recyclerView.setVisibility(View.GONE);
                    holder.more_img.setImageResource(R.drawable.ic_more);
                }else{
                    currentPosition = position;
                    notifyDataSetChanged();
                }

                }

                /*sellingtypeid=products.getId();
                Bundle bundle = new Bundle();
                bundle.putString("status",sellingtypeid);
                bundle.putString("navg_from1",What_Areu_Selling_Fragment.sellnavigation);
               // bundle.putString("ID", What_Areu_Selling_Fragment.sellingdetailsid);
                System.out.println("whatareusellingtypeID"+sellingtypeid);
                selectedFragment = Spices_Category_Fragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("spicescateory");
                selectedFragment.setArguments(bundle);
                transaction.commit();*/

        });



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }










}
