package com.gofly.holiday.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.holiday.fragment.HolidaySearchFragment;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.parsingModel.holiday.PackageCountryList;
import com.gofly.model.parsingModel.holiday.PackageDuration;

import java.util.List;

/**
 * Created by ptblr-1195 on 11/4/18.
 */

public class PackageCountryAdapter extends CommonRecyclerAdapter {

    Activity activity;
    HolidaySearchFragment holidaySearchFragment;
    List<PackageCountryList> packageCountryLists;

    public PackageCountryAdapter(Activity activity,
                                 HolidaySearchFragment holidaySearchFragment,
                                 List<PackageCountryList> packageCountryLists) {
        this.activity = activity;
        this.holidaySearchFragment = holidaySearchFragment;
        this.packageCountryLists = packageCountryLists;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonViewHolder(viewGroup, R.layout.city_list_item) {
            @Override
            public void onItemSelected(int position) {
                holidaySearchFragment.updateCountry(
                        packageCountryLists.get(position).getCountry_name(),packageCountryLists.get(position).getPackage_country());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder viewHolder, int position) {
        View view = viewHolder.getView();
        TextView durationText = view.findViewById(R.id.place_name);
        durationText.setText(packageCountryLists.get(position).getCountry_name());
    }

    @Override
    public int getItemCount() {
        return packageCountryLists.size();
    }
}
