package com.gofly.myaccount.Redeemgift;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class RedeemGiftActivity extends BaseActivity implements WebInterface {

    String walletAmount="";
    String voucherCode="";
    AlertDialog alertDialog;
    WebServiceController wsc;
    RequestParams params=new RequestParams();
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;

    @BindView(R.id.tv_redeem)
    TextView tv_redeem;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_walletamount)
    TextView tv_walletamount;
    @BindView(R.id.et_vouchercode)
    EditText et_vouchercode;
    @BindView(R.id.tv_nameFirst)
    TextView tv_nameFirst;
    @OnClick(R.id.back_button)
    void goBackPage()
    {
        //  intentAndFragmentService.intentDisplay(SupercashbackTransactionActivity.this, MyAccountPageActivity.class, null);
        finish();
    }

    @OnClick(R.id.tv_redeem)
    void OpenDialogSuccess()
    {
        params=new RequestParams();
        voucherCode=et_vouchercode.getText().toString();
        if (voucherCode.equals(""))
        {
            commonUtils.toastShort("Please enter voucher code",RedeemGiftActivity.this);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", applicationPreference.getData(applicationPreference.userId));
            jsonObject.put("giftCardCode", voucherCode);
            params.put("redeemedGiftCard", jsonObject.toString());

            wsc.postRequest(apiConstants.REDEEM_GIFT, params, 2);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_redeem_gift;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text_toolbar.setText("Gift Card Redeem");
        if (applicationPreference.getData("login_flag").equals("true"))
        {
            String nameFirst=applicationPreference.getData(applicationPreference.userName);
            tv_nameFirst.setText(String.valueOf(nameFirst.charAt(0)));
            tv_name.setText(applicationPreference.getData(applicationPreference.userName)+" "+applicationPreference.getData(applicationPreference.userLastName));
            tv_email.setText(applicationPreference.getData(applicationPreference.userEmail));
        }
        wsc = new WebServiceController(this, RedeemGiftActivity.this);
        params.put("user_id", applicationPreference.getData(applicationPreference.userId));

        wsc.postRequest(apiConstants.WALLET_BALANCE,params,1);
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
                    if (jsonObject.getInt("status") == 1) {
                        walletAmount = jsonObject.getString("wallet_ballence");
                        tv_walletamount.setText(Global.currencySymbol + " " + walletAmount);


                        //  Toast.makeText(getContext(),walletAmount,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1)
                    {

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RedeemGiftActivity.this);
                        LayoutInflater inflater = getLayoutInflater();

                        View dialogView = inflater.inflate(R.layout.redeemgiftdialog, null);
                        dialogBuilder.setView(dialogView);
                        // dialogView.getParent().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        alertDialog = dialogBuilder.create();

                        alertDialog.show();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        TextView tv_amount_voucher=dialogView.findViewById(R.id.tv_amount_voucher);
                        TextView tv_referenceId=dialogView.findViewById(R.id.tv_referenceId);
                        TextView tv_ok=dialogView.findViewById(R.id.tv_ok);
                        String amount=jsonObject.getString("crntBlc");
                        String refId=jsonObject.getString("refNumer");

                        tv_amount_voucher.setText("Amount "+amount+" credited to wallet");
                        tv_referenceId.setText(refId);




                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                finish();


                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
