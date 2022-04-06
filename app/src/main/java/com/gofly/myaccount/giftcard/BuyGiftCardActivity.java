package com.gofly.myaccount.giftcard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gofly.R;

import com.gofly.main.activity.BaseActivity;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class BuyGiftCardActivity extends BaseActivity implements WebInterface
{
    String name,email,mobileno;
    String walletAmount="";
    String id,image,desc,cardType,cardPrice,finalPrice;
    ArrayList<String> giftCardListModelArrayList=new ArrayList<>();
    WebServiceController wsc;
    RequestParams params=new RequestParams();
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_card_type)
    TextView tv_card_type;
    @BindView(R.id.tv_card_description)
    TextView tv_card_description;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_email_id)
    EditText et_email_id;
    @BindView(R.id.et_phoneno)
    EditText et_phoneno;
    @BindView(R.id.cb_terms)
    CheckBox cb_terms;
    @BindView(R.id.iv_gifycard)
    ImageView iv_gifycard;
    @BindView(R.id.tv_buy)
    TextView tv_buy;
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;

    @BindView(R.id.tv_walletamount)
    TextView tv_walletamount;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.tv_nameFirst)
    TextView tv_nameFirst;

    @OnClick(R.id.back_button)
    void goBackPage()
    {
        finish();
    }

    @OnClick(R.id.cv_purchasegiftcard)
    void OpenWalletRechrgePage()
    {
        Intent intent = new Intent(getApplicationContext(), PurchasedGiftTransactionActivity.class);
        intent.putExtra("walletamount", getIntent().getExtras().getString("walletamount"));
        startActivity(intent);

       /* intentAndFragmentService.fragmentDisplay(this,R.id.fragment_container,
                new PurchasedGiftCardTransactionFragment(),null,true);*/
    }
    @OnClick(R.id.tv_buy)
    void buyGiftCard()
    {
        name=et_name.getText().toString();
        if (name.equals(""))
        {
            commonUtils.toastShort("Please enter name",BuyGiftCardActivity.this);
            return;
        }
        email=et_email_id.getText().toString();
        if (email.equals(""))
        {
            commonUtils.toastShort("Please enter email id",BuyGiftCardActivity.this);
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            commonUtils.toastShort("Enter a valid email",BuyGiftCardActivity.this);
            return;
        }
        mobileno=et_phoneno.getText().toString();
        if (mobileno.equals(""))
        {
            commonUtils.toastShort("Please enter mobile number",BuyGiftCardActivity.this);
            return;
        }
        if (!cb_terms.isChecked())
        {
            commonUtils.toastShort("Please Select Terms an condition",BuyGiftCardActivity.this);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("user_id", applicationPreference.getData(applicationPreference.userId));
            jsonObject1.put("gift_card_id", id);
            jsonObject1.put("name", name);
            jsonObject1.put("email", email);
            jsonObject1.put("mobileno", mobileno);
            jsonObject.put("gift_card_details",jsonObject1);

            params.put("purchase_gift_card", jsonObject);
            wsc.postRequest(apiConstants.PURCHASE_GIFTCARD, params, 1);


        }

        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_buy_gift_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_buy_gift_card);
        text_toolbar.setText("Gift Card");
        String nameFirst=applicationPreference.getData(applicationPreference.userName);
        tv_nameFirst.setText(String.valueOf(nameFirst.charAt(0)));
        tv_name.setText(applicationPreference.getData(applicationPreference.userName)+" "+applicationPreference.getData(applicationPreference.userLastName));
        tv_email.setText(applicationPreference.getData(applicationPreference.userEmail));
        tv_walletamount.setText(getIntent().getExtras().getString("walletamount"));
        wsc = new WebServiceController(this, BuyGiftCardActivity.this);
        walletAmount=getIntent().getExtras().getString("walletamount");

        id=getIntent().getExtras().getString("id");
        desc=getIntent().getExtras().getString("desc");
        image=getIntent().getExtras().getString("image");
        cardType=getIntent().getExtras().getString("cardType");
        cardPrice=getIntent().getExtras().getString("cardPrice");
        finalPrice=getIntent().getExtras().getString("finalPrice");
        tv_price.setText(finalPrice);
        tv_card_type.setText(cardType);
        tv_card_description.setText(desc);
        Picasso.get()
                .load(image)
                .placeholder(R.drawable.backgroundflightimg)
                .error(R.drawable.backgroundflightimg)
                .into(iv_gifycard);


      /*  if(image.length() != 0){
            Picasso.get()
                    .load(image)
                    .placeholder(R.drawable.backgroundflightimg)
                    .error(R.drawable.backgroundflightimg)
                    .into(iv_gifycard);
        }else {
            iv_gifycard.setImageDrawable(getApplicationContext().getResources()
                    .getDrawable(R.drawable.backgroundflightimg));
        }
*/



    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void getResponse(String response, int flag) {
        Log.e("response",response);
        switch (flag) {
            case 1:

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String url=jsonObject.getString("redirect");

                    Toast.makeText(BuyGiftCardActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), GiftCardPaymentActivity.class);
                    intent.putExtra("weburl", url);
                    intent.putExtra("walletamount", walletAmount);
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
