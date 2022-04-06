package com.gofly.myaccount.referearn;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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

public class ReferAndEarnActivity extends BaseActivity implements WebInterface {


    String emailid="",mobileno="",refercode="";
    String walletAmount="";
    AlertDialog alertDialog;
    WebServiceController wsc;
    RequestParams params=new RequestParams();
    @BindView(R.id.tv_name) TextView tv_name;
    @BindView(R.id.tv_email) TextView tv_email;
    @BindView(R.id.tv_walletamount) TextView tv_walletamount;
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;

    @BindView(R.id.tv_send)
    TextView tv_send;

    @BindView(R.id.et_email_id)
    EditText et_email_id;

    @BindView(R.id.et_mobile)
    EditText et_mobile;

    @BindView(R.id.tv_refercode)
    TextView tv_refercode;

    @BindView(R.id.tv_nameFirst)
    TextView tv_nameFirst;

    @OnClick(R.id.back_button)
    void goBackPage()
    {
        //  intentAndFragmentService.intentDisplay(SupercashbackTransactionActivity.this, MyAccountPageActivity.class, null);
        finish();
    }

    @OnClick(R.id.tv_send)
    void OpenDialogSuccess()
    {
        emailid=et_email_id.getText().toString();
        if (emailid.equals(""))
        {
            commonUtils.toastShort("Please Enter Friend Email ID ",ReferAndEarnActivity.this);
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailid).matches()) {
            commonUtils.toastShort("Enter a valid email", ReferAndEarnActivity.this);
            return;
        }
        mobileno=et_mobile.getText().toString();
        if (mobileno.equals(""))
        {
            commonUtils.toastShort("Please Enter Friend Mobile No",ReferAndEarnActivity.this);
            return;
        }
        params=new RequestParams();
        params.put("user_id",applicationPreference.getData(applicationPreference.userId));
        params.put("receiver_email",emailid);
        params.put("receiver_phone",mobileno);
        params.put("referal_code",refercode);
        wsc.postRequest(apiConstants.SEND_REFERCODE,params,3);



    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_refer_and_earn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text_toolbar.setText("Refer a Friend");
        if (applicationPreference.getData("login_flag").equals("true"))
        {
            String nameFirst=applicationPreference.getData(applicationPreference.userName);
            tv_nameFirst.setText(String.valueOf(nameFirst.charAt(0)));
            tv_name.setText(applicationPreference.getData(applicationPreference.userName)+" "+applicationPreference.getData(applicationPreference.userLastName));
            tv_email.setText(applicationPreference.getData(applicationPreference.userEmail));
        }
        wsc = new WebServiceController(this, ReferAndEarnActivity.this);
        params.put("user_id", applicationPreference.getData(applicationPreference.userId));
      //  params.put("user_id", "1266");

        wsc.postRequest(apiConstants.WALLET_BALANCE,params,1);
      //  setContentView(R.layout.activity_refer_and_earn);
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


                        wsc.postRequest(apiConstants.GET_REFERCODE, params, 2);

                        //  Toast.makeText(getContext(),walletAmount,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
            {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1)
                    {
                        refercode=jsonObject.getString("referal_code");
                        tv_refercode.setText(refercode);

                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            break;
            case 3:
            {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1)
                    {

                        commonUtils.toastShort(jsonObject.getString("msg"),ReferAndEarnActivity.this);

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ReferAndEarnActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.referearndialog, null);
                        dialogBuilder.setView(dialogView);

                        alertDialog = dialogBuilder.create();
                        alertDialog.show();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                        TextView tv_ok=dialogView.findViewById(R.id.tv_ok);
                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
