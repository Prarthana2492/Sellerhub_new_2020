package com.SevenNine.Partnercode.Fragment;

import android.animation.Animator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SevenNine.Partnercode.Adapter.AddProductListAdapter;
import com.SevenNine.Partnercode.Bean.Sellbean;
import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;
import com.SevenNine.Partnercode.Urls;
import com.SevenNine.Partnercode.Volly_class.Crop_Post;
import com.SevenNine.Partnercode.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class SearchProductsFromHome extends Fragment {

    public static ArrayList<Sellbean> newOrderBeansList_subcat = new ArrayList<>();
    private List<Sellbean> searchresultAraaylist = new ArrayList<>();

    public static RecyclerView recyclerView_main;
    public static AddProductListAdapter livestock_types_adapter;
    JSONObject jsonObject1;
    Fragment selectedFragment = null;
    public static TextView toolbar_title,name;
    EditText search;
    public static String livestock_status;
    LinearLayout back_feed,linearLayout,main_layout;
    JSONArray get_categorylist_array;
    JSONArray get_soiltype;
    public static String sellingcatId,sellnavigation;
    SessionManager sessionManager;
    private Handler mHandler= new Handler();

    public static SearchProductsFromHome newInstance() {
        SearchProductsFromHome fragment = new SearchProductsFromHome();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_prod_from_home, container, false);
        getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark1));

       /* Status_bar_change_singleton.getInstance().color_change(getActivity());
        HomePage_With_Bottom_Navigation.linear_bottonsheet.setVisibility(View.GONE);
        HomePage_With_Bottom_Navigation.view.setVisibility(View.GONE);*/
        recyclerView_main=view.findViewById(R.id.recycler_cat_detail);
        search=view.findViewById(R.id.search);
        name=view.findViewById(R.id.name);
        sessionManager=new SessionManager(getActivity());
        linearLayout = view.findViewById(R.id.linearLayout);
        main_layout = view.findViewById(R.id.main_layout);
     //   getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mHandler.post(
                new Runnable() {
                    public void run() {
                        InputMethodManager inputMethodManager =  (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInputFromWindow(search.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                        search.requestFocus();
                    }
                });
        /*InputMethodManager inputMethodManager =
                (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                main_layout.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);*/

//        toolbar_title.setText("Select Category");
        // sellingdetailsid=Inventory_Details_Fragment.SId;
       /* if (getArguments()==null){
            System.out.println("hommmmmmee");
            sellingcatId=HomeFragment.shop_cat_id;

        }else{
            sellingcatId=getArguments().getString("sellingCatId");

        }*/
        search.requestFocus();
        search.setCursorVisible(true);

        setupUI(main_layout);

       /* if (DiscoverCategoryFragment.search_st!=null){
            System.out.println("requesttttt");
            search.setCursorVisible(true);
        }*/
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

        newOrderBeansList_subcat.clear();
        // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView_main.setLayoutManager(mLayoutManager_farm);
        recyclerView_main.setItemAnimator(new DefaultItemAnimator());
       /* Sellbean1 bean45 = new Sellbean1("Fresh Carrot, Orrange ","1",R.drawable.veg,"500g","₹100","₹120","");
        newOrderBeansList_subcat.add(bean45);
        Sellbean1 bean55= new Sellbean1("Fresh Beet Root, ","1",R.drawable.veg,"250g","₹100","₹120","");
        newOrderBeansList_subcat.add(bean55);
        Sellbean1 bean65 = new Sellbean1("Fresh Radish, White, ","1",R.drawable.veg,"500g","₹100","₹120","");
        newOrderBeansList_subcat.add(bean65);
        Sellbean1 bean75= new Sellbean1("Fresh Carrot, Orrange, ","1",R.drawable.veg,"250g","₹100","₹120","");
        newOrderBeansList_subcat.add(bean75);
        livestock_types_adapter = new CategoryProdDetailAdapter(getActivity(), newOrderBeansList_subcat);
        recyclerView_main.setAdapter(livestock_types_adapter);*/
        try{
            // newOrderBeansList.clear();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("SellingListMasterId",1);
            // jsonObject.put("SellingTypeId", What_Areu_Looking_Adapter.sellingtypeid);

            System.out.println("jhfdfdjc111"+jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.GetProductListsForSearch, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("GetSellingTypeeeeeeeepp"+result);


                    try{
                        get_soiltype = result.getJSONArray("ProductListsForSearch");
                        System.out.println("idddddddddddddddddddddddddddddddddddddddddnext"+get_soiltype);

                        if (get_soiltype.length()==0){
                            Bundle bundle = new Bundle();
                            bundle.putString("status1","0");
                            bundle.putString("page","page");
                            bundle.putString("masterId1", "1");
                            selectedFragment = AddProductFragment.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout1, selectedFragment);
                            transaction.addToBackStack("spicescateory1");
                            selectedFragment.setArguments(bundle);
                            transaction.commit();
                        }else{

                            for(int i=0;i<get_soiltype.length();i++) {
                                JSONObject jsonObject1 = get_soiltype.getJSONObject(i);
                                Sellbean sellbean = new Sellbean(jsonObject1.getString("ProductName"), jsonObject1.getString("ProductListId"), jsonObject1.getString("ProductIcon"));
                                newOrderBeansList_subcat.add(sellbean);
                            }
                        }
                        livestock_types_adapter=new AddProductListAdapter(getActivity(),newOrderBeansList_subcat);
                        recyclerView_main.setAdapter(livestock_types_adapter);
                        livestock_types_adapter.notifyDataSetChanged();


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sorting(s.toString());
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                search.requestFocus();
                search.setCursorVisible(true);

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        return view;
    }


    public  void sorting(String filter_text){

        final String text = filter_text.toLowerCase();

            searchresultAraaylist.clear();
            for (int i = 0; i < newOrderBeansList_subcat.size(); i++) {

                if (newOrderBeansList_subcat.get(i).getName().toLowerCase().contains(text)) {
                    searchresultAraaylist.add(newOrderBeansList_subcat.get(i));

                }
            }
        livestock_types_adapter=new AddProductListAdapter(getActivity(),searchresultAraaylist);
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
   /* private void setFocusCursor(){
        mBinding.replyConversationsFooter.footerEditText.setFocusable(true);
        mBinding.replyConversationsFooter.footerEditText.setFocusableInTouchMode(true);
        mBinding.replyConversationsFooter.footerEditText.requestFocus();
    }*/
}
