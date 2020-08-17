package com.SevenNine.Partnercode.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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


import com.SevenNine.Partnercode.Adapter.NewOrderAdapter;
import com.SevenNine.Partnercode.Bean.NewOrderBean;
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


public class NewOrderFragment extends Fragment {

    public static List<NewOrderBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    LinearLayout back_feed,filter_lay;
    SessionManager sessionManager;
    NewOrderAdapter madapter;
    JSONObject lngObject;
    Fragment selectedFragment;
    TextView toolbar_title,filter,time;
    String status;

    public static NewOrderFragment newInstance() {
        NewOrderFragment fragment = new NewOrderFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_order_recy, container, false);
        recyclerView=view.findViewById(R.id.new_order_recy);
        filter_lay=view.findViewById(R.id.filter_lay);
        time=view.findViewById(R.id.time);
        filter=view.findViewById(R.id.filter);


        filter_lay.setVisibility(View.VISIBLE);

        sessionManager=new SessionManager(getActivity());
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));

        System.out.println("orderrrrrrrrrrrrrr");

        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
      //  OrderDetails();
        /*NewOrderBean bean=new NewOrderBean("Parle-G Gold Milk Glucose..","23-Apr-2020","");
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);


        madapter=new NewOrderAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(madapter);
*/

    //    LoanInformation();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    selectedFragment = HomeFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.commit();

                    return true;
                }
                return false;

            }
        });


        try {



            Bundle bundle =getArguments();
            System.out.println("buuunnn"+bundle);
            if (bundle != null) {

                String order_text = bundle.getString("setText");
                String bundlestatus=bundle.getString("bundlestatus");

                System.out.println("lkdjksj"+order_text);

                time.setText(order_text);

                if (bundlestatus.equals("1")){
                    // System.out.println("bundlestatus"+bundlestatus);
                    //CANCEL
                    status="CancelOrders";
                    FilterOrderList();
                }/*else if (bundlestatus.equals("2")) {
                    //All
                    status="";
                }*/  else if(bundlestatus.equals("3")) {
                    //OPEN
                    System.out.println("bbbbuunnddllee");
                    status="OpenOrders";
                    FilterOrderList();

                } else if (bundlestatus.equals("6month")){
                    status="Last6Month";
                    FilterOrderList();

                }else if (bundlestatus.equals("30days")){
                    status="OneMonth";
                    FilterOrderList();

                }else if (bundlestatus.equals("year")){
                    status="YearWisedata";
                    FilterOrderList();

                }else if (bundlestatus.equals("year1")){
                    status="";
                    FilterOrderList();

                }else if (bundlestatus.equals("year2")){
                    status="";
                    FilterOrderList();

                }else{
                    //All
                    status="AllOrders";
                    FilterOrderList();
                }
               /* if(order_text.equals("Open Orders")){
                    System.out.println("order_texttttt" + order_text);
                // Orderlist();

                time.setText(order_text);

            }else if (order_text.equals("Open Orders")){
                    time.setText(order_text);
                }
            else{

                time.setText("Last 6 months");
               // AllOrders();
            }*/}else{
                status="AllOrders";
                FilterOrderList();
            }
        }catch (Exception e){

        }
        System.out.println("sttttt"+status);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("kfhksjhfl");
                selectedFragment = FilterFragment.newInstance();
                FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home, selectedFragment);
                transaction.addToBackStack("ordersshk");
                transaction.commit();
            }
        });
        return view;
    }

    private void OrderDetails() {
       // newOrderBeansList.clear();

        try {
            JSONObject userRequestjsonObject = new JSONObject();
            userRequestjsonObject.put("UserId",sessionManager.getRegId("userId"));
            // userRequestjsonObject.put("UserId","1");
            System.out.println("uiuuuuuussseeettttiiinnnngg"+userRequestjsonObject);

            Login_post.login_posting(getActivity(), Urls.GetOrderList, userRequestjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statussssss000lll" + result);
                    JSONArray jsonArray = new JSONArray();

                    try {

                        jsonArray = result.getJSONArray("orderfromcart");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String ProductName=jsonObject1.getString("ProductName");
                            String SellingListIcon=jsonObject1.getString("SellingListIcon");
                            String CreatedOn=jsonObject1.getString("CreatedOn");
                            String TxnId=jsonObject1.getString("PayUTransactionId");
                            String Amount=jsonObject1.getString("Amount");
                            String SelectedQuantity=jsonObject1.getString("Quantity");
                            String OfferPrice=jsonObject1.getString("OfferPrice");
                            String ProductInfo=jsonObject1.getString("CustAddress");
                            String DeliveryCharges=jsonObject1.getString("DeliveryCharges");
                            String SellingCategoryName=jsonObject1.getString("SellingCategoryName");
                            String SellingListName=jsonObject1.getString("SellingListName");
                            String ProductDescription=jsonObject1.getString("ProductDescription");
                            String Brand=jsonObject1.getString("Brand");
                            String ProductIcon=jsonObject1.getString("ProductIcon");
                            String mode=jsonObject1.getString("mode");
                            String MRP=jsonObject1.getString("MRP");

                            /*PreferedBranchBean bean=new PreferedBranchBean(Name,StreeAddress,StreeAddress1,State,Pincode,"",Id);
                            newOrderBeansList.add(bean);*/

                            NewOrderBean img1=new NewOrderBean(ProductName,CreatedOn,SellingListIcon,TxnId,Amount,SelectedQuantity,OfferPrice,ProductInfo,mode,
                                    MRP,SellingCategoryName,ProductIcon,SellingListName,Brand,ProductDescription,DeliveryCharges);
                            newOrderBeansList.add(img1);

                          //  System.out.println("adreess_list_size"+newOrderBeansList.size());

                        }
                        madapter = new NewOrderAdapter(getActivity(), newOrderBeansList);
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

    private void FilterOrderList() {
        newOrderBeansList.clear();

        try {
            JSONObject userRequestjsonObject = new JSONObject();
            userRequestjsonObject.put("UserId",sessionManager.getRegId("userId"));
            userRequestjsonObject.put("Status",status);
            // userRequestjsonObject.put("UserId","1");
            System.out.println("uiuuuuuussseeettttiiinnnngg"+userRequestjsonObject);

            Login_post.login_posting(getActivity(), Urls.GetFiltersforOrderDetails, userRequestjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statussssss000lll" + result);
                    JSONArray jsonArray = new JSONArray();

                    try {

                        jsonArray = result.getJSONArray("filterfororderdetails");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String ProductName=jsonObject1.getString("ProductName");
                            String ProductIcon=jsonObject1.getString("ProductIcon");
                            String CreatedOn=jsonObject1.getString("CreatedOn");
                            String TxnId=jsonObject1.getString("PayUTransactionId");
                            String Amount=jsonObject1.getString("Amount");
                            String SelectedQuantity=jsonObject1.getString("SelectedQuantity");
                            String SellingCategoryName=jsonObject1.getString("SellingCategoryName");
                            String ProductInfo=jsonObject1.getString("CustAddress");
                            String mode=jsonObject1.getString("mode");
                            String MRP=jsonObject1.getString("MRP");
                            String OfferPrice = jsonObject1.getString("OfferPrice");
                            String CustAddress=jsonObject1.getString("CustAddress");
                            String DeliveryCharges = jsonObject1.getString("DeliveryCharges");
                            String SellingListName = jsonObject1.getString("SellingListName");
                            String Brand = jsonObject1.getString("Brand");
                            String ProductDescription = jsonObject1.getString("ProductDescription");

                            /*PreferedBranchBean bean=new PreferedBranchBean(Name,StreeAddress,StreeAddress1,State,Pincode,"",Id);
                            newOrderBeansList.add(bean);*/

                            NewOrderBean img1=new NewOrderBean(ProductName,CreatedOn,"",TxnId,Amount,SelectedQuantity,OfferPrice,ProductInfo,mode,
                                    MRP,SellingCategoryName,ProductIcon,SellingListName,Brand,ProductDescription,DeliveryCharges);
                            newOrderBeansList.add(img1);
                            //  System.out.println("adreess_list_size"+newOrderBeansList.size());

                        }
                        madapter = new NewOrderAdapter(getActivity(), newOrderBeansList);
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
