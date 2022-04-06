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
import com.gofly.model.parsingModel.hotel.AmenitiesModel;

import java.util.List;

/**
 * Created by ptblr-1195 on 23/3/18.
 */

public class AmenitiesAdapter extends CommonRecyclerAdapter {

    Activity activity;
    HotelFilterFragment hotelFilterFragment;
    List<AmenitiesModel> amenitiesList;

    public AmenitiesAdapter(Activity activity,
                            HotelFilterFragment hotelFilterFragment,
                            List<AmenitiesModel> amenitiesList) {
        this.activity = activity;
        this.hotelFilterFragment = hotelFilterFragment;
        this.amenitiesList = amenitiesList;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonViewHolder(viewGroup, R.layout.hotel_filter_item) {
            @Override
            public void onItemSelected(int position) {
                hotelFilterFragment.notifyAction(position,
                        amenitiesList.get(position).getSelected(),
                        amenitiesList.get(position).getAmenityName());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView amenityName = view.findViewById(R.id.flight_name);
        CheckBox isSelected = view.findViewById(R.id.is_selected);

        String upperString = amenitiesList.get(position).getAmenityName().substring(0, 1).toUpperCase() + amenitiesList.get(position).getAmenityName().substring(1).toLowerCase();
        amenityName.setText(upperString);

        if(amenitiesList.get(position).getSelected()){
            isSelected.setChecked(true);
        }else {
            isSelected.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return amenitiesList.size();
    }
}
