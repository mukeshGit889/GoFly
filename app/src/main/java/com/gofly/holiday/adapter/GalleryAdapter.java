package com.gofly.holiday.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gofly.holiday.fragment.HolidayDetailFragment;
import com.squareup.picasso.Picasso;
import com.gofly.R;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.utils.webservice.ApiConstants;

import java.util.List;

/**
 * Created by ptblr-1195 on 17/4/18.
 */

public class GalleryAdapter extends CommonRecyclerAdapter {

    Activity activity;
    List<String> galleryList;
    ApiConstants apiConstants;
    HolidayDetailFragment holidayDetailFragment;

    public GalleryAdapter(Activity activity,
                          List<String> galleryList,
                          HolidayDetailFragment holidayDetailFragment) {
        this.activity = activity;
        this.galleryList = galleryList;
        apiConstants = new ApiConstants();
        this.holidayDetailFragment=holidayDetailFragment;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonViewHolder(viewGroup, R.layout.gallery_item) {
            @Override
            public void onItemSelected(int position) {
            holidayDetailFragment.openGalleryImages();
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder viewHolder, int i) {
        View view = viewHolder.getView();
        ImageView imageView = view.findViewById(R.id.image_view);
        imageView.bringToFront();

        Picasso.get()
                .load(galleryList.get(i))
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

}