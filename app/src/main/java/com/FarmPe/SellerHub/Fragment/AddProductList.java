package com.FarmPe.SellerHub.Fragment;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
import com.FarmPe.SellerHub.Adapter.AddProductListAdapter;
import com.FarmPe.SellerHub.Bean.Sellbean;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class AddProductList extends Fragment {

    public static ArrayList<Sellbean> newOrderBeansList = new ArrayList<>();
    private List<Sellbean> searchresultAraaylist = new ArrayList<>();

    public static RecyclerView recyclerView;
    public static AddProductListAdapter livestock_types_adapter;
    Fragment selectedFragment = null;
    TextView toolbar_title;
    EditText search;
    public static String livestock_status;
    LinearLayout back_feed,linearLayout;
    JSONArray get_categorylist_array;
    JSONArray get_soiltype;
   public static String sellingdetailsid,sellnavigation,selling_masterid;

    public static AddProductList newInstance() {
        AddProductList fragment = new AddProductList();
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
        selling_masterid = getArguments().getString("status");
        search=view.findViewById(R.id.search);
        recyclerView=view.findViewById(R.id.recycler_what_looking);
        toolbar_title=view.findViewById(R.id.setting_tittle);
        back_feed=view.findViewById(R.id.back_feed);
        linearLayout = view.findViewById(R.id.linearLayout);
        toolbar_title.setText("Add Product");

       // sellingdetailsid=Inventory_Details_Fragment.SId;
        System.out.println("selleditiddd"+sellingdetailsid);

        /*if (getArguments() != null) {
            sellnavigation= String.valueOf(getArguments().getString("navg_from").equals("invtry_details"));
        }
*/

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

        setupUI(linearLayout);

        newOrderBeansList.clear();
        // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Sellbean bean = new Sellbean("Sunfeast Bounce Tangy Orange","1","");
        newOrderBeansList.add(bean);
        Sellbean bean1 = new Sellbean("Oreo Original Chocolatey Sandwich","1","");
        newOrderBeansList.add(bean1);
        Sellbean bean2 = new Sellbean("Sunfeast Dark Fantacy Choco Fills","1","");
        newOrderBeansList.add(bean2);
        Sellbean bean3 = new Sellbean("Oreo Cadbury Dipped Cookie","1","");
        newOrderBeansList.add(bean3);
        Sellbean bean4 = new Sellbean("Sunfeast Dark Fantasy Choco Fills Luxuria","1","");
        newOrderBeansList.add(bean4);
        Sellbean bean5 = new Sellbean("Gone Mad Gery Sugar Cheese Crackers","1","");
        newOrderBeansList.add(bean5);
        Sellbean bean6 = new Sellbean("Parle Hide & Seek Black Bourbon Choco","1","");
        newOrderBeansList.add(bean6);
        Sellbean bean7 = new Sellbean("Hide & Seek Black Bourbon Vanilla","1","");
        newOrderBeansList.add(bean7);
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

       /* try{
            // newOrderBeansList.clear();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("SellingListMasterId",1);
           // jsonObject.put("SellingTypeId", What_Areu_Looking_Adapter.sellingtypeid);

            System.out.println("jhfdfdjc111"+jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.GetProductLists, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("GetSellingTypeeeeeeeepp"+result);


                    try{
                        get_soiltype = result.getJSONArray("ProductLists");
                        System.out.println("idddddddddddddddddddddddddddddddddddddddddnext"+get_soiltype);

                        if (get_soiltype.length()==0){
                            Bundle bundle = new Bundle();
                            bundle.putString("status1","0");
                            bundle.putString("page","page");
                            bundle.putString("masterId1", selling_masterid);
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
                            newOrderBeansList.add(sellbean);
                        }
                        }
                        livestock_types_adapter=new AddProductListAdapter(getActivity(),newOrderBeansList);
                        recyclerView.setAdapter(livestock_types_adapter);
                        livestock_types_adapter.notifyDataSetChanged();


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }*/



        return view;
    }

    public  void sorting(String filter_text){

        final String text = filter_text.toLowerCase();

        searchresultAraaylist.clear();
        for (int i = 0; i < newOrderBeansList.size(); i++) {

            if (newOrderBeansList.get(i).getName().toLowerCase().contains(text)) {
                searchresultAraaylist.add(newOrderBeansList.get(i));

            }
        }
        livestock_types_adapter=new AddProductListAdapter(getActivity(),searchresultAraaylist);
        recyclerView.setAdapter(livestock_types_adapter);

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
