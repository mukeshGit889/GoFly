package com.gofly.holiday.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.holiday.fragment.HolidayCitySearch;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.parsingModel.holiday.HolidayCity;

import java.util.List;

/**
 * Created by ptblr-1195 on 10/4/18.
 */

public class HolidayCityAdapter extends CommonRecyclerAdapter {

    Activity activity;
    HolidayCitySearch holidayCitySearch;
    List<HolidayCity> holidayCities;

    public HolidayCityAdapter(Activity activity,
                              HolidayCitySearch holidayCitySearch,
                              List<HolidayCity> holidayCities) {
        this.activity = activity;
        this.holidayCitySearch = holidayCitySearch;
        this.holidayCities = holidayCities;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new CommonViewHolder(parent, R.layout.city_list_item) {
            @Override
            public void onItemSelected(int position) {
                holidayCitySearch.notifyCountryId(
                        holidayCities.get(position).getCityId(),
                        holidayCities.get(position).getCityName());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int i) {
        View view = holder.getView();
        TextView countryName = view.findViewById(R.id.place_name);
        countryName.setText(holidayCities.get(i).getCityName());
    }

    @Override
    public int getItemCount() {
        return holidayCities.size();
    }

}