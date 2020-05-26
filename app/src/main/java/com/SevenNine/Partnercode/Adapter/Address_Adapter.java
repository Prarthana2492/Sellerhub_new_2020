package com.SevenNine.Partnercode.Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SevenNine.Partnercode.Bean.Add_New_Address_Bean;
import com.SevenNine.Partnercode.Fragment.NewAddressFragment;
import com.SevenNine.Partnercode.R;
import com.SevenNine.Partnercode.SessionManager;
import com.SevenNine.Partnercode.Urls;
import com.SevenNine.Partnercode.Volly_class.Crop_Post;
import com.SevenNine.Partnercode.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONObject;

import java.util.List;

public class Address_Adapter extends RecyclerView.Adapter<Address_Adapter.MyViewHolder> {


    private List<Add_New_Address_Bean> productList;
    Activity activity;
    Fragment selectedFragment;
    public static String add_id;
    SessionManager sessionManager;
    String status,message;


    public Address_Adapter(Activity activity, List<Add_New_Address_Bean> moviesList) {
        this.productList = moviesList;
        this.activity=activity;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout item,linearLayout;
        public TextView bankname,name,phone_no,ifsc,area,city,remove,edit;


        public MyViewHolder(View view) {
            super(view);

           // item=view.findViewById(R.id.item);
            bankname=view.findViewById(R.id.bankname);
            name=view.findViewById(R.id.name);
          //  phone_no=view.findViewById(R.id.ph_no);
            area=view.findViewById(R.id.area);
            remove=view.findViewById(R.id.delete);
            edit=view.findViewById(R.id.edit);


            sessionManager = new SessionManager(activity);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.address_adapter, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Add_New_Address_Bean products = productList.get(position);
                  add_id = products.getAdd_id();


       // holder.bankname.setText(products.getBankname());
        holder.name.setText(products.getAdd_name()+" " +products.getAdd_door_no()+" " +products.getAdd_street()+",");
      //  holder.phone_no.setText(products.getPhonenumber()+" | "+products.getIfsccode());
        holder.area.setText(products.getAdd_landmark()+" "+ products.getAdd_state()+","+products.getAdd_pincode());



        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_id = products.getAdd_id();

                try{
                    JSONObject jsonObject  = new JSONObject();
                    jsonObject.put("UserAddressId",add_id);
                    jsonObject.put("UserId",sessionManager.getRegId("userId"));

                    Crop_Post.crop_posting(activity, Urls.DeleteUserAddress, jsonObject, new VoleyJsonObjectCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject result) {
                            System.out.println("111111dddd" + result);

                            try{

                                status = result.getString("Status");
                                message = result.getString("Message");

                                if(status.equals("1")){


                                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

                                }

                                productList.remove(position);
                                notifyDataSetChanged();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });


                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_id = products.getAdd_id();

                Bundle bundle = new Bundle();
                bundle.putString("prof_add_status","edit_add_addressss");
                bundle.putString("addr_name",products.getAdd_name());
                bundle.putString("addr_mobile_number",products.getAdd_mobile());
                bundle.putString("addr_address",products.getAdd_street());
                bundle.putString("addr_pincode",products.getAdd_pincode());
                bundle.putString("addr_landmark",products.getAdd_landmark());
                bundle.putString("addr_state",products.getAdd_state());
                bundle.putString("addr_district",products.getAdd_district());
                bundle.putString("addr_block",products.getAdd_taluk());
                bundle.putString("addr_village",products.getAdd_village());

                selectedFragment = NewAddressFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.addToBackStack("address_list_page");
                transaction.commit();

            }
        });




    }





    @Override
    public int getItemCount() {
        return productList.size();
    }










}
