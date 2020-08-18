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
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.SevenNine.Partnercode.Adapter.InventoryAdapter;
import com.SevenNine.Partnercode.Adapter.OffersAdapter;
import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;
import com.SevenNine.Partnercode.Urls;
import com.SevenNine.Partnercode.Volly_class.Crop_Post;
import com.SevenNine.Partnercode.Volly_class.VoleyJsonObjectCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONObject;


public class ProductConfirmationFragment extends Fragment {
    public static RecyclerView recyclerView;
    LinearLayout back_feed,submit,offer_lay,price_lay,exp_lay;
    SessionManager sessionManager;
    JSONObject lngObject;
    Fragment selectedFragment;
    String comments;
    TextView toolbar_title,prod_name,price,actual_price,offer_perc,abt_product,exp_date,delivery_charges,quantity,brand;
    ImageView prod_img;

    public static ProductConfirmationFragment newInstance() {
        ProductConfirmationFragment fragment = new ProductConfirmationFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prod_confirmation_lay, container, false);
        back_feed=view.findViewById(R.id.back_feed);
        prod_name=view.findViewById(R.id.prod_name);
        price=view.findViewById(R.id.price);
        actual_price=view.findViewById(R.id.mrp);
        price_lay=view.findViewById(R.id.price_lay);
        offer_lay=view.findViewById(R.id.offer_lay);
        offer_perc=view.findViewById(R.id.offer_price);
        prod_img=view.findViewById(R.id.prod_img);
        exp_date=view.findViewById(R.id.exp_date);
        exp_lay=view.findViewById(R.id.exp_lay);
        delivery_charges=view.findViewById(R.id.delivery_charges);
        brand=view.findViewById(R.id.brand);
        quantity=view.findViewById(R.id.quantity);
        submit=view.findViewById(R.id.continuebtn);

        sessionManager=new SessionManager(getActivity());
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.dark_green));
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
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
                   /* selectedFragment = OffersListFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.addToBackStack("track24");
                    transaction.commit();*/
                    return true;
                }
                return false;

            }
        });

        prod_name.setText(": "+getArguments().getString("ProdName"));
       /* price.setText(getArguments().getString("product_price_st"));
        actual_price.setText(getArguments().getString("product_mrp_st"));
        abt_product.setText(getArguments().getString("product_name_st")+", "+getArguments().getString("prod_brand_st")+", "+getArguments().getString("prod_quant"));
*/
      //  price.setText("Rs"+OffersAdapter.product_price_st);
        actual_price.setText(": ₹"+getArguments().getString("ProdMRP"));
       // price.setText(": ₹"+getArguments().getString("ProdPrice"));
      //  offer_perc.setText(": ₹"+getArguments().getString("ProdOfferPrice"));
        quantity.setText(": "+getArguments().getString("ProdQuantity")+ "Kg");
        delivery_charges.setText(": ₹"+getArguments().getString("ProdDeliveryCharge"));
        if (getArguments().getString("ProdOfferPrice")!=null){
            offer_perc.setText(": ₹"+getArguments().getString("ProdOfferPrice"));
            price_lay.setVisibility(View.GONE);
            offer_lay.setVisibility(View.VISIBLE);
            exp_lay.setVisibility(View.VISIBLE);
            exp_date.setText(": "+getArguments().getString("ProdExpiryDate"));
        }else{
            price_lay.setVisibility(View.VISIBLE);
            offer_lay.setVisibility(View.GONE);
            price.setText(": ₹"+getArguments().getString("ProdPrice"));
            exp_lay.setVisibility(View.GONE);
        }

       /* if (getArguments().getString("ProdName")!=null){
            prod_name.setText(": "+getArguments().getString("ProdName"));
        }else{
            brand.setVisibility(View.VISIBLE);
            brand.setText(": "+getArguments().getString("ProdBrand"));

        }*/
        if (getArguments().getString("ProdBrand").equals("")){
         brand.setVisibility(View.GONE);
        } else{
            brand.setVisibility(View.VISIBLE);
            brand.setText(": "+getArguments().getString("ProdBrand"));

        }

       /* double off_price_calcu=(((Double.parseDouble(OffersAdapter.product_mrp_st)-Double.parseDouble(OffersAdapter.product_price_st))/(Double.parseDouble(OffersAdapter.product_mrp_st)))*100);
        System.out.println("jhfdiueshfr"+off_price_calcu);
        int offer_per_int=(int)off_price_calcu;
        String off_price_text=String.valueOf(offer_per_int);
        offer_perc.setText(off_price_text+"%");*/

        Glide.with(getActivity()).load(getArguments().getString("ProdImage"))
                .thumbnail(0.5f)
                //.crossFade()
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_gallery__default))
                .into(prod_img);
    //    LoanInformation();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.comment_popup);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final EditText comment = (EditText) dialog.findViewById(R.id.comment);
                final TextView submit_comment = (TextView)dialog.findViewById(R.id.submit_comment) ;
                final TextView cancel = (TextView)dialog.findViewById(R.id.cancel) ;
                //   final TextView popuptxt = (TextView)dialog.findViewById(R.id.popup_heading) ;
                LinearLayout image = (LinearLayout) dialog.findViewById(R.id.close_popup);


                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });

                submit_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        comments=comment.getText().toString();
                        dialog.dismiss();
                        AddProduct();

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
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

            jsonObject.put("Quantity", Integer.parseInt(getArguments().getString("ProdQuantity")));
            System.out.println("aaaaaa" + jsonObject);

            //   jsonObject.put("UnitOfPriceId", uom.getText().toString());
            System.out.println("bbbbbb" + jsonObject);

            jsonObject.put("MRP", actual_price.getText().toString().substring(3));
            System.out.println("ccccc" + jsonObject);

            //  jsonObject.put("SKU", sku.getText().toString());

            System.out.println("dddddd" + jsonObject);

            //  jsonObject.put("ModelId", "1");
            jsonObject.put("ProductListId", Integer.parseInt(AddProductFragment.productlistid));
            System.out.println("eeeeee" + jsonObject);

            jsonObject.put("SellingCategoryId", 1);
            System.out.println("csdrttt" + jsonObject);

            jsonObject.put("SellingTypeId", 1);
            System.out.println("ggggggg" + jsonObject);

            jsonObject.put("IsOfferAvailable", AddProductFragment.IsOfferAvailable);
            System.out.println("222222222" + jsonObject);

            if (AddProductFragment.IsOfferAvailable == 1) {
                jsonObject.put("OfferExpiresOn", exp_date.getText().toString().substring(2));
                jsonObject.put("OfferPrice", offer_perc.getText().toString().substring(3));
                jsonObject.put("Amount", "0");

                System.out.println("333333" + jsonObject);

            }else{
                jsonObject.put("OfferExpiresOn", "1/1/2020");
                jsonObject.put("OfferPrice", "0");
                jsonObject.put("Amount", price.getText().toString().substring(3));
                System.out.println("3333333" + jsonObject);

            }
            if (delivery_charges.getText().toString().equals(": 0")){
                jsonObject.put("DeliveryCharges", "0");
                System.out.println("4444444" + jsonObject);

            }else{
                jsonObject.put("DeliveryCharges", delivery_charges.getText().toString().substring(3));
                System.out.println("444444444" + jsonObject);

            }
            if (getArguments().getString("ProdBrand")==null){
                jsonObject.put("Brand", "1");

            }else{
              //  jsonObject.put("Brand", brand.getText().toString().substring(2));
                jsonObject.put("Brand",brand.getText().toString().substring(2));
            }
            jsonObject.put("SellingListMasterId", 1);
            System.out.println("uuuuuuuuuuu" + jsonObject);

            jsonObject.put("ExpiryDate", "1/1/2020");
            jsonObject.put("Comments", comments);
            System.out.println("xxxxxx" + jsonObject);

            jsonObject.put("UserId", sessionManager.getRegId("userId"));
            jsonObject.put("Createdby", sessionManager.getRegId("userId"));
            System.out.println("555555555" + jsonObject);

            if (InventoryAdapter.prod_id!=null){
                jsonObject.put("ProductId", Integer.parseInt(InventoryAdapter.prod_id));
                System.out.println("6666666" + jsonObject);

            }else{
                jsonObject.put("ProductId", 0);
                System.out.println("6666666" + jsonObject);

            }

            System.out.println("rtyrtyrty" + jsonObject);

            Crop_Post.crop_posting(getActivity(), Urls.AddUpdateProductDetails, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("GetSellingTypeeeeeeee" + result);


                    try {
                        String status = result.getString("Status");
                        if (status.equals("Success")) {
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
                           /* if (InventoryAdapter.prod_id != null) {
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
                            }*/
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
