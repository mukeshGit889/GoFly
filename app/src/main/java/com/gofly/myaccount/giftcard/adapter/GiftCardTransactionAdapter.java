package com.gofly.myaccount.giftcard.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.parsingModel.GiftCardTransactionModel;
import com.gofly.utils.Global;

import java.util.ArrayList;

public class GiftCardTransactionAdapter extends CommonRecyclerAdapter {


    ArrayList<GiftCardTransactionModel> giftCardTransactionModelList;
    Context mContext;


    public GiftCardTransactionAdapter(ArrayList<GiftCardTransactionModel> giftCardTransactionModelList, Context mContext) {
        this.giftCardTransactionModelList = giftCardTransactionModelList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.giftcardtransactionrow) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {

        View lView = holder.getView();

        TextView cardType, giftCardCode,txtDate, referenceId, giftcardamount, paidAmount;

        cardType = lView.findViewById(R.id.tv_card_type);
        giftCardCode = lView.findViewById(R.id.tv_giftcardcode);
        txtDate = lView.findViewById(R.id.tv_r_date);
        referenceId = lView.findViewById(R.id.tv_refid);
        giftcardamount = lView.findViewById(R.id.tv_giftcardamount);
        paidAmount = lView.findViewById(R.id.tv_paidamount);

        txtDate.setText(Global.changeDateFormat(giftCardTransactionModelList.get(position).getCreated_datetime()));
        cardType.setText(giftCardTransactionModelList.get(position).getCard_type());
        giftCardCode.setText(giftCardTransactionModelList.get(position).getGift_card_code());
        giftcardamount.setText(giftCardTransactionModelList.get(position).getGiftcardAmount());
        paidAmount.setText(giftCardTransactionModelList.get(position).getPaidAmount());

        // Date lAvlDate = CommonUtils.convertStrToDate(transferBookingInfoList.get(position).getTravel_date(), "yyyy-MM-dd");

     //   txtDate.setText(CommonUtils.conDateToStr(lAvlDate, "dd-MMM-yyyy"));

        referenceId.setText(giftCardTransactionModelList.get(position).getBook_id());






    }

    @Override
    public int getItemCount() {
        return giftCardTransactionModelList.size();
    }
}
