package com.SevenNine.Partnercode.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.SevenNine.Partnercode.Bean.StateBean;
import com.SevenNine.Partnercode.Fragment.AddNewAddressFragment;
import com.SevenNine.Partnercode.Fragment.Add_NewBankDetails_Fragment;
import com.SevenNine.Partnercode.Fragment.NewAddressFragment;
import com.SevenNine.Partnercode.R;

import java.util.List;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.MyStateHolder> {
    List<StateBean> stateBeans;
    Activity activity;
    public static String districtid;



    public DistrictAdapter(List<StateBean> stateBeans,Activity activity) {
        this.stateBeans = stateBeans;
        this.activity=activity;
    }


    @NonNull
    @Override
    public MyStateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View stateview=LayoutInflater.from(parent.getContext()).inflate(R.layout.state_name,parent,false);
        return new MyStateHolder(stateview);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyStateHolder holder, int position) {
        final StateBean stateBean=stateBeans.get(position);
        holder.statename.setText(stateBean.getName());



        holder.state_name_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                districtid=stateBean.getId();


                /*Add_New_Address_Fragment.district_txt.setText(holder.statename.getText().toString());
                Add_New_Address_Fragment.drawer.closeDrawers();*/
                if (Add_NewBankDetails_Fragment.page!=null){
                   // AddNewAddressFragment.ed_dstrt.setText(holder.statename.getText().toString());
                  //  AddNewAddressFragment.drawer.closeDrawers();

                    Add_NewBankDetails_Fragment.district.setText(holder.statename.getText().toString());
                    Add_NewBankDetails_Fragment.drawer.closeDrawers();
                }else {
                     AddNewAddressFragment.ed_dstrt.setText(holder.statename.getText().toString());
                      AddNewAddressFragment.drawer.closeDrawers();
                  //  NewAddressFragment.district.setText(holder.statename.getText().toString());
                  //  NewAddressFragment.drawer.closeDrawers();
                }
               // NewAddressFragment.district.setText(holder.statename.getText().toString());
              //  NewAddressFragment.drawer.closeDrawers();

            }
        });


    }

    @Override
    public int getItemCount() {
        return stateBeans.size();
    }

    public class MyStateHolder extends RecyclerView.ViewHolder{

        TextView statename;
        LinearLayout state_name_layout;

        public MyStateHolder(View itemView) {
            super(itemView);
            statename=itemView.findViewById(R.id.state_item);
            state_name_layout=itemView.findViewById(R.id.state_name_layout);


        }
    }
}
