package com.SevenNine.Partnercode.Fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.SevenNine.Partnercode.Activity.Status_bar_change_singleton;
import com.SevenNine.Partnercode.Adapter.AddProductListAdapter;
import com.SevenNine.Partnercode.Adapter.InventoryAdapter;
import com.SevenNine.Partnercode.Adapter.OffersAdapter;
import com.SevenNine.Partnercode.Bean.InventoryBean;
import com.SevenNine.Partnercode.Bean.Sellbean;
import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;
import com.SevenNine.Partnercode.Urls;
import com.SevenNine.Partnercode.Volly_class.Crop_Post;
import com.SevenNine.Partnercode.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class OffersListFragment extends Fragment {

    public static ArrayList<InventoryBean> newOrderBeansList_subcat = new ArrayList<>();
    private List<InventoryBean> searchresultAraaylist = new ArrayList<>();

    public static RecyclerView recyclerView_main;
    public static OffersAdapter livestock_types_adapter;
    JSONObject jsonObject1;
    Fragment selectedFragment = null;
    public static TextView toolbar_title,name;
    ImageView search;
    EditText search_off;
    LinearLayout back_feed,linearLayout,search_lay,title_bar,main_layout;
    JSONArray get_categorylist_array;
    JSONArray get_soiltype;
    ImageView list_prod;
    public static String sellingcatId,sellnavigation;
    SessionManager sessionManager;
    private Handler mHandler= new Handler();

    public static OffersListFragment newInstance() {
        OffersListFragment fragment = new OffersListFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offers_recy_lay, container, false);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryDark1));
        Status_bar_change_singleton.getInstance().color_change(getActivity());


        recyclerView_main=view.findViewById(R.id.recycler_cat_detail);
        name=view.findViewById(R.id.name);
       // list_prod=view.findViewById(R.id.list_prod);
sessionManager=new SessionManager(getActivity());
        linearLayout = view.findViewById(R.id.linearLayout);
        toolbar_title = view.findViewById(R.id.toolbar_title);
        back_feed = view.findViewById(R.id.back_feed);
        search_lay = view.findViewById(R.id.search_lay);
        search_off = view.findViewById(R.id.search_home);
        search = view.findViewById(R.id.search);
        title_bar = view.findViewById(R.id.title_bar);
        main_layout = view.findViewById(R.id.main_layout);
       // list_prod_lay = view.findViewById(R.id.list_prod_lay);
//        toolbar_title.setText("Select Category");
        // sellingdetailsid=Inventory_Details_Fragment.SId;
    //    sellingcatId=getArguments().getString("sellingCatId");
        toolbar_title.setText("Offers");
      //  list_prod.setVisibility(View.GONE);
      //  list_prod_lay.setVisibility(View.GONE);

      /*  mHandler.post(
                new Runnable() {
                    public void run() {
                        InputMethodManager inputMethodManager =  (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInputFromWindow(search_off.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                        search_off.requestFocus();
                    }
                });
*/

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = HomeFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.commit();
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    selectedFragment = HomeFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.commit();
                    return true;
                }

                return false;
            }
        });
      //  search_off.requestFocus();
      //  search_off.setCursorVisible(true);

        setupUI(main_layout);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_bar.setVisibility(View.GONE);
                search_lay.setVisibility(View.VISIBLE);
            }
        });

        search_off.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sorting(s.toString());
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
              //  search_off.requestFocus();
              //  search_off.setCursorVisible(true);

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        newOrderBeansList_subcat.clear();
        // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView_main.setLayoutManager(mLayoutManager_farm);
        recyclerView_main.setItemAnimator(new DefaultItemAnimator());

        try{

            //  newOrderBeansList_subcat_veg.clear();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));

            System.out.println("jhfdfdjc111"+jsonObject);
            Crop_Post.crop_posting(getActivity(), Urls.GetOfferDetails, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {

                    System.out.println("GetSellingTypeeeeeeee"+result);


                    try{

                        get_soiltype = result.getJSONArray("offerdetails");

                        for(int i=0;i<get_soiltype.length();i++){

                            JSONObject jsonObject1 = get_soiltype.getJSONObject(i);
                            InventoryBean sellbean = new InventoryBean(jsonObject1.getString("ProductName"),jsonObject1.getString("ProductId"),jsonObject1.getString("Brand"),jsonObject1.getString("Quantity"),jsonObject1.getString("Amount"),jsonObject1.getString("MRP"),jsonObject1.getString("ProductIcon"),jsonObject1.getString("SellingListIcon"),"","","",jsonObject1.getString("OfferPrice"),jsonObject1.getString("DeliveryCharges"),jsonObject1.getString("OfferExpiresOn"),jsonObject1.getString("IsOfferAvailable"),"");

                            newOrderBeansList_subcat.add(sellbean);
                        }
                        livestock_types_adapter=new OffersAdapter(getActivity(),newOrderBeansList_subcat);
                        recyclerView_main.setAdapter(livestock_types_adapter);
                     //   name.setText(jsonObject1.getString("SellingCategoryName"));

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


    public  void sorting(String filter_text){

        final String text = filter_text.toLowerCase();

        searchresultAraaylist.clear();
        for (int i = 0; i < newOrderBeansList_subcat.size(); i++) {

            if (newOrderBeansList_subcat.get(i).getProd_name().toLowerCase().contains(text)) {
                searchresultAraaylist.add(newOrderBeansList_subcat.get(i));

            }
        }
        livestock_types_adapter=new OffersAdapter(getActivity(),searchresultAraaylist);
        recyclerView_main.setAdapter(livestock_types_adapter);

    }


    public void setupUI(View view) {


        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }

            });
        }
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {


        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(
                        INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();

        if (focusedView != null) {

            try {
                assert inputManager != null;
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (AssertionError e) {
                e.printStackTrace();
            }
        }
    }


}
