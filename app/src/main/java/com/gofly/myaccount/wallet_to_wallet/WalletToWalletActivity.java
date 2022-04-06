package com.gofly.myaccount.wallet_to_wallet;

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

import com.gofly.LandingActivityNew;
import com.gofly.R;
import com.gofly.main.activity.BaseActivity;
import com.gofly.main.activity.MainActivity;
import com.gofly.model.parsingModel.WalletModel;
import com.gofly.myaccount.MyAccountPageActivity;
import com.gofly.utils.Global;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletToWalletActivity extends BaseActivity implements WebInterface {

    String emailId="",amount="",walletAmount;
    WebServiceController wsc;
    RequestParams params=new RequestParams();
    AlertDialog alertDialog;
    @BindView(R.id.text_toolbar) TextView text_toolbar;
    @BindView(R.id.tv_walletamount) TextView tv_walletamount;
    @BindView(R.id.tv_transfers) TextView tv_transfers;
    @BindView(R.id.et_email_id) EditText et_email_id;
    @BindView(R.id.et_amount) EditText et_amount;
    @BindView(R.id.tv_name) TextView tv_name;
    @BindView(R.id.tv_email) TextView tv_email;
    @BindView(R.id.tv_nameFirst) TextView tv_nameFirst;

    @OnClick(R.id.back_button)
    void goBackPage()
    {
        //  intentAndFragmentService.intentDisplay(SupercashbackTransactionActivity.this, MyAccountPageActivity.class, null);
        finish();
    }

    @OnClick(R.id.tv_transfers)
    void OpenDialogSuccess()
    {







        emailId=et_email_id.getText().toString();
        if (emailId.equals(""))
        {
            commonUtils.toastShort("Please enter Email id",WalletToWalletActivity.this);
            return;
        }
       /* if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            commonUtils.toastShort("Enter a valid email", WalletToWalletActivity.this);
            return;
        }*/

        amount=et_amount.getText().toString();
        if (amount.equals(""))
        {
            commonUtils.toastShort("Please enter Amount",WalletToWalletActivity.this);
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", applicationPreference.getData(applicationPreference.userId));
            jsonObject.put("email_or_phone", emailId);
            jsonObject.put("send_amount", amount);
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("wallet_transfer",jsonObject);
            params.put("wallet_transfer", jsonObject1.toString());
            wsc.postRequest(
                    apiConstants.WALLET_TO_WALLET_TRANSFER,
                    params,
                    2);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_wallet_to_wallet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text_toolbar.setText("Wallet to Wallet Transfer");
        if (applicationPreference.getData("login_flag").equals("true"))
        {
            String nameFirst=applicationPreference.getData(applicationPreference.userName);
            tv_nameFirst.setText(String.valueOf(nameFirst.charAt(0)));
            tv_name.setText(applicationPreference.getData(applicationPreference.userName)+" "+applicationPreference.getData(applicationPreference.userLastName));
            tv_email.setText(applicationPreference.getData(applicationPreference.userEmail));
        }
        wsc = new WebServiceController(this, WalletToWalletActivity.this);
        params.put("user_id", applicationPreference.getData(applicationPreference.userId));

        wsc.postRequest(apiConstants.WALLET_BALANCE,params,1);
        //  setContentView(R.layout.activity_wallet_to_wallet);
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
                    if(jsonObject.getInt("status") == 1)
                    {
                        walletAmount=jsonObject.getString("wallet_ballence");
                        tv_walletamount.setText(Global.currencySymbol+" "+walletAmount);


                        //  Toast.makeText(getContext(),walletAmount,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("response").equals("SUCCESS"))
                    {
                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(WalletToWalletActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.wallettowalletdialog, null);
                        dialogBuilder.setView(dialogView);
                        alertDialog = dialogBuilder.create();
                        alertDialog.show();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                        TextView transferAmount=dialogView.findViewById(R.id.tv_amounttransfer);
                        TextView success_message=dialogView.findViewById(R.id.tv_success_message);
                        TextView tv_ok=dialogView.findViewById(R.id.tv_ok);
                        TextView tv_referenceId=dialogView.findViewById(R.id.referenceno);
                        //transferAmount.setText("hello");

                        transferAmount.setText("Amount "+amount+" Transfered");
                        tv_referenceId.setText(jsonObject.getString("wallet_tnxId"));
                        success_message.setText("The amount has been transfered to "+emailId+" successfully");

                        LandingActivityNew.getInstance().walletApiCall();

                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                intentAndFragmentService.intentDisplay(WalletToWalletActivity.this, MyAccountPageActivity.class, null);
                                finish();

                            }
                        });

                    }
                    else
                    {
                        String status=jsonObject.getString("message");
                         commonUtils.toastShort(status, WalletToWalletActivity.this);
                       // Toast.makeText(getApplicationContext(),jsonObject.getString("response"),Toast.LENGTH_SHORT).show();

                    }







                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
