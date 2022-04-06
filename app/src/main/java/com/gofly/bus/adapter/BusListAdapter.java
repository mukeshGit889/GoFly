package com.gofly.bus.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.bus.fragment.BusListingFragment;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.parsingModel.bus.BusListModel;
import com.gofly.utils.Global;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 27-03-2018.
 */

public class BusListAdapter extends CommonRecyclerAdapter {

    Activity activity;
    BusListingFragment busListingFragment;
    List<BusListModel> busListModelList;
    List<JSONObject> busListModelList1 = new ArrayList<JSONObject>();

    public BusListAdapter(Activity activity,
                          BusListingFragment busListingFragment,
                          List<BusListModel> busListModelList,List<JSONObject> busListModelList1) {
        this.activity = activity;
        this.busListingFragment = busListingFragment;
        this.busListModelList = busListModelList;
        this.busListModelList1 = busListModelList1;

    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.bus_list_item) {
            @Override
            public void onItemSelected(int position) {
                busListingFragment.navigateToDetail(
                        position,
                        busListModelList.get(position).getCompanyName(),
                        busListModelList.get(position).getBusType(),
                        busListModelList.get(position).getDepartureTime(),
                        busListModelList.get(position).getArrivalTime(),
                        busListModelList.get(position).getPrice(),
                        busListModelList.get(position).getRoutScheduleId(),
                        busListModelList.get(position).getRoutCode(),
                        busListModelList.get(position).getDepartDate(),
                        busListModelList.get(position).getResultToken(),
                        busListModelList1.get(position));


            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView companyName, busType, departureTime, arrivalTime, travelTime, price, seatCount;
        LinearLayout sold_out_ll;
        companyName = view.findViewById(R.id.company_name);
        busType = view.findViewById(R.id.bus_type);
        departureTime = view.findViewById(R.id.departure_time);
        arrivalTime = view.findViewById(R.id.arrival_time);
        travelTime = view.findViewById(R.id.travel_time);
        price = view.findViewById(R.id.price);
        seatCount = view.findViewById(R.id.seat_count);
        sold_out_ll = view.findViewById(R.id.sold_out_ll);

        companyName.setText(busListModelList.get(position).getCompanyName());
        busType.setText(busListModelList.get(position).getBusType());
        departureTime.setText(busListModelList.get(position).getDepartureTime());
        arrivalTime.setText(busListModelList.get(position).getArrivalTime());
        travelTime.setText(busListModelList.get(position).getTravelTime());
        /*price.setText(activity.getResources().getString(R.string.Rs)
                +" "+busListModelList.get(position).getPrice());*/
        price.setText(Global.currencySymbol
                +" "+busListModelList.get(position).getPrice());
        seatCount.setText(busListModelList.get(position).getSeatCount());

        if(Integer.parseInt(busListModelList.get(position).getSeatCount())==0)
        {
            sold_out_ll.setVisibility(View.VISIBLE);
        }else{
            sold_out_ll.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return busListModelList.size();
    }

}