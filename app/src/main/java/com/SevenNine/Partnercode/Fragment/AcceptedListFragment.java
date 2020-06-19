package com.SevenNine.Partnercode.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
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

import com.SevenNine.Partnercode.Adapter.AcceptedOrderAdapter;
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


public class AcceptedListFragment extends Fragment {

    public static List<NewOrderBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    LinearLayout back_feed;
    SessionManager sessionManager;
    AcceptedOrderAdapter madapter;
    JSONObject lngObject;
    Fragment selectedFragment;
    TextView toolbar_title;

    public static AcceptedListFragment newInstance() {
        AcceptedListFragment fragment = new AcceptedListFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_order_recy, container, false);
        recyclerView=view.findViewById(R.id.new_order_recy);

        sessionManager=new SessionManager(getActivity());
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));

       // System.out.println("orderrrrrrrrrrrrrr");

      //  newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        OrderDetails();
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

        return view;
    }

    private void OrderDetails() {
        newOrderBeansList.clear();

        try {
            JSONObject userRequestjsonObject = new JSONObject();
            userRequestjsonObject.put("UserId",sessionManager.getRegId("userId"));
            // userRequestjsonObject.put("UserId","1");
            System.out.println("uiuuuuuussseeettttiiinnnngg"+userRequestjsonObject);

            Login_post.login_posting(getActivity(), Urls.GetAcceptedOrdersDetails, userRequestjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statussssss000lll" + result);
                    JSONArray jsonArray = new JSONArray();

                    try {

                        jsonArray = result.getJSONArray("AcceptedOrders");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String RESPMsg=jsonObject1.getString("SellingListName");
                            String SellingListIcon=jsonObject1.getString("SellingListIcon");
                            String ProductIcon=jsonObject1.getString("ProductIcon");
                            String ProductName=jsonObject1.getString("ProductName");
                            String CreatedOn=jsonObject1.getString("CreatedOn");
                            String TxnId=jsonObject1.getString("PayUTransactionId");
                            String Amount=jsonObject1.getString("Amount");
                            String Quantity=jsonObject1.getString("Quantity");
                            String UnitOfPrice=jsonObject1.getString("UnitOfPrice");
                            String ProductInfo=jsonObject1.getString("ProductInfo");
                            String SellingCategoryName=jsonObject1.getString("CategoryName");
                            String mode=jsonObject1.getString("mode");
                            String Brand=jsonObject1.getString("Brand");
                            String ProductDescription=jsonObject1.getString("ProductDescription");
                            String mrp=jsonObject1.getString("MRP");

                            /*PreferedBranchBean bean=new PreferedBranchBean(Name,StreeAddress,StreeAddress1,State,Pincode,"",Id);
                            newOrderBeansList.add(bean);*/

                            NewOrderBean img1=new NewOrderBean(ProductName,CreatedOn,SellingListIcon,TxnId,Amount,Quantity,UnitOfPrice,ProductInfo,mode,
                                    mrp,SellingCategoryName,ProductIcon,RESPMsg,Brand,ProductDescription);
                            newOrderBeansList.add(img1);

                          //  System.out.println("adreess_list_size"+newOrderBeansList.size());
                            madapter = new AcceptedOrderAdapter(getActivity(), newOrderBeansList);
                            recyclerView.setAdapter(madapter);
                        }

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
