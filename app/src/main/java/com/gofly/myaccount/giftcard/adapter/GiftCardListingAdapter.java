package com.gofly.myaccount.giftcard.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.myaccount.giftcard.GiftCardActivity;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.parsingModel.GiftCardListModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GiftCardListingAdapter extends CommonRecyclerAdapter {


    List<GiftCardListModel> giftCardListModelList;
    GiftCardActivity giftCardActivity;


    public GiftCardListingAdapter(List<GiftCardListModel> giftCardListModelList,GiftCardActivity giftCardActivity) {
        this.giftCardListModelList = giftCardListModelList;
        this.giftCardActivity = giftCardActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.giftcardlistrow) {
            @Override
            public void onItemSelected(int position) {
            giftCardActivity.buyGiftCard(giftCardListModelList.get(position).getGift_id(),
                    giftCardListModelList.get(position).getImage(),
                    giftCardListModelList.get(position).getCard_discription(),
                    giftCardListModelList.get(position).getCard_type(),
                    giftCardListModelList.get(position).getCard_price(),
                    giftCardListModelList.get(position).getFinal_price());

            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {

        View lView = holder.getView();

        TextView   amount,buy;
        ImageView image;


        amount = lView.findViewById(R.id.tv_price);

        buy = lView.findViewById(R.id.tv_buy);
        image = lView.findViewById(R.id.iv_gifycard);
        amount.setText(giftCardListModelList.get(position).getFinal_price());
        if(giftCardListModelList.get(position).getImage().length() != 0){
            Picasso.get()
                    .load(giftCardListModelList.get(position).getImage())
                    .placeholder(R.drawable.backgroundflightimg)
                    .error(R.drawable.backgroundflightimg)
                    .into(image);
        }else {
            image.setImageDrawable(giftCardActivity.getResources()
                    .getDrawable(R.drawable.backgroundflightimg));
        }









    }

    @Override
    public int getItemCount() {
        return giftCardListModelList.size();
    }
}
