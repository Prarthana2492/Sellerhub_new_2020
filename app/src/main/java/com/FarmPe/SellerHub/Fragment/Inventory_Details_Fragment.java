package com.FarmPe.SellerHub.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;
import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONObject;


public class Inventory_Details_Fragment extends Fragment {



    TextView toolbar_title,bank_name,sell_name,sell_price,edit_variety,edit_quantity,edit_uom,edit_price,edit_quality;
    LinearLayout back_feed;
    Fragment selectedFragment;
    JSONObject lngObject;
    SessionManager sessionManager;
    JSONArray jsonArray;
    ImageView sell_image,edit_sell_icon;
    public  static String SM_Id ,SC_Id,SId,Quantity,UOM,Price,SImage,SNamw,SVariety,SCatgry_name,Name,SQuality,SMinPrice,SMaxPrice;
   public static String sellingedit_id,selling_masterid,selling_categoryid,edit_image;

    public static Inventory_Details_Fragment newInstance() {
        Inventory_Details_Fragment fragment = new Inventory_Details_Fragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inventory_prod_recy, container, false);

      back_feed=view.findViewById(R.id.back_feed);
      sell_image=view.findViewById(R.id.image);
      sell_name=view.findViewById(R.id.sellname);
      sell_price=view.findViewById(R.id.price);
      edit_variety=view.findViewById(R.id.e_variety);
      edit_quantity=view.findViewById(R.id.e_quantity);
      edit_uom=view.findViewById(R.id.e_uom);
      edit_price=view.findViewById(R.id.e_price);
      edit_sell_icon=view.findViewById(R.id.edit_sell);
      edit_quality=view.findViewById(R.id.e_quality);
       // bank_name=view.findViewById(R.id.bank_name);

        sessionManager = new SessionManager(getActivity());

        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.dark_green));


        sellingedit_id= getArguments().getString("SellAddId");

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack ("track", FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
        });

       // bank_name.setText(Html.fromHtml("<b>Bank of Baroda</b>"+" "+"Crop Loan"));
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack ("track", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }
                return false;
            }
        });







        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId",sessionManager.getRegId("userId"));
            jsonObject.put("SellingDetailsId",sellingedit_id);
            Crop_Post.crop_posting(getActivity(), Urls.GetSellDetailsbySellId, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("inventorydetails"+result);
                    try{
                        jsonArray = result.getJSONArray("SellDetails");

                        for(int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            SId = jsonObject1.getString("SellingDetailsId");
                            SM_Id = jsonObject1.getString("SellingListMasterId");
                            SC_Id = jsonObject1.getString("SellingCategoryId");
                            Quantity = jsonObject1.getString("SellingQuantity");
                            UOM = jsonObject1.getString("UnitOfPrice");
                            Price = jsonObject1.getString("Price");
                            SImage = jsonObject1.getString("SellingListIcon");
                            SNamw = jsonObject1.getString("SellingListName");
                            SVariety = jsonObject1.getString("SellingVariety");
                            SCatgry_name = jsonObject1.getString("SellingCategoryName");
                            SQuality = jsonObject1.getString("SellingQuality");
                            SMinPrice = jsonObject1.getString("MinPrice");
                            SMaxPrice = jsonObject1.getString("MaxPrice");


                                if(SNamw.equals("")){
                                    Name=SCatgry_name;
                                }else{
                                    Name=SNamw;
                                }


                            sell_name.setText(Name+", "+SQuality+", "+SVariety +", "+Quantity+UOM);
                            sell_price.setText("Rs "+SMinPrice +" - "+SMaxPrice+"/"+UOM);

                            edit_variety.setText(SVariety);
                            edit_quantity.setText(Quantity);
                            edit_quality.setText(SQuality);
                            edit_uom.setText(UOM);
                            edit_price.setText(SMinPrice+" - "+SMaxPrice);


                            Glide.with(getActivity()).load(SImage)
                                    .thumbnail(0.5f)
                                    // .crossFade()
                                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                                            .error(R.drawable.ic_gallery__default))
                                    .into(sell_image);

                            //bean = new Inventorydetailsbean(jsonObject1.getString("SellingDetailsId"),jsonObject1.getString("SellingListName"),jsonObject1.getString("UnitOfPrice"),jsonObject1.getString("Price"),jsonObject1.getString("SellingQuantity"),jsonObject1.getString("SellingImage"),jsonObject1.getString("SellingVariety"));

                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

        edit_sell_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellingedit_id=SId;
                selling_masterid=SM_Id;
                selling_categoryid=SC_Id;
                edit_image=SImage;

                Bundle bundle = new Bundle();
                bundle.putString("navg_from","invtry_details");
                bundle.putString("Edit_Id",sellingedit_id);
                bundle.putString("EditMaster_Id",selling_masterid);
                bundle.putString("EditCategory_Id",selling_categoryid);
                bundle.putString("Quality",edit_quality.getText().toString());
                bundle.putString("Variety",edit_variety.getText().toString());
                bundle.putString("Minquantity",edit_quantity.getText().toString());
                bundle.putString("Minprice",SMinPrice);
                bundle.putString("Maxprice",SMaxPrice);
                bundle.putString("UOM",UOM);
                System.out.println("idaaaaa"+sellingedit_id);
                selectedFragment = SellDetailsEditFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("inventoryd");
                selectedFragment.setArguments(bundle);
                transaction.commit();


                // bundle.putString("AppliName",name_val.getText().toString().substring(1));
                //        bundle.putString("AppliGender",gender_val.getText().toString());
                //        bundle.putString("AppliDob",dob_val.getText().toString());
                //        bundle.putString("AppliAge",age_val.getText().toString());
                //        bundle.putString("Applifathername",father_name_val.getText().toString());
                //        selectedFragment = ApplicantDetails.newInstance();
                //        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //        transaction.replace(R.id.frame_layout, selectedFragment);
                //        selectedFragment.setArguments(bundle);
                //        transaction.addToBackStack("appli");
                //        transaction.commit();
            }
        });








        return view;
    }


}
