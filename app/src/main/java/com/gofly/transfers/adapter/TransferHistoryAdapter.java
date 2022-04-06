package com.gofly.transfers.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.parsingModel.sightSeeing.HistoryData;
import com.gofly.model.parsingModel.transfers.TransferBookingInfo;
import com.gofly.utils.CommonUtils;

import java.util.Date;
import java.util.List;

public class TransferHistoryAdapter extends CommonRecyclerAdapter {


    List<TransferBookingInfo> transferBookingInfoList;
    Context mContext;


    public TransferHistoryAdapter(List<TransferBookingInfo> transferBookingInfoList, Context mContext) {
        this.transferBookingInfoList = transferBookingInfoList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.traansferbooking_history) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {

        View lView = holder.getView();

        TextView txtName, txtDate, bookingId, txtAddress, totalFare, bookingStatus;

        txtName = lView.findViewById(R.id.txtName);
        txtDate = lView.findViewById(R.id.txtDate);
        bookingId = lView.findViewById(R.id.bookingId);
       // txtAddress = lView.findViewById(R.id.txtAddress);
        totalFare = lView.findViewById(R.id.totalFare);

        bookingStatus = lView.findViewById(R.id.bookingStatus);

        txtName.setText(transferBookingInfoList.get(position).getProduct_name());
        Date lAvlDate = CommonUtils.convertStrToDate(transferBookingInfoList.get(position).getTravel_date(), "yyyy-MM-dd");
       // txtDate.setText(transferBookingInfoList.get(position).getTravel_date());
        txtDate.setText(CommonUtils.conDateToStr(lAvlDate, "dd-MMM-yyyy"));

        bookingId.setText(transferBookingInfoList.get(position).getBooking_id());

       // txtAddress.setText(transferBookingInfoList.get(position).getTxtAddress());
        totalFare.setText(transferBookingInfoList.get(position).getCurrency() + " " + transferBookingInfoList.get(position).getTotal_fare());

        bookingStatus.setText(transferBookingInfoList.get(position).getBookingStatus());


    }

    @Override
    public int getItemCount() {
        return transferBookingInfoList.size();
    }
}
