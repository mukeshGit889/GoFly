package com.gofly.holiday.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.gofly.R;
import com.gofly.utils.ZoomableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderViewPagerAdapter extends PagerAdapter {

    Context context;
    private int selectedPos ;
    List<String> arrayList;


    public ImageSliderViewPagerAdapter(Context context, int selectedPosition, List<String>  images){
        this.context =context;
        this.selectedPos = selectedPosition;
        this.arrayList = images;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.image_slider_viewpager_layout, container, false);

        ZoomableImageView imageViewPreview =  view.findViewById(R.id.image_preview);


        Picasso.get().load(arrayList.get(position)).error(R.drawable.no_data_img).into(imageViewPreview);


        imageViewPreview.setMaxZoom(4);


        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((View) object));
    }
}
