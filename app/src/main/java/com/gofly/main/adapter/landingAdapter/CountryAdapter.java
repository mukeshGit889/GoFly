package com.gofly.main.adapter.landingAdapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.myaccount.kyc.KycActivity;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.CountryInfo;
import com.gofly.utils.Global;

import java.util.ArrayList;

public class CountryAdapter extends CommonRecyclerAdapter {


  //  List<WalletTransctionModel> walletTransctionModelList;
    KycActivity  kycActivity;
    ArrayList<CountryInfo> countryList;



    public CountryAdapter(ArrayList<CountryInfo> countryList, KycActivity kycActivity) {
        this.countryList = countryList;
        this.kycActivity = kycActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.countryrow) {
            @Override
            public void onItemSelected(int position) {

                kycActivity.selectedCountry(position);
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
