package com.gofly.main.adapter.landingAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.main.activity.MainActivity;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.parsingModel.landingPage.TopFlightModel;
import com.gofly.model.parsingModel.landingPage.TopHotelModel;
import com.gofly.utils.IntentAndFragmentService;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ptblr-1195 on 20/3/18.
 */

public class TopFlightAdapter extends CommonRecyclerAdapter {
    IntentAndFragmentService intentAndFragmentService;
    Activity activity;
    List<TopFlightModel> topFlightModelList;

    public TopFlightAdapter(Activity activity,
                            List<TopFlightModel> topFlightModelList) {
        this.activity = activity;
        this.topFlightModelList = topFlightModelList;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.top_flight_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        View view = holder.getView();
        ImageView flightImage = view.findViewById(R.id.package_image);
        TextView cityName = view.findViewById(R.id.city_name);


        Picasso.get()
                .load(topFlightModelList.get(position).getImage())
                .placeholder(R.drawable.hotel_placeholder)
                .error(R.drawable.hotel_placeholder)
                .into(flightImage);
        cityName.setText("From "+topFlightModelList.get(position).getFrom_airport_name()+" To "+topFlightModelList.get(position).getTo_airport_name());


        flightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentAndFragmentService = new IntentAndFragmentService();
                Bundle bundle = new Bundle();
                bundle.putInt("calling_fragment",8);
                bundle.putString("fromcityname",topFlightModelList.get(position).getFrom_airport_name());
                bundle.putString("fromcityid",topFlightModelList.get(position).getFrom_airport_code());
                bundle.putString("tocityname",topFlightModelList.get(position).getTo_airport_name());
                bundle.putString("tocityid",topFlightModelList.get(position).getTo_airport_code());


                intentAndFragmentService.intentDisplay(activity, MainActivity.class, bundle);
            }
        });

    }

    final Callback loaderCallBack = new Callback() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(Exception e) {

        }
    };

    @Override
    public int getItemCount() {
        return topFlightModelList.size();
    }
}
