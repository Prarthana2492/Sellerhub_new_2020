package com.SevenNine.Partnercode.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.SevenNine.Partnercode.Adapter.OrderDetailsAdapter;
import com.SevenNine.Partnercode.Adapter.ThisweekPaymentAdapter;
import com.SevenNine.Partnercode.Bean.OrderDetailBean;
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


public class PaymentDetailsFragment extends Fragment {

    public static List<OrderDetailBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    LinearLayout back_feed;
    SessionManager sessionManager;
    OrderDetailsAdapter madapter;
    JSONObject lngObject;
    Fragment selectedFragment;
    Double item_cost_D,total_amt_D,postage_D=0.0;
    TextView toolbar_title,last_month_text,filter,item_cost,delivery_charges,tax,total,main_total,no_of_transactions,bhim_amount,instru_total;
    String status;
    public static PaymentDetailsFragment newInstance() {
        PaymentDetailsFragment fragment = new PaymentDetailsFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_details, container, false);
        last_month_text = view.findViewById(R.id.last_month_text);
        item_cost = view.findViewById(R.id.items_cost);
        delivery_charges = view.findViewById(R.id.delivery_charges);
        tax = view.findViewById(R.id.tax);
        total = view.findViewById(R.id.total);
        main_total = view.findViewById(R.id.main_total);
        filter = view.findViewById(R.id.filter);
        bhim_amount = view.findViewById(R.id.bhim_amount);
        instru_total = view.findViewById(R.id.instru_total);
        no_of_transactions = view.findViewById(R.id.no_of_transactions);
        last_month_text.setText("Last 6 Month");

        sessionManager=new SessionManager(getActivity());
        last_month_text.setText("All");
        status="AllPayment";
        FilterDashboard();

/*

        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.dark_green));
*/


      //  PaymentsTabLayout.last_month_text.setText("Last 6 Months");
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

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_cost_D=0.0;
                total_amt_D=0.0;
                postage_D=0.0;
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.layout_filterpopup_dashboard);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final TextView all = (TextView) dialog.findViewById(R.id.all);
                final TextView today = (TextView)dialog.findViewById(R.id.today) ;
                final TextView last_1_week = (TextView)dialog.findViewById(R.id.last_one_week) ;
                final TextView last_6_month = (TextView)dialog.findViewById(R.id.last_six_month) ;
                final TextView last_1_month = (TextView)dialog.findViewById(R.id.last_one_month) ;
                //   final TextView popuptxt = (TextView)dialog.findViewById(R.id.popup_heading) ;
                LinearLayout image = (LinearLayout) dialog.findViewById(R.id.close_popup);


                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });

                all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status="AllPayment";
                        last_month_text.setText("All");
                        dialog.dismiss();
                        FilterDashboard();

                    }
                });
                today.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status="Todaysdata";
                        last_month_text.setText("All");
                        dialog.dismiss();
                        FilterDashboard();

                    }
                });
                last_1_week.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status="OneWeekData";
                        dialog.dismiss();
                        FilterDashboard();

                    }
                });
                last_1_month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status="OneMonth";
                        dialog.dismiss();
                        FilterDashboard();

                    }
                });
                last_6_month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status="Last6Month";
                        dialog.dismiss();
                        FilterDashboard();

                    }
                });
                dialog.show();
            }
        });


        return view;
    }

    private void FilterDashboard() {
       // newOrderBeansList.clear();

        try {
            JSONObject userRequestjsonObject = new JSONObject();
            userRequestjsonObject.put("UserId",sessionManager.getRegId("userId"));
            userRequestjsonObject.put("Status",status);
            // userRequestjsonObject.put("UserId","1");
            System.out.println("uiuuuuuussseeettttiiinnnngg"+userRequestjsonObject);

            Login_post.login_posting(getActivity(), Urls.GetPaymentDashboardDetails, userRequestjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statussssss000lll" + result);
                    JSONArray jsonArray = new JSONArray();

                    try {

                        jsonArray = result.getJSONArray("paymentdashboarddetails");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String num_orders=jsonObject1.getString("num_orders");
                            String TotalAmount=jsonObject1.getString("TotalAmount");
                            String PostagePackaging=jsonObject1.getString("PostagePackaging");
                            String ItemCost=jsonObject1.getString("ItemCost");

                            System.out.println("jhsajkdkj"+TotalAmount+" "+ItemCost);
                            /*item_cost.setText("₹" +ItemCost);
                            total.setText("₹" +TotalAmount);
                            main_total.setText("₹" +TotalAmount);
                            delivery_charges.setText("₹" +PostagePackaging);
                            no_of_transactions.setText(num_orders);*/



                            no_of_transactions.setText(num_orders);
                            if (TotalAmount.equals("")){
                                item_cost.setText("₹" +"0.00");
                                total.setText("₹" +"0.00");
                                main_total.setText("₹" +"0.00");
                                delivery_charges.setText("₹" +"0.00");
                                instru_total.setText("₹" +"0.00");
                                bhim_amount.setText("₹" +"0.00");
                                tax.setText("₹" +"0.00");

                            }else {
                                item_cost_D=Double.parseDouble(ItemCost);
                                total_amt_D=Double.parseDouble(TotalAmount);
                                postage_D=Double.parseDouble(PostagePackaging);
                                String strDitemcost = String.format("%.2f", item_cost_D);
                                item_cost.setText("₹" +strDitemcost);
                                String strTotalAmount = String.format("%.2f", total_amt_D);
                                total.setText("₹" +strTotalAmount);
                                main_total.setText("₹" +strTotalAmount);
                                bhim_amount.setText("₹" +strTotalAmount);
                                instru_total.setText("₹" +strTotalAmount);
                                String strdeliverycharges = String.format("%.2f", postage_D);
                                delivery_charges.setText("₹" +strdeliverycharges);
                                tax.setText("₹" +"0.00");
                            }


                            /*PreferedBranchBean bean=new PreferedBranchBean(Name,StreeAddress,StreeAddress1,State,Pincode,"",Id);
                            newOrderBeansList.add(bean);*/


                            //  System.out.println("adreess_list_size"+newOrderBeansList.size());

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
