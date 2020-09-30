package com.FarmPe.SellerHub.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
import com.FarmPe.SellerHub.Adapter.SelectSubCategoryAdapter;
import com.FarmPe.SellerHub.Adapter.What_Areu_Looking_Adapter;
import com.FarmPe.SellerHub.Bean.Sellbean;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SelectSubCategoryFragment extends Fragment {

    public static ArrayList<Sellbean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static SelectSubCategoryAdapter livestock_types_adapter;
    Fragment selectedFragment = null;
    TextView toolbar_title;
    public static String livestock_status;
    LinearLayout back_feed,linearLayout,search_lay;
    JSONArray get_categorylist_array;
    JSONArray get_soiltype;
    public static String sellingdetailsid,sellnavigation,sellingcat;

    public static SelectSubCategoryFragment newInstance() {
        SelectSubCategoryFragment fragment = new SelectSubCategoryFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.livestock_recy_layout, container, false);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.dark_green));
       /* Status_bar_change_singleton.getInstance().color_change(getActivity());
        HomePage_With_Bottom_Navigation.linear_bottonsheet.setVisibility(View.GONE);
        HomePage_With_Bottom_Navigation.view.setVisibility(View.GONE);*/

        recyclerView=view.findViewById(R.id.recycler_what_looking);
        toolbar_title=view.findViewById(R.id.setting_tittle);
        back_feed=view.findViewById(R.id.back_feed);
        search_lay=view.findViewById(R.id.search_lay);
        linearLayout = view.findViewById(R.id.linearLayout);
        toolbar_title.setText("Select Sub Category");

        search_lay.setVisibility(View.GONE);
        // sellingdetailsid=Inventory_Details_Fragment.SId;
        System.out.println("selleditiddd"+sellingdetailsid);
        sellingcat = getArguments().getString("status");

       /* if (getArguments() != null) {
            sellnavigation= String.valueOf(getArguments().getString("navg_from").equals("invtry_details"));
        }*/


        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();

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
       /* Sellbean bean = new Sellbean("Cookies","1",R.drawable.biscuit_cookies);
        newOrderBeansList.add(bean);
        Sellbean bean1 = new Sellbean("Cream Biscuits","1",R.drawable.biscuit_cream);
        newOrderBeansList.add(bean1);
        Sellbean bean2 = new Sellbean("Digestive","1",R.drawable.digestive);
        newOrderBeansList.add(bean2);
        Sellbean bean3 = new Sellbean("Milk & Glucose","1",R.drawable.biscuit_milk_glucose);
        newOrderBeansList.add(bean3);
        Sellbean bean4 = new Sellbean("Salted","1",R.drawable.biscuit_salted);
        newOrderBeansList.add(bean4);
        Sellbean bean5 = new Sellbean("Cheeslets","1",R.drawable.biscuit_cheeslets);
        newOrderBeansList.add(bean5);
        Sellbean bean6 = new Sellbean("Wafers & Rusks","1",R.drawable.wafers_rusk);
        newOrderBeansList.add(bean6);*/

        try{
            // newOrderBeansList.clear();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("SellingCategoryId",sellingcat);
          //  jsonObject.put("SellingTypeId", What_Areu_Looking_Adapter.sellingtypeid);


            System.out.println("jhfdfdjc111"+jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.GetSellingList, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("GetSellingTypeeeeeeee"+result);


                    try{
                        get_soiltype = result.getJSONArray("SellingList");
                        System.out.println("idddddddddddddddddddddddddddddddddddddddddnext"+get_soiltype);

                        for(int i=0;i<get_soiltype.length();i++){
                            JSONObject jsonObject1 = get_soiltype.getJSONObject(i);
                            Sellbean sellbean = new Sellbean(jsonObject1.getString("SellingListName"),jsonObject1.getString("SellingListMasterId"),jsonObject1.getString("SellingListIcon"));
                            newOrderBeansList.add(sellbean);
                        }
                        livestock_types_adapter=new SelectSubCategoryAdapter(getActivity(),newOrderBeansList);
                        recyclerView.setAdapter(livestock_types_adapter);
                        livestock_types_adapter.notifyDataSetChanged();


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }



}
