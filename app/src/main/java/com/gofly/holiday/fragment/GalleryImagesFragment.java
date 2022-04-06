package com.gofly.holiday.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.holiday.adapter.ImageSliderViewPagerAdapter;
import com.gofly.main.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class GalleryImagesFragment extends BaseFragment {
    boolean viewPageFlag=false;
    private TextView[] dots;
    List<String> galleryList = new ArrayList<String>();
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.left)
    LinearLayout left;
    @BindView(R.id.right)
    LinearLayout right;

    private static int selectedPosition = 0;
  //  BannerSliderAdapter bannerSliderAdapter;
    public GalleryImagesFragment()
    {

    }
    public GalleryImagesFragment(List<String> galleryList)
    {
       this.galleryList=galleryList;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_gallery_images;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
       /* bannerSliderAdapter = new BannerSliderAdapter(GalleryImagesFragment.this,galleryList);
        view_pager.setAdapter(bannerSliderAdapter);
        view_pager.removeOnPageChangeListener(pageChangeListener);

        addBottomDots(0);*/

        ImageSliderViewPagerAdapter myViewPagerAdapter = new ImageSliderViewPagerAdapter(getActivity(),selectedPosition,galleryList);
        viewpager.setAdapter(myViewPagerAdapter);
        viewpager.setCurrentItem(selectedPosition);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.arrowScroll(View.FOCUS_LEFT);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.arrowScroll(View.FOCUS_RIGHT);
            }
        });

    }

}