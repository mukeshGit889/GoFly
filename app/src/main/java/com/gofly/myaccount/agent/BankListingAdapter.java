package com.gofly.myaccount.agent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.parsingModel.GiftCardListModel;
import com.gofly.myaccount.giftcard.GiftCardActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BankListingAdapter extends CommonRecyclerAdapter {


    List<String> bankList;
    AddAgentActivity addAgentActivity;


    public BankListingAdapter(List<String> bankList, AddAgentActivity addAgentActivity) {
        this.bankList = bankList;
        this.addAgentActivity = addAgentActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.bank_list_row) {
            @Override
            public void onItemSelected(int position) {

                addAgentActivity.setBankName(bankList.get(position));
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {

        View lView = holder.getView();

        TextView   bankName;


        bankName = lView.findViewById(R.id.tv_bankName);
        bankName.setText(bankList.get(position));










    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }
}
