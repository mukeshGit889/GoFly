package com.gofly.myaccount.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gofly.LandingActivityNew;
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

public class RechargeWalletActivity extends BaseActivity implements WebInterface {

    String rechargeAmount="";

    WebServiceController wsc;
    RequestParams params=new RequestParams();

    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    @BindView(R.id.tv_walletamount)
    TextView tv_walletamount;
    @BindView(R.id.back_button)
    ImageView back_button;

    @BindView(R.id.tv_rechrge)
    TextView tv_rechrge;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.et_amount)
    EditText et_amount;

    @BindView(R.id.tv_nameFirst)
    TextView tv_nameFirst;

    @OnClick(R.id.back_button)
    void goBackPage()
    {
        intentAndFragmentService.intentDisplay(RechargeWalletActivity.this, WalletTransactionActivity.class, null);
        finish();
    }

    @OnClick(R.id.tv_rechrge)
    void OpenWalletRechrgePage()
    {

        rechargeAmount=et_amount.getText().toString();
        if (rechargeAmount.equals(""))
        {
            commonUtils.toastShort("Please enter Amount",RechargeWalletActivity.this);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", applicationPreference.getData(applicationPreference.userId));
            jsonObject.put("recharge_amount", rechargeAmount);
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("wallet_recharge",jsonObject);
            params.put("recharge_wallet", jsonObject1.toString());
            wsc.postRequest(
                    apiConstants.ADD_WALLETAMOUNT,
                    params,
                    1);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recharge_wallet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text_toolbar.setText("Wallet Transaction");
        wsc = new WebServiceController(this, RechargeWalletActivity.this);
        if (applicationPreference.getData("login_flag").equals("true"))
        {
            String nameFirst=applicationPreference.getData(applicationPreference.userName);
            tv_nameFirst.setText(String.valueOf(nameFirst.charAt(0)));
            tv_name.setText(applicationPreference.getData(applicationPreference.userName)+" "+applicationPreference.getData(applicationPreference.userLastName));
            tv_email.setText(applicationPreference.getData(applicationPreference.userEmail));
        }
        Bundle bundle= getIntent().getExtras();
        if (bundle!=null)
        {
            String walletAmount=bundle.getBundle("bundleWithValues").getString("walletamount");
            tv_walletamount.setText(Global.currencySymbol+" "+walletAmount);
        }

       // setContentView(R.layout.activity_recharge_wallet);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void getResponse(String response, int flag)
    {
        Log.e("response",response);
        switch (flag) {
            case 1:

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String url=jsonObject.getString("redirect");

                        Toast.makeText(RechargeWalletActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                    LandingActivityNew.getInstance().walletApiCall();
                    Intent intent = new Intent(getApplicationContext(), WalletWebviewActivity.class);
                    intent.putExtra("weburl", url);
                    startActivity(intent);
                    finish();
                    //    intentAndFragmentService.intentDisplay(RechargeWalletActivity.this, RechargeSuccessfulActivity.class, null);


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
