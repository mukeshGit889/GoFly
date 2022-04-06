package com.gofly.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.main.activity.SignUpActivity;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.CountryInfo;
import com.gofly.myaccount.kyc.KycActivity;
import com.gofly.utils.Global;

import java.util.ArrayList;

public class SignUpCountryAdapter extends CommonRecyclerAdapter {


  //  List<WalletTransctionModel> walletTransctionModelList;
    SignUpActivity signUpActivity;
    ArrayList<CountryInfo> countryList;



    public SignUpCountryAdapter(ArrayList<CountryInfo> countryList, SignUpActivity signUpActivity) {
        this.countryList = countryList;
        this.signUpActivity = signUpActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.countryrow) {
            @Override
            public void onItemSelected(int position) {

                signUpActivity.selectedCountry(position);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {

        View lView = holder.getView();

        TextView countryName;

        countryName = lView.findViewById(R.id.tv_countryname);



        countryName.setText("("+Global.countryList.get(position).getCountry_code()+")"+Global.countryList.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return Global.countryList.size();
    }
}
