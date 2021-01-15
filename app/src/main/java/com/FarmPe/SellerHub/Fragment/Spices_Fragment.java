package com.FarmPe.SellerHub.Fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.SellerHub.Activity.Status_bar_change_singleton;
import com.FarmPe.SellerHub.Adapter.ShopByCategoryAdapterNext;
import com.FarmPe.SellerHub.Adapter.Spices_Adapter;
import com.FarmPe.SellerHub.Adapter.Spices_Category_Adapter;
import com.FarmPe.SellerHub.Bean.MainVerticalBean;
import com.FarmPe.SellerHub.Bean.Sellbean;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.Urls;
import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Spices_Fragment extends Fragment {

 // public static ArrayList<Sellbean> newOrderBeansList = new ArrayList<>();
    public static ArrayList<MainVerticalBean> newOrderBeansList = new ArrayList<>();
    public static RecyclerView recyclerView;
    public static Spices_Adapter livestock_types_adapter;
    Fragment selectedFragment = null;
    TextView toolbar_title;
    public static String livestock_status;
    LinearLayout back_feed,linearLayout,search_layout;
    JSONArray get_soiltype;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    public  static  String sellingcat,sell_navigation2,shopby_cat;
    Sellbean addTractorBean3;


    public static Spices_Fragment newInstance() {
        Spices_Fragment fragment = new Spices_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.livestock_recy_layout, container, false);


        Status_bar_change_singleton.getInstance().color_change(getActivity());



        recyclerView=view.findViewById(R.id.recycler_what_looking);

        toolbar_title=view.findViewById(R.id.setting_tittle);
        toolbar_title.setText("Select Sub Category");
        back_feed=view.findViewById(R.id.back_feed);
        linearLayout = view.findViewById(R.id.linearLayout);
        search_layout = view.findViewById(R.id.search_lay);
        search_layout.setVisibility(View.GONE);

        sellingcat = getArguments().getString("status");
       /* if (getArguments() != null) {
            sell_navigation2 = getArguments().getString("navg_from2");

        }*/
        System.out.println("sellinfcatid"+sellingcat);



        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getArguments().getString("status").equals(Spices_Category_Adapter.selling_category_id)) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack ("spicesfragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                }else {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("spicescateoryshop", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                }


               /* FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack ("spicesfragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
            }
        });



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack ("spicesfragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }
                return false;
            }
        });


        newOrderBeansList.clear();
        // livestock_types_adapter = new Livestock_Types_Adapter( getActivity(),newOrderBeansList);
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        MainVerticalBean bean = new MainVerticalBean("Fenugreek","1","",R.drawable.fenugreek_2);
        newOrderBeansList.add(bean);
        MainVerticalBean bean1 = new MainVerticalBean("Fennel","1","",R.drawable.fennel);
        newOrderBeansList.add(bean1);
        MainVerticalBean bean2 = new MainVerticalBean("Celery","1","",R.drawable.celery);
        newOrderBeansList.add(bean2);
        MainVerticalBean bean3 = new MainVerticalBean("Cinnamon","1","",R.drawable.cinnamon);
        newOrderBeansList.add(bean3);
        MainVerticalBean bean4= new MainVerticalBean("Cambodge","1","",R.drawable.cambodge);
        newOrderBeansList.add(bean4);

        livestock_types_adapter=new Spices_Adapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(livestock_types_adapter);


//        try{
//            newOrderBeansList.clear();
//            JSONObject jsonObject = new JSONObject();
//
//            jsonObject.put("SellingCategoryId",sellingcat);
//            System.out.println("abcdef"+jsonObject);
//
//
//                if(getArguments().getString("status").equals(ShopByCategoryAdapterNext.sellingtypeid)){
//                    jsonObject.put("SellingCategoryId",ShopByCategoryAdapterNext.sellingtypeid);
//
//                    System.out.println("abcdefghijklmnopffffffff"+ShopByCategoryAdapterNext.sellingtypeid);
//                }
//
//
//            else {
//
//            }
//
//
//
//            Crop_Post.crop_posting(getActivity(), Urls.GetSellingList, jsonObject, new VoleyJsonObjectCallback() {
//                @Override
//                public void onSuccessResponse(JSONObject result) {
//                    System.out.println("GetSellingTypeeeeeeee"+result);
//
//
//                 try{
//                        get_soiltype = result.getJSONArray("SellingList");
//                        System.out.println("idddddddddddddddddddddddddddddddddddddddddnext"+get_soiltype);
//
//                        for(int i=0;i<get_soiltype.length();i++){
//                            JSONObject jsonObject1 = get_soiltype.getJSONObject(i);
//                            addTractorBean3 = new Sellbean(jsonObject1.getString("SellingListName"),jsonObject1.getString("SellingListMasterId"),jsonObject1.getString("SellingListIcon"));
//                            newOrderBeansList.add(addTractorBean3);
//                        }
//
//                        livestock_types_adapter.notifyDataSetChanged();
//
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }


        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (getFromPref(getActivity(), ALLOW_KEY)) {
                showSettingsAlert();
            } else if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.CAMERA)) {
                    showAlert();
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        }

        return view;
    }


    public static void saveToPreferences(Context context, String key,
                                         Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }



    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }




    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getActivity().finish();
                    }
                });


        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });

        alertDialog.show();
    }



    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");


        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });


        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(getActivity());
                    }
                });
        alertDialog.show();
    }



    @Override
    public void onRequestPermissionsResult
            (int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (getActivity(), permission);

                        if (showRationale) {
                            showAlert();

                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(getActivity(), ALLOW_KEY, true);
                        }
                    }
                }
            }
        }
        // other 'case' lines to check for other
        // permissions this app might request
    }



    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }
}
