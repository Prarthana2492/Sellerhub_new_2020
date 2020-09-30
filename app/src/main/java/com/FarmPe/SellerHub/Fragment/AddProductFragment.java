package com.FarmPe.SellerHub.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
import com.FarmPe.SellerHub.Adapter.InventoryAdapter;
import com.FarmPe.SellerHub.Adapter.SelectCategoryAdapter;
import com.FarmPe.SellerHub.Adapter.UOMAdapter;
import com.FarmPe.SellerHub.Adapter.What_Areu_Looking_Adapter;
import com.FarmPe.SellerHub.Bean.SelectLanguageBean;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class AddProductFragment extends Fragment {
    Fragment selectedFragment;
    TextView norecords,price_text;
    LinearLayout Continue,linearLayout,backfeed,offer_lay,exp_lay;
    EditText product_code,product_name,product_description,quantity,amount,sku,mrp,search,delivery_charge,off_price;
    public  static EditText brand;
    public  static TextView uom,expiry_date;
    SessionManager sessionManager;
    public static String status,productlistid,sellingmasterid,sellingcatid,sellingtypeid;
    JSONObject lngObject;
    Calendar myCalendar;
    RecyclerView recyclerView;
    String date_str;
    public static int IsOfferAvailable;
    CheckBox offer_checkbox;
    int mrp_int,amount_int;
    public static ArrayList<SelectLanguageBean> newOrderBeansList = new ArrayList<>();
    private List<SelectLanguageBean> searchresultAraaylist = new ArrayList<>();
    public static UOMAdapter livestock_types_adapter3;
    JSONArray jsonArray;
    SelectLanguageBean selectLanguageBean;

    public static String search_status="status";
    String prod_image,prod_name;

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
      //  product_code = view.findViewById(R.id.prod_code);
       // product_name = view.findViewById(R.id.prod_name);
       // product_description = view.findViewById(R.id.prod_description);
        quantity = view.findViewById(R.id.quantity);
     //   uom = view.findViewById(R.id.uom);
        amount = view.findViewById(R.id.amount);
      //  sku = view.findViewById(R.id.sku);
        brand = view.findViewById(R.id.brand);
        mrp = view.findViewById(R.id.mrp);
        search = view.findViewById(R.id.search);
     //   model = view.findViewById(R.id.model);
        expiry_date = view.findViewById(R.id.expiry_date);
        recyclerView = view.findViewById(R.id.recycler_view);
        offer_checkbox = view.findViewById(R.id.offer_checkbox);
        delivery_charge = view.findViewById(R.id.delivery_charge);
        off_price = view.findViewById(R.id.off_price);
        offer_lay = view.findViewById(R.id.offer_lay);
        exp_lay = view.findViewById(R.id.exp_lay);
        norecords = view.findViewById(R.id.norecords);
        price_text = view.findViewById(R.id.price_text);
        norecords.setVisibility(View.GONE);
        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout_op);

        if (getArguments()!=null){
            prod_image=getArguments().getString("prod_img");
            prod_name=getArguments().getString("prod_name");
    if (getArguments().getString("page")!=null) {
        productlistid = getArguments().getString("status1");
        sellingmasterid = getArguments().getString("masterId1");
        sellingcatid = SelectCategoryAdapter.selling_category_id;
        sellingtypeid = What_Areu_Looking_Adapter.sellingtypeid;
    }else if (getArguments().getString("status")!=null){
        productlistid = getArguments().getString("status");
        sellingmasterid = getArguments().getString("masterId");
        sellingcatid = SelectCategoryAdapter.selling_category_id;
        sellingtypeid = What_Areu_Looking_Adapter.sellingtypeid;

    }else{
        productlistid=getArguments().getString("productlistid");
        sellingmasterid=getArguments().getString("sellingmasterid");
        sellingtypeid=getArguments().getString("sellingtypeid");
        sellingcatid="1";
    }
}

        if (InventoryAdapter.prod_id!=null){
            quantity.setText(InventoryAdapter.quantity);
            mrp.setText(InventoryAdapter.mrp);
           // amount.setText(InventoryAdapter.amount);
            delivery_charge.setText(InventoryAdapter.deliver_charges);
            prod_image=InventoryAdapter.prod_img;
            prod_name=InventoryAdapter.prod_name;
            if (InventoryAdapter.isofferactive.equals("true")){
                offer_checkbox.setChecked(true);
               // off_price.setVisibility(View.VISIBLE);
                price_text.setText("Offer Price");
                exp_lay.setVisibility(View.VISIBLE);
                amount.setText(InventoryAdapter.offer_price);
               // off_price.setText(InventoryAdapter.amount);
                expiry_date.setText(InventoryAdapter.exp_date.substring(0,10));
            }else{
                price_text.setText("Price");
                amount.setText(InventoryAdapter.amount);
                exp_lay.setVisibility(View.GONE);

            }
            if (InventoryAdapter.brand.equals("1")){
                brand.setText("");
            }else{
                brand.setText(InventoryAdapter.brand);
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
                    } else if (getArguments().getString("sellingtypeid")!=null){
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack("confirm", FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
                        }else if (getArguments().getString("sellingtypeid")!=null){
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.popBackStack("confirm", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }else {
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.popBackStack("spicescateory12", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        }
                    }
                    return true;
                }
                return false;

            }
        });

        if (offer_checkbox.isChecked()){
           // offer_lay.setVisibility(View.VISIBLE);
            price_text.setText("Offer Price");
            exp_lay.setVisibility(View.VISIBLE);
            IsOfferAvailable=1;
        }else{
            offer_lay.setVisibility(View.GONE);
            IsOfferAvailable=0;
            exp_lay.setVisibility(View.GONE);
            price_text.setText("Price");

        }
        offer_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (offer_checkbox.isChecked()){
                  //  offer_lay.setVisibility(View.VISIBLE);
                    price_text.setText("Offer Price");
                    exp_lay.setVisibility(View.VISIBLE);
                    IsOfferAvailable=1;
                }else{
                    offer_lay.setVisibility(View.GONE);
                    price_text.setText("Price");
                    exp_lay.setVisibility(View.GONE);
                    IsOfferAvailable=0;
                }
            }
        });

        mrp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {

                mrp.removeTextChangedListener(this);
                try {
                    String originalString = editable.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,##,##,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    mrp.setText(formattedString);
                    mrp.setSelection(mrp.getText().length());
                    mrp_int=Integer.parseInt(mrp.getText().toString());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                mrp.addTextChangedListener(this);
            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                amount.removeTextChangedListener(this);
                try {
                    String originalString = editable.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,##,##,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    amount.setText(formattedString);
                    amount.setSelection(amount.getText().length());
                    amount_int=Integer.parseInt(amount.getText().toString());
                    mrp_int=Integer.parseInt(mrp.getText().toString());

                    if (amount_int>mrp_int){
                        Toast toast = Toast.makeText(getActivity(), "Price should be less than MRP", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                        toast.show();
                        amount.getText().clear();
                    }

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                amount.addTextChangedListener(this);
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
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                //following line to restrict future date selection
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
                /*new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/



            }
        });
      /*  uom.setOnClickListener(new View.OnClickListener() {
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
        });*/
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mrp.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter MRP", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                }  else if (quantity.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter Quantity", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                }/*else if (IsOfferAvailable==1){
                    if (off_price.getText().toString().equals("")){
                        Toast toast = Toast.makeText(getActivity(), "Enter Offer Price", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                        toast.show();
                    }else if (expiry_date.getText().toString().equals("")){
                        Toast toast = Toast.makeText(getActivity(), "Select Expiry Date", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }*/
                /*else if (brand.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter Brand", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                }*/ /*else if (expiry_date.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Select Expiry date", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                } */
                else if (IsOfferAvailable==1){

                    if (amount.getText().toString().equals("")){
                        Toast toast = Toast.makeText(getActivity(), "Enter Offer Price", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                        toast.show();
                    }else if (expiry_date.getText().toString().equals("")){
                        Toast toast = Toast.makeText(getActivity(), "Select Expiry Date", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                        toast.show();
                    }else{
                       // AddProduct();
                        Bundle bundle=new Bundle();
                        bundle.putString("ProdName",prod_name);
                      //  bundle.putString("ProdPrice",amount.getText().toString());
                        bundle.putString("ProdMRP",mrp.getText().toString());
                        bundle.putString("ProdOfferPrice",amount.getText().toString());
                        bundle.putString("ProdDeliveryCharge",delivery_charge.getText().toString());
                        bundle.putString("ProdBrand",brand.getText().toString());
                        bundle.putString("ProdQuantity",quantity.getText().toString());
                        bundle.putString("ProdExpiryDate",expiry_date.getText().toString());
                        bundle.putString("ProdImage", prod_image);
                        selectedFragment = ProductConfirmationFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.addToBackStack("spicescateory1");
                        selectedFragment.setArguments(bundle);
                        transaction.commit();
                    }
                }else if (amount.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Enter Price", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else {
                    System.out.println("uuuuuuuu" + IsOfferAvailable);
                   // AddProduct();
                    Bundle bundle=new Bundle();
                    bundle.putString("ProdName",prod_name);
                    bundle.putString("ProdPrice",amount.getText().toString());
                    bundle.putString("ProdMRP",mrp.getText().toString());
                  //  bundle.putString("ProdOfferPrice",amount.getText().toString());
                    bundle.putString("ProdDeliveryCharge",delivery_charge.getText().toString());
                    bundle.putString("ProdBrand",brand.getText().toString());
                    bundle.putString("ProdQuantity",quantity.getText().toString());
                  //  bundle.putString("ProdExpiryDate",expiry_date.getText().toString());
                    bundle.putString("ProdImage", prod_image);
                    selectedFragment = ProductConfirmationFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.addToBackStack("spicescateory1");
                    selectedFragment.setArguments(bundle);
                    transaction.commit();

                }
                }
           /* }*/
        });



        return view;
    }

    private void AddProduct(){
        try {
            // newOrderBeansList.clear();
            JSONObject jsonObject = new JSONObject();
            //  jsonObject.put("ProductId",productlistid);
            jsonObject.put("ProductCode", "123456");
            System.out.println("hhhhhhh" + jsonObject);

            //  jsonObject.put("ProductName", product_name.getText().toString());
            jsonObject.put("ProductDescription", "Vegetables");
            System.out.println("iiiiiii" + jsonObject);

            jsonObject.put("Quantity", quantity.getText().toString());
            System.out.println("aaaaaa" + jsonObject);

            //   jsonObject.put("UnitOfPriceId", uom.getText().toString());
            jsonObject.put("Amount", amount.getText().toString());
            System.out.println("bbbbbb" + jsonObject);

            jsonObject.put("MRP", mrp.getText().toString());
            System.out.println("ccccc" + jsonObject);

            //  jsonObject.put("SKU", sku.getText().toString());

            System.out.println("dddddd" + jsonObject);

            //  jsonObject.put("ModelId", "1");
            jsonObject.put("ProductListId", productlistid);
            System.out.println("eeeeee" + jsonObject);

            jsonObject.put("SellingCategoryId", sellingcatid);
            System.out.println("ffffff" + jsonObject);

            jsonObject.put("SellingTypeId", sellingtypeid);
            System.out.println("ggggggg" + jsonObject);

            jsonObject.put("IsOfferAvailable", IsOfferAvailable);
            System.out.println("222222222" + jsonObject);

            if (IsOfferAvailable == 1) {
                jsonObject.put("OfferExpiresOn", expiry_date.getText().toString());
                jsonObject.put("OfferExpiresOn", "1/7/2020");
                jsonObject.put("OfferPrice", off_price.getText().toString());
                System.out.println("333333" + jsonObject);

            }else{
                jsonObject.put("OfferExpiresOn", "1/1/2020");
                jsonObject.put("OfferPrice", "0");
                System.out.println("3333333" + jsonObject);

            }
            if (delivery_charge.getText().toString().equals("0")){
                jsonObject.put("DeliveryCharges", "0");
                System.out.println("4444444" + jsonObject);

            }else{
                jsonObject.put("DeliveryCharges", delivery_charge.getText().toString());
                System.out.println("444444444" + jsonObject);

            }
            if (brand.getText().toString().equals("")){
                jsonObject.put("Brand", "Brand");

            }else{
                jsonObject.put("Brand", brand.getText().toString());
            }
            jsonObject.put("SellingListMasterId", sellingmasterid);
            jsonObject.put("ExpiryDate", "1/1/2020");
            jsonObject.put("UserId", sessionManager.getRegId("userId"));
            System.out.println("555555555" + jsonObject);

            if (InventoryAdapter.prod_id!=null){
                jsonObject.put("ProductId", InventoryAdapter.prod_id);
                System.out.println("6666666" + jsonObject);

            }else{
                jsonObject.put("ProductId", "0");
                System.out.println("6666666" + jsonObject);

            }

            System.out.println("jhfdfdjc111" + jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.AddUpdateProductDetails, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("GetSellingTypeeeeeeee" + result);


                    try {
                        String status = result.getString("Status");
                        if (status.equals("Success")) {
                            if (InventoryAdapter.prod_id != null) {
                                selectedFragment = InventoryList.newInstance();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout1, selectedFragment);
                                transaction.addToBackStack("spicescateory");
                                transaction.commit();
                                InventoryAdapter.prod_id=null;
                                InventoryAdapter.quantity=null;
                                InventoryAdapter.brand=null;
                                InventoryAdapter.mrp=null;
                                InventoryAdapter.amount=null;
                            } else {
                                selectedFragment = HomeFragment.newInstance();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout1, selectedFragment);
                                transaction.addToBackStack("spicescateory");
                                transaction.commit();
                            }
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
        String myFormat = "MM/dd/yyyy"; //In which you need put here
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

