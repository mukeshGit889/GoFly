package com.gofly.myaccount.giftcard;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.myaccount.giftcard.adapter.GiftCardTransactionAdapter;
import com.gofly.main.activity.BaseActivity;
import com.gofly.model.parsingModel.GiftCardTransactionModel;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class PurchasedGiftTransactionActivity extends BaseActivity implements WebInterface {

    ArrayList<GiftCardTransactionModel>giftCardTransactionModelArrayList=new ArrayList<>();
    WebServiceController wsc;
    RequestParams params=new RequestParams();
    String walletAmount="";
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;

    @BindView(R.id.tv_walletamount)
    TextView tv_walletamount;
    @BindView(R.id.back_button)
    ImageView back_button;



    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.tv_nameFirst)
    TextView tv_nameFirst;

    @BindView(R.id.rv_puchasecardlisting)
    RecyclerView rv_puchasecardlisting;

    GiftCardTransactionAdapter giftCardTransactionAdapter;

    @OnClick(R.id.back_button)
    void goBackPage()
    {
        finish();
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_purchased_gift_transaction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text_toolbar.setText("Gift Card");
        wsc = new WebServiceController(this, PurchasedGiftTransactionActivity.this);

        String nameFirst=applicationPreference.getData(applicationPreference.userName);
        tv_nameFirst.setText(String.valueOf(nameFirst.charAt(0)));
        tv_name.setText(applicationPreference.getData(applicationPreference.userName)+" "+applicationPreference.getData(applicationPreference.userLastName));
        tv_email.setText(applicationPreference.getData(applicationPreference.userEmail));
        tv_walletamount.setText(getIntent().getExtras().getString("walletamount"));
        params.put("user_id", applicationPreference.getData(applicationPreference.userId));

        wsc.postRequest(apiConstants.GIFTCARD_TRANSACTION,params,1);
       // wsc.postRequest(apiConstants.GIFTCARD_LIST,params,1);

    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void getResponse(String response, int flag) {
        Log.e("response", response);
        switch (flag) {
            case 1:

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("getGiftCardDetails");

                    for(int i=0;i<jsonArray.length();i++)
                    {


                        giftCardTransactionModelArrayList.add(new GiftCardTransactionModel(
                                jsonArray.getJSONObject(i).getString("book_id"),
                                jsonArray.getJSONObject(i).getString("gift_card_code"),
                                jsonArray.getJSONObject(i).getString("card_price"),
                                jsonArray.getJSONObject(i).getString("total_amount"),
                                jsonArray.getJSONObject(i).getString("created_datetime"),
                                jsonArray.getJSONObject(i).getString("card_type")



                        ));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(giftCardTransactionModelArrayList.size()==0)
                {
                    commonUtils.toastShort("No Record  found",PurchasedGiftTransactionActivity.this);
                    return;
                }

                commonUtils.linearLayout(rv_puchasecardlisting,PurchasedGiftTransactionActivity.this);
                giftCardTransactionAdapter = new GiftCardTransactionAdapter(giftCardTransactionModelArrayList,
                        PurchasedGiftTransactionActivity.this);
                rv_puchasecardlisting.setAdapter(giftCardTransactionAdapter);
                break;
        }
    }
}
