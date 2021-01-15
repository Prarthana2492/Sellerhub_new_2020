package com.FarmPe.SellerHub.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
import com.FarmPe.SellerHub.Adapter.Spices_Category_Adapter;
import com.FarmPe.SellerHub.Bean.MainVerticalBean;
import com.FarmPe.SellerHub.Bean.Sellbean;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.Urls;
import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Spices_Category_Fragment extends Fragment {


   public static RecyclerView recyclerView;
   public static Spices_Category_Adapter livestock_types_adapter;
   Fragment selectedFragment = null;
   TextView toolbar_title;
   public static String livestock_status;
   LinearLayout back_feed,linearLayout,search_layout;
   JSONArray get_categorylist_array;
   public static ArrayList<MainVerticalBean> newOrderBeansList = new ArrayList<>();
   JSONArray get_soiltype;
   Sellbean addTractorBean3;
  public static String sellingid,sell_navigation1;


   public static Spices_Category_Fragment newInstance() {
       Spices_Category_Fragment fragment = new Spices_Category_Fragment();
       return fragment;
   }


   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.livestock_recy_layout, container, false);
       Status_bar_change_singleton.getInstance().color_change(getActivity());





       recyclerView=view.findViewById(R.id.recycler_what_looking);
       toolbar_title=view.findViewById(R.id.setting_tittle);
       toolbar_title.setText("Select Category");
       back_feed=view.findViewById(R.id.back_feed);
       linearLayout = view.findViewById(R.id.linearLayout);
       search_layout = view.findViewById(R.id.search_lay);
       search_layout.setVisibility(View.GONE);
      // sellingid = getArguments().getString("status");

//
//       if (getArguments() != null) {
//           sell_navigation1 = getArguments().getString("navg_from1");
//
//       }

       back_feed.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentManager fm = getActivity().getSupportFragmentManager();
               fm.popBackStack ("spicescateory", FragmentManager.POP_BACK_STACK_INCLUSIVE);
           }
       });





       view.setFocusableInTouchMode(true);
       view.requestFocus();
       view.setOnKeyListener(new View.OnKeyListener() {


           @Override
           public boolean onKey(View v, int keyCode, KeyEvent event) {
               if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                   FragmentManager fm = getActivity().getSupportFragmentManager();
                   fm.popBackStack ("spicescateory", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                   return true;
               }
               return false;
           }
       });


       newOrderBeansList.clear();
       // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
       GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
       recyclerView.setLayoutManager(mLayoutManager_farm);
       recyclerView.setItemAnimator(new DefaultItemAnimator());



       MainVerticalBean bean = new MainVerticalBean("Fenugreek","1","",R.drawable.fenugreek_2);
       newOrderBeansList.add(bean);
       MainVerticalBean bean1 = new MainVerticalBean("Fennel","1","",R.drawable.fennel);
       newOrderBeansList.add(bean1);
       MainVerticalBean bean2 = new MainVerticalBean("Celery","1","",R.drawable.celery);
       newOrderBeansList.add(bean2);
       MainVerticalBean bean3 = new MainVerticalBean("Cinnamon","1","",R.drawable.cinnamon);
       newOrderBeansList.add(bean3);
       MainVerticalBean bean4= new MainVerticalBean("Cambodge","1","",R.drawable.cambodge);
       newOrderBeansList.add(bean4);

       livestock_types_adapter=new Spices_Category_Adapter(getActivity(),newOrderBeansList);
       recyclerView.setAdapter(livestock_types_adapter);
       livestock_types_adapter.notifyDataSetChanged();


//       try{
//
//           newOrderBeansList.clear();
//           JSONObject jsonObject = new JSONObject();
//           jsonObject.put("SellingTypeId",sellingid);
//           Crop_Post.crop_posting(getActivity(), Urls.GetSellingCategoryList, jsonObject, new VoleyJsonObjectCallback() {
//               @Override
//               public void onSuccessResponse(JSONObject result) {
//
//                   System.out.println("GetSellingTypeeeeeeee"+result);
//
//
//                   try{
//
//                       get_soiltype = result.getJSONArray("SellingCategoryList");
//
//                       for(int i=0;i<get_soiltype.length();i++){
//
//                           JSONObject jsonObject1 = get_soiltype.getJSONObject(i);
//                           addTractorBean3 = new Sellbean(jsonObject1.getString("SellingCategoryName"),jsonObject1.getString("SellingCategoryId"),jsonObject1.getString("SellingCategoryIcon"));
//
//                           newOrderBeansList.add(addTractorBean3);
//                       }
//                       livestock_types_adapter.notifyDataSetChanged();
//
//                   }catch (Exception e){
//                       e.printStackTrace();
//                   }
//               }
//           });
//
//       }catch (Exception e){
//           e.printStackTrace();
//       }



       return view;
   }



}
