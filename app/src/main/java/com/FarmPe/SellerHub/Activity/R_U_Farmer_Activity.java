package com.FarmPe.SellerHub.Activity;


import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.FarmPe.SellerHub.R;
import com.FarmPe.SellerHub.SessionManager;
import com.FarmPe.SellerHub.Urls;
import com.FarmPe.SellerHub.Volly_class.Crop_Post;
import com.FarmPe.SellerHub.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONException;
import org.json.JSONObject;


public class R_U_Farmer_Activity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{


    TextView toolbar_title;
    Fragment selectedFragment;
    String toast_internet,toast_nointernet;
    EditText user_name;
    RadioGroup radio_group_farmer;
    JSONObject lngObject;
    SessionManager sessionManager;
    LinearLayout back_feed,main_layout,continuebtn;
    public static String status,message;
    int selectedId;
    String stat="3";
    public static boolean connectivity_check;
    ConnectivityReceiver connectivityReceiver;
    RadioGroup radioGroup;
    String name;
    @Override
    protected void onStop()
    {
        unregisterReceiver(connectivityReceiver);
        super.onStop();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }


    private void showSnack(boolean isConnected) {
        String message = null;
        int color=0;
        if (isConnected) {
            if(connectivity_check) {
                message = "Good! Connected to Internet";
                color = Color.WHITE;
                int duration= 1000;

                Snackbar.make(findViewById(android.R.id.content),toast_nointernet, duration).show();

                connectivity_check=false;
            }

        } else {
            message = "No Internet Connection";
            color = Color.RED;

            int duration=1000;
            connectivity_check=true;

            Snackbar.make(findViewById(android.R.id.content),toast_nointernet, duration).show();


        }
    }

    @Override
    public void onResume() {
        super.onResume();

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        MyApplication.getInstance().setConnectivityListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_u_farmer_layout);

        checkConnection();

        sessionManager = new SessionManager(this);
        back_feed=findViewById(R.id.back_feed);
        main_layout=findViewById(R.id.main_layout);
        toolbar_title=findViewById(R.id.toolbar_title);
        continuebtn=findViewById(R.id.continuebtn);
        radioGroup= findViewById(R.id.radio_group_time);
        user_name=findViewById(R.id.username);


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String Name = intent.getStringExtra("name");
        String UserType = intent.getStringExtra("type");


       user_name.setText(intent.getStringExtra("name"));




        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = group.findViewById(checkedId);

                if (radioButton.getTag().toString().equals("1")) {
                    stat = "1";


                } else if (radioButton.getTag().toString().equals("2")) {
                    stat = "2";


                }  else if (radioButton.getTag().toString().equals("3")) {
                    stat = "3";


                }else if (radioButton.getTag().toString().equals("4")) {
                    stat = "4";


                }else if (radioButton.getTag().toString().equals("5")) {
                    stat = "5";
                }
                else if (radioButton.getTag().toString().equals("6")) {
                    stat = "6";
                }
               else{

                }
            }

        });

        System.out.println("radioidselectedId"+stat);
        try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));
            toast_internet = lngObject.getString("GoodConnectedtoInternet");
            toast_nointernet = lngObject.getString("NoInternetConnection");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        name = user_name.getText().toString();
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(user_name.getText().toString().equals("")){
                    Toast toast = Toast.makeText(R_U_Farmer_Activity.this,"Please enter name", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    toast.show();

                }else if((radioGroup.getCheckedRadioButtonId()==-1)){

                    Toast toast = Toast.makeText(R_U_Farmer_Activity.this,"Please select any one option ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    toast.show();

                }




                else{
                    AddUpdateUserDetails();

                }

                //  sessionManager.createRegisterSession(sessionManager.getRegId("phone"));
            }
        });




    }





    private void AddUpdateUserDetails() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserName",user_name.getText().toString());
            jsonObject.put("UserTypeId",stat);
           /* jsonObject.put("UserId",sessionManager.getRegId("userId"));
            jsonObject.put("CreatedBy",sessionManager.getRegId("userId"));
         */ jsonObject.put("UserId",sessionManager.getRegId("userId"));
            jsonObject.put("CreatedBy",sessionManager.getRegId("userId"));
//UserDetailsId
            System.out.println("nnnnnnnnnnnnnnnaaaaaaaaa"+jsonObject);

            Crop_Post.crop_posting(R_U_Farmer_Activity.this, Urls.AddUpdateUserDetails, jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("AddFeedbackkkkkkkkkkkkkkkkkkkkkkk"+result);

                    try{

                        status= result.getString("Status");
                        message = result.getString("Message");
                        if(!(status.equals("0"))){
                            Intent intent1 = new Intent(R_U_Farmer_Activity.this,LandingPageActivity.class);
                            startActivity(intent1);
                           /* Intent intent = new Intent(R_U_Farmer_Activity.this,FirmShopDetailsActivity.class);
                            startActivity(intent);*/
                            sessionManager.createLoginSession2(name);
                           // sessionManager.save_name(jsonObject.getString("PhoneNo"));
                            // Toast.makeText(R_U_Farmer_Activity.this,message,Toast.LENGTH_SHORT).show();

                             /*if(getIntent().getStringExtra("navi_from").equals("profile")){

                                 Intent intent1 = new Intent(R_U_Farmer_Activity.this,Privacy_Activity.class);
                                 startActivity(intent1);
                             }*/

                        }

                    }catch (Exception e){
                        e.printStackTrace();

                    }


                }
            });




        }catch (Exception e){
            e.printStackTrace();
        }


       /* user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (radioGroup.getCheckedRadioButtonId() != -1)
                {
                    // no radio buttons are checked
                    if (user_name.getText().toString().equals("")) {
                        continuebtn.setBackgroundResource(R.drawable.grey_curved_border);

                    } else {
                        continuebtn.setBackgroundResource(R.drawable.border_filled_red_not_curved);

                        continuebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               AddUpdateUserDetails();
                                Intent intent = new Intent(R_U_Farmer_Activity.this,FirmShopDetailsActivity.class);
                                startActivity(intent);
                                //  sessionManager.createRegisterSession(sessionManager.getRegId("phone"));
                            }
                        });
                    }
                }

            }
        });
*/

    }

    @Override
    public void onBackPressed() {

        Intent intent = getIntent();

        if( intent.getStringExtra("navi_from").equals("profile")){
            Intent intent1 = new Intent(R_U_Farmer_Activity.this,Privacy_Activity.class);
            startActivity(intent1);
        }else{
               Intent intent1=new Intent(R_U_Farmer_Activity.this,NewSignUpActivity.class);

        startActivity(intent1);
        }



    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}