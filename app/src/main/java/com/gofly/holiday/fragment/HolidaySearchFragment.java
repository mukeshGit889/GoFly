package com.gofly.holiday.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gofly.holiday.adapter.PackageCountryAdapter;
import com.gofly.main.activity.MainActivity;
import com.gofly.model.parsingModel.holiday.PackageCountryList;
import com.gofly.utils.webservice.ApiConstants;
import com.loopj.android.http.RequestParams;
import com.gofly.R;
import com.gofly.holiday.adapter.PackageAdapter;
import com.gofly.holiday.adapter.PackageDurationAdapter;
import com.gofly.holiday.interfaces.HolidayNotify;
import com.gofly.interfaces.DatePickerNotify;
import com.gofly.main.adapter.landingAdapter.InternationalPackageLanding;
import com.gofly.main.fragment.BaseFragment;
import com.gofly.model.parsingModel.PromoCodeInfo;
import com.gofly.model.parsingModel.holiday.PackageDuration;
import com.gofly.model.parsingModel.holiday.PackageListModel;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import org.json.JSONArray;
import org.json.JSONException;
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

public class HolidaySearchFragment extends BaseFragment
        implements WebInterface, HolidayNotify
{
    PackageAdapter packageAdapter;
    String packageTypeId=null;
    Dialog holidaytypedialog;
    WebServiceController webServiceController;
    String countryId = "", packageId = "", tripDuration = "",amount="",
            packageResponse = null, durationRespone = null,CountryRespone = null;
    List<PackageListModel> packageListModels = new ArrayList<PackageListModel>();
    List<PackageDuration> packageDurations = new ArrayList<PackageDuration>();
    List<PackageCountryList> packageCountryLists = new ArrayList<PackageCountryList>();
    AlertDialog alert;
    String checkInDate="",startDate;
    InternationalPackageLanding internationalPackageLanding;
    List<PromoCodeInfo> packageModels = new ArrayList<PromoCodeInfo>();
    PackageDurationAdapter packageDurationAdapter;
    PackageCountryAdapter packageCountryAdapter;

    @BindView(R.id.package_type)
    TextView packageNameText;

    @BindView(R.id.country_name)
    TextView countryNameText;

    @BindView(R.id.duration)
    TextView durationText;

    @BindView(R.id.search_city_name)
    TextView countrySearchName;

    @BindView(R.id.tv_budget)
    TextView selectAmount;

    /**
     * navigate to HolidayCitySearchFragment
     * */

    @OnClick(R.id.ll_flight)
    void openholidaySearch(){
        ((MainActivity)getActivity()).fragmentCallingAction(1);
    }
    @OnClick(R.id.ll_hotels)
    void openhotelSearch(){
        //   callActivity(2);
        ((MainActivity)getActivity()).fragmentCallingAction(2);
    }

    @OnClick(R.id.ll_bus)
    void openbusSearch(){
        ((MainActivity)getActivity()).fragmentCallingAction(3);
    }


    @OnClick(R.id.ll_more)
    void openMorePage(){
        // callActivity(9);
        ((MainActivity)getActivity()).fragmentCallingAction(9);    }

    @OnClick(R.id.country_name)
    void countrySearch(){


        alert = new AlertDialog.Builder(getActivity()).create();
        //alert.setTitle("Package Durati");

        RecyclerView recyclerView = new RecyclerView(getActivity());
        recyclerView.setPadding(10,10,10,10);
        commonUtils.linearLayout(recyclerView, getActivity());

        packageCountryAdapter =
                new PackageCountryAdapter(getActivity(),
                        HolidaySearchFragment.this, packageCountryLists);
        recyclerView.setAdapter(packageCountryAdapter);

        alert.setView(recyclerView);
        alert.show();
       /* intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new HolidayCitySearch(HolidaySearchFragment.this), null, true);*/
    }





    @BindView(R.id.package_list)
    RecyclerView packageListView;


    public HolidaySearchFragment()
    {

    }
    /**
     * Lode data in popup
     * */

    @OnClick(R.id.package_type)
    void selectPackage(){
        alert = new AlertDialog.Builder(getActivity()).create();
        alert.setTitle("Package Type");
        RecyclerView recyclerView = new RecyclerView(getActivity());
        recyclerView.setPadding(10,10,10,10);
        commonUtils.linearLayout(recyclerView, getActivity());

         packageAdapter = new PackageAdapter(
                getActivity(), HolidaySearchFragment.this, packageListModels);
        recyclerView.setAdapter(packageAdapter);

        alert.setView(recyclerView);
        alert.show();
        /*holidaytypedialog = new Dialog(getActivity());
        holidaytypedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        holidaytypedialog.setCancelable(true);
        holidaytypedialog.setContentView(R.layout.holiday_type_dialog);
        holidaytypedialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        TextView tv_all=holidaytypedialog.findViewById(R.id.tv_all);
        TextView tv_domestic=holidaytypedialog.findViewById(R.id.tv_domestic);
        TextView tv_international=holidaytypedialog.findViewById(R.id.tv_international);

        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packageNameText.setText("All");
                packageTypeId="";
                holidaytypedialog.dismiss();
            }
        });
        tv_domestic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packageNameText.setText("Domestic");
                packageTypeId="domestic";
                holidaytypedialog.dismiss();
            }
        });

        tv_international.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packageNameText.setText("International");
                packageTypeId="international";
                holidaytypedialog.dismiss();
            }
        });

        holidaytypedialog.show();*/
    }

    /**
     * Lode data in popup
     * */

    @OnClick(R.id.duration)
    void selectDuration(){

        holidaytypedialog = new Dialog(getActivity());
        holidaytypedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        holidaytypedialog.setCancelable(true);
        holidaytypedialog.setContentView(R.layout.duration_type_dialog);
        holidaytypedialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        TextView tv_all_durations=holidaytypedialog.findViewById(R.id.tv_all_durations);
        TextView tv_1_3=holidaytypedialog.findViewById(R.id.tv_1_3);
        TextView tv_4_7=holidaytypedialog.findViewById(R.id.tv_4_7);
        TextView tv_8_12=holidaytypedialog.findViewById(R.id.tv_8_12);
        TextView tv_12=holidaytypedialog.findViewById(R.id.tv_12);

        tv_all_durations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                durationText.setText("All Duration");
                tripDuration="";
                holidaytypedialog.dismiss();
            }
        });
        tv_1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                durationText.setText("1-3");
                tripDuration="1-3";
                holidaytypedialog.dismiss();
            }
        });

        tv_4_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                durationText.setText("4-7");
                tripDuration="4-7";
                holidaytypedialog.dismiss();
            }
        });
        tv_8_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                durationText.setText("8-12");
                tripDuration="8-12";
                holidaytypedialog.dismiss();
            }
        });
        tv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                durationText.setText("12+");
                tripDuration="12+";
                holidaytypedialog.dismiss();
            }
        });

        holidaytypedialog.show();

      /*  alert = new AlertDialog.Builder(getActivity()).create();
        alert.setTitle("Package Duration");

        RecyclerView recyclerView = new RecyclerView(getActivity());
        recyclerView.setPadding(10,10,10,10);
        commonUtils.linearLayout(recyclerView, getActivity());

         packageDurationAdapter =
                new PackageDurationAdapter(getActivity(),
                        HolidaySearchFragment.this, packageDurations);
        recyclerView.setAdapter(packageDurationAdapter);

        alert.setView(recyclerView);
        alert.show();*/
    }

    @OnClick(R.id.tv_budget)
    void selectAmount(){

        holidaytypedialog = new Dialog(getActivity());
        holidaytypedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        holidaytypedialog.setCancelable(true);
        holidaytypedialog.setContentView(R.layout.budget_type_dialog);
        holidaytypedialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        TextView tv_all_amount=holidaytypedialog.findViewById(R.id.tv_all_amount);
        TextView tv_100_500=holidaytypedialog.findViewById(R.id.tv_100_500);
        TextView tv_500_1000=holidaytypedialog.findViewById(R.id.tv_500_1000);
        TextView tv_1000_5000=holidaytypedialog.findViewById(R.id.tv_1000_5000);
        TextView tv_5000=holidaytypedialog.findViewById(R.id.tv_5000);


        tv_all_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAmount.setText("All");
                amount="";
                holidaytypedialog.dismiss();
            }
        });
        tv_100_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAmount.setText("Rs 100-500");
                amount="100-500";
                holidaytypedialog.dismiss();
            }
        });

        tv_500_1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAmount.setText("Rs 500-1000");
                amount="500-1000";
                holidaytypedialog.dismiss();
            }
        });
        tv_1000_5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAmount.setText("Rs 1000-5000");
                amount="1000-5000";
                holidaytypedialog.dismiss();
            }
        });
        tv_5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAmount.setText("Rs 5000>");
                amount="5000>";
                holidaytypedialog.dismiss();
            }
        });


        holidaytypedialog.show();

      /*  commonUtils.callDatePicker(this,HolidaySearchFragment.this,checkInDate,
                1);*/
    }

    /**
     * Search Package
     * */
    @OnClick(R.id.search_holiday)
    void searchPackage(){


       /* if(countryNameText.getText().toString().equals("")){
           commonUtils.toastShort("Please Select Location", getActivity());
        }else if(durationText.getText().toString().equals("")){
            commonUtils.toastShort("Please Select Package Duration", getActivity());
        }else {
            applicationPreference.setData(
                    applicationPreference.holidayCountryName,
                    countryNameText.getText().toString());
            applicationPreference.setData(applicationPreference.holidayCountryId,
                    countryId);

            applicationPreference.setData(
                    applicationPreference.holidayPackageName,
                    packageNameText.getText().toString());
            applicationPreference.setData(
                    applicationPreference.holidayPackageId,
                    packageId);

            applicationPreference.setData(
                    applicationPreference.holidayDuration,
                    tripDuration);

            SearchPackage searchPackage = new SearchPackage(
                    countryNameText.getText().toString(), durationText.getText().toString(), checkInDate);
            Gson gson = new Gson();
            RequestParams requestParams = new RequestParams();
            requestParams.put("package_search",gson.toJson(searchPackage));

            webServiceController.postRequest(
                    apiConstants.PACKAGE_SEARCH,
                    requestParams,
                    1);
        }*/
        if(packageNameText.getText().toString().equals("")){
            commonUtils.toastShort("Select Holiday Type", getActivity());
            return;
        }
        WebServiceController lController = new WebServiceController(getContext(), this);
           // JSONObject jsonObject=new JSONObject();
          //  jsonObject.put("holiday_type",packageTypeId);
          /*  RequestParams requestParams = new RequestParams();
            requestParams.put("country",countryId);
        requestParams.put("package_type",packageId);
        requestParams.put("duration",tripDuration);
        requestParams.put("budget",amount);*/
           // requestParams.put("package_search",jsonObject.toString());

        String param = "?country="+ countryId+"&package_type=" + packageId + "&duration=" + tripDuration + "&budget=" + amount;
        lController.getRequest(ApiConstants.PACKAGE_SEARCH_DATA + param, 1, true);

           /* webServiceController.postRequest(
                    apiConstants.PACKAGE_SEARCH_DATA,
                    requestParams,
                    1);*/

    }




    @Override
    protected int getLayoutResource() {
        return R.layout.holiday_search_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceController = new WebServiceController(getActivity(),
                HolidaySearchFragment.this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      /*  Calendar calendar = Calendar.getInstance();
        
        startDate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)
                +"-"+calendar.get(Calendar.DAY_OF_MONTH);
        notifyDate(startDate, 1);*/

        commonUtils.horizontalLayout(packageListView, getActivity());
        RequestParams params = new RequestParams();
       // webServiceController.postRequest(apiConstants.URL_PROMOCODE_LIST, params, 0);
        webServiceController.postRequest(apiConstants.HOLIDAY_ALL_TOURLIST, params, 0);
        //callPackageDurationApi();
        /*if(applicationPreference.getData(applicationPreference.holidayCountryName).length() != 0){
            countryNameText.setText(applicationPreference.getData(applicationPreference.holidayCountryName));
            countrySearchName.setText(applicationPreference.getData(applicationPreference.holidayCountryName));
            countryId = applicationPreference.getData(applicationPreference.holidayCountryId);

            packageNameText.setText(applicationPreference.getData(applicationPreference.holidayPackageName));
            packageId = applicationPreference.getData(applicationPreference.holidayPackageId);

            durationText.setText(applicationPreference.getData(applicationPreference.holidayDuration));
            tripDuration = applicationPreference.getData(applicationPreference.holidayDuration);
        }*/
    }

    @Override
    public void getResponse(String response, int flag) {
        switch (flag)
        {
            case  0:
                packageResponse = response;
                packageListModels.clear();
                packageCountryLists.clear();;
                try {
                    JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("countries");
                        JSONArray jsonArray1 = jsonObject.getJSONArray("package_types");
                        packageListModels.add(new PackageListModel
                                (
                                        "All Package Types",
                                        "",
                                        "",
                                        ""));
                        int i = 0;
                        while (i < jsonArray1.length())
                        {

                            JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                            packageListModels.add(new PackageListModel(
                                    jsonObject1.getString("package_types_name"),
                                    jsonObject1.getString("package_types_id"),
                                    "",
                                    ""));
                            i++;
                        }
                    int j = 0;
                        packageCountryLists.add(new PackageCountryList("All",""));

                    while (j < jsonArray.length())
                    {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                        packageCountryLists.add(new PackageCountryList(
                                jsonObject1.getString("country_name"),
                                jsonObject1.getString("package_country")));
                        j++;
                    }
                        //packageAdapter.notifyDataSetChanged();
                       /* try {
                            packageId = packageListModels.get(0).getPackageTypeId();
                            packageNameText.setText(packageListModels.get(0).getPackageName());
                        } catch (Exception e1) {
                            packageNameText.setText("");
                        }*/


                    }
                catch (Exception e)
                {
                    e.printStackTrace();

                }


              /*  packageModels.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1)
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject promoObj = jsonArray.getJSONObject(i);
                            packageModels.add(new PromoCodeInfo(promoObj.getString("module"),
                                    promoObj.getString("promo_code"),
                                    promoObj.getString("description"),
                                    promoObj.getString("expiry_date"),
                                    promoObj.getString("status"),
                                    "https://" +
                                            promoObj.getString("promo_code_image")));
                            Log.e("PromoImage", "https://" +
                                    promoObj.getString("promo_code_image"));
                        }
                        internationalPackageLanding = new
                                InternationalPackageLanding(getActivity(), packageModels);
                        packageListView.setAdapter(internationalPackageLanding);

                    }
                    }
                catch (Exception e)
                {
                    e.printStackTrace();

                }
*/


                break;
            case 1:
                intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                        new HolidayListingFragment(
                                response, packageNameText.getText().toString(),
                                tripDuration), null, true);
                break;
            case 2:
                packageResponse = response;
                packageListModels.clear();
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    int i=0;
                    while (i<jsonArray.length()){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        packageListModels.add(new PackageListModel(
                                jsonObject.getString("package_types_name"),
                                jsonObject.getString("package_types_id"),
                                jsonObject.optString("Duration"),
                                jsonObject.optString("Country_id")));
                        i++;
                    }
                    //packageAdapter.notifyDataSetChanged();
                    try {
                        packageId=packageListModels.get(0).getPackageTypeId();
                        packageNameText.setText(packageListModels.get(0).getPackageName());
                    }catch (Exception e1){
                        packageNameText.setText("");
                    }
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("package_type_id", packageListModels.get(0).getPackageTypeId());
                    webServiceController.postRequest(
                            apiConstants.PACKAGE_DURATION,
                            requestParams,
                            3);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 3:
                durationRespone = response;
                packageDurations.clear();
                try{
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("days");
                    int i=0;
                    while (i<jsonArray.length()){
                        packageDurations.add(new PackageDuration(jsonArray.getString(i)));
                        /*JSONObject jsonObject = jsonArray.getJSONObject(i);
                        packageDurations.add(new PackageDuration(
                                jsonObject.getString("min_days")
                                        +"-"+
                                        jsonObject.getString("max_days")));*/
                        i++;
                    }
                    //packageDurationAdapter.notifyDataSetChanged();
                    try {
                        tripDuration=packageDurations.get(0).getPackageDuration();
                        durationText.setText(packageDurations.get(0).getPackageDuration());
                    }catch (Exception e1){
                        durationText.setText("");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
            case 4:

                break;
        }
    }

    /**n
     * On getting country id call package List
     * */
    @Override
    public void countryId(String countryId, String countryName) {
        this.countryId = countryId;
        countryNameText.setText(countryName);
        countrySearchName.setText(countryName);

       /* RequestParams requestParams = new RequestParams();
        webServiceController.postRequest(
                apiConstants.PACKAGE_TYPE_LIST,
                requestParams,
                2);*/
    }

    public void updatePackageDetails(String packageName, String packageTypeId,
                                     String duration) {
        alert.dismiss();
        this.packageId = packageTypeId;
        packageNameText.setText(packageName);

       /* RequestParams requestParams = new RequestParams();
        requestParams.put("package_type_id", packageId);
        webServiceController.postRequest(
                apiConstants.PACKAGE_DURATION,
                requestParams,
                3);*/
    }

    public void updateCountry(String packageCountry,String CounrtyId) {
        alert.dismiss();
        countryNameText.setText(packageCountry);
        this.countryId = CounrtyId;
      //  tripDuration = packageDuration;
    }

    public void updateDuration(String packageDuration) {
        alert.dismiss();
        durationText.setText(packageDuration);
        tripDuration = packageDuration;
    }

   /* @Override
    public void notifyDate(String dateValue, Integer datePickerValue) {
        String endDate_str;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date endDate = dateFormat.parse(dateValue);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH);
            endDate_str = sdf.format(endDate);
            checkInDate = dateValue;
            dateOfJourney.setText(endDate_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }*/

    public void callPackageDurationApi()
    {
        RequestParams requestParams = new RequestParams();
        webServiceController.postRequest(
                apiConstants.PACKAGE_DURATION,
                requestParams,
                3);
    }


}