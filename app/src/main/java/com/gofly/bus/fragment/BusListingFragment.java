package com.gofly.bus.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gofly.R;
import com.gofly.bus.adapter.BusListAdapter;
import com.gofly.interfaces.FilterNotify;
import com.gofly.main.activity.MainActivity;
import com.gofly.main.fragment.BaseFragment;
import com.gofly.model.parsingModel.bus.BusListModel;
import com.gofly.model.parsingModel.bus.BusOperatorsInfo;
import com.gofly.model.parsingModel.flight.FlightList;
import com.gofly.utils.Global;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ADMIN on 27-03-2018.
 */

public class BusListingFragment extends BaseFragment implements FilterNotify
{

    ArrayList<BusOperatorsInfo> busOperatorList=new ArrayList<BusOperatorsInfo>();
    ImageView action_type;
   // ImageView action_user;
    @BindView(R.id.bus_list)
    RecyclerView busList;

    @BindView(R.id.from_city_display)
    TextView fromCity;

    @BindView(R.id.to_city_display)
    TextView toCity;

    @BindView(R.id.travel_date)
    TextView travelDate;

    /*@BindView(R.id.buses_found)
    TextView buses_found;*/



    @OnClick(R.id.filter_action)
    void busFilter(){
        intentAndFragmentService.fragmentDisplay(getActivity(),R.id.main_frame,
                new BusFilterFragment(operatorsListString,busOperatorList, priceBreakup, BusListingFragment.this,
                        minPrice, maxPrice, departureList,arrivalList, false,selectBusType),null, true);
    }

    String fromCityName, toCityName,fromCityId,toCityId, travelDateValue,
            response, searchId, minPrice = null,
            maxPrice = null,bookingSource;
    List<BusListModel> busListModelList = new ArrayList<BusListModel>();
    List<BusListModel> backupList = new ArrayList<BusListModel>();
    List<BusListModel> tempBusLists = new ArrayList<BusListModel>();
    List<Integer> departureList = new ArrayList<Integer>();
    List<Integer> arrivalList = new ArrayList<Integer>();
    BusListAdapter busListAdapter;
    List<JSONObject> busListModelList1 = new ArrayList<JSONObject>();

    @SuppressLint("ValidFragment")
    public BusListingFragment(String fromCityName,String fromCityId, String toCityName,String toCityId,
                              String travelDateValue, String response) {
        this.fromCityName = fromCityName;
        this.fromCityId=fromCityId;
        this.toCityName = toCityName;
        this.toCityId=toCityId;
        this.travelDateValue = travelDateValue;
        this.response = response;
    }

    public BusListingFragment() {
        /*
        * Empty Constructor*/
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.bus_list_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        action_type = getActivity().findViewById(R.id.action_type);
     //   action_user = getActivity().findViewById(R.id.action_user);
        action_type.setVisibility(View.VISIBLE);
      //  action_user.setVisibility(View.GONE);
        commonUtils.linearLayout(busList, getActivity());
        fromCity.setText(fromCityName);
        toCity.setText(toCityName);

        busListAdapter = new BusListAdapter(getActivity(), BusListingFragment.this,
                busListModelList,busListModelList1);
        busList.setAdapter(busListAdapter);
        dateModify();
        parseData();

        action_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentAndFragmentService.fragmentDisplay(getActivity(),R.id.main_frame,
                        new BusFilterFragment(operatorsListString,busOperatorList, priceBreakup, BusListingFragment.this,
                                minPrice, maxPrice, departureList,arrivalList, false,selectBusType),null, true);
            }
        });
     //   action_type = getActivity().findViewById(R.id.action_type);
     //   action_type.setVisibility(View.VISIBLE);
    }
    String priceBreakup,operatorsListString;
    String fareValue;
    private void parseData() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    searchId = jsonObject.getString("search_id");
                    bookingSource = jsonObject.getString("booking_source");
                    operatorsListString = jsonObject.getJSONArray("operator").toString();
                    priceBreakup = jsonObject.getJSONObject("filter").getJSONObject("p").toString();
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    int i=0;
                    while (i<dataArray.length()){
                        JSONObject dataObject = dataArray.getJSONObject(i);
                       /* busOperatorList.add(new BusOperatorsInfo(dataObject.getString("CompanyName"),
                                dataObject.getString("CompanyId"),false));*/
                        fareValue =String.format("%.2f",(Double.parseDouble(dataObject.getString("Fare"))/
                                Double.parseDouble(Global.currencyValue)));
                        busListModelList1.add(dataObject);
                        busListModelList.add(new BusListModel(dataObject.getString("CompanyName"),
                                dataObject.getString("CompanyId"),
                                dataObject.getString("BusTypeName"),
                                dataObject.getString("DepartureTime"),
                               changeTimeFormat(dataObject.getString("DeptTime")),
                                dataObject.getString("ArrivalTime"),
                               changeTimeFormat(dataObject.getString("ArrTime")),
                              //  dataObject.getString("Duration"),
                                "",
                                //dataObject.getString("Fare"),
                                fareValue,
                                String.valueOf(dataObject.getInt("AvailableSeats")),
                                dataObject.getString("RouteCode"),
                                dataObject.getString("RouteScheduleId"),
                                dataObject.getString("DeptTime").split(" ")[0],
                                dataObject.getString("ResultToken"),
                              //  dataObject.getJSONObject("BusTypeName").getString("IsAC"),
                             //   dataObject.getJSONObject("BusTypeName").getString("Seating")
                                dataObject.getString("BusTypeName"),
                                dataObject.getString("BusTypeName")

                        ));
                        i++;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        backupList.clear();
                        backupList.addAll(busListModelList);
                        busListAdapter.notifyDataSetChanged();
                    //    buses_found.setText(busListModelList.size()+" Buses Found /"+backupList.size());
                    }
                });
            }
        };
        thread.start();
    }

    private void dateModify() {
        String cIn, cOut;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try{
            Date checkIn = dateFormat.parse(travelDateValue);
            SimpleDateFormat sdf = new SimpleDateFormat("dd, MMM",Locale.ENGLISH);
            cIn = sdf.format(checkIn);
            travelDate.setText(cIn);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void navigateToDetail(int position,String companyname,String bustype,String departureTime,String arrivalTime,String price,String routScheduleId, String routCode,
                                 String departDate, String resultToken,JSONObject jsonObject) {

        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new BusDetailFragment(companyname,bustype,departureTime,arrivalTime,price,fromCityName,toCityName,fromCityId,toCityId,routScheduleId, routCode,
                        departDate, resultToken, searchId, bookingSource,jsonObject), null, true);
    }

    @Override
    public void filterTypeOne(List<FlightList> flightLists, List<Integer> stopCount, List<Integer> departure, List<Integer> arrival, String minPrice, String maxPrice) {
        //for flight one way
    }

    @Override
    public void filterTypeTwo(List<FlightList> flightLists,List<Integer> stopCount, String minPrice, String maxPrice) {
        //for flight multicity
    }

    @Override
    public void sortingNotify(int price, int departure, int arrival, int duration) {
        //for sorting
    }

    String selectBusType;
    @Override
    public void filterTypeBus(List<BusOperatorsInfo> busOprList, List<Integer> departure,
                              List<Integer> arrival, String minPrice, String maxPrice,
                              String selectBusType) {
        //for bus filter
       // commonUtils.toastShort("Filter Designed Successfully",getActivity());
        busListModelList.clear();
        busListModelList.addAll(backupList);

        this.busOperatorList.clear();
        this.arrivalList.clear();
        this.departureList.clear();

        this.busOperatorList.addAll(busOprList);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.selectBusType=selectBusType;
        this.departureList.addAll(departure);
        this.arrivalList.addAll(arrival);
        this.selectBusType = selectBusType;

        try {
            if(this.busOperatorList.size() == 0 && minPrice == null && maxPrice == null && departure.size() == 0 &&
                    arrival.size() == 0&& selectBusType=="NONE"){
                busListAdapter.notifyDataSetChanged();
                // buses_found.setText(busListModelList.size()+" Buses Found /"+backupList.size());
            }else {
                if(this.busOperatorList.size() != 0){
                    sortBusOperator(busOperatorList,minPrice,maxPrice,
                            this.departureList,this.arrivalList,selectBusType);
                }else {
                    sortPrice(minPrice,maxPrice,
                            this.departureList,this.arrivalList, busListModelList,selectBusType);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    private void sortPrice(String minPrice, String maxPrice,List<Integer> departure,
                           List<Integer> arrival, List<BusListModel> sortedList,String selectBusType) {
        if(minPrice != null && maxPrice != null){
            tempBusLists.clear();
            tempBusLists.addAll(sortedList);
            busListModelList.clear();
            int i=0;
            while (i < tempBusLists.size()){
                String[] flightPrice = tempBusLists.get(i).getPrice().split(" ");
                if(Float.valueOf(flightPrice[0]) >=
                        Float.valueOf(minPrice) &&
                        Float.valueOf(flightPrice[0]) <=
                                Float.valueOf(maxPrice)){
                    busListModelList.add(tempBusLists.get(i));
                }
                i++;
            }
        }else if(minPrice != null){
            tempBusLists.clear();
            tempBusLists.addAll(sortedList);
            tempBusLists.clear();
            int i=0;
            while (i < tempBusLists.size()){
                String[] flightPrice = tempBusLists.get(i).getPrice().split(" ");
                if(Float.parseFloat(flightPrice[0]) >=
                        Float.parseFloat(minPrice)){
                    busListModelList.add(tempBusLists.get(i));
                }
                i++;
            }
        }else if(maxPrice != null){
            tempBusLists.clear();
            tempBusLists.addAll(sortedList);
            busListModelList.clear();
            int i=0;
            while (i < tempBusLists.size()){
                String[] flightPrice = tempBusLists.get(i).getPrice().split(" ");
                if(Float.parseFloat(flightPrice[0]) <=
                        Float.parseFloat(maxPrice)){
                    busListModelList.add(tempBusLists.get(i));
                }
                i++;
            }
        }

        sortBusTypeNew(departure, arrival, busListModelList,selectBusType);
    }

    /*private void sortBusType(List<Integer> busType, List<Integer> departure,
                          List<Integer> arrival, List<BusListModel> sortedList,String selectBusType) {
        if(busType.size() != 0){
            tempBusLists.clear();
            tempBusLists.addAll(sortedList);
            busListModelList.clear();

            int j=0;
            while (j < busType.size()){
                int i=0;
                while (i < tempBusLists.size()){
                    String[] stop = tempBusLists.get(i).getBusType().split(",");
                    if(Integer.valueOf(stop[0]) == busType.get(j)){
                        busListModelList.add(tempBusLists.get(i));
                    }
                    i++;
                }
                j++;
            }
        }

      //  sortDepartureTime(departure, arrival, busListModelList);
        sortBusTypeNew(busType, departure, arrival, busListModelList,selectBusType);
    }*/

   private void sortBusTypeNew(List<Integer> departure,
                            List<Integer> arrival, List<BusListModel> sortedList,String selectBusType) {
       if(!selectBusType.equals("NONE")){
           tempBusLists.clear();
           tempBusLists.addAll(sortedList);
           busListModelList.clear();

           //int j=0;
           //while (j < busType.size()){
               int i=0;
               while (i < tempBusLists.size()){
                  // if (selectBusType.equals("Luxura A/C Sleeper (2+1)")) {

                   if (selectBusType.equals("AC_SEATER"))
                   {
                       if (tempBusLists.get(i).getBusType().equals("A/C Seater / Sleeper (2+1)")
                               /*||tempBusLists.get(i).getBusType().equals("AC Sleeper (2+1)")
                               ||tempBusLists.get(i).getBusType().equals("Volvo A/C B11R Multi Axle Semi Sleeper (2+2)")
                               ||tempBusLists.get(i).getBusType().equals("Volvo Multi-Axle A/C Semi Sleeper (2+2)")
                               ||tempBusLists.get(i).getBusType().equals("A/C Sleeper (2+1)")
                               ||tempBusLists.get(i).getBusType().equals("Volvo A/C Sleeper (2+1)")
                               ||tempBusLists.get(i).getBusType().equals("A/C Semi Sleeper / Sleeper (2+1)")
                               ||tempBusLists.get(i).getBusType().equals("Scania Multi-Axle AC Semi Sleeper (2+2)")
                               ||tempBusLists.get(i).getBusType().equals("Bharat Benz A/C Sleeper (2+1)")
                               ||tempBusLists.get(i).getBusType().equals("Capella A/C Sleeper (2+1)")
                               ||tempBusLists.get(i).getBusType().equals("Luxura A/C Sleeper (2+1)")*/
                       ){
                           busListModelList.add(tempBusLists.get(i));
                       }
                       else {
                         //  Toast.makeText(getActivity(), "No AC Seater bus found", Toast.LENGTH_SHORT).show();
                       }
                   }
                   else if (selectBusType.equals("NON_AC_SEATER"))
                   {
                       if (tempBusLists.get(i).getBusType().equals("NON A/C Seater / Sleeper (2+1)")
                       ||tempBusLists.get(i).getBusType().equals("NON A/C Airbus (2+2)")){
                           busListModelList.add(tempBusLists.get(i));
                       }
                       else {
                         //  Toast.makeText(getActivity(), "No NON AC Seater bus found", Toast.LENGTH_SHORT).show();

                       }
                   }
                   else if (selectBusType.equals("AC_SLEEPER")){

                       if (tempBusLists.get(i).getBusType().equals("AC Sleeper (2+1)")
                               ||tempBusLists.get(i).getBusType().equals("Volvo A/C B11R Multi Axle Semi Sleeper (2+2)")
                               ||tempBusLists.get(i).getBusType().equals("Volvo Multi-Axle A/C Semi Sleeper (2+2)")
                               ||tempBusLists.get(i).getBusType().equals("A/C Sleeper (2+1)")
                               ||tempBusLists.get(i).getBusType().equals("Volvo A/C Sleeper (2+1)")
                               ||tempBusLists.get(i).getBusType().equals("A/C Semi Sleeper / Sleeper (2+1)")
                               ||tempBusLists.get(i).getBusType().equals("Scania Multi-Axle AC Semi Sleeper (2+2)")
                               ||tempBusLists.get(i).getBusType().equals("Bharat Benz A/C Sleeper (2+1)")
                               ||tempBusLists.get(i).getBusType().equals("Capella A/C Sleeper (2+1)")
                               ||tempBusLists.get(i).getBusType().equals("Luxura A/C Sleeper (2+1)")
                           ||tempBusLists.get(i).getBusType().equals("A/C Seater / Sleeper (2+1)")){
                           busListModelList.add(tempBusLists.get(i));
                       }
                       else {
                           //Toast.makeText(getActivity(), "No AC Sleeper bus found", Toast.LENGTH_SHORT).show();

                       }

                   }
                   else if (selectBusType.equals("NON_AC_SLEEPER")){
                       if (tempBusLists.get(i).getBusType().equals("NON A/C Seater / Sleeper (2+1)")
                       ||tempBusLists.get(i).getBusType().equals("NON A/C Sleeper (2+1)")){

                           busListModelList.add(tempBusLists.get(i));

                       }
                       else {
                          // Toast.makeText(getActivity(), "No NON AC Sleeper bus found", Toast.LENGTH_SHORT).show();

                       }


                   }

                      /* if (tempBusLists.get(i).getBusType().equals(selectBusType)) {
                           busListModelList.add(tempBusLists.get(i));
                       } else {
                          // busListModelList.add(tempBusLists.get(i));
                       }*/
                   //}
                  /* String[] stop = (tempBusLists.get(i).getIsAC()+"_"+tempBusLists.get(i).getSeatingType()).split(" ");
                   if(selectBusType.equals("AC_SLEEPER")||selectBusType.equals("NON_AC_SLEEPER")){
                       if(stop[0].equals(selectBusType)){
                           busListModelList.add(tempBusLists.get(i));
                       }
                   }else if(selectBusType.equals("AC_SEATER")||selectBusType.equals("NON_AC_SEATER")){
                       if(stop[0].equals("AC_SLEEPER")||stop[0].equals("NON_AC_SLEEPER")){

                       }else {
                           if(selectBusType.equals("NON_AC_SEATER")){
                               if(stop[0].contains("NON")){
                                   busListModelList.add(tempBusLists.get(i));
                               }else {

                               }
                           }else {
                               busListModelList.add(tempBusLists.get(i));
                           }

                       }
                   }else {
                       busListModelList.add(tempBusLists.get(i));
                   }*/

                   i++;

           }
       }

       sortDepartureTime(departure, arrival, busListModelList);
   }

    String departureTime = null, arrivalTime = null;

    private void sortDepartureTime(List<Integer> departure,
                                   List<Integer> arrival,
                                   List<BusListModel> sortedList) {
        if(departure.size() != 0){
            tempBusLists.clear();
            tempBusLists.addAll(sortedList);
            busListModelList.clear();
            int j=0;
            while (j < departure.size()){
                departureTime = String.valueOf(departure.get(j));
                int i=0;
                while (i < tempBusLists.size()){
                    String[] time = tempBusLists.get(i).getDeptTime24hr().split(":");
                    switch (departureTime){
                        case "0":
                            sortDataTime(0,6,Integer.parseInt(time[0]),i);
                            break;
                        case "6":
                            sortDataTime(6,12,Integer.parseInt(time[0]),i);
                            break;
                        case "12":
                            sortDataTime(12,18,Integer.parseInt(time[0]),i);
                            break;
                        case "18":
                            sortDataTime(18,24,Integer.parseInt(time[0]),i);
                            break;
                    }
                    i++;
                }
                j++;
            }
        }

        sortArrivalTime(arrival, busListModelList);
    }

    private void sortArrivalTime(List<Integer> arrival,
                                 List<BusListModel> sortedList) {
        if(arrival.size() != 0){
            tempBusLists.clear();
            tempBusLists.addAll(sortedList);
            busListModelList.clear();

            int j=0;
            while (j < arrival.size()){
                arrivalTime = String.valueOf(arrival.get(j));
                int i=0;
                while (i < tempBusLists.size()){
                    String[] time = tempBusLists.get(i).getArrTime24hr().split(":");
                    switch (arrivalTime){
                        case "0":
                            sortDataTime(0,6,Integer.parseInt(time[0]),i);
                            break;
                        case "6":
                            sortDataTime(6,12,Integer.parseInt(time[0]),i);
                            break;
                        case "12":
                            sortDataTime(12,18,Integer.parseInt(time[0]),i);
                            break;
                        case "18":
                            sortDataTime(18,24,Integer.parseInt(time[0]),i);
                            break;
                    }
                    i++;
                }
                j++;
            }
        }

        if(busListModelList.size() != 0){
      //      buses_found.setText(busListModelList.size()+" Buses Found /"+backupList.size());
            busListAdapter.notifyDataSetChanged();
        }else {
          //  buses_found.setText(busListModelList.size()+" Buses Found /"+backupList.size());
            commonUtils.toastShort("No Bus Found.",getActivity());
            busListAdapter.notifyDataSetChanged();
        }
    }

    private void sortDataTime(int minTime, int maxTime, int value, int position) {
        if(value >= minTime && value < maxTime){
            busListModelList.add(tempBusLists.get(position));
        }
    }

    private void sortBusOperator(List<BusOperatorsInfo> busOprList, String minPrice,
                            String maxPrice,
                            List<Integer> departure,
                            List<Integer> arrival,String selectBusType) {
        tempBusLists.clear();
        tempBusLists.addAll(busListModelList);
        busListModelList.clear();

        int i=0;
        while (i < busOprList.size()){
            int j=0;
            while (j < tempBusLists.size()){
                String[] busCodeCode = tempBusLists.get(j).getCompanyID().split(" ");
                if(busCodeCode[0].equals(busOprList.get(i).getOperatorCode())){
                    busListModelList.add(tempBusLists.get(j));
                }
                j++;
            }
            i++;
        }

        sortPrice(minPrice,maxPrice,departure,arrival,busListModelList,selectBusType);
    }


    public String changeTimeFormat(String departureDate){//2018-05-31 05:00:00
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try{
            Date endDate = dateFormat.parse(departureDate);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
            departureDate = sdf.format(endDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return departureDate;
    }

    @Override
    public void onResume() {
        super.onResume();
        action_type.setVisibility(View.VISIBLE);
    }
   /* @Override
    public void onPause() {
        super.onPause();
        action_type.setVisibility(View.GONE);
    }*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        action_type.setVisibility(View.GONE);

    }
}