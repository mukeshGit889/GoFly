package com.gofly;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gofly.flight.fragment.FlightDetailFragment;
import com.gofly.flight.fragment.PaymentFlight;
import com.gofly.main.fragment.BaseFragment;
import com.gofly.utils.Global;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentTypeFragment extends BaseFragment implements WebInterface {
    Integer status = 0;
    String url = null;
    String payment_gateway_url="";
     String total_amount="";
     String walletSelectType="";
    int frameType;
    WebServiceController wsc;
    RequestParams params=new RequestParams();
    RequestParams params1=new RequestParams();

    @BindView(R.id.tv_amt)
    TextView tv_amt;

    @BindView(R.id.linear_wallet)
    LinearLayout linear_wallet;

    @BindView(R.id.tv_walletAmt)
    TextView tv_walletAmt;

    @BindView(R.id.tv_payPaymentGatway)
    TextView tv_payPaymentGatway;

    @BindView(R.id.proceed_pay)
    TextView proceed_pay;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.linear_wallet)
    void selectWallet()
    {
        walletSelectType="on";
        linear_wallet.setBackground(getResources().getDrawable(R.drawable.bg_orangeaccept));
        tv_payPaymentGatway.setBackground(getResources().getDrawable(R.drawable.bg_accept));
        tv_walletAmt.setTextColor(getResources().getColor(R.color.white));

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.tv_payPaymentGatway)
    void selectPymentGateway()
    {
        walletSelectType="off";
        tv_payPaymentGatway.setBackground(getResources().getDrawable(R.drawable.bg_orangeaccept));
        linear_wallet.setBackground(getResources().getDrawable(R.drawable.bg_accept));
        tv_walletAmt.setTextColor(getResources().getColor(R.color.blue_text));
    }



    @OnClick(R.id.proceed_pay)
    void proceedToPay()
    {
        if (walletSelectType.equals(""))
        {
            Toast.makeText(getContext(),"Please Select Payment Type",Toast.LENGTH_SHORT).show();
            return;
        }

    if(frameType==1)
        {
            if(payment_gateway_url.equals(""))
            {
                if (applicationPreference.getData("login_flag").equals("true"))
                {
                    params.put("wallet_bal",walletSelectType);
                    params.put("user_id", applicationPreference.getData(applicationPreference.userId));
                }
                else
                {
                    params.put("wallet_bal", "off");
                    params.put("user_id", "") ;

                }

                Log.e("fl_pg_params",params.toString());
                wsc.postRequest(apiConstants.FLIGHT_BOOKING_URL,params,2);
            }


        }
        else if(frameType==2)
        {
            if (applicationPreference.getData("login_flag").equals("true"))
            {
                params.put("wallet_bal",walletSelectType);
                params.put("user_id", applicationPreference.getData(applicationPreference.userId));

            }
            else
            {
                params.put("wallet_bal", "off");
                params.put("user_id", "") ;

            }
            Log.e("ho_pg_params",params.toString());

            wsc.postRequest(apiConstants.HOTEL_ROOM_PRE_BOOK,params,3);
        }
        else if(frameType==3)
        {

        if (applicationPreference.getData("login_flag").equals("true"))
        {
          //  params.put("wallet_bal",walletSelectType);
           // params.put("user_id", applicationPreference.getData(applicationPreference.userId));

        }
        else
        {
          //  params.put("wallet_bal", "off");
           // params.put("user_id", "") ;
        }
            Log.e("bus_pg_params",params.toString());
            wsc.postRequest(apiConstants.BUS_PRE_BOOK,params,4);
        }

    else if(frameType==4)
    {

        if (applicationPreference.getData("login_flag").equals("true"))
        {
            params.put("wallet_bal",walletSelectType);
            params.put("user_id", applicationPreference.getData(applicationPreference.userId));

        }
        else
        {
            params.put("wallet_bal", "off");
            params.put("user_id", "") ;

        }
        Log.e("transfer_pg_params",params.toString());
        wsc.postRequest(apiConstants.TRANSFERS_PRE_BOOKING,params,5);
    }
    else if(frameType==5)
    {

        if (applicationPreference.getData("login_flag").equals("true"))
        {

            params.put("wallet_bal",walletSelectType);
            params.put("user_id", applicationPreference.getData(applicationPreference.userId));

        }
        else
        {
            params.put("wallet_bal", "off");
            params.put("user_id", "") ;

        }
        Log.e("activities_pg_params",params.toString());
        //lController.postRequest(ApiConstants.PRE_BOOKING, params, 2);
        wsc.postRequest(apiConstants.PRE_BOOKING,params,6);
    }

     /*   if (paymentType.equals("2"))
        {


            if (frameType==1)
            {
                intentAndFragmentService.fragmentDisplay(getActivity(),
                        R.id.support_frame, new PaymentFlight(payment_gateway_url), null, false);
            }
            if (frameType==2)
            {
                intentAndFragmentService.fragmentDisplay(getActivity(),
                        R.id.main_frame, new PaymentFlight(payment_gateway_url), null, false);
            }


        }*/

    }




    public PaymentTypeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public PaymentTypeFragment(RequestParams params,String totalAmount,int frameType)
    {
        this.params=params;
      // this. payment_gateway_url = url;
       this.total_amount=totalAmount;
       this.frameType=frameType;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_payment_type;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_amt.setText(Global.currencySymbol+" "+total_amount);
        wsc = new WebServiceController(getActivity(), PaymentTypeFragment.this);

        if (applicationPreference.getData("login_flag").equals("true"))
        {
            params1.put("user_id", applicationPreference.getData(applicationPreference.userId));

        }
        else
        {
            params1.put("user_id", "") ;
        }
       // params.put("user_id", applicationPreference.getData(applicationPreference.userId));
        wsc.postRequest(apiConstants.WALLET_BALANCE,params1,1);
    }

    @Override
    public void getResponse(String response, int flag) {
        Log.e("payment page response",response);
        switch (flag) {
            case 1:

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status") == 1)
                    {
                        String walletAmount=jsonObject.getString("wallet_ballence");
                        tv_walletAmt.setText(Global.currencySymbol+" "+walletAmount);

                      //  Toast.makeText(getContext(),walletAmount,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 2:

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status") == 1)
                    {    status = 1;
                        JSONObject dataobj=jsonObject.getJSONObject("data");
                        url =dataobj.getString("return_url");
                    }else {
                        status = 0;
                        commonUtils.toastShort("Failed to process request", getActivity());
                    }
                }catch (Exception e){
                    commonUtils.toastShort(e.getMessage(), getActivity());
                }

                if(status != 0){

                    /*intentAndFragmentService.fragmentDisplay(getActivity(),
                            R.id.support_frame, new PaymentTypeFragment(url,totalFinal_Amount,frameType), null, true);*/
                    intentAndFragmentService.fragmentDisplay(getActivity(),
                    R.id.support_frame, new PaymentFlight(url), null, false);
                }
                break;
            case 3:
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status") == 1){
                        status = 1;
                        JSONObject dataobj=jsonObject.getJSONObject("data");
                        url =dataobj.getString("return_url");
                    }else {
                        status = 0;
                        commonUtils.toastShort("Failed to process request", getActivity());
                    }
                }catch (Exception e){
                    commonUtils.toastShort(e.getMessage(), getActivity());
                }

                if(status != 0)
                {

                   /* intentAndFragmentService.fragmentDisplay(getActivity(),
                            R.id.main_frame, new PaymentTypeFragment(url,hotel_grandtotal_price,frameType), null, true);*/


                    intentAndFragmentService.fragmentDisplay(getActivity(),
                    R.id.main_frame, new PaymentFlight(url), null, false);
                }
                break;

            case 4:
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status") == 1){
                        status = 1;
                        JSONObject dataobj=jsonObject.getJSONObject("data");
                        url = dataobj.getString("return_url");
                    }else {
                        status = 0;
                        commonUtils.toastShort("Failed to process request", getActivity());
                    }
                }catch (Exception e){
                    commonUtils.toastShort(e.getMessage(), getActivity());
                }

                if(status != 0){
                    intentAndFragmentService.fragmentDisplay(getActivity(),
                            R.id.main_frame, new PaymentFlight(url), null, true);
                }
                break;

            case 5:
                String url=null;
                int status=0;
                Log.e("response", response);

                try{
                    JSONObject dataObj;
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status") == 1){
                        status = 1;
                        dataObj=jsonObject.getJSONObject("data");
                        url = dataObj.getString("return_url");
                    }else {
                        status = 0;
                        commonUtils.toastShort("Failed to process request", getActivity());
                    }
                }catch (Exception e){
                    commonUtils.toastShort(e.getMessage(), getActivity());
                }

                if(status != 0) {
                    intentAndFragmentService.fragmentDisplay(getActivity(),
                            R.id.main_frame, new PaymentFlight(url), null, false);
                }
                break;

            case 6:
                try {

                    JSONObject lJsonObject = new JSONObject(response);

                    if (lJsonObject.getInt("status") == 1) {
                        JSONObject lData = lJsonObject.getJSONObject("data");
                        String Url = lData.getString("payment_url");
                        Intent intent;
                        intent = new Intent(getContext().getApplicationContext(), WebViewActivity.class);
                        intent.putExtra("weburl", Url);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                        LandingActivityNew.getInstance().walletApiCall();

                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_type, container, false);
    }*/
}
