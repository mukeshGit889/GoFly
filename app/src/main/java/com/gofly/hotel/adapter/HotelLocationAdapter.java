package com.gofly.hotel.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.hotel.fragment.HotelFilterFragment;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.parsingModel.LocationModel;

import java.util.List;

public class HotelLocationAdapter extends CommonRecyclerAdapter {

    Activity activity;
    HotelFilterFragment filterFragment;
    List<LocationModel> hotelLocation;

    public HotelLocationAdapter(Activity activity,
                                HotelFilterFragment filterFragment,
                                List<LocationModel> hotelLocation) {
        this.activity = activity;
        this.filterFragment = filterFragment;
        this.hotelLocation = hotelLocation;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.hotel_filter_item) {
            @Override
            public void onItemSelected(int position) {
                filterFragment.notifyLocationAction(position,
                        hotelLocation.get(position).getSelected(),
                        hotelLocation.get(position).getLocationName());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView airlineName = view.findViewById(R.id.flight_name);
        CheckBox isSelected = view.findViewById(R.id.is_selected);

        if(hotelLocation.get(position).getSelected()){
            isSelected.setChecked(true);
        }else {
            isSelected.setChecked(false);
        }

        airlineName.setText(hotelLocation.get(position).getLocationName());
    }

    @Override
    public int getItemCount() {
        return hotelLocation.size();
    }

}
