
package com.FarmPe.SellerHub.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
Fragment selectedFragment;
    public static DrawerLayout drawer;
    ImageView search;
    LinearLayout menu,prof_tab;
    String userid;
    SessionManager sessionManager;
    DrawerLayout drawer_layout;
    TextView home,logout,search_by_cat,disc_store,my_orders,list_prod,inventory,account,store,offers,payments,notification;
    public static TextView cart_count_text,user_name_menu;
static boolean fragloaded;
    CircleImageView image_acc;
    boolean doubleBackToExitPressedOnce = false;

static Fragment myloadingfragment;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_navigation_menu, container, false);

        menu=view.findViewById(R.id.menu);
        home=view.findViewById(R.id.home);
      //  shop_cat=view.findViewById(R.id.shop_cat);
      //  disc_store=view.findViewById(R.id.disc_store);
        my_orders=view.findViewById(R.id.my_orders);
        account=view.findViewById(R.id.account);
        user_name_menu=view.findViewById(R.id.user_name_menu);
        prof_tab=view.findViewById(R.id.prof_tab);
       // list_prod=view.findViewById(R.id.list_prod);
        logout=view.findViewById(R.id.logout);
        search_by_cat=view.findViewById(R.id.search_by_cat);
    //    store=view.findViewById(R.id.stores);
      // offers=view.findViewById(R.id.offers);
        inventory=view.findViewById(R.id.inventory);
        payments=view.findViewById(R.id.payments);
        image_acc=view.findViewById(R.id.image_acc);
        drawer_layout=view.findViewById(R.id.drawer_layout1);
        search=view.findViewById(R.id.search);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryDark));
        drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout);

        sessionManager=new SessionManager((getActivity()));
        NavigationView navigationView = (NavigationView)view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (doubleBackToExitPressedOnce) {

                        Intent intent1 = new Intent(Intent.ACTION_MAIN);
                        intent1.addCategory(Intent.CATEGORY_HOME);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                        startActivity(intent1);
                        getActivity().finish();                   }
                    // System.exit(0);

                    // home_img.setImageResource(R.drawable.ic_home_green);

                    doubleBackToExitPressedOnce = true;

                   /* Snackbar snackbar = Snackbar
                            .make(drawer_layout,"Please click back again to exit", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.orange));
                    tv.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    } else {
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    }
                    snackbar.show();*/
                    Toast toast = Toast.makeText(getActivity(),"Please click back again to exit", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    toast.show();
                    //   Toast.makeText(getApplicationContext(), toast_click_back, Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce=false;
                        }
                    }, 3000);

                }

                return true;
            }
        });
//        Bundle bundle=getArguments();
//        if (bundle!=null){
//            if (bundle.getString("status")!=null){
//                selectedFragment = PreviewProductDetails.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout_home, selectedFragment);
//                transaction.commit();
//            }else if(bundle.getString("order_details")!=null) {
//                selectedFragment = OrderTabFragment.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout_home, selectedFragment);
//                transaction.commit();
//            }else
//            {
//                selectedFragment = HomeLandingFragment.newInstance();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frame_layout_home, selectedFragment);
//                transaction.commit();
//            }
//        }else{
//            selectedFragment = HomeLandingFragment.newInstance();
//            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.frame_layout_home, selectedFragment);
//            transaction.commit();
//        }

       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               selectedFragment = SearchProductsFromHome.newInstance();
               FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
               transaction.replace(R.id.frame_layout1, selectedFragment);
               transaction.addToBackStack("homesearch");
               transaction.commit();
           }
       });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = (DrawerLayout)view.findViewById(R.id.drawer_layout1);
                drawer.openDrawer(GravityCompat.START);
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment = HomeLandingFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout_home, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });

                prof_tab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         selectedFragment = NewProfileFragmentEdit.newInstance();
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.frame_layout1, selectedFragment);
                                        transaction.addToBackStack("homepage");
                                        transaction.commit();
                    }
                });


                // selectedFragment = NewProfileFragmentEdit.newInstance();
                //                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //                transaction.replace(R.id.frame_layout, selectedFragment);
                //                transaction.addToBackStack("homepage");
                //                transaction.commit();

               /* list_prod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment = What_Are_looking.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });*/
                search_by_cat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment = ShopByCategoryFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.addToBackStack("dhsks");
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });


//                store.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        selectedFragment = Store.newInstance();
//                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.frame_layout1, selectedFragment);
//                        transaction.commit();
//                        drawer.closeDrawers();
//                    }
//                });
                my_orders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment = OrderTabFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout_home, selectedFragment);
                        transaction.addToBackStack("dhsks");
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });

                account.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment = AaSettingFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.addToBackStack("dhsks");
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });

                inventory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment = InventoryList.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.addToBackStack("dhsks");
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });

                payments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment = PaymentsTabLayout.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.addToBackStack("dhsks");
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });





               /* offers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedFragment = OffersListFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.addToBackStack("dhsks");
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });*/
               /* logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sessionManager.logoutUser();
                        getActivity().finish();
                        drawer.closeDrawers();
                    }
                });
*/
//                try{
//                    JSONObject jsonObject = new JSONObject();
//                    JSONObject post_object = new JSONObject();
//
//                    jsonObject.put("Id",sessionManager.getRegId("userId"));
//                    post_object.put("objUser",jsonObject);
//
//
//                    Crop_Post.getProfileDetails(getActivity(), Urls.Get_Profile_Details1, post_object, new VoleyJsonObjectCallback() {
//                        @Override
//                        public void onSuccessResponse(JSONObject result) {
//                            System.out.println("ggpgpgpg" + result);
//
//                            try{
//
//                                JSONObject jsonObject1 = result.getJSONObject("user");
//                                String ProfileName1 = jsonObject1.getString("UserName");
//                                System.out.println("11111" + jsonObject1.getString("FullName"));
//                                String ProfilePhone = jsonObject1.getString("PhoneNo");
//                                String ProfileEmail = jsonObject1.getString("EmailId");
//                                String ProfileImage = jsonObject1.getString("ProfilePic");
//                                System.out.println("11111" + ProfileName1);
//
//
//
//                                //  name.setText(ProfileName1);
//                                // phone_no.setText(ProfilePhone);
//                                //  user_name_menu.setText(ProfileName1);
//
//                                // user_name_menu.setFilters(new InputFilter[]{EMOJI_FILTER});
//                                //  phone_no.setFilters(new InputFilter[]{EMOJI_FILTER});
//                                //profile_mail.setFilters(new InputFilter[]{EMOJI_FILTER});
//
//
//                               /* if (!(ProfileImage.equals(""))){
//                                    Glide.with(getActivity()).load(ProfileImage)
//
//                                            .thumbnail(0.5f)
//                                            //  .crossFade()
//                                            .error(R.drawable.avatarmale)
//                                            .into(image_acc);
//                                }else{
//                                  *//*  try {
//
//                                        JSONObject jsonObject = new JSONObject();
//                                        jsonObject.put("UserId", sessionManager.getRegId("userId"));
//
//
//                                        Crop_Post.crop_posting(getActivity(), Urls.GetCImagelist, jsonObject, new VoleyJsonObjectCallback() {
//                                            @Override
//                                            public void onSuccessResponse(JSONObject result) {
//                                                System.out.println("dhfjfjd" + result);
//
//
//                                                try {
//
//                                                    JSONArray imagelist_array = result.getJSONArray("captureImagelist");
//
//                                                    for (int i = 0; i < imagelist_array.length(); i++) {
//
//
//                                                        JSONObject jsonObject1 = imagelist_array.getJSONObject(i);
//                                                        String image_view = jsonObject1.getString("Image1");
//
//
//
//                                                        Glide.with(getActivity()).load(image_view)
//                                                                .thumbnail(0.5f)
//                                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                                                .error(R.drawable.avatarmale)
//                                                                .into(image_acc);
//                                                    }
//
//
//
//
//                                                } catch (Exception e) {
//                                                    e.printStackTrace();
//                                                }
//
//                                            }
//                                        });
//
//
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }*//*
//                                }*/
//                       /* Glide.with(getActivity()).load(ProfileImage)
//
//                                .thumbnail(0.5f)
//                                //  .crossFade()
//                                .error(R.drawable.avatarmale)
//                                .into(prod_img);*/
//
//
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//
//                        }
//                    });
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }


                //get name  and  image //

//                try{
//
//                    JSONObject jsonObject = new JSONObject();
//                    JSONObject post_object = new JSONObject();
//
//                    jsonObject.put("Id",sessionManager.getRegId("userId"));
//                    post_object.put("objUser",jsonObject);
//
//
//                    Crop_Post.crop_posting(getActivity(), Urls.Get_Profile_Details, post_object, new VoleyJsonObjectCallback() {
//                        @Override
//                        public void onSuccessResponse(JSONObject result) {
//                            System.out.println("ggpgpgpg" + result);
//
//                            try{
//
//                                JSONObject jsonObject1 = result.getJSONObject("user");
//                                String  ProfileName = jsonObject1.getString("UserName");
//                                String UserType = jsonObject1.getString("UserType");
//                                //  ID = jsonObject1.getString("id");
//                                System.out.println("nameeeeeeeeeeeeeeeeeeeeeeeeeee"+ProfileName);
//                                user_name_menu.setText("Hello "+ProfileName);
//                               // usertype.setText(UserType);
//
//
//
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//
//                        }
//                    });
//
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }


//                try {
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("UserId", sessionManager.getRegId("userId"));
//
//                    Crop_Post.crop_posting(getActivity(), Urls.GetCImagelist, jsonObject, new VoleyJsonObjectCallback() {
//                        @Override
//                        public void onSuccessResponse(JSONObject result) {
//                            System.out.println("dhfjfjd" + result);
//
//                            try {
//                                JSONArray imagelist_array = result.getJSONArray("captureImagelist");
//                                for (int i = 0; i < imagelist_array.length(); i++) {
//
//                                    JSONObject jsonObject1 = imagelist_array.getJSONObject(i);
//                                 //   image_id = jsonObject1.getString("CImageId");
//                                    String image_view = jsonObject1.getString("Image1");
//
//                                    Glide.with(getActivity()).load(image_view)
//                                            .thumbnail(0.5f)
//                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                            .error(R.drawable.ic_gallery__default)
//                                            .into(image_acc);
//                                }
//
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }




            }
        });

        return view;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

}

