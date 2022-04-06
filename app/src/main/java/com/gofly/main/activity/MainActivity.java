package com.gofly.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.gofly.LandingActivityNew;
import com.gofly.R;
import com.gofly.bus.fragment.BusSearchFragment;
import com.gofly.carhire.CarHireActivity;
import com.gofly.carhire.CarHireFragment;
import com.gofly.flight.fragment.FlightFragment;
import com.gofly.holiday.fragment.HolidaySearchFragment;
import com.gofly.hotel.fragment.HotelSearchFragment;
import com.gofly.interfaces.ProfileFilterNotify;
import com.gofly.model.parsingModel.PromoCodeInfo;
import com.gofly.more.MoreActivity;
import com.gofly.more.MoreFragment;
import com.gofly.myaccount.MyAccountPageActivity;
import com.gofly.sight_seeing.fragment.SightSeeingSelectionFragment;
import com.gofly.transfers.fragment.TransfersSearchFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    Bundle bundle;
    @OnClick(R.id.iv_back)
    void backAction(){
        onBackPressed();
    }

    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @BindView(R.id.action_type)
    ImageView actionType;

    @BindView(R.id.sort_action)
    ImageView sortList;

    @OnClick(R.id.action_type)
    void actionType(){
        switch (actionValue){
            case 0:
                profileFilterNotify.notifyProfileView();
                break;
            case 1:
                profileFilterNotify.notifyFilter();
                break;
        }
    }

    @OnClick(R.id.sort_action)
    void sortAction(){
        profileFilterNotify.notifySort();
    }

    Integer actionValue = 0;
    ProfileFilterNotify profileFilterNotify;
    Bundle extras;
    ArrayList<PromoCodeInfo> promoList;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         extras = getIntent().getExtras();
        fragmentCallingAction(extras.getBundle("bundleWithValues")
                .getInt("calling_fragment"));
        }

    public void fragmentCallingAction(int i) {
        switch (i){
            case 1:
                intentAndFragmentService.fragmentDisplay(this,R.id.main_frame,
                        new FlightFragment(),null,false);
                break;
            case 2:
                intentAndFragmentService.fragmentDisplay(this,R.id.main_frame,
                        new HotelSearchFragment(), null, false);
                break;
            case 3:
                intentAndFragmentService.fragmentDisplay(this,R.id.main_frame,
                        new BusSearchFragment(), null, false);
                break;
            case 4:
                intentAndFragmentService.fragmentDisplay(this, R.id.main_frame,
                        new HolidaySearchFragment(), null, false);
                break;
            case 5:
                intentAndFragmentService.fragmentDisplay(this, R.id.main_frame,
                        new TransfersSearchFragment(), null, false);
               break;
            case 6:
                String hotelCityID,hotelCityName;
                hotelCityName=extras.getBundle("bundleWithValues").getString("cityname");
                hotelCityID=extras.getBundle("bundleWithValues").getString("cityid");
                intentAndFragmentService.fragmentDisplay(this,R.id.main_frame,
                        new HotelSearchFragment(hotelCityID, hotelCityName,5), null, false);
                break;
            case 7:
                intentAndFragmentService.fragmentDisplay(this,R.id.main_frame,new SightSeeingSelectionFragment(),
                        null,false);

                break;
            case 8:
                String fromCityID,fromCityName,toCityID,toCityName;
                fromCityID=extras.getBundle("bundleWithValues").getString("fromcityid");
                fromCityName=extras.getBundle("bundleWithValues").getString("fromcityname");
                toCityID=extras.getBundle("bundleWithValues").getString("tocityid");
                toCityName=extras.getBundle("bundleWithValues").getString("tocityname");


                intentAndFragmentService.fragmentDisplay(this,R.id.main_frame,
                        new FlightFragment(fromCityID, fromCityName,toCityID,toCityName,8), null, false);
                break;


            case 9:

             //   intentAndFragmentService.intentDisplay(this, MoreActivity.class, bundle);

                intentAndFragmentService.fragmentDisplay(this,R.id.main_frame,new MoreFragment(),
                        null,false);

                break;

            case 10:
                intentAndFragmentService.fragmentDisplay(this,R.id.main_frame,new CarHireFragment(),
                        null,false);


                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @OnClick(R.id.logo)
    public void doRefresh(){
        intentAndFragmentService.intentDisplay(this, LandingActivityNew.class, bundle);
    }


    public void hideToolBar(int i)
    {
        switch (i){
            case 1:
                toolBar.setVisibility(View.VISIBLE);
                break;
            case 2:
                toolBar.setVisibility(View.GONE);
                break;
        }
    }

    public void toolbarActionIcon(Object object, Integer actionValue){
        profileFilterNotify = (ProfileFilterNotify) object;
        switch (actionValue){
            case 0:
                sortList.setVisibility(View.GONE);
                this.actionValue = actionValue;
                break;
            case 1:
                sortList.setVisibility(View.VISIBLE);
                this.actionValue = actionValue;
                break;

        }
    }
    @Override
    public void onBackPressed() {
      super.onBackPressed();
    }

}