package com.gofly.myaccount.wallet;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.main.activity.BaseActivity;
import com.gofly.utils.Global;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class RechargeSuccessfulActivity extends BaseActivity implements WebInterface {
   boolean apiFlag=false;
    WebServiceController wsc;
    String webUrl;
    RequestParams params=new RequestParams();
    String walletAmount="";
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    @BindView(R.id.tv_walletamount)
    TextView tv_walletamount;
    @BindView(R.id.back_button)
    ImageView back_button;

    @BindView(R.id.tv_rechargedetails)
    TextView tv_rechargedetails;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.tv_referenceId)
    TextView tv_referenceId;

    @BindView(R.id.tv_nameFirst)
    TextView tv_nameFirst;

    @OnClick(R.id.back_button)
    void goBackPage()
    {
        intentAndFragmentService.intentDisplay(RechargeSuccessfulActivity.this, WalletTransactionActivity.class, null);
        finish();
    }
    @OnClick(R.id.tv_rechargedetails)
    void goToTransactionPage()
    {
        intentAndFragmentService.intentDisplay(RechargeSuccessfulActivity.this, WalletTransactionActivity.class, null);
        finish();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recharge_successful;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text_toolbar.setText("Wallet Transaction");
        webUrl=getIntent().getExtras().getString("weburl");

        wsc = new WebServiceController(this, RechargeSuccessfulActivity.this);
        if (applicationPreference.getData("login_flag").equals("true"))
        {
            String nameFirst=applicationPreference.getData(applicationPreference.userName);
            tv_nameFirst.setText(String.valueOf(nameFirst.charAt(0)));
            tv_name.setText(applicationPreference.getData(applicationPreference.userName)+" "+applicationPreference.getData(applicationPreference.userLastName));
            tv_email.setText(applicationPreference.getData(applicationPreference.userEmail));
        }
        if (applicationPreference.getData("login_flag").equals("true"))
        {
            params.put("user_id", applicationPreference.getData(applicationPreference.userId));

        }
        else
        {
            params.put("user_id", "") ;
        }

        wsc.postRequest(apiConstants.WALLET_BALANCE,params,1);
      //  setContentView(R.layout.activity_recharge_successful);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

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
                        walletAmount=jsonObject.getString("wallet_ballence");
                        tv_walletamount.setText(Global.currencySymbol+" "+walletAmount);
                        if (!apiFlag)
                        {
                            wsc.getRequest(webUrl,2,true);

                        }

                        //  Toast.makeText(getContext(),walletAmount,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1=jsonObject.getJSONObject("request_params");
                    tv_referenceId.setText(jsonObject1.getString("txnid"));
                    apiFlag=true;
                    wsc.postRequest(apiConstants.WALLET_BALANCE,params,1);




                        //  Toast.makeText(getContext(),walletAmount,Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;


        }
    }
}
