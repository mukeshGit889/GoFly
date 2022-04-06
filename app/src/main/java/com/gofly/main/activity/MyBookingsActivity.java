package com.gofly.main.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.gofly.bus.adapter.BusBookingAdapter;
import com.gofly.hotel.adapter.HotelBookingAdapter;
import com.gofly.model.parsingModel.bus.BusBookHistoryInfo;
import com.gofly.model.parsingModel.hotel.listing.HotelBookHistoryInfo;
import com.gofly.model.parsingModel.sightSeeing.HistoryData;
import com.gofly.model.parsingModel.transfers.TransferBookingInfo;
import com.gofly.sight_seeing.adapter.SightSeeingHistory;
import com.gofly.transfers.adapter.TransferHistoryAdapter;
import com.loopj.android.http.RequestParams;
import com.gofly.R;
import com.gofly.flight.adapter.BookingHistoryAdapter;
import com.gofly.model.parsingModel.flight.BookingHistoryInfo;
import com.gofly.utils.Global;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MyBookingsActivity extends BaseActivity implements WebInterface {
    WebServiceController webServiceController;
    ArrayList<BookingHistoryInfo> bookingHistoryList=new ArrayList<BookingHistoryInfo>();
    ArrayList<HotelBookHistoryInfo> hotelbookingHistoryList=new ArrayList<HotelBookHistoryInfo>();
    ArrayList<BusBookHistoryInfo> busbookingHistoryList=new ArrayList<BusBookHistoryInfo>();
    ArrayList<TransferBookingInfo> transferBookingInfoArrayList=new ArrayList<TransferBookingInfo>();
    ArrayList<HistoryData> sightSeeingBookingInfoArrayList=new ArrayList<HistoryData>();

    BookingHistoryAdapter bAdapter;
    HotelBookingAdapter hotelAdapter;
    BusBookingAdapter busAdapter;
    TransferHistoryAdapter transferHistoryAdapter;
    SightSeeingHistory sightSeeingHistoryAdapter;


    @OnClick(R.id.back_button)
    void goBack(){
        finish();
    }
    @BindView(R.id.rv_bookings)
    RecyclerView rv_bookings;

    @OnClick(R.id.tv_flight)
    void goToFlightBooking(){
        callBookingHistoryApi();
    }

    @OnClick(R.id.tv_hotel)
    void goToHotelBooking(){
        callHotelBookHistoryApi();
    }

    @OnClick(R.id.tv_bus)
    void goToBusBooking(){
        callBusBookHistoryApi();
    }

    @OnClick(R.id.tv_transfer)
    void goToTransferBooking(){
        callTransferBookHistoryApi();
    }

    @OnClick(R.id.tv_activity)
    void goToActivityBooking(){
        callActivityBookHistoryApi();
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_my_bookings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceController=new WebServiceController(this,MyBookingsActivity.this);
        callBookingHistoryApi();
        //setContentView(R.layout.activity_my_bookings);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
    public void callBookingHistoryApi(){
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id",applicationPreference.getData("user_id"));
        webServiceController.postRequest(apiConstants.FLIGHT_BOOKING_HISTORY,requestParams,1);
    }

    public void callHotelBookHistoryApi(){
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id",applicationPreference.getData("user_id"));
        webServiceController.postRequest(apiConstants.HOTEL_TRANSACTION_HISTORY,requestParams,2);
    }


    public void callBusBookHistoryApi(){
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id",applicationPreference.getData("user_id"));
        webServiceController.postRequest(apiConstants.BUS_TRANSACTION_HISTORY,requestParams,3);
    }

    public void callTransferBookHistoryApi(){
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id",applicationPreference.getData("user_id"));
        webServiceController.postRequest(apiConstants.TRANSFER_BOOKING_HISTORY,requestParams,7);
    }

    public void callActivityBookHistoryApi(){
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id","1266");
        webServiceController.postRequest(apiConstants.ACTIVITY_BOOKING_HISTORY,requestParams,8);
    }
    @Override
    public void getResponse(String response, int flag) {
        switch (flag){
            case 1:
                //booking history
                /*Bundle bundle = new Bundle();
                bundle.putInt("calling_fragment",1);
                bundle.putString("response",response);
                intentAndFragmentService.intentDisplay(this, FlightBookHistoryActivity.class, bundle);*/
                loadFlightData(response);
                break;
            case 2:
                loadHotelData(response);
                break;
            case 3:
                loadBusData(response);
                break;
            case 4:
                //cancel flight response
                Log.e("response",response);
                try {
                    JSONObject object = new JSONObject(response);
                    commonUtils.toastShort(object.getString("message"),MyBookingsActivity.this);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case 5:
                //cancel hotel response
                Log.e("response",response);
                try {
                    JSONObject object = new JSONObject(response);
                    commonUtils.toastShort(object.getString("message"),MyBookingsActivity.this);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case 6:
                //cancel bus response
                Log.e("response",response);
                try {
                    JSONObject object = new JSONObject(response);
                    commonUtils.toastShort(object.getString("message"),MyBookingsActivity.this);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case 7:
                loadTransferData(response);
                break;

            case 8:
                loadActivityData(response);
                break;
        }

    }
    String fare="0.0";
    public void loadFlightData(String flightResponse){
        try {
            JSONObject jsonObject=new JSONObject(flightResponse);
            JSONArray jsonArray=jsonObject.getJSONArray("flights");
            JSONObject jobj;
            bookingHistoryList.clear();
            for (int i=0;i<jsonArray.length();i++){
                jobj=jsonArray.getJSONObject(i);
                //fare=String.format("%0.2f",Double.parseDouble(jobj.getString("total_fare"))/ Double.parseDouble(Global.currencyValue));
                fare=(Double.parseDouble(jobj.getString("total_fare"))/ Double.parseDouble(Global.currencyValue))+"";


                bookingHistoryList.add(new BookingHistoryInfo(
                        jobj.getString("origin"),
                        jobj.getString("status"),
                        jobj.getString("trip_type"),
                        jobj.getString("journey_start"),
                        jobj.getString("journey_end"),
                        jobj.getString("from_loc"),
                        jobj.getString("to_loc"),
                        jobj.getString("pnr"),
                       // jobj.getString("total_fare"),
                        //jobj.getString("currency"),
                        fare,
                        Global.currencySymbol,
                        jobj.getString("app_reference"),
                        jobj.getString("booking_source")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(bookingHistoryList.size()==0){
            commonUtils.toastShort("No bookings found",MyBookingsActivity.this);
            return;
        }

        commonUtils.linearLayout(rv_bookings,this);
        bAdapter = new BookingHistoryAdapter(this,
                MyBookingsActivity.this, bookingHistoryList);
        rv_bookings.setAdapter(bAdapter);
    }

    public void loadHotelData(String hotelResponse){
        hotelbookingHistoryList.clear();
        try {
            JSONObject jsonObject=new JSONObject(hotelResponse);
            JSONObject hotelObj=jsonObject.getJSONObject("hotels");
            JSONObject dataObj=hotelObj.getJSONObject("data");
            JSONArray bookingArr=dataObj.getJSONArray("booking_details");
            for (int i=0;i<bookingArr.length();i++){
               // fare=String.format("%0.2f",Double.parseDouble(bookingArr.getJSONObject(i).getString("total_fare"))/ Double.parseDouble(Global.currencyValue));
                fare=Double.parseDouble(bookingArr.getJSONObject(i).getString("total_fare"))/ Double.parseDouble(Global.currencyValue)+"";
                hotelbookingHistoryList.add(new HotelBookHistoryInfo(
                        bookingArr.getJSONObject(i).getString("status"),
                        bookingArr.getJSONObject(i).getString("app_reference"),
                        bookingArr.getJSONObject(i).getString("check_in"),
                        bookingArr.getJSONObject(i).getString("check_out"),
                        //bookingArr.getJSONObject(i).getString("currency"),
                        Global.currencySymbol,
                        bookingArr.getJSONObject(i).getString("location"),
                        bookingArr.getJSONObject(i).getString("room_type_name"),
                        //bookingArr.getJSONObject(i).getString("total_fare"),
                        fare,
                        new JSONObject(bookingArr.getJSONObject(i).getString("attributes")).getString("HotelName"),
                        new JSONObject(bookingArr.getJSONObject(i).getString("attributes")).getString("HotelAddress"),
                        new JSONObject(bookingArr.getJSONObject(i).getString("attributes")).getJSONArray("CancellationPolicy").get(0).toString()
                        ));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(hotelbookingHistoryList.size()==0){
            commonUtils.toastShort("No bookings found",MyBookingsActivity.this);
            return;
        }

        commonUtils.linearLayout(rv_bookings,this);
        hotelAdapter = new HotelBookingAdapter(this,
                MyBookingsActivity.this, hotelbookingHistoryList);
        rv_bookings.setAdapter(hotelAdapter);
    }

    public void loadBusData(String busResponse){
        hotelbookingHistoryList.clear();
        try {
            JSONObject jsonObject=new JSONObject(busResponse);
            JSONObject hotelObj=jsonObject.getJSONObject("hotels");
            JSONObject dataObj=hotelObj.getJSONObject("data");
            JSONArray bookingArr=dataObj.getJSONArray("booking_details");
            for (int i=0;i<bookingArr.length();i++){
                //fare=String.format("%0.2f",Double.parseDouble(bookingArr.getJSONObject(i).getString("fare"))/ Double.parseDouble(Global.currencyValue));
                fare=Double.parseDouble(bookingArr.getJSONObject(i).getString("fare"))/ Double.parseDouble(Global.currencyValue)+"";

                busbookingHistoryList.add(new BusBookHistoryInfo(
                        bookingArr.getJSONObject(i).getString("status"),
                        bookingArr.getJSONObject(i).getString("pnr"),
                        bookingArr.getJSONObject(i).getString("ticket"),
                        bookingArr.getJSONObject(i).getString("app_reference"),
                        //bookingArr.getJSONObject(i).getString("currency"),
                        Global.currencySymbol,
                        bookingArr.getJSONObject(i).getString("departure_from"),
                        bookingArr.getJSONObject(i).getString("arrival_to"),
                        bookingArr.getJSONObject(i).getString("operator"),
                        //bookingArr.getJSONObject(i).getString("fare"),
                        fare,
                        bookingArr.getJSONObject(i).getString("journey_datetime"),
                        bookingArr.getJSONObject(i).getString("departure_datetime"),
                        bookingArr.getJSONObject(i).getString("arrival_datetime"),
                        "-"
                        ));
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        if(busbookingHistoryList.size()==0){
            commonUtils.toastShort("No bookings found",MyBookingsActivity.this);
            return;
        }

        commonUtils.linearLayout(rv_bookings,this);
        busAdapter = new BusBookingAdapter(this,
                MyBookingsActivity.this, busbookingHistoryList);
        rv_bookings.setAdapter(busAdapter);
    }
    public void loadTransferData(String transferResponse){
        transferBookingInfoArrayList.clear();
        try {
            JSONObject jsonObject=new JSONObject(transferResponse);
            JSONObject transferObj=jsonObject.getJSONObject("transfer");

            JSONArray bookingArr=transferObj.getJSONArray("booking_details");
            for (int i=0;i<bookingArr.length();i++){
                //fare=String.format("%0.2f",Double.parseDouble(bookingArr.getJSONObject(i).getString("fare"))/ Double.parseDouble(Global.currencyValue));
                fare=Double.parseDouble(bookingArr.getJSONObject(i).getString("total_fare"))/ Double.parseDouble(Global.currencyValue)+"";

                transferBookingInfoArrayList.add(new TransferBookingInfo(
                        bookingArr.getJSONObject(i).getString("status"),
                        bookingArr.getJSONObject(i).getString("app_reference"),
                        Global.currencySymbol,
                        fare,
                        bookingArr.getJSONObject(i).getString("product_name"),
                        bookingArr.getJSONObject(i).getString("travel_date")


                ));
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        if(transferBookingInfoArrayList.size()==0)
        {
            commonUtils.toastShort("No bookings found",MyBookingsActivity.this);
            return;
        }

        commonUtils.linearLayout(rv_bookings,this);
        transferHistoryAdapter = new TransferHistoryAdapter(transferBookingInfoArrayList,
                MyBookingsActivity.this);
        rv_bookings.setAdapter(transferHistoryAdapter);
    }
    public void loadActivityData(String historyResponse){
        sightSeeingBookingInfoArrayList.clear();
        try {
            JSONObject jsonObject=new JSONObject(historyResponse);
            JSONObject sightseeingObj=jsonObject.getJSONObject("sightseeing");
            JSONArray bookingArr=sightseeingObj.getJSONArray("booking_details");
            for (int i=0;i<bookingArr.length();i++){
                //fare=String.format("%0.2f",Double.parseDouble(bookingArr.getJSONObject(i).getString("fare"))/ Double.parseDouble(Global.currencyValue));
                fare=Double.parseDouble(bookingArr.getJSONObject(i).getString("total_fare"))/ Double.parseDouble(Global.currencyValue)+"";

                sightSeeingBookingInfoArrayList.add(new HistoryData(
                        bookingArr.getJSONObject(i).getString("product_name"),
                        bookingArr.getJSONObject(i).getString("travel_date"),
                        bookingArr.getJSONObject(i).getString("app_reference"),
                        new JSONObject(bookingArr.getJSONObject(i).getString("attributes")).getString("address"),
                        fare,
                        bookingArr.getJSONObject(i).getString("status"),
                        Global.currencySymbol





                ));
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        if(sightSeeingBookingInfoArrayList.size()==0){
            commonUtils.toastShort("No bookings found",MyBookingsActivity.this);
            return;
        }

        commonUtils.linearLayout(rv_bookings,this);
        sightSeeingHistoryAdapter = new SightSeeingHistory(sightSeeingBookingInfoArrayList,
                MyBookingsActivity.this );
        rv_bookings.setAdapter(sightSeeingHistoryAdapter);
    }


    public void cancelFlight(int position){
        RequestParams requestParams = new RequestParams();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("app_reference",bookingHistoryList.get(position).getApp_reference());
            jsonObject.put("booking_source",bookingHistoryList.get(position).getBooking_source());
            jsonObject.put("transaction_origin",bookingHistoryList.get(position).getTransaction_origin());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestParams.put("flight_cancel",jsonObject.toString());
        webServiceController.postRequest(apiConstants.FLIGHT_CANCEL,requestParams,4);
    }
    public void cancelHotel(int position)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("book_id",hotelbookingHistoryList.get(position).getBooking_id());
        requestParams.put("booking_source","PTBSID0000000001");
        webServiceController.postRequest(apiConstants.HOTEL_CANCEL,requestParams,5);
    }
    public void cancelBus(int position)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("book_id",busbookingHistoryList.get(position).getApp_reference());
        requestParams.put("booking_source","PTBSID0000000003");
        webServiceController.postRequest(apiConstants.BUS_CANCEL,requestParams,6);

    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
