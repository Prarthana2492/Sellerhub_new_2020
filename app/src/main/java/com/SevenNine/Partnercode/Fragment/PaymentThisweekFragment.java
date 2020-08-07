package com.SevenNine.Partnercode.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.SevenNine.Partnercode.Adapter.NewOrderAdapter;
import com.SevenNine.Partnercode.Adapter.ThisweekPaymentAdapter;
import com.SevenNine.Partnercode.Adapter.TodayPaymentAdapter;
import com.SevenNine.Partnercode.Bean.NewOrderBean;
import com.SevenNine.Partnercode.Bean.TodayPaymentBean;
import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;
import com.SevenNine.Partnercode.Urls;
import com.SevenNine.Partnercode.Volly_class.Login_post;
import com.SevenNine.Partnercode.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PaymentThisweekFragment extends Fragment {

    public static List<TodayPaymentBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    LinearLayout back_feed;
    SessionManager sessionManager;
    ThisweekPaymentAdapter madapter;
    JSONObject lngObject;
    TextView toolbar_title,last_month_text,filter;
    String status;

    public static PaymentThisweekFragment newInstance() {
        PaymentThisweekFragment fragment = new PaymentThisweekFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_order_recy, container, false);
        recyclerView=view.findViewById(R.id.new_order_recy);
        last_month_text = view.findViewById(R.id.last_month_text);
        filter = view.findViewById(R.id.filter);
        last_month_text.setText("This Week");

        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));

        sessionManager=new SessionManager(getActivity());
      //  PaymentsTabLayout.last_month_text.setText("This Week");

        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ThisweekDetails();
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.layout_filterpopup);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final TextView credited_to = (TextView) dialog.findViewById(R.id.credited_to);
                final TextView asce = (TextView)dialog.findViewById(R.id.sort_ascendi) ;
                final TextView desc = (TextView)dialog.findViewById(R.id.sort_desendi) ;
                //   final TextView popuptxt = (TextView)dialog.findViewById(R.id.popup_heading) ;
                LinearLayout image = (LinearLayout) dialog.findViewById(R.id.close_popup);


                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });

                asce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status="Ascending";
                        last_month_text.setText("Ascending");
                        dialog.dismiss();
                        FilterThisweek();

                    }
                });
                desc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status="descending";
                        last_month_text.setText("Descending");
                        dialog.dismiss();
                        FilterThisweek();

                    }
                });
                credited_to.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // last_month_text.setText("Descending");
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
       /* TodayPaymentBean bean=new TodayPaymentBean("ABC XYZ","404-8614299-2321138","","HDFC Bank","09-Mar-2020","9898670456");
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);


        madapter=new ThisweekPaymentAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(madapter);*/


    //    LoanInformation();


        return view;
    }

    private void ThisweekDetails() {
         newOrderBeansList.clear();

        try {
            JSONObject userRequestjsonObject = new JSONObject();
            userRequestjsonObject.put("UserId",sessionManager.getRegId("userId"));
            // userRequestjsonObject.put("UserId","1");
            System.out.println("uiuuuuuussseeettttiiinnnngg"+userRequestjsonObject);

            Login_post.login_posting(getActivity(), Urls.Get7NineWeeklyPaymentDetails, userRequestjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statussssss000lll" + result);
                    JSONArray jsonArray = new JSONArray();

                    try {

                        jsonArray = result.getJSONArray("NineWeeklyPaymentDetails");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String ProductName=jsonObject1.getString("ProductName");
                            String SellingListIcon=jsonObject1.getString("SellingListIcon");
                            String CreatedOn=jsonObject1.getString("CreatedOn");
                            String PayUTransactionId=jsonObject1.getString("PayUTransactionId");
                            String Amount=jsonObject1.getString("Amount");
                            String SelectedQuantity=jsonObject1.getString("Quantity");
                            String UnitOfPrice=jsonObject1.getString("UnitOfPrice");
                            String ProductInfo=jsonObject1.getString("CustAddress");
                            String SellingCategoryName=jsonObject1.getString("SellingCategoryName");
                            String SellingListName=jsonObject1.getString("SellingListName");
                            String ProductDescription=jsonObject1.getString("ProductDescription");
                            String Brand=jsonObject1.getString("Brand");
                            String ProductIcon=jsonObject1.getString("ProductIcon");
                            String mode=jsonObject1.getString("mode");
                            String MRP=jsonObject1.getString("MRP");

                            /*PreferedBranchBean bean=new PreferedBranchBean(Name,StreeAddress,StreeAddress1,State,Pincode,"",Id);
                            newOrderBeansList.add(bean);*/

                            TodayPaymentBean bean=new TodayPaymentBean("",PayUTransactionId,"",mode,CreatedOn,Amount);

                            newOrderBeansList.add(bean);

                            //  System.out.println("adreess_list_size"+newOrderBeansList.size());

                        }
                        madapter = new ThisweekPaymentAdapter(getActivity(), newOrderBeansList);
                        recyclerView.setAdapter(madapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void FilterThisweek() {
        newOrderBeansList.clear();

        try {
            JSONObject userRequestjsonObject = new JSONObject();
            userRequestjsonObject.put("UserId",sessionManager.getRegId("userId"));
            userRequestjsonObject.put("Status",status);
            // userRequestjsonObject.put("UserId","1");
            System.out.println("uiuuuuuussseeettttiiinnnngg"+userRequestjsonObject);

            Login_post.login_posting(getActivity(), Urls.GetFiltersOnThisWeekPaymentDetails, userRequestjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statussssss000lll" + result);
                    JSONArray jsonArray = new JSONArray();

                    try {

                        jsonArray = result.getJSONArray("FiltersforWeeklyPaymentDetails");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String ProductName=jsonObject1.getString("ProductName");
                            String SellingListIcon=jsonObject1.getString("SellingListIcon");
                            String CreatedOn=jsonObject1.getString("CreatedOn");
                            String PayUTransactionId=jsonObject1.getString("PayUTransactionId");
                            String Amount=jsonObject1.getString("Amount");
                            String SelectedQuantity=jsonObject1.getString("Quantity");
                            String UnitOfPrice=jsonObject1.getString("UnitOfPrice");
                            String ProductInfo=jsonObject1.getString("CustAddress");
                            String SellingCategoryName=jsonObject1.getString("SellingCategoryName");
                            String SellingListName=jsonObject1.getString("SellingListName");
                            String ProductDescription=jsonObject1.getString("ProductDescription");
                            String Brand=jsonObject1.getString("Brand");
                            String ProductIcon=jsonObject1.getString("ProductIcon");
                            String mode=jsonObject1.getString("mode");
                            String MRP=jsonObject1.getString("MRP");

                            /*PreferedBranchBean bean=new PreferedBranchBean(Name,StreeAddress,StreeAddress1,State,Pincode,"",Id);
                            newOrderBeansList.add(bean);*/

                            TodayPaymentBean bean=new TodayPaymentBean("",PayUTransactionId,"",mode,CreatedOn,Amount);

                            newOrderBeansList.add(bean);

                            //  System.out.println("adreess_list_size"+newOrderBeansList.size());

                        }
                        madapter = new ThisweekPaymentAdapter(getActivity(), newOrderBeansList);
                        recyclerView.setAdapter(madapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
