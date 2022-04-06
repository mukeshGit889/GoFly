package com.gofly.myaccount.wallet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.parsingModel.WalletTransctionModel;
import com.gofly.utils.Global;

import java.util.List;

public class WalletAdapter extends CommonRecyclerAdapter {


    List<WalletTransctionModel> walletTransctionModelList;
    Context mContext;


    public WalletAdapter(List<WalletTransctionModel> walletTransctionModelList, Context mContext) {
        this.walletTransctionModelList = walletTransctionModelList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.wallet_transactionrow) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {

        View lView = holder.getView();

        TextView description, txtDate, referenceId, amount, typeofTransaction;

        description = lView.findViewById(R.id.tv_description);
        txtDate = lView.findViewById(R.id.tv_r_date);
        referenceId = lView.findViewById(R.id.tv_refid);

        amount = lView.findViewById(R.id.tv_amount);

        typeofTransaction = lView.findViewById(R.id.tv_rechargeon);
        txtDate.setText(Global.changeDateFormat(walletTransctionModelList.get(position).getCreated_datetime()));

        description.setText(walletTransctionModelList.get(position).getDescription());
      //  Date lAvlDate = CommonUtils.convertStrToDate(transferBookingInfoList.get(position).getTravel_date(), "yyyy-MM-dd");

     //   txtDate.setText(CommonUtils.conDateToStr(lAvlDate, "dd-MMM-yyyy"));

        referenceId.setText(walletTransctionModelList.get(position).getReference_number());
        if (!walletTransctionModelList.get(position).getCredit_amount().equals(""))
        {
            amount.setText("+"+walletTransctionModelList.get(position).getCredit_amount());
            typeofTransaction.setText("Recharged on");
            amount.setTextColor(mContext.getResources().getColor(R.color.greenColor));
        }
        else
        {
            amount.setText("-"+walletTransctionModelList.get(position).getDebit_amount());
            typeofTransaction.setText("Deducted on");
            amount.setTextColor(mContext.getResources().getColor(R.color.redColor));
        }



       // typeofTransaction.setText(walletTransctionModelList.get(position).getBookingStatus());


    }

    @Override
    public int getItemCount() {
        return walletTransctionModelList.size();
    }
}
