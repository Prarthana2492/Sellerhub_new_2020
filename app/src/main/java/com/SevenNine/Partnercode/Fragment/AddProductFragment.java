package com.SevenNine.Partnercode.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SevenNine.Partnercode.Activity.Status_bar_change_singleton;
import com.SevenNine.Partnercode.Adapter.SelectCategoryAdapter;
import com.SevenNine.Partnercode.Adapter.SelectSubCategoryAdapter;
import com.SevenNine.Partnercode.Adapter.UOMAdapter;
import com.SevenNine.Partnercode.Adapter.What_Areu_Looking_Adapter;
import com.SevenNine.Partnercode.Bean.SelectLanguageBean;
import com.SevenNine.Partnercode.Bean.Sellbean;
import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;
import com.SevenNine.Partnercode.Urls;
import com.SevenNine.Partnercode.Volly_class.Crop_Post;
import com.SevenNine.Partnercode.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class AddProductFragment extends Fragment {
    Fragment selectedFragment;
    TextView norecords;
    LinearLayout Continue,linearLayout,backfeed;
    EditText product_code,product_name,product_description,quantity,amount,sku,model,search;
    public  static EditText brand;
    public  static TextView uom,expiry_date;
    SessionManager sessionManager;
    String status,productlistid,sellingmasterid,shop_ads_toast;
    JSONObject lngObject;
    Calendar myCalendar;
    RecyclerView recyclerView;
    String date_str;
    public static ArrayList<SelectLanguageBean> newOrderBeansList = new ArrayList<>();
    private List<SelectLanguageBean> searchresultAraaylist = new ArrayList<>();
    public static UOMAdapter livestock_types_adapter3;
    JSONArray jsonArray;
    SelectLanguageBean selectLanguageBean;

    public static String search_status="status";

    public static DrawerLayout drawer;
    public static AddProductFragment newInstance() {
        AddProductFragment fragment = new AddProductFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_product_navi, container, false);

        Status_bar_change_singleton.getInstance().color_change(getActivity());
        myCalendar = Calendar.getInstance();
        sessionManager=new SessionManager(getActivity());
        Continue = view.findViewById(R.id.continuebtn);
        backfeed = view.findViewById(R.id.back_feed);
        linearLayout = view.findViewById(R.id.main_layout);
        product_code = view.findViewById(R.id.prod_code);
        product_name = view.findViewById(R.id.prod_name);
        product_description = view.findViewById(R.id.prod_description);
        quantity = view.findViewById(R.id.quantity);
        uom = view.findViewById(R.id.uom);
        amount = view.findViewById(R.id.amount);
        sku = view.findViewById(R.id.sku);
        brand = view.findViewById(R.id.brand);
        search = view.findViewById(R.id.search);
        model = view.findViewById(R.id.model);
        expiry_date = view.findViewById(R.id.expiry_date);
        recyclerView = view.findViewById(R.id.recycler_view);
        norecords = view.findViewById(R.id.norecords);
        norecords.setVisibility(View.GONE);
        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout_op);

        if (getArguments()!=null){
    if (getArguments().getString("page")!=null) {
        productlistid = getArguments().getString("status1");
        sellingmasterid = getArguments().getString("masterId1");
    }else{
        productlistid = getArguments().getString("status");
        sellingmasterid = getArguments().getString("masterId");
    }
}


        setupUI(linearLayout);
        backfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments()!=null) {
                    if (getArguments().getString("page") != null) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("spicescateory999", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    } else {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("spicescateory12", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    if (getArguments()!=null) {
                        if (getArguments().getString("page") != null) {
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.popBackStack("spicescateory999", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        } else {
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.popBackStack("spicescateory12", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }
                    }
                    return true;
                }
                return false;

            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        expiry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        uom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                drawer.openDrawer(GravityCompat.END);
                search_status="state";
                search.setText("");


                newOrderBeansList.clear();
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                livestock_types_adapter3 = new UOMAdapter(getActivity(),newOrderBeansList);

                recyclerView.setAdapter(livestock_types_adapter3);

                PrepareUOM();
            }
        });
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (product_code.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter Product code", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (product_name.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter Product name", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (product_description.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter Product description", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (quantity.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter quantity", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (uom.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Select Unit of Measurement", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (amount.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter Amount", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (sku.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter SKU", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (expiry_date.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Select Expiry date", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                } else {

                    try {
                        // newOrderBeansList.clear();
                        JSONObject jsonObject = new JSONObject();
                        //  jsonObject.put("ProductId",productlistid);
                        jsonObject.put("ProductCode", product_code.getText().toString());
                        jsonObject.put("ProductName", product_name.getText().toString());
                        jsonObject.put("ProductDescription", product_description.getText().toString());
                        jsonObject.put("Quantity", quantity.getText().toString());
                        jsonObject.put("UnitOfPriceId", uom.getText().toString());
                        jsonObject.put("Amount", amount.getText().toString());
                        jsonObject.put("SKU", sku.getText().toString());
                        jsonObject.put("BrandId", "1");
                        jsonObject.put("ModelId", "1");
                        jsonObject.put("ProductListId", productlistid);
                        jsonObject.put("SellingCategoryId", SelectCategoryAdapter.selling_category_id);
                        jsonObject.put("SellingListMasterId", sellingmasterid);
                        jsonObject.put("ExpiryDate", expiry_date.getText().toString());
                        jsonObject.put("UserId", sessionManager.getRegId("userId"));

                        System.out.println("jhfdfdjc111" + jsonObject);

                        Crop_Post.crop_posting(getActivity(), Urls.AddUpdateProductDetails, jsonObject, new VoleyJsonObjectCallback() {
                            @Override
                            public void onSuccessResponse(JSONObject result) {
                                System.out.println("GetSellingTypeeeeeeee" + result);


                                try {
                                    String status = result.getString("Status");
                                    if (status.equals("Success")) {
                                        selectedFragment = HomeFragment.newInstance();
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.frame_layout1, selectedFragment);
                                        transaction.addToBackStack("spicescateory");
                                        transaction.commit();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });



        return view;
    }

    private void PrepareUOM() {


        recyclerView.setVisibility(View.VISIBLE);

        try {

            newOrderBeansList.clear();
            JSONObject jsonObject = new JSONObject();


            Crop_Post.crop_posting(getActivity(), Urls.GetUnitOfPriceList, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("GetCropQuantityListeeeeeee" + result);


                    try {

                        jsonArray = result.getJSONArray("SellingUnitOfPrice");
                        if (jsonArray != null && jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                selectLanguageBean = new SelectLanguageBean(jsonObject1.getString("UnitOfPrice"), jsonObject1.getInt("UnitOfPriceId"), "");

                                newOrderBeansList.add(selectLanguageBean);
                            }
                            sorting(newOrderBeansList);
                            livestock_types_adapter3.notifyDataSetChanged();
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            norecords.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date_str=sdf.format(myCalendar.getTime());
        System.out.println("ddddaaattteee"+date_str);
        expiry_date.setText(sdf.format(myCalendar.getTime()));
    }
    public void sorting(List<SelectLanguageBean> arrayList){

        Collections.sort(arrayList, new Comparator() {
            @Override
            public int compare(Object state_name1, Object state_name2) {
                //use instanceof to verify the references are indeed of the type in question
                return ((SelectLanguageBean)state_name1).getVendor()
                        .compareTo(((SelectLanguageBean)state_name2).getVendor());
            }
        });
    }

    private void sorting(String filter_text) {

        final String text = filter_text.toLowerCase();



        if (search_status.equals("state")) {
            searchresultAraaylist.clear();
            for (int i = 0; i < newOrderBeansList.size(); i++) {

                if (newOrderBeansList.get(i).getVendor().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(newOrderBeansList.get(i));

                }
            }
            if (searchresultAraaylist.size() == 0) {
                recyclerView.setVisibility(View.GONE);
                norecords.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                norecords.setVisibility(View.GONE);
                livestock_types_adapter3 = new UOMAdapter( getActivity(),searchresultAraaylist);
                recyclerView.setAdapter(livestock_types_adapter3);
            }

        }}
    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        /*InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);*/

        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();

        if (focusedView != null) {

            try{
                assert inputManager != null;
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }catch(AssertionError e){
                e.printStackTrace();
            }
        }
    }


    public static InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            String specialChars = ".1/*!@#$%^&*()\"{}_[]|\\?/<>,.:-'';§£¥₹...%&+=€π|";
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL||type==Character.MATH_SYMBOL||specialChars.contains("" + source)) {
                    return "";
                }
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        if (dstart == 0)
                            return "";
                    }else if(Character.isDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;

            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };


}

