package com.gofly.bus.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gofly.bus.adapter.BoardingPointAdapter;
import com.gofly.bus.adapter.DropingPointAdapter;
import com.loopj.android.http.RequestParams;
import com.gofly.R;
import com.gofly.main.fragment.BaseFragment;
import com.gofly.model.parsingModel.bus.DropOffBean;
import com.gofly.model.parsingModel.bus.PickUpBean;
import com.gofly.model.parsingModel.bus.SeatingBean;
import com.gofly.model.requestModel.bus.BusDetailRequest;
import com.gofly.traveller.TravellerFragment;
import com.gofly.utils.Global;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BusDetailFragment extends BaseFragment implements WebInterface, AdapterView.OnItemClickListener {
    ImageView action_type;
    AlertDialog alertDialog;
    String resultindex;
    boolean upper = false;
    HashMap<Integer, String> seating_selection_list1 = new HashMap<>();
    HashMap<Integer, String> seq_no_list = new HashMap<>();
    HashMap<Integer, String> fare_list = new HashMap<>();
    private static DecimalFormat df2 = new DecimalFormat(".##");
    @BindView(R.id.seating_table)
    TableLayout seating_table;
    List<JSONObject> allseats = new ArrayList<JSONObject>();
    List<JSONObject> allseats1 = new ArrayList<JSONObject>();
    List<JSONObject> pickuppointlist = new ArrayList<JSONObject>();
    List<JSONObject> dropoffpointlist = new ArrayList<JSONObject>();
    List<JSONObject> pickuppointlist_selected = new ArrayList<JSONObject>();
    List<JSONObject> dropoffpointlist_seleted = new ArrayList<JSONObject>();
    // List<String> final_seat_index_list=new ArrayList<String>();
    // JSONObject final_seat_index_list=new JSONObject();
    JSONArray jsonArray = new JSONArray();
    JSONArray jsonArray1 = new JSONArray();
    JSONArray jsonArray2 = new JSONArray();
    JSONArray jsonArray3 = new JSONArray();
    JSONArray jsonArray4 = new JSONArray();

    @BindView(R.id.top_lay)
    LinearLayout topLayout;

    @BindView(R.id.text_one)
    TextView seatCount;

    @BindView(R.id.total_price)
    TextView totalPrice;

    @BindView(R.id.destination_lower)
    TextView lowerBirthText;

    @BindView(R.id.destination_upper)
    TextView upperBirthText;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.destination_lower)
    void lowerBirth() {
       /* if (click == 0) {
            lowerBirthText.setBackgroundColor(Color.parseColor("#ffffff"));
            lowerBirthText.setBackgroundColor(Color.parseColor("#2c7ee9"));
            setSleeperLayout(rows_g, columns_g, seating_list, "1");
            click = 1;
        }*/
        lowerBirthText.setTextColor(getActivity().getResources().getColor(R.color.white));
        lowerBirthText.setBackground(getActivity().getResources().getDrawable(R.drawable.orange_butten));
        upperBirthText.setTextColor(getActivity().getResources().getColor(R.color.b_six));
        upperBirthText.setBackground(getActivity().getResources().getDrawable(R.drawable.white_butten));
        setOldSleeperLayout(rows_g, columns_g, seating_list, "Lower");

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.destination_upper)
    void upperBirth() {
       /* if (click == 1) {
            upperBirthText.setBackgroundColor(Color.parseColor("#2c7ee9"));
            upperBirthText.setBackgroundColor(Color.parseColor("#ffffff"));
            setSleeperLayout(rows_g, columns_g, seating_list, "2");
            click = 0;
        }*/
        upperBirthText.setTextColor(getActivity().getResources().getColor(R.color.white));
        upperBirthText.setBackground(getActivity().getResources().getDrawable(R.drawable.orange_butten));

        lowerBirthText.setTextColor(getActivity().getResources().getColor(R.color.b_six));
        lowerBirthText.setBackground(getActivity().getResources().getDrawable(R.drawable.white_butten));

        setOldSleeperLayout(rows_g, columns_g, seating_list, "Upper");
    }

    @OnClick(R.id.book_now)
    void bookNowAction() {

        String seatNom = seatCount.getText().toString();

        if (TextUtils.isEmpty(seatNom)) {
            Toast.makeText(getActivity(), "Please select seat.", Toast.LENGTH_SHORT).show();

        }// else if (pickupLocation == null) {
        else if (boardingname == null) {
            boarding_name.setFocusable(true);
            boarding_name.setError("");
            Toast.makeText(getActivity(), "Please select boarding point.", Toast.LENGTH_SHORT).show();

        } else if ((dropingTo == null) || dropingTo.equalsIgnoreCase("")) {

            dropping_name.setFocusable(true);
            dropping_name.setError("");
            Toast.makeText(getActivity(), "Please select dropping point.", Toast.LENGTH_SHORT).show();
        } else {
            if (!totalPrice.getText().toString().equals("0") ||
                    !totalPrice.getText().toString().equals("")) {
                call_mobile_bookingAPI();

            } else {
                Toast.makeText(getActivity(), "Please select a seat to proceed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @BindView(R.id.boarding_name)
    TextView boarding_name;

    @BindView(R.id.dropping_name)
    TextView dropping_name;


    @OnClick(R.id.boarding_name)
    void selectBoardingPoint() {
        selectBoard();
    }

    @OnClick(R.id.dropping_name)
    void selectDroppingPoint() {
        selectDrop();
    }

    SeatingBean seatingbean;
    PickUpBean pickupbean;
    List<SeatingBean> finalSeat = new ArrayList<SeatingBean>();
    ArrayList<SeatingBean> seating_list = new ArrayList<>();
    ArrayList<PickUpBean> pick_up_list = new ArrayList<PickUpBean>();
    ArrayList<DropOffBean> drop_off_list = new ArrayList<DropOffBean>();
    List<Integer> tracking_list = new ArrayList<Integer>();
    List<String> seating_selection_list = new ArrayList<String>();
    HashMap<String, String> map_rates_storing = new HashMap<String, String>();
    JSONArray cancArray = new JSONArray();
    String routScheduleId, routCode, departDate, resultToken, searchId, bookingSource,
            isSleeper = "", boardingname = "", boarding_id = "", schedule_id = "", schedule_date = "",
            busoperator = "", from_id = "", to_id = "", isAc = "", from_bus = "", to_bus = "", time = "",
            arrival_time = "", base_fare = "", resp_fare = "", bus_name = "", bus_type = "", bus_type_name = "", CommAmount = "",
            token = "", token_key = "", booking_source = "", journeyDate, arrival_date, rootSchedulerId,
            searSelectionResponse, pickupLocation = null, charity_value = "", super_cash_discount = "", convenience_fee = "";
    String supercashback = "", charityAmt = "";
    Integer rows_g, columns_g, click;
    //private int click = 0;
    WebServiceController webServiceController;
    String fare, fromcity, tocity, companyname, bustypename, buslabel, depaturetime, arrivaltime;
    JSONObject jsonObject1;

    @SuppressLint("ValidFragment")
    public BusDetailFragment(String companyname, String bustypename, String depaturetime, String arrivaltime, String fare, String fromcity, String tocity, String from_id, String to_id, String routScheduleId, String routCode, String departDate,
                             String resultToken, String searchId, String bookingSource, JSONObject jsonObject1) {
        this.companyname = companyname;
        this.bustypename = bustypename;

        this.depaturetime = depaturetime;
        this.arrivaltime = arrivaltime;
        this.fare = fare;
        this.fromcity = fromcity;
        this.tocity = tocity;
        this.from_id = from_id;
        this.to_id = to_id;
        this.routScheduleId = routScheduleId;
        this.routCode = routCode;
        this.departDate = departDate;
        this.resultToken = resultToken;
        this.searchId = searchId;
        this.bookingSource = bookingSource;
        this.jsonObject1 = jsonObject1;
    }

    public BusDetailFragment() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.bus_seating_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceController = new WebServiceController(getActivity(), BusDetailFragment.this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        action_type = getActivity().findViewById(R.id.action_type);
        action_type.setVisibility(View.GONE);
        schedule_id = routScheduleId;
        schedule_date = depaturetime;
        from_bus = fromcity;
        to_bus = tocity;
        // isAc = routeObject.getString("HasAC");
        journeyDate = depaturetime;
        arrival_time = arrivaltime;
        arrival_date = arrivaltime;
        base_fare = String.valueOf(Double.parseDouble(fare) / Double.parseDouble(Global.currencyValue));
        resp_fare = String.valueOf(Double.parseDouble(fare) / Double.parseDouble(Global.currencyValue));
        bus_name = companyname;
        //  bus_type = buslabel;
        bus_type_name = bustypename;

        callDetailApi();
        callboardingApi();
    }

    private void callDetailApi() {
        BusDetailRequest busDetailRequest = new BusDetailRequest(routScheduleId, departDate, routCode, searchId, resultToken, bookingSource);
        RequestParams requestParams = new RequestParams();
        requestParams.put("bus_details", gson.toJson(busDetailRequest));

        if (applicationPreference.getData("login_flag").equals("true")) {
            requestParams.put("user_id", applicationPreference.getData(applicationPreference.userId));

        } else {
            requestParams.put("user_id", "");
        }
        webServiceController.postRequest(apiConstants.BUS_DETAIL, requestParams, 1);
    }

    private void callboardingApi() {
        BusDetailRequest busDetailRequest =
                new BusDetailRequest(routScheduleId, departDate, routCode,
                        searchId, resultToken, bookingSource);
        RequestParams requestParams = new RequestParams();
        requestParams.put("boarding_details", gson.toJson(busDetailRequest));

        if (applicationPreference.getData("login_flag").equals("true")) {
            requestParams.put("user_id", applicationPreference.getData(applicationPreference.userId));

        } else {
            requestParams.put("user_id", "");
        }
        webServiceController.postRequestt(apiConstants.BUS_BOARDING, requestParams, 2);
    }

    private void call_mobile_bookingAPI() {
        for (int i = 0; i < jsonArray3.length(); i++) {
            try {
                //   final_seat_index_list.add("\"" +allseats1.get(i).getString("SeatIndex")+"\"");
                jsonArray4.put(jsonArray3.getJSONObject(i).getString("SeatIndex"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.i("final_seat_index_list", String.valueOf(jsonArray4));
        BusDetailRequest busDetailRequest =
                new BusDetailRequest(routScheduleId, departDate, routCode,
                        searchId, resultToken, bookingSource);
        RequestParams requestParams = new RequestParams();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("traceId", resultToken);
            jsonObject.put("bus_res", jsonObject1);
            jsonObject.put("application_currency", "INR");
            jsonObject.put("booking_source", bookingSource);
            jsonObject.put("dropdetails", jsonArray1);
            jsonObject.put("dropId", drop_idd);
            jsonObject.put("pickup-details", jsonArray);
            jsonObject.put("pickupId", pickup_idd);
            jsonObject.put("search_id", searchId);
            jsonObject.put("seat", jsonArray4);
            jsonObject.put("selected_seat_response", jsonArray3);
            jsonObject.put("resultIndex", resultindex);
            Log.i("jsonobject_mobile", resultindex);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // requestParams.put("book_params", gson.toJson(busDetailRequest));
        requestParams.put("book_params", jsonObject);

        if (applicationPreference.getData("login_flag").equals("true")) {
            requestParams.put("user_id", applicationPreference.getData(applicationPreference.userId));

        } else {
            requestParams.put("user_id", "");
        }
        Log.i("bus_res", String.valueOf(jsonObject1));
        webServiceController.postRequestt(apiConstants.BUS_MOBILE_BOOKING, requestParams, 3);
    }

    String currency = "";

    //{"status":0,"message":"Unable To Fetch The Data"}
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void getResponse(String response, int flag) {
        if (flag == 1) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status") == 1) {
                    //TODO : Parse data
                    JSONObject datObject = jsonObject.getJSONObject("SeatDetails");
                    JSONObject detailsObject = datObject.getJSONObject("GetBusSeatLayOutResult");
                    JSONObject detailsObject1 = detailsObject.getJSONObject("SeatLayoutDetails");
                    JSONObject detailsObject2 = detailsObject1.getJSONObject("SeatLayout");
                    JSONArray detailsObject3 = detailsObject2.getJSONArray("SeatDetails");
                    resultindex = jsonObject.getString("resultIndex");


                    //   JSONObject routeObject = detailsObject.getJSONObject("Route");

                /*if(routeObject.has("HasSleeper")){
                    isSleeper = routeObject.getString("HasSleeper");
                }else {
                    isSleeper="false";
                }

                schedule_id = routeObject.getString("RouteScheduleId");
                schedule_date = routeObject.getString("DepartureTime");
                from_bus = routeObject.getString("From");
                to_bus = routeObject.getString("To");
                isAc = routeObject.getString("HasAC");
                journeyDate= routeObject.getString("DeptTime");
                arrival_time = routeObject.getString("ArrivalTime");
                arrival_date= routeObject.getString("ArrTime");
                base_fare = String.valueOf((double)routeObject.getInt("Fare")/ Double.parseDouble(Global.currencyValue));
                resp_fare = String.valueOf((double)routeObject.getInt("Fare")/ Double.parseDouble(Global.currencyValue));
                bus_name = routeObject.getString("CompanyName");
                bus_type = routeObject.getString("BusLabel");
                bus_type_name = routeObject.getString("BusTypeName");*/
                    //CommAmount = routeObject.optString("CommAmount");
        /*        if(routeObject.has("CommAmount")){
                    CommAmount = String.valueOf((double)routeObject.getInt("CommAmount")/
                            Double.parseDouble(Global.currencyValue));
                }else {
                    CommAmount="0.0";
                }
*/
                    //   JSONObject currencyObj = datObject.optJSONObject("currency_obj");


              /*  currency = currencyObj.optString("to_currency");
                JSONObject resultObj = detailsObject.getJSONObject("result");
                cancArray = resultObj.optJSONArray("Canc");
                JSONObject layoutObj = resultObj.getJSONObject("layout");
                rows_g = layoutObj.getInt("MaxRows");
                columns_g = layoutObj.getInt("MaxCols");
                supercashback=resultObj.getString("super_cashback_discount");
                charityAmt=resultObj.getString("charity_value");

                JSONArray valueArray = resultObj.getJSONArray("value");*/
                    int i = 0;
                    int j = 0;
                /*while (i < detailsObject3.length()){
                    JSONObject valueObj = detailsObject3.getJSONObject(i);
                    seatingbean = new SeatingBean(valueObj.getInt("seq_no"),
                            Integer.parseInt(valueObj.getString("RowNo")),Integer.parseInt(valueObj.getString("ColumnNo")),
                            valueObj.getInt("width"),valueObj.getInt("height"),
                            valueObj.getInt("SeatType"), valueObj.getString("SeatIndex"),
                            (double)valueObj.getInt("SeatFare")/Double.parseDouble(Global.currencyValue),
                            //(double)valueObj.getInt("Fare")/Double.parseDouble(Global.currencyValue),
                            //valueObj.getInt("base_fare"),
                            (double)valueObj.getInt("SeatFare")/Double.parseDouble(Global.currencyValue),
                            valueObj.getInt("SeatStatus"), valueObj.getString("decks"),
                            valueObj.getInt("MaxRows"),valueObj.getInt("MaxCols"),
                            valueObj.getInt("SeatStatus"));
                    seating_list.add(seatingbean);
                    i++;
                }*/
                    rows_g = 12;
                    columns_g = 12;
                    while (i < detailsObject3.length()) {
                        JSONObject valueObj = detailsObject3.getJSONObject(i);
                        allseats.add(valueObj);
                        if (valueObj.getString("IsUpper").equals("Upper")) {
                            upper = true;
                        }
                        // rows_g = Integer.parseInt(valueObj.getString("RowNo"));
                        //columns_g = Integer.parseInt(valueObj.getString("ColumnNo"));
                        System.out.println("row_coloum" + Integer.parseInt(String.valueOf(valueObj.getString("ColumnNo"))));
                        System.out.println("row_coloum12" + Integer.parseInt(String.valueOf(valueObj.getString("RowNo"))));

                        seatingbean = new SeatingBean(i,
                                Integer.parseInt(valueObj.getString("RowNo")),
                                Integer.parseInt(valueObj.getString("ColumnNo")),
                                //  Integer.parseInt(valueObj.getString("ColumnNo")),
                                // Integer.parseInt(valueObj.getString("RowNo")) ,
                                //i,i,
                                // 1,1,
                                valueObj.getInt("Width"),
                                valueObj.getInt("Height"),
                                valueObj.getInt("SeatType"),
                                valueObj.getString("SeatName"),
                                (double) valueObj.getInt("SeatFare") / Double.parseDouble(Global.currencyValue),
                                //(double)valueObj.getInt("Fare")/Double.parseDouble(Global.currencyValue),
                                //valueObj.getInt("base_fare"),
                                (double) valueObj.getInt("SeatFare") / Double.parseDouble(Global.currencyValue),
                                valueObj.getInt("SeatStatus"), valueObj.getString("IsUpper"),
                                rows_g, columns_g,
                                valueObj.getInt("SeatStatus"));

                        seating_list.add(seatingbean);
                        i++;
                    }
               /* while (j < detailsObject3.getJSONArray(1).length()){
                    JSONObject valueObj = detailsObject3.getJSONArray(1).getJSONObject(j);
                    System.out.println("row_coloum"+ Integer.parseInt(String.valueOf(valueObj.getString("ColumnNo").charAt(2))));
                    seatingbean = new SeatingBean(j,
                            Integer.parseInt(valueObj.getString("RowNo")),  Integer.parseInt(valueObj.getString("ColumnNo")),
//i,i,
                            // 1,1,
                            valueObj.getInt("Width"),valueObj.getInt("Height"),
                            valueObj.getInt("SeatType"), valueObj.getString("SeatIndex"),
                            (double)valueObj.getInt("SeatFare")/Double.parseDouble(Global.currencyValue),
                            //(double)valueObj.getInt("Fare")/Double.parseDouble(Global.currencyValue),
                            //valueObj.getInt("base_fare"),
                            (double)valueObj.getInt("SeatFare")/Double.parseDouble(Global.currencyValue),
                            1, "Lower",
                            1,10,
                            1);
                    seating_list.add(seatingbean);
                    j++;
                }*/

              /*  JSONArray json_pickup = resultObj.optJSONArray("Pickups");
                JSONObject json_pick_object = null;

                for (int j = 0; j< json_pickup.length(); j++) {
                    json_pick_object = json_pickup.getJSONObject(j);

                    pickupbean = new PickUpBean();

                    pickupbean.setPickupid(json_pick_object.getString("PickupCode"));
                    pickupbean.setPickupname(json_pick_object.getString("PickupName"));
                    pickupbean.setPickuptime(json_pick_object.getString("PickupTime"));
                    pickupbean.setAddress(json_pick_object.getString("Address"));
                    pickupbean.setLandmark(json_pick_object.getString("Landmark"));

                    pickupbean.setContact(json_pick_object.getString("Contact"));

                    pick_up_list.add(pickupbean);
                }

                JSONArray dropArray = resultObj.optJSONArray("Dropoffs");


                for (int k = 0; k < dropArray.length(); k++) {
                    try {
                        JSONObject dropObj = dropArray.getJSONObject(k);

                        DropOffBean dropOffBean = new DropOffBean();
                        dropOffBean.setDropoffCode(dropObj.getString("DropoffCode"));
                        dropOffBean.setDropoffName(dropObj.getString("DropoffName"));
                        dropOffBean.setDropoffTime(dropObj.getString("DropoffTime"));

                        drop_off_list.add(dropOffBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/

                    if (isSleeper.equals("true")) {
                     /*   if (upper) {
                           // topLayout.setVisibility(View.VISIBLE);
                            lowerBirthText.setVisibility(View.VISIBLE);
                            upperBirthText.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                           // topLayout.setVisibility(View.VISIBLE);
                            lowerBirthText.setVisibility(View.VISIBLE);
                        }*/
                        topLayout.setVisibility(View.VISIBLE);
                        if (rows_g > 0 && columns_g > 0) {
                            setOldSleeperLayout(rows_g, columns_g, seating_list, "Lower");
                        }

                    } else {
                       /* if (upper) {
                            // topLayout.setVisibility(View.VISIBLE);
                            lowerBirthText.setVisibility(View.VISIBLE);
                            upperBirthText.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            // topLayout.setVisibility(View.VISIBLE);
                            lowerBirthText.setVisibility(View.VISIBLE);
                        }*/
                        topLayout.setVisibility(View.VISIBLE);

                        if (rows_g > 0 && columns_g > 0) {
                            setOldSleeperLayout(rows_g, columns_g, seating_list, "Lower");
                        }

                    }

                } else {
                    commonUtils.toastShort(jsonObject.getString("message"), getActivity());
                    getActivity().onBackPressed();
                }
            } catch (Exception e) {
                e.printStackTrace();
                commonUtils.toastShort(e.getMessage(), getActivity());
            }
        } else if (flag == 2) {

            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status") == 1) {
                    //TODO : Parse data
                    JSONObject datObject = jsonObject.getJSONObject("data");

                    JSONObject datObject1 = datObject.getJSONObject("BoardingDetails");
                    JSONArray json_pickup = datObject1.getJSONArray("BoardingPointsDetails");
                    JSONArray dropArray = datObject1.getJSONArray("DroppingPointsDetails");
                    //   JSONArray json_pickup = resultObj.optJSONArray("Pickups");
                    JSONObject json_pick_object = null;

                    for (int j = 0; j < json_pickup.length(); j++) {
                        json_pick_object = json_pickup.getJSONObject(j);
                        pickuppointlist.add(json_pick_object);

                        pickupbean = new PickUpBean();

                        pickupbean.setPickupid(json_pick_object.getString("CityPointIndex"));
                        pickupbean.setPickupname(json_pick_object.getString("CityPointName"));
                        pickupbean.setPickuptime(json_pick_object.getString("CityPointTime"));
                        pickupbean.setAddress(json_pick_object.getString("CityPointAddress"));
                        pickupbean.setLandmark(json_pick_object.getString("CityPointLandmark"));

                        pickupbean.setContact(json_pick_object.getString("CityPointContactNumber"));

                        pick_up_list.add(pickupbean);
                    }

                    // JSONArray dropArray = resultObj.optJSONArray("Dropoffs");


                    for (int k = 0; k < dropArray.length(); k++) {
                        try {
                            JSONObject dropObj = dropArray.getJSONObject(k);
                            dropoffpointlist.add(dropObj);
                            DropOffBean dropOffBean = new DropOffBean();
                            dropOffBean.setDropoffCode(dropObj.getString("CityPointIndex"));
                            dropOffBean.setDropoffName(dropObj.getString("CityPointName"));
                            dropOffBean.setDropoffTime(dropObj.getString("CityPointTime"));

                            drop_off_list.add(dropOffBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                JSONObject jsonObject = new JSONObject(response);
                token = jsonObject.getString("token");
                token_key = jsonObject.getString("tokenKey");
                booking_source = jsonObject.getString("booking_source");
                super_cash_discount = jsonObject.getString("super_cash_discount");
                charity_value = jsonObject.getString("charity_value");
                convenience_fee = jsonObject.getString("convenience_fees");

                Log.i("convenience_fee", convenience_fee);
                proceedPay();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setOldSleeperLayout(final Integer rows, final Integer columns,
                                     final ArrayList<SeatingBean> seating_items, String deck) {
        if (seating_table.getChildCount() > 0) {
            seating_table.removeAllViews();
        }

        TableRow[] tablerow = new TableRow[rows + 1];
        ImageView[][] imageview = new ImageView[rows][columns];

        tablerow[0] = new TableRow(getActivity());
        System.out.println("coloums" + columns);
        for (int r = 0; r < columns; r++) {

            imageview[r][0] = new ImageView(getActivity());
            imageview[r][0].setRotation(-90);

            if (r == columns - 1 && deck == "Lower") {
                // imageview[r][0].setImageResource(R.drawable.ic_stairing);
                // imageview[11][0].setImageResource(R.drawable.ic_stairing);
            } else {

            }

           /* int trHeight = 50;
            int trWidth = 1;
            ViewGroup.LayoutParams layoutpParams = new ViewGroup.LayoutParams(trWidth, trHeight);
            tablerow[0].setLayoutParams(layoutpParams);*/
            tablerow[0].addView(imageview[r][0]);
        }

        try {
            TableLayout.LayoutParams params12 = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT);
            //        params12.setMargins(10,15,12,0);
            params12.setMargins(10, 15, 12, 0);
            //  params12.setLayoutDirection(Gravity.START|Gravity.CENTER_VERTICAL);
            seating_table.addView(tablerow[0], params12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * Main logic
         * */
        for (int c = 0; c < rows; c++) {
            tablerow[c + 1] = new TableRow(getActivity());
            for (int r = 0; r < columns; r++) {
                // for(int r=columns-1;r>=0;r--){
                imageview[c][r] = new ImageView(getActivity());
                imageview[c][r].setRotation(-90);
                Log.d("data", "[" + c + " " + r + "]");
                // LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
                // imageview[c][r].setLayoutParams(layoutParams);
               /* android.view.ViewGroup.LayoutParams layoutParams = imageview[c][r].getLayoutParams();
                layoutParams.width = 8;
                layoutParams.height = 8;
                imageview[c][r].setLayoutParams(layoutParams);*/
                // tablerow[0].addView(lp);

                for (int iter = 0; iter < seating_items.size(); iter++) {
                    SeatingBean bean = seating_items.get(iter);
                    if (bean.getDecks().equals(deck)) {
                        if (c == bean.getRow() && r == bean.getCol()) {
                            if (bean.getIsAvailable() == 0 && bean.getSeatNo().equals("")) {
                                //Do Nothing
                            } else {
                                if (bean.getStatus() == 1 || bean.getStatus() == 2 || bean.getStatus() == 3) {
                                    Log.e("available...", "true");
                                    imageview[c][r].setId(iter);
                                    imageview[c][r].setOnClickListener(new View.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                        @SuppressLint({"UseValueOf", "SetTextI18n"})
                                        @Override
                                        public void onClick(View v) {
                                            ImageView view = (ImageView) v;
                                            view.setRotation(-90);
                                            seatingbean = seating_items.get(view.getId());
                                            if (tracking_list.contains(view.getId())) {
                                                Log.e("tracking_list", "contains view");
                                                if (seatingbean.getSeatType() == 2) {
                                                    /** SLEEPER */
                                                    if (seatingbean.getStatus() == 1 || seatingbean.getStatus() == 2 || seatingbean.getStatus() == 3) {
                                                      /*  ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(
                                                                ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                                                                ViewGroup.MarginLayoutParams.WRAP_CONTENT
                                                        );
                                                        marginLayoutParams.setMargins(8, 8, 18, 8);
                                                        view.setLayoutParams(marginLayoutParams);*/
                                                        view.setImageResource(ordinarySleeperSeat("2"));
                                                    }
                                                    /*if(busSeatingBean.getIsAvail() == 2){
                                                        view.setImageResource(ordinarySleeperSeat(String.valueOf(busSeatingBean.getWidth())));
                                                    }else if(busSeatingBean.getIsAvail() == 3){
                                                        view.setImageResource(ordinarySleeperladies(String.valueOf(busSeatingBean.getWidth())));
                                                    }else{
                                                        view.setImageResource(ordinarySleeperSeat(String.valueOf(busSeatingBean.getWidth())));
                                                    }*/
                                                } else {
                                                    /** SEATER */
                                                    if (seatingbean.getStatus() == 1 || seatingbean.getStatus() == 2 || seatingbean.getStatus() == 3) {
                                                        view.setImageResource(R.drawable.ic_available_new);
                                                        // jsonArray3.clear();
                                                        jsonArray3.remove(jsonArray3.length() - 1);
                                                        System.out.println("seating_list123" + jsonArray3);
                                                    }
                                                    /*if(busSeatingBean.getIsAvail() == 2){
                                                        view.setImageResource(R.drawable.item_seat_albooked);
                                                    }else if(busSeatingBean.getIsAvail() == 3){
                                                        view.setImageResource(R.drawable.item_seat_albooked);
                                                    }else if(busSeatingBean.getIsAvail() == 1){
                                                        view.setImageResource(R.drawable.item_seat_ordinary);
                                                    }*/
                                                }

                                              /*  if(!totalPrice.getText().toString().equals("")){
                                                    double totalclaal = Double.parseDouble(totalPrice.getText().toString())-Double.parseDouble(String.valueOf(seatingbean.getTotalFare()));
                                                    totalPrice.setText(""+Math.round(totalclaal));
                                                }*/
                                                if (!totalPrice.getText().toString().equals("")) {
                                                    double totalclaal = Double.parseDouble(totalPrice.getText().toString().split(" ")[1]) - Double.parseDouble(String.valueOf(seatingbean.getTotalFare()));
                                                    totalPrice.setText(Global.currencySymbol + " " + (df2.format(totalclaal)));
                                                }
                                                tracking_list.remove(new Integer(view.getId()));
                                                seating_selection_list1.remove(view.getId());
                                                seq_no_list.remove(view.getId());
                                                fare_list.remove(view.getId());
                                                StringBuilder sb = new StringBuilder();
                                                Collection<String> val = seating_selection_list1.values();
                                                ArrayList<String> ssl = new ArrayList<String>(val);
                                                for (String s : ssl) {
                                                    sb.append(s);
                                                    if (sb.length() > 0)
                                                        sb.append(",");
                                                }

                                                seatCount.setText(sb.toString());

                                                if (map_rates_storing.containsKey(seatingbean.getSeatNo())) {
                                                    map_rates_storing.remove(seatingbean.getSeatNo());
                                                    finalSeat.add(seatingbean);
                                                }
                                            } else {
                                                if (tracking_list.size() < 6) {
                                                    if (seatingbean.getSeatType() == 2) {
                                                        /** SLEEPER */
                                                        if (seatingbean.getStatus() == 1 || seatingbean.getStatus() == 2 || seatingbean.getStatus() == 3) {
                                                            view.setImageResource(ordinarySleeperSelected("2"));
                                                        }
                                                    } else {
                                                        /** SEATER */
                                                        if (seatingbean.getStatus() == 1 || seatingbean.getStatus() == 2 || seatingbean.getStatus() == 3) {
                                                            view.setImageResource(R.drawable.ic_selected_new);
                                                            view.setRotation(-90);
                                                            //  view.setImageResource(R.drawable.ic_selected_new);
                                                            // JSONArray jsonArray=new JSONArray();

                                                            // seating_list.get(view.getId());
                                                            //allseats1.add(allseats.get(view.getId()));
                                                            jsonArray3.put(allseats.get(view.getId()));
                                                            // System.out.println("seating_list"+ allseats.get(view.getId()));
                                                            System.out.println("seating_list12" + jsonArray3);
                                                        }
                                                    }

                                                    tracking_list.add(view.getId());
                                                    seating_selection_list1.put(view.getId(), seatingbean.getSeatNo());
                                                    seq_no_list.put(view.getId(), String.valueOf(seatingbean.getSeqNo()));
                                                    fare_list.put(view.getId(), String.valueOf(seatingbean.getTotalFare()));

                                                    if (map_rates_storing.containsKey(seatingbean.getSeatNo())) {
                                                        map_rates_storing.put(seatingbean.getSeatNo(), String.valueOf(seatingbean.getTotalFare()));
                                                        finalSeat.add(seatingbean);
                                                    } else {
                                                        map_rates_storing.put(seatingbean.getSeatNo(), String.valueOf(seatingbean.getTotalFare()));
                                                        finalSeat.add(seatingbean);
                                                    }

                                                   /* if(!totalPrice.getText().toString().equals("")){
                                                        double totalclaal = Double.parseDouble(totalPrice.getText().toString())+Double.parseDouble(String.valueOf(seatingbean.getTotalFare()));
                                                        totalPrice.setText(""+Math.round(totalclaal));
                                                    }else{
                                                        totalPrice.setText(""+Math.round(Float.parseFloat(String.valueOf(seatingbean.getTotalFare()))));
                                                    }*/
                                                    if (!totalPrice.getText().toString().equals("")) {
                                                        double totalclaal = Double.parseDouble(totalPrice.getText().toString().split(" ")[1]) + Double.parseDouble(String.valueOf(seatingbean.getTotalFare()));
                                                        totalPrice.setText(Global.currencySymbol + " " + (df2.format(totalclaal)));
                                                    } else {
                                                        totalPrice.setText(Global.currencySymbol + " " + (df2.format(Float.parseFloat(String.valueOf(seatingbean.getTotalFare())))));
                                                    }

                                                    StringBuilder sb = new StringBuilder();
                                                    Collection<String> val = seating_selection_list1.values();
                                                    ArrayList<String> ssl = new ArrayList<String>(val);
                                                    for (String s : ssl) {
                                                        sb.append(s);
                                                        if (sb.length() > 0)
                                                            sb.append(",");
                                                    }

                                                    seatCount.setText(sb.toString());
                                                } else {
                                                    Toast.makeText(getActivity(), "Maximum 6 seats allowed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    });


                                    if (bean.getSeatType() == 2) {
                                        /** SLEEPER */
                                        topLayout.setVisibility(View.VISIBLE);
                                        click = 1;
                                        if (bean.getStatus() == 1 || bean.getStatus() == 2 || bean.getStatus() == 3) {
                                            /*ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(
                                                    ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                                                    ViewGroup.MarginLayoutParams.WRAP_CONTENT
                                            );
                                            marginLayoutParams.setMargins(8, 8, 18, 8);
                                            imageview[c][r].setLayoutParams(marginLayoutParams);*/
                                            imageview[c][r].setImageResource(ordinarySleeperSeat("2"));
                                            // imageview[c][r].setRotation(-90);
                                        } else {
                                            if (bean.getStatus() == 0 || bean.getStatus() == -2) {
                                                imageview[c][r].setImageResource(ordinarySleeperbooked("2"));
                                            } else if (bean.getStatus() == -3) {
                                                imageview[c][r].setImageResource(ordinarySleeperladies("2"));
                                            }
                                        }
                                        if (tracking_list.contains(iter)) {
                                            imageview[c][r].setImageResource(ordinarySleeperSelected(String.valueOf(bean.getWidth())));
                                        }
                                    } else {
                                        /** SEATER */
                                        if (bean.getStatus() == 1 || bean.getStatus() == 2 || bean.getStatus() == 3) {
                                            imageview[c][r].setImageResource(R.drawable.ic_available_new);
                                            //imageview[c][r].setRotation(-90);
                                        } else {
                                            if (bean.getStatus() == 0 || bean.getStatus() == -2) {
                                                imageview[c][r].setImageResource(R.drawable.seat_blocked);
                                            } else if (bean.getStatus() == -3) {
                                                imageview[c][r].setImageResource(R.drawable.seat_res_ladies);
                                            }
                                        }
                                        if (tracking_list.contains(iter)) {
                                            imageview[c][r].setImageResource(R.drawable.ic_blocked_new);
                                        }
                                    }

                                    /*if(bean.getSeat_ty() == 2)
                                    {
                                        top_lay.setVisibility(View.VISIBLE);
                                        click = 1;
                                      if (bean.getIsAvail() == 2) {
                                            imageview[c][r].setImageResource(ordinarySleeperSelected(String.valueOf(bean.getWidth())));
                                        } else if (bean.getIsAvail() == 3) {
                                            imageview[c][r].setImageResource(ordinarySleeperladies(String.valueOf(bean.getWidth())));
                                        } else {
                                            imageview[c][r].setImageResource(ordinarySleeperSeat(String.valueOf(bean.getWidth())));
                                        }

                                        if (tracking_list.contains(iter)) {
                                            imageview[c][r].setImageResource(ordinarySleeperSelected(String.valueOf(bean.getWidth())));
                                        }

                                    } else{
                                        if(busSeatingBean.getIsAvail() == 2){
                                            imageview[c][r].setImageResource(R.drawable.item_seat_albooked);
                                        }else if(busSeatingBean.getIsAvail() == 3){
                                            imageview[c][r].setImageResource(R.drawable.item_seat_albooked);
                                        }else if(busSeatingBean.getIsAvail() == 1){
                                            imageview[c][r].setImageResource(R.drawable.item_seat_ordinary);
                                        }else if(busSeatingBean.getIsAvail() == -2){
                                            imageview[c][r].setImageResource(R.drawable.item_seat_albooked);
                                        }else if(busSeatingBean.getIsAvail() == -3){
                                            imageview[c][r].setImageResource(R.drawable.item_seat_albooked);
                                        }
                                        if (tracking_list.contains(iter)) {
                                            imageview[c][r].setImageResource(R.drawable.item_seat_booked);
                                        }
                                    }*/

                                } else {
                                    if (bean.getSeatType() == 2) {
                                        /** SLEEPER */
                                        if (bean.getStatus() == 0 || bean.getStatus() == -2) {
                                            imageview[c][r].setImageResource(ordinarySleeperbooked("2"));
                                            // imageview[c][r].setRotation(-90);

                                        } else if (bean.getStatus() == -3) {
                                            imageview[c][r].setImageResource(ordinarySleeperladies("2"));
                                        }

                                    } else {
                                        /** SEATER */
                                        if (bean.getStatus() == 0 || bean.getStatus() == -2) {
                                            imageview[c][r].setImageResource(R.drawable.ic_blocked_new);
                                            // imageview[c][r].setRotation(-90);
                                        } else if (bean.getStatus() == -3) {
                                            imageview[c][r].setImageResource(R.drawable.ic_booked_female_new);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, 100);
                //imageview[c][r].setLayoutParams(layoutParams);

                TableRow.LayoutParams layoutParams2 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                //layoutParams2.setMargins(10, 15, 25, 0);
                layoutParams2.setLayoutDirection(Gravity.START);
                tablerow[c + 1].addView(imageview[c][r], layoutParams2);

            }
            try {
                TableLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(10, 15, 25, 0);
                ///  params.setLayoutDirection(Gravity.START|Gravity.CENTER_VERTICAL);
                seating_table.addView(tablerow[c + 1],params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public int ordinarySleeperSeat(String width) {
        jsonArray3.remove(jsonArray3.length() - 1);
        System.out.println("seating_list125" + jsonArray3);
        return (width.equals("1")) ? R.drawable.ic_available_sleep_new : R.drawable.ic_available_sleep_new;
    }

    public int ordinarySleeperladies(String width) {
        return (width.equals("1")) ? R.drawable.ic_res_female_sleep_new : R.drawable.ic_res_female_sleep_new;
    }

    public int ordinarySleeperbooked(String width) {
        return (width.equals("1")) ? R.drawable.ic_blocked_sleeper_new : R.drawable.ic_blocked_sleeper_new;
    }

    public int ordinarySleeperSelected(String width) {
        try {
            allseats1.add(allseats.get(View.generateViewId()));
            jsonArray3.put(allseats.get(View.generateViewId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println("seating_list"+ allseats.get(view.getId()));
        System.out.println("seating_list124" + jsonArray3);
        System.out.println("seating_listupper" + allseats1);
        return (width.equals("1")) ? R.drawable.ic_selected_sleeper_new : R.drawable.ic_selected_sleeper_new;
    }

    public int ordinarySleeperMaleSelected(String width) {
        return (width.equals("1")) ? R.drawable.ic_res_male_sleep_new : R.drawable.ic_res_male_sleep_new;
    }

    Dialog builder;

    public void selectBoard() {
        /*builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final ListView listview = new ListView(getActivity());
        listview.setBackgroundColor(Color.WHITE);
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(listview);
        builder.setContentView(layout);
        CustomAdapter adapter = new CustomAdapter(getActivity(), pick_up_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        builder.show();*/


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.boarding_point_list, null);
        dialogBuilder.setView(dialogView);
        // dialogView.getParent().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog = dialogBuilder.create();

        alertDialog.show();

        RecyclerView rv_boardingList = dialogView.findViewById(R.id.rv_bordinglist);
        BoardingPointAdapter boardingPointAdapter = new BoardingPointAdapter(BusDetailFragment.this, pick_up_list, pickuppointlist);
        rv_boardingList.setAdapter(boardingPointAdapter);
    }

    public void selectDrop() {
      /*  builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final ListView listview = new ListView(getActivity());
        listview.setBackgroundColor(Color.WHITE);
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(listview);
        builder.setContentView(layout);
        DropAdapter adapter = new DropAdapter(getActivity(), drop_off_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        builder.show();*/

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.boarding_point_list, null);
        dialogBuilder.setView(dialogView);
        // dialogView.getParent().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog = dialogBuilder.create();

        alertDialog.show();

        RecyclerView rv_boardingList = dialogView.findViewById(R.id.rv_bordinglist);
        DropingPointAdapter dropingPointAdapter = new DropingPointAdapter(BusDetailFragment.this, drop_off_list, dropoffpointlist);
        rv_boardingList.setAdapter(dropingPointAdapter);
    }

    String dropname = "", drop_id = "", drop_idd = "", pickup_idd;
    String dropoffLocation = "";
    String pickTime = "", dropTime = "";
    String boardingFrom = "", dropingTo = "";


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String tag = view.getTag().toString();


        if (tag.contains("CustomAdapter")) {
            // pickuppointlist_selected.add(pickuppointlist.get(position));
            // Log.i("pickup", String.valueOf(pickuppointlist_selected));
            boardingname = ((TextView) view.findViewById(R.id.boad_name)).getText().toString();
            boarding_id = ((TextView) view.findViewById(R.id.boad_id)).getText().toString();
            pickupLocation = pick_up_list.get(position).getPickupid();
            // pickup_idd=pick_up_list.get(position).getPickupid();
            pickTime = pick_up_list.get(position).getPickuptime();

            String address = pick_up_list.get(position).getAddress();
            String landmark = pick_up_list.get(position).getLandmark();
            String contact = pick_up_list.get(position).getContact();

            boardingFrom = boardingname + "," + "Address : " + address + ", Landmark : " + landmark + ", Phone :" + contact;


            boarding_name.setText(boardingname);
            builder.dismiss();
        } else {
            // dropoffpointlist_seleted.add(dropoffpointlist.get(position));
            // Log.i("pickup11", String.valueOf(dropoffpointlist_seleted));
            dropname = ((TextView) view.findViewById(R.id.boad_name)).getText().toString();
            drop_id = ((TextView) view.findViewById(R.id.boad_id)).getText().toString();
            dropoffLocation = drop_off_list.get(position).getDropoffCode();
            // drop_idd=drop_off_list.get(position).getDropoffCode();
            dropTime = drop_off_list.get(position).getDropoffTime();
            dropping_name.setText(dropname);

            dropingTo = dropname;

            builder.dismiss();
        }

    }

    public void pickUplistdata(JSONObject jsonObject, String pickup_id) {
        pickuppointlist_selected.add(jsonObject);
        pickup_idd = pickup_id;
        jsonArray.put(jsonObject);
        Log.i("pickup_updated", String.valueOf(pickuppointlist_selected));

    }

    public void dropofflistdata(JSONObject jsonObject, String drop_id) {
        dropoffpointlist_seleted.add(jsonObject);
        drop_idd = drop_id;
        jsonArray1.put(jsonObject);
        Log.i("pickup_updated12", String.valueOf(dropoffpointlist_seleted));

    }

    public void pickUpAddress(int position) {
        alertDialog.dismiss();
        boardingname = pick_up_list.get(position).getPickupname() + "-" + getHourMin(pick_up_list.get(position).getPickuptime());
        boarding_id = pick_up_list.get(position).getPickupid();
        pickupLocation = pick_up_list.get(position).getPickupid();

        pickTime = pick_up_list.get(position).getPickuptime();

        String address = pick_up_list.get(position).getAddress();
        String landmark = pick_up_list.get(position).getLandmark();
        String contact = pick_up_list.get(position).getContact();

        boardingFrom = boardingname + "," + "Address : " + address + ", Landmark : " + landmark + ", Phone :" + contact;


        boarding_name.setText(boardingname);
    }

    public void dropOffAddress(int position) {
        alertDialog.dismiss();
        dropname = drop_off_list.get(position).getDropoffName() + "-" + getHourMin(drop_off_list.get(position).getDropoffTime());
        drop_id = drop_off_list.get(position).getDropoffCode();
        dropoffLocation = drop_off_list.get(position).getDropoffCode();

        dropTime = drop_off_list.get(position).getDropoffTime();
        dropping_name.setText(dropname);

        dropingTo = dropname;

    }

    public void proceedPay() {
        try {
            Bundle probundle = new Bundle();
            probundle.putString("from", from_bus);
            probundle.putString("to", to_bus);
            probundle.putString("bus", bus_name);
            probundle.putString("seats", seatCount.getText().toString().split(" ")[0]);
            probundle.putString("total", totalPrice.getText().toString());
            probundle.putString("token", token);
            probundle.putString("token_key", token_key);
            probundle.putString("search_id", searchId);
            probundle.putString("scheduler_id", schedule_id);
            probundle.putString("journey_date", journeyDate);
            probundle.putString("pickup_id", pickupLocation);


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("RouteScheduleId", schedule_id);
            jsonObject.put("JourneyDate", journeyDate);
            jsonObject.put("PickUpID", boarding_id);
            jsonObject.put("DropID", drop_id);


            jsonObject.put("DepartureTime", schedule_date);
            jsonObject.put("ArrivalTime", arrival_time);

            //jsonObject.put("ArrTime", arrival_date);

            jsonObject.put("departure_from", from_bus);
            jsonObject.put("arrival_to", to_bus);
            jsonObject.put("Form_id", from_id);
            jsonObject.put("To_id", to_id);
            jsonObject.put("boarding_from", boardingFrom);
            jsonObject.put("dropping_to", dropingTo);
            jsonObject.put("bus_type", bus_type_name);
            jsonObject.put("operator", bus_name);
            jsonObject.put("CommPCT", null);
            jsonObject.put("CommAmount", CommAmount);
            jsonObject.put("CancPolicy", cancArray);


            JSONObject seatObj = new JSONObject();
           /* seatObj.put("markup_price_summary", totalPrice.getText().toString());
            seatObj.put("total_price_summary", totalPrice.getText().toString());
            seatObj.put("domain_deduction_fare", totalPrice.getText().toString());*/
            seatObj.put("markup_price_summary", totalPrice.getText().toString().split(" ")[1]);
            seatObj.put("total_price_summary", totalPrice.getText().toString().split(" ")[1]);
            seatObj.put("domain_deduction_fare", totalPrice.getText().toString().split(" ")[1]);

            seatObj.put("default_currency", Global.currencySymbol);

            JSONObject seats = new JSONObject();


            for (int i = 0; i < finalSeat.size(); i++) {
                SeatingBean seatingBean = finalSeat.get(i);
                JSONObject jObj = new JSONObject();

                jObj.put("Fare", base_fare);
                jObj.put("Markup_Fare", resp_fare);
                jObj.put("IsAcSeat", isAc);
                jObj.put("SeatType", seatingBean.getSeatType());
                jObj.put("seq_no", seatingBean.getSeqNo());


                String deck = seatingBean.getDecks();
                if (deck.equalsIgnoreCase("Lower")) {
                    deck = "Lower";
                } else {
                    deck = "Upper";
                }
                jObj.put("decks", deck);

                seats.put("" + seatingBean.getSeatNo(), jObj);

            }


            seatObj.put("seats", seats);
            jsonObject.put("seat_attr", seatObj);


            String tokenString = jsonObject.toString();

            // Log.d(TAG, "tokenString  >" + tokenString);


            // byte[] data = tokenString.getBytes("UTF-8");
            //  String base64 = Base64.encodeToString(data, Base64.DEFAULT);

            probundle.putString("token", tokenString);
            probundle.putString("ResultToken", resultToken);
            probundle.putString("ArrivalDate", arrival_date);
            probundle.putString("supercashback", supercashback);
            probundle.putString("charityamt", charityAmt);

            // IntentAndFragmentService.fragmentdisplay(getActivity(), R.id.bus_frame, new BusTravellerFragment(), probundle, true, false);
            intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                    new TravellerFragment(
                            seating_selection_list1.size(),
                            0, 0, 0,
                            totalPrice.getText().toString().split(" ")[1], token, token_key, booking_source, charity_value, super_cash_discount, convenience_fee, 3), probundle, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getHourMin(String example) {
        example = example.replace("T", " ");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            java.util.Date date = format.parse(example);
            SimpleDateFormat format11 = new SimpleDateFormat("hh:mm a");
            String rightdate = format11.format(date);
            return rightdate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /*  @Override
      public void onPause() {
          super.onPause();
          action_type.setVisibility(View.VISIBLE);
      }*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        action_type.setVisibility(View.VISIBLE);
    }

}