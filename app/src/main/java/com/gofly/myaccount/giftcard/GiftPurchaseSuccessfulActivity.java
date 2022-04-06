package com.gofly.myaccount.giftcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofly.R;

import com.gofly.main.activity.BaseActivity;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class GiftPurchaseSuccessfulActivity extends BaseActivity implements WebInterface {

   String walletAmount;
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_referenceId)
    TextView tv_referenceId;
    WebServiceController wsc;
    String webUrl;
    @BindView(R.id.tv_walletamount)
    TextView tv_walletamount;
    @BindView(R.id.back_button)
    ImageView back_button;
    @BindView(R.id.cv_purchasegiftcard)
    CardView cv_purchasegiftcard;

    @BindView(R.id.tv_nameFirst)
    TextView tv_nameFirst;

    @OnClick(R.id.back_button)
    void goToBack()
    {
        Intent intent = new Intent(getApplicationContext(), GiftCardActivity.class);
        startActivity(intent);
        finish();

    }
    @OnClick(R.id.tv_rechargedetails)
    void goToTransactionPage()
    {
        Intent intent = new Intent(getApplicationContext(), PurchasedGiftTransactionActivity.class);
        intent.putExtra("walletamount", walletAmount);
        startActivity(intent);
        finish();

    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_gift_purchase_successful;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_gift_purchase_successful);

        text_toolbar.setText("Gift Card");
        String nameFirst=applicationPreference.getData(applicationPreference.userName);
        tv_nameFirst.setText(String.valueOf(nameFirst.charAt(0)));
        webUrl=getIntent().getExtras().getString("weburl");
        walletAmount=getIntent().getExtras().getString("walletamount");

        wsc = new WebServiceController(this, GiftPurchaseSuccessfulActivity.this);

            tv_name.setText(applicationPreference.getData(applicationPreference.userName)+" "+applicationPreference.getData(applicationPreference.userLastName));
            tv_email.setText(applicationPreference.getData(applicationPreference.userEmail));
            tv_walletamount.setText(walletAmount);

        wsc.getRequest(webUrl,1,true);
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
                    JSONObject jsonObject1=jsonObject.getJSONObject("request_params");
                    tv_referenceId.setText(jsonObject1.getString("txnid"));





                    //  Toast.makeText(getContext(),walletAmount,Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
