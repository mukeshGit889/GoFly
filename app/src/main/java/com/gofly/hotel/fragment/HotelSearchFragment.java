package com.gofly.hotel.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gofly.main.activity.MainActivity;
import com.gofly.more.MoreActivity;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.gofly.R;
import com.gofly.flight.fragment.CitySearchFragment;
import com.gofly.interfaces.CitySearch;
import com.gofly.interfaces.DatePickerNotify;
import com.gofly.main.fragment.BaseFragment;
import com.gofly.model.parsingModel.CurrencyConverter;
import com.gofly.model.parsingModel.hotel.search.RoomGuest;
import com.gofly.model.parsingModel.hotel.search.SearchMain;
import com.gofly.model.parsingModel.hotel.travellerSelection.TravellerSelectionMain;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ptblr-1195 on 21/3/18.
 */

public class HotelSearchFragment extends BaseFragment implements
        DatePickerNotify, CitySearch, WebInterface {

    Bundle bundle;
    Dialog nightCountdialog;
    public HotelSearchFragment(){

    }
    String hotelCityName;
    int callingFragment=0;
    ArrayList<CurrencyConverter> currencyList=new ArrayList<CurrencyConverter>();
    @SuppressLint("ValidFragment")
    public HotelSearchFragment(String hotelCityID, String hotelCityName,int callingFragment){
        cityId=hotelCityID;
        this.hotelCityName=hotelCityName;
        this.callingFragment=callingFragment;
    }

    @BindView(R.id.week_name) TextView ciWeekName;

    @BindView(R.id.date_value) TextView ciDateValue;

    @BindView(R.id.month_value) TextView ciMonthValue;

    @BindView(R.id.to_week_name) TextView coWeekValue;

    @BindView(R.id.to_date_value) TextView coDateValue;

    @BindView(R.id.to_month_value) TextView coMonthValue;

    @BindView(R.id.search_city_name) TextView searchCityName;

    @BindView(R.id.night_count) TextView nightCountText;

    @BindView(R.id.city_name) TextView cityNameText;

    @BindView(R.id.passenger_count) TextView passengerCountText;

    @BindView(R.id.passenger_detail_count) TextView passengerDetailCount;

    @BindView(R.id.room_count) TextView roomCountText;

    @OnClick(R.id.ll_flight)
    void openFlightSearch(){
        //   callActivity(2);
        ((MainActivity)getActivity()).fragmentCallingAction(1);
    }

    @OnClick(R.id.ll_bus)
    void openbusSearch(){
        ((MainActivity)getActivity()).fragmentCallingAction(3);
    }

    @OnClick(R.id.ll_holidays)
    void openholidaySearch(){
        ((MainActivity)getActivity()).fragmentCallingAction(4);
    }
    @OnClick(R.id.ll_more)
    void openMorePage(){
        // callActivity(9);
    //    intentAndFragmentService.intentDisplay(getContext(), MoreActivity.class, bundle);
        ((MainActivity)getActivity()).fragmentCallingAction(9);
    }

    @OnClick(R.id.night_count)
    void openNightCountDialog()
    {
        nightCountdialog = new Dialog(getActivity());
        nightCountdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nightCountdialog.setCancelable(true);
        nightCountdialog.setContentView(R.layout.nightcoutdialog);
        nightCountdialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        TextView onenight=nightCountdialog.findViewById(R.id.onenight);
        TextView secondnight=nightCountdialog.findViewById(R.id.secondnight);
        TextView thirdnight=nightCountdialog.findViewById(R.id.thirdnight);
        TextView fourthight=nightCountdialog.findViewById(R.id.fourthight);
        TextView fivenight=nightCountdialog.findViewById(R.id.fivenight);
        TextView sixnight=nightCountdialog.findViewById(R.id.sixnight);
        TextView sevennight=nightCountdialog.findViewById(R.id.sevennight);
        TextView eightnight=nightCountdialog.findViewById(R.id.eightnight);
        TextView ninenight=nightCountdialog.findViewById(R.id.ninenight);
        TextView tennight=nightCountdialog.findViewById(R.id.tennight);



        onenight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightCountText.setText("1 Night's");

                nightCountdialog.dismiss();
                setCheckOutDateByNightCount(checkInDate,1);
            }
        });
        secondnight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightCountText.setText("2 Night's");

                nightCountdialog.dismiss();
                setCheckOutDateByNightCount(checkInDate,2);
            }
        });

        thirdnight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightCountText.setText("3 Night's");
                nightCountdialog.dismiss();
                setCheckOutDateByNightCount(checkInDate,3);
            }
        });
        fourthight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightCountText.setText("4 Night's");

                nightCountdialog.dismiss();
                setCheckOutDateByNightCount(checkInDate,4);

            }
        });
        fivenight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightCountText.setText("5 Night's");

                nightCountdialog.dismiss();
                setCheckOutDateByNightCount(checkInDate,5);

            }
        });
        sixnight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightCountText.setText("6 Night's");

                nightCountdialog.dismiss();
                setCheckOutDateByNightCount(checkInDate,6);

            }
        });
        sevennight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightCountText.setText("7 Night's");

                nightCountdialog.dismiss();
                setCheckOutDateByNightCount(checkInDate,7);

            }
        });
        eightnight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightCountText.setText("8 Night's");

                nightCountdialog.dismiss();
                setCheckOutDateByNightCount(checkInDate,8);

            }
        });
        ninenight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightCountText.setText("9 Night's");

                nightCountdialog.dismiss();
                setCheckOutDateByNightCount(checkInDate,9);

            }
        });
        tennight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightCountText.setText("10 Night's");

                nightCountdialog.dismiss();
                setCheckOutDateByNightCount(checkInDate,10);

            }
        });

        nightCountdialog.show();
    }


    @OnClick(R.id.check_in)
    void checkIn(){
        commonUtils.callDatePicker(this,HotelSearchFragment.this,checkInDate,
                1);
    }



    @OnClick(R.id.check_out)
    void checkOut(){
        if(checkOutDate.equals(""))
        {
            checkOutDate = checkInDate;
        }
        commonUtils.callDatePicker(this,HotelSearchFragment.this,checkInDate,checkOutDate,
                2);
    }

    @OnClick(R.id.city_name)
    void cityName(){
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new CitySearchFragment(HotelSearchFragment.this, 5),
                null, true);
    }

    @OnClick(R.id.guest_layout)
    void guestLayoutClick(){
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new GuestRoomFragment(HotelSearchFragment.this, travellerSelectionMains), null, true);
    }

    @OnClick(R.id.room_layout)
    void roomLayoutClick(){
        guestLayoutClick();
    }

    @OnClick(R.id.search_hotel)
    void searchHotel(){
        if(cityId != null){
            applicationPreference.setData(
                    applicationPreference.hotelCityCode, cityId);
            applicationPreference.setData(
                    applicationPreference.hotelCityName,
                    cityNameText.getText().toString());

            List<RoomGuest> roomGuests = new ArrayList<RoomGuest>();
            if(travellerSelectionMains.size() != 0){
                int i =0;
                while (i < travellerSelectionMains.size()){
                    List<String> childAge = new ArrayList<String>();
                    switch (travellerSelectionMains.get(i).getChildValue()){
                        case 0:

                            break;
                        case 1:
                            childAge.add(travellerSelectionMains.get(i).getChildAgeOne());
                            break;
                        case 2:
                            childAge.add(travellerSelectionMains.get(i).getChildAgeOne());
                            childAge.add(travellerSelectionMains.get(i).getChildAgeTwo());
                            break;
                    }
                    RoomGuest roomGuest = new RoomGuest(
                            String.valueOf(travellerSelectionMains.get(i).getAdultValue()),
                            String.valueOf(travellerSelectionMains.get(i).getChildValue()),
                            childAge);
                    roomGuests.add(roomGuest);
                    i++;
                }
            }else {
                List<String> childAge = new ArrayList<String>();
                RoomGuest roomGuest = new RoomGuest(
                        adultCount, childCount, childAge);
                roomGuests.add(roomGuest);
            }

            SearchMain searchMain = new SearchMain(roomGuests, roomCount,
                    nightCount, cityId, checkInDate, checkOutDate);

            Gson gson = new Gson();
            String searchData = gson.toJson(searchMain);
            RequestParams requestParams = new RequestParams();
            requestParams.put("hotel_search", searchData);
            webServiceController.postRequest(apiConstants.HOTEL_SEARCH, requestParams, 1);
        }else {
            commonUtils.toastShort("Please select the city name.", getActivity());
        }
    }

    String checkInDate="", checkOutDate="", startDate, adultCount = "1",
            childCount = "0", roomCount = "1", nightCount = "1",
            cityId = null;
    WebServiceController webServiceController;
    List<TravellerSelectionMain> travellerSelectionMains =
            new ArrayList<TravellerSelectionMain>();

    @Override
    protected int getLayoutResource() {
        return R.layout.hotel_search_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        startDate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)
                +"-"+calendar.get(Calendar.DAY_OF_MONTH);
        notifyDate(startDate, 1);

        webServiceController = new WebServiceController(getActivity(),HotelSearchFragment.this);
        if(callingFragment==5){
            searchCityName.setText(hotelCityName);
            cityNameText.setText(hotelCityName);
        }else {
            if (applicationPreference.getData(
                    applicationPreference.hotelCityCode).length() != 0) {
                cityId = applicationPreference.getData(applicationPreference.hotelCityCode);
                cityNameText.setText(applicationPreference.getData(
                        applicationPreference.hotelCityName));
                searchCityName.setText(applicationPreference.getData(
                        applicationPreference.hotelCityName));
            }
        }


        }

    @Override
    public void notifyDate(String dateValue, Integer datePickerValue)
    {
        String endDate_str;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date endDate = dateFormat.parse(dateValue);
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMMM-dd EEEE",Locale.ENGLISH);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yy EEEE",Locale.ENGLISH);
            endDate_str = sdf.format(endDate);
            String[] splitDate = endDate_str.split(" ");
            //String[] dateValueArray = splitDate[0].split("-");

            switch (datePickerValue){
                case 1:
                    ciWeekName.setText(splitDate[3]);
                   // ciDateValue.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);
                    ciDateValue.setText(splitDate[0]);
                    ciMonthValue.setText(splitDate[1]);
                    checkInDate = dateValue;

                    setCheckOutDate(checkInDate);

                    getDateDifference();
                    break;
                case 2:
                    coWeekValue.setText(splitDate[3]);
                   // coDateValue.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);
                    coDateValue.setText(splitDate[0]);
                    coMonthValue.setText(splitDate[1]);

                    checkOutDate = dateValue;
                    getDateDifference();
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setCheckOutDate(String dateValue)
    {
        String endDate_str;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try{

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(dateValue));
            calendar.add(Calendar.DATE, 1);
            dateValue = dateFormat.format(calendar.getTime());

            Date endDate = dateFormat.parse(dateValue);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yy EEEE",Locale.ENGLISH);
            endDate_str = sdf.format(endDate);
            String[] splitDate = endDate_str.split(" ");


            coWeekValue.setText(splitDate[3]);
           // coDateValue.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);
            coDateValue.setText(splitDate[0]);
            coMonthValue.setText(splitDate[1]);
            checkOutDate = dateFormat.format(calendar.getTime());
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    private void getDateDifference() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1, date2;
        try{
            date1 = format.parse(checkInDate);
            date2 = format.parse(checkOutDate);

            int diffInDays = (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));

            if(diffInDays >= 1){
                nightCountText.setText(String.valueOf(diffInDays)+ " Night's");
                nightCount = String.valueOf(diffInDays);
            }else {
                setCheckOutDate(checkInDate);
                switch (diffInDays){
                    case 0:
                        date1 = format.parse(checkInDate);
                        date2 = format.parse(checkOutDate);

                        diffInDays = (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
                        nightCountText.setText(String.valueOf(diffInDays)+ " Night's");
                        nightCount = String.valueOf(diffInDays);
                        commonUtils.toastShort(
                                "Check in and Check out date cannot be same.", getActivity());
                        break;
                    default:
                        commonUtils.toastShort(
                                "Check out date cannot be less than Check In date.", getActivity());
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void citySearchResult(Integer type, String cityName, String cityCode) {
        searchCityName.setText(cityName);
        cityNameText.setText(cityName);
        cityId = cityCode;
    }

    @Override
    public void getResponse(String response, int flag)
    {

        switch (flag)
        {
                default:
                    Boolean isSuccess = false;
                    JSONObject jsonObject;
                    String message = null;
                    try{
                        jsonObject = new JSONObject(response);
                        if(jsonObject.optInt("status") == 1 || jsonObject.optBoolean("status")){
                            isSuccess = true;
                        }else {
                            message = jsonObject.getString("message");
                            isSuccess = false;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if(isSuccess){
                        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                                new HotelListFragment(cityNameText.getText().toString(),
                                        checkInDate, checkOutDate, adultCount, roomCount, response,
                                        nightCount, childCount),
                                null, true);
                    }else {
                        commonUtils.toastShort(message, getActivity());
                    }
                    break;
        }

    }

    public void updateTravellerCount(Integer finalAdultCount, Integer finalChildCount, Integer finalRoomCount,
                                     List<TravellerSelectionMain> travellerSelectionMains) {
        adultCount = String.valueOf(finalAdultCount);
        childCount = String.valueOf(finalChildCount);
        roomCount = String.valueOf(finalRoomCount);

        passengerCountText.setText(String.valueOf(finalAdultCount+finalChildCount));
        passengerDetailCount.setText(adultCount+" AD "+childCount+" CH");
        roomCountText.setText(String.valueOf(finalRoomCount));

        this.travellerSelectionMains.clear();
        this.travellerSelectionMains = travellerSelectionMains;
    }


    private void setCheckOutDateByNightCount(String dateValue,int datecount)
    {
        String endDate_str;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try{

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(dateValue));
            calendar.add(Calendar.DATE, datecount);
            dateValue = dateFormat.format(calendar.getTime());

            Date endDate = dateFormat.parse(dateValue);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yy EEEE",Locale.ENGLISH);
            endDate_str = sdf.format(endDate);
            String[] splitDate = endDate_str.split(" ");


            coWeekValue.setText(splitDate[3]);
            // coDateValue.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);
            coDateValue.setText(splitDate[0]);
            coMonthValue.setText(splitDate[1]);
            checkOutDate = dateFormat.format(calendar.getTime());
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
}