package com.gofly;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofly.carhire.CarHireActivity;
import com.gofly.guessit.GuessITActivity;
import com.gofly.interfaces.NotifyRefresh;
import com.gofly.main.activity.CommonViewActivity;
import com.gofly.main.activity.WalletActivity;
import com.gofly.more.MoreActivity;
import com.gofly.myaccount.MyAccountPageActivity;
import com.gofly.main.adapter.landingAdapter.TopFlightAdapter;
import com.gofly.model.parsingModel.landingPage.TopAirlinesModel;
import com.facebook.login.LoginManager;
import com.gofly.model.parsingModel.landingPage.TopFlightModel;
import com.gofly.myaccount.wallet.WalletTransactionActivity;
import com.loopj.android.http.RequestParams;
import com.gofly.main.activity.BaseActivity;
import com.gofly.main.activity.LoginActivity;
import com.gofly.main.activity.MainActivity;
import com.gofly.main.activity.MyBookingsActivity;
import com.gofly.main.adapter.CurrencyAdapter;
import com.gofly.main.adapter.landingAdapter.TopAirlineAdapter;
import com.gofly.main.adapter.landingAdapter.InternationalPackageLanding;
import com.gofly.main.adapter.landingAdapter.TopHotelAdapter;
import com.gofly.model.parsingModel.CurrencyConverter;
import com.gofly.model.parsingModel.PromoCodeInfo;
import com.gofly.model.parsingModel.landingPage.TopHotelModel;
import com.gofly.utils.Global;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import butterknife.BindView;
import butterknife.OnClick;

public class LandingActivityNew extends BaseActivity implements WebInterface, NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.OnConnectionFailedListener {
    private static LandingActivityNew instance;

    RequestParams params=new RequestParams();
    @OnClick(R.id.drawer)
    void openDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }
    ArrayList<CurrencyConverter> currencyList=new ArrayList<CurrencyConverter>();
    Dialog dialog;
    @OnClick(R.id.ll_currency_sel)
    void selectCountry()
    {
        RequestParams requestParams = new RequestParams();
        webServiceController.postRequest(apiConstants.CURRENCY_CONVERTER,requestParams,2);
        }
    TextView  tv_userName;
    ImageView  iv_userImage;
    @BindView(R.id.login_icon) ImageView login_icon;
    @BindView(R.id.currency_name) TextView currency_name;
    @BindView(R.id.currency_icon) ImageView currency_icon;
    @BindView(R.id.package_list) RecyclerView packageListView;
    @BindView(R.id.top_hotel) RecyclerView topHotelListView;
    @BindView(R.id.rv_topAirlineList) RecyclerView rv_topAirlineList;
    @BindView(R.id.rv_topFlight) RecyclerView rv_topFlight;
    @BindView(R.id.tv_nameFirst) TextView tv_nameFirst;
    @BindView(R.id.tv_walletAmt) TextView tv_walletAmt;
    @OnClick(R.id.cv_banner_one)
    void openBannerPage(){
        intentAndFragmentService.intentDisplay(this, GuessITActivity.class, null);

    }
    @OnClick(R.id.cv_banner_two)
    void openExclusivePage(){
        intentAndFragmentService.intentDisplay(this, GuessITActivity.class, null);

    }

    @OnClick(R.id.flight_search)
    void flightSearch(){
        callActivity(1);
    }

    @OnClick(R.id.hotel_search)
    void hotelSearch(){
        callActivity(2);
    }

    @OnClick(R.id.bus_search)
    void busSearch(){
        callActivity(3);
    }

    @OnClick(R.id.holiday_search)
    void holidaySearch(){
        callActivity(4);
    }

    @OnClick(R.id.transfers_search)
    void transferSearch(){
        callActivity(5);
    }

    @OnClick(R.id.activities_search)
    void activitiesSearch(){
        callActivity(7);

    }
    @OnClick(R.id.car_hire)
    void carHire(){
        callActivity(10);
       // intentAndFragmentService.intentDisplay(this, CarHireActivity.class, null);

    }
    @OnClick(R.id.guessIT)
    void guessIT(){
        // callActivity(10);
   /*     Intent intent;
        intent = new Intent(LandingActivityNew.this, WebViewActivity.class);
        intent.putExtra("weburl", "https://guess-it.pro/login");
         startActivity(intent);*/

        intentAndFragmentService.intentDisplay(this, GuessITActivity.class, null);

        //https://goflyx.com/
    }
    @OnClick(R.id.ll_flight)
    void openflightSearch(){
        callActivity(1);
    }

    @OnClick(R.id.ll_hotels)
    void openhotelSearch(){
        callActivity(2);
    }

    @OnClick(R.id.ll_bus)
    void openbusSearch(){
        callActivity(3);
    }

    @OnClick(R.id.ll_holidays)
    void openholidaySearch(){
        callActivity(4);
    }

    @OnClick(R.id.ll_more)
    void openMorePage(){
        callActivity(9);
      //  intentAndFragmentService.intentDisplay(this, MoreActivity.class, bundle);
    }
    @OnClick(R.id.tv_nameFirst)
    void openMyAccountMenu(){
        if(applicationPreference.getData(applicationPreference.login_flag).equals("true")){
            intentAndFragmentService.intentDisplay(this, MyAccountPageActivity.class, bundle);



        }
        else {
            intentAndFragmentService.intentDisplay(LandingActivityNew.this,LoginActivity.class,null);
            finish();

            //commonUtils.toastShort(getString(R.string.please_login_first),getApplicationContext());
        }
    }
    @OnClick(R.id.login_icon)
    void openMyAccount()
    {
        if(applicationPreference.getData(applicationPreference.login_flag).equals("true")){
            intentAndFragmentService.intentDisplay(this, MyAccountPageActivity.class, bundle);



        }
        else {
            intentAndFragmentService.intentDisplay(LandingActivityNew.this,LoginActivity.class,null);
            finish();

            //commonUtils.toastShort(getString(R.string.please_login_first),getApplicationContext());
        }
    }
   public  void logOut()
   {
        if(applicationPreference.getData(applicationPreference.login_type).equals("google"))
        {
            GoogleSignInOptions options =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail().build();
            GoogleApiClient mGoogleApiClient;
            mGoogleApiClient = new GoogleApiClient.Builder(LandingActivityNew.this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                    .build();
            if (mGoogleApiClient.isConnected()) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mGoogleApiClient.disconnect();
                mGoogleApiClient.connect();
            }
        }else if(applicationPreference.getData(applicationPreference.login_type).equals("facebook"))
        {
            LoginManager.getInstance().logOut();
        }
        applicationPreference.setData(
                applicationPreference.login_flag,
                "false");
        applicationPreference.clearAll();
        tv_userName.setText("");
        iv_userImage.setImageDrawable(getResources().getDrawable(R.drawable.user_dummy));
        intentAndFragmentService.intentDisplay(LandingActivityNew.this,
                LoginActivity.class, null);
        finish();
    }


    private void callActivity(int i)
    {
        bundle = new Bundle();
        bundle.putInt("calling_fragment",i);
        intentAndFragmentService.intentDisplay(this, MainActivity.class, bundle);
    }

    @OnClick(R.id.linear_wallet)
    void openWalletPage(){
        if(applicationPreference.getData(applicationPreference.login_flag).equals("true")){
            intentAndFragmentService.intentDisplay(this, WalletTransactionActivity.class, bundle);
        }else{
            intentAndFragmentService.intentDisplay(this, LoginActivity.class, bundle);

        }
    }


    Bundle bundle;
    List<PromoCodeInfo> packageModels = new ArrayList<PromoCodeInfo>();
    List<TopHotelModel> topHotelModels = new ArrayList<TopHotelModel>();
    List<TopAirlinesModel> topAirlinesList = new ArrayList<TopAirlinesModel>();
    List<TopFlightModel> topFlightModelList = new ArrayList<TopFlightModel>();

    TopAirlineAdapter topAirlineAdapter;
    InternationalPackageLanding internationalPackageLanding;
    TopHotelAdapter topHotelAdapter;
    TopFlightAdapter topFlightAdapter;
    WebServiceController webServiceController;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_landing_new;
    }
    public static LandingActivityNew getInstance() {
        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        currency_name.setText(Global.currencySymbol);
        instance = this;
        switch (Global.currencySymbol){
            case "AUD" : currency_icon.setImageResource(R.drawable.flag_australia);
                break;
            case "SAR" : currency_icon.setImageResource(R.drawable.flag_saudi_arabia);
                break;
            case "GBP" : currency_icon.setImageResource(R.drawable.flag_united_kingdom);
                break;
            case "INR" : currency_icon.setImageResource(R.drawable.flag_india);
                break;
            case "USD" : currency_icon.setImageResource(R.drawable.flag_united_states_of_america);
                break;
            case "CAD" : currency_icon.setImageResource(R.drawable.cad);
                break;
            case "BDT" : currency_icon.setImageResource(R.drawable.bdt);
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem nav_logout = menu.findItem(R.id.nav_logout);
        if(applicationPreference.getData(applicationPreference.login_flag).equals("true"))
        {
            nav_logout.setTitle(R.string.logout);

                String nameFirst=applicationPreference.getData(applicationPreference.userName);
                tv_nameFirst.setText(String.valueOf(nameFirst.charAt(0)));
                tv_nameFirst.setVisibility(View.VISIBLE);
                login_icon.setVisibility(View.GONE);
            params.put("user_id", applicationPreference.getData(applicationPreference.userId));

        }
        else
        {
            nav_logout.setTitle(R.string.login);
            tv_nameFirst.setVisibility(View.GONE);
            login_icon.setVisibility(View.VISIBLE);
        }

        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        tv_userName = (TextView)header.findViewById(R.id.tv_userName);
        iv_userImage = (ImageView)header.findViewById(R.id.iv_userImage);
        tv_userName.setText(applicationPreference.getData(applicationPreference.userName));
        if(!applicationPreference.getData(applicationPreference.user_dp).equals("") && applicationPreference.getData(applicationPreference.user_dp)!=null)
        {
                    Picasso.get()
                    .load(applicationPreference.getData(applicationPreference.user_dp)).placeholder(R.drawable.user_dummy)
                    .into(iv_userImage);
        }

        commonUtils.horizontalLayout(packageListView, this);
        internationalPackageLanding = new InternationalPackageLanding(this, packageModels);
        packageListView.setAdapter(internationalPackageLanding);

        commonUtils.horizontalLayout(rv_topAirlineList, this);
        topAirlineAdapter = new TopAirlineAdapter(this, topAirlinesList);
        rv_topAirlineList.setAdapter(topAirlineAdapter);

        commonUtils.horizontalLayout(topHotelListView, this);
        topHotelAdapter = new TopHotelAdapter(this, topHotelModels);
        topHotelListView.setAdapter(topHotelAdapter);

        commonUtils.horizontalLayout(rv_topFlight, this);
        topFlightAdapter = new TopFlightAdapter(this, topFlightModelList);
        rv_topFlight.setAdapter(topFlightAdapter);

       webServiceController = new WebServiceController(this, LandingActivityNew.this);
        webServiceController.getRequest(apiConstants.HOME_PAGE_API, 1, true);


      //  webServiceController.postRequest(apiConstants.WALLET_BALANCE,params,6);
     /*   try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.travojet",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
*/



    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {

        int id = item.getItemId();
        Intent intent;
        intent=new Intent(getApplicationContext(),CommonViewActivity.class);


        if (id == R.id.nav_home)
        {

        }
        else if (id == R.id.nav_myAccount)
        {

            if(applicationPreference.getData(applicationPreference.login_flag).equals("true")){
                intentAndFragmentService.intentDisplay(this, MyAccountPageActivity.class, bundle);
          /*  if(applicationPreference.getData(applicationPreference.login_flag).equals("true")){
                intentAndFragmentService.intentDisplay(this, MyAccountActivity.class, bundle);*/


            }
            else {
                commonUtils.toastShort(getString(R.string.please_login_first),getApplicationContext());
            }

        } else if (id == R.id.nav_myBooking)
        {
            if(applicationPreference.getData(applicationPreference.login_flag).equals("true")){
                intentAndFragmentService.intentDisplay(this, MyBookingsActivity.class, bundle);

            }else {
                commonUtils.toastShort(getString(R.string.please_login_first),getApplicationContext());
            }

        }
        else if (id == R.id.nav_abountUs)
        {
            intent.putExtra("page_type",1);
            startActivity(intent);
        }
        else if (id == R.id.nav_contactUs)
        {
            intent.putExtra("page_type",4);
            startActivity(intent);
        }
        else if (id == R.id.nav_tandc)
        {
            intent.putExtra("page_type",3);
            startActivity(intent);

        }
        else if (id == R.id.nav_privacy)
        {
            intent.putExtra("page_type",2);
            startActivity(intent);
        }
        else if (id == R.id.nav_logout)
        {
            logOut();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void getResponse(String response, int flag)
    {
        try {
            if (flag == 1) {
                topHotelModels.clear();
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status") == 1)
                {
                    JSONObject dataObj = jsonObject.getJSONObject("data");
                    JSONObject hotelObj = dataObj.getJSONObject("hotel");

                    Random r = new Random();
                    int Low = 99;
                    int High = 500;


                    JSONArray hotelArray = hotelObj.getJSONArray("list");
                    int k = 0;
                    while (k < hotelArray.length()) {
                        int Result = r.nextInt(High - Low) + Low;
                        JSONObject hotelParsing = hotelArray.getJSONObject(k);
                        topHotelModels.add(new TopHotelModel(
                                hotelParsing.getString("city_name"),
                                //"https://goflyx.com/extras/custom/TMX1512291534825461/images/" + hotelParsing.getString("image"),
                                "https://goflyx.com" + hotelObj.getString("img_url")+hotelParsing.getString("image"),
                                hotelParsing.getString("cache_hotels_count"),
                                hotelParsing.getString("origin")));
                        k++;
                    }

                    JSONObject flightObj = dataObj.getJSONObject("flight");

                    Random r1 = new Random();
                    int Low1 = 99;
                    int High1 = 500;

                    JSONArray flightArray = flightObj.getJSONArray("list");
                    int l = 0;
                    while (l < flightArray.length()) {
                        int Result = r1.nextInt(High1 - Low1) + Low1;
                        JSONObject flightParsing = flightArray.getJSONObject(l);
                        topFlightModelList.add(new TopFlightModel(
                                flightParsing.getString("origin"),
                                flightParsing.getString("from_airport_code"),
                                flightParsing.getString("from_airport_name"),
                                flightParsing.getString("to_airport_code"),
                                flightParsing.getString("to_airport_name"),
                                //"https://goflyx.com/extras/custom/TMX1512291534825461/images/" + flightParsing.getString("image")
                                "https://goflyx.com" + flightObj.getString("img_url")+flightParsing.getString("image")));
                        l++;
                    }



                }
                topFlightAdapter.notifyDataSetChanged();
                topHotelAdapter.notifyDataSetChanged();
                RequestParams params = new RequestParams();
                webServiceController.postRequest(apiConstants.URL_PROMOCODE_LIST, params, 3);

            }
            if (flag == 2)
            {
                currencyList.clear();

                JSONObject jsonObject = new JSONObject(response);
                JSONArray currArr = jsonObject.getJSONArray("converter");
                for (int i = 0; i < currArr.length(); i++) {
                    if (currArr.getJSONObject(i).getString("status").equals("1")) {
                        currencyList.add(new CurrencyConverter(
                                currArr.getJSONObject(i).getString("id"),
                                currArr.getJSONObject(i).getString("status"),
                                currArr.getJSONObject(i).getString("country"),
                                currArr.getJSONObject(i).getString("value"),
                                currArr.getJSONObject(i).getString("currency_symbol")
                        ));
                    }

                }
                showCurrencyDialog();
            }
            if (flag == 3) {

                packageModels.clear();

                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status") == 1) {
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
                }
                internationalPackageLanding.notifyDataSetChanged();
                RequestParams params = new RequestParams();
                webServiceController.postRequest(apiConstants.TOP_AIRLINES, params, 4);
            }

            if (flag == 4) {

                topAirlinesList.clear();

                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status") == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject promoObj = jsonArray.getJSONObject(i);
                        topAirlinesList.add(new TopAirlinesModel(promoObj.getString("airline_name"),
                                apiConstants.URL + promoObj.getString("logo")));
                    }
                }
                topAirlineAdapter.notifyDataSetChanged();
             //   RequestParams params = new RequestParams();
              //  webServiceController.postRequest(apiConstants.HOME_UPDATE_CHECK_API, params, 5);
                if(applicationPreference.getData(applicationPreference.login_flag).equals("true"))
                {
                    webServiceController.postRequest(apiConstants.WALLET_BALANCE,params,6);

                }

            }


            if (flag == 5)
            {
                try {

                    JSONObject res = new JSONObject(response);
                    if (res.getInt("status") == 1) {
                        int version = res.getJSONObject("data").getInt("app_version_android");

                        try {
                            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            Log.e("Api version response", version + "   system version response " + pInfo.versionCode);
                            if (version > pInfo.versionCode) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                                dialog.setTitle(getResources().getString(R.string.update));
                                dialog.setMessage(getResources().getString(R.string.update_msg));
                                dialog.setCancelable(false);

                                //Setting message manually and performing action on button click
                                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                        } catch (android.content.ActivityNotFoundException anfe) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                        }
                                        finish();
                                    }
                                })
                                       /* .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //  Action for 'NO' Button
                                                dialog.cancel();
                                            }
                                        })*/;
                                //Creating dialog box
                                AlertDialog alert = dialog.create();
                                //Setting the title manually
                                alert.show();
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }
            if(flag==6)
            {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.getInt("status") == 1)
                {
                    String walletAmount="";
                    walletAmount=jsonObject.getString("wallet_ballence");
                    tv_walletAmt.setText(Global.currencySymbol+" "+walletAmount);



                    //  Toast.makeText(getContext(),walletAmount,Toast.LENGTH_SHORT).show();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        commonUtils.toastShort(connectionResult.toString(),getApplicationContext());
    }

    private void showCurrencyDialog(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.select_currency_layout);
        RecyclerView rv_currency=dialog.findViewById(R.id.rv_currency);
        commonUtils.linearLayout(rv_currency,this);
        CurrencyAdapter currencyAdapter=new CurrencyAdapter(this,LandingActivityNew.this,currencyList,1);
        rv_currency.setAdapter(currencyAdapter);

        dialog.show();
    }

    public void setCurrency(String currSymbol,String currCountry,String currValue){
        dialog.dismiss();
        currency_name.setText(currCountry);
        switch (currCountry){
            case "AUD" : currency_icon.setImageResource(R.drawable.flag_australia);
                break;
            case "SAR" : currency_icon.setImageResource(R.drawable.flag_saudi_arabia);
                break;
            case "GBP" : currency_icon.setImageResource(R.drawable.flag_united_kingdom);
                break;
            case "INR" : currency_icon.setImageResource(R.drawable.flag_india);
                break;
            case "USD" : currency_icon.setImageResource(R.drawable.flag_united_states_of_america);
                break;
            case "CAD" : currency_icon.setImageResource(R.drawable.cad);
                break;
            case "BDT" : currency_icon.setImageResource(R.drawable.bdt);
                break;
            default:
                break;
        }
        Global.currencySymbol=currCountry;
        Global.currencyValue=currValue;
        Log.e("data",Global.currencySymbol);
    }

    public  void walletApiCall()
    {
        if(applicationPreference.getData(applicationPreference.login_flag).equals("true"))
        {
            webServiceController.postRequest(apiConstants.WALLET_BALANCE,params,6);

        }

    }


}
