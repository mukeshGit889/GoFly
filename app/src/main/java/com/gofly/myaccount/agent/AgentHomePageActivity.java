package com.gofly.myaccount.agent;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gofly.LandingActivityNew;
import com.gofly.R;
import com.gofly.main.activity.BaseActivity;
import com.gofly.myaccount.MyAccountPageActivity;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class AgentHomePageActivity extends BaseActivity implements WebInterface {
    WebServiceController wsc;
    RequestParams params=new RequestParams();
    Dialog dialog;
    String walletAmount="";
    boolean registerFlag=false,purchaseKit=false;
    @BindView(R.id.linear_agent) LinearLayout linear_agent;
    @BindView(R.id.tv_walletAmount) TextView tv_walletAmount;
    @BindView(R.id.tv_register) TextView tv_register;
    @BindView(R.id.tv_buy) TextView tv_buy;
    @BindView(R.id.tv_redeem_to_wallet) TextView tv_redeem_to_wallet;
    @BindView(R.id.tv_settlement) TextView tv_settlement;
    @OnClick(R.id.iv_back)
    void backAction(){
        onBackPressed();
    }

    @OnClick(R.id.tv_register)
    void openAddAgentPage(){
        intentAndFragmentService.intentDisplay(AgentHomePageActivity.this, AddAgentActivity.class, null);

    }
    @OnClick(R.id.tv_buy)
    void openWelcomeKitPage(){
        intentAndFragmentService.intentDisplay(AgentHomePageActivity.this, WelcomeKitActivity.class, null);

    }

    @OnClick(R.id.tv_redeem_to_wallet)
    void openRedeemToWalletDialog(){
        tv_redeem_to_wallet.setBackground(getResources().getDrawable(R.drawable.rectangel_orange));
        tv_redeem_to_wallet.setTextColor(getResources().getColor(R.color.white));
        tv_settlement.setBackground(getResources().getDrawable(R.drawable.curve_orange));
        tv_settlement.setTextColor(getResources().getColor(R.color.colorPrimary));

        dialog = new Dialog(AgentHomePageActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.redeem_to_wallet_dialog);
        dialog.setCanceledOnTouchOutside(true);
       dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView iv_cancel=dialog.findViewById(R.id.iv_cancel);
        final EditText et_reddem_amount=dialog.findViewById(R.id.et_reddem_amount);
        final EditText et_TDS=dialog.findViewById(R.id.et_TDS);
        final EditText et_finalAmount=dialog.findViewById(R.id.et_finalAmount);
        TextView tv_submit=dialog.findViewById(R.id.tv_submit);

        et_reddem_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length()>0)
                {
                    Double rAmount=Double.parseDouble(et_reddem_amount.getText().toString());

                    Double amount=rAmount*5/100;
                    Double tds=amount;
                    Double famount=rAmount-amount;

                    et_TDS.setText(""+tds);
                    et_finalAmount.setText(""+famount);
                }
                else
                {
                    et_TDS.setText("");
                    et_finalAmount.setText("");
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (et_reddem_amount.getText().toString().equals(""))
                {
                    commonUtils.toastShort("Please enter redeem amount",AgentHomePageActivity.this);
                    return;
                }
                Double rAmount=Double.parseDouble(et_reddem_amount.getText().toString());

                if (rAmount<100)
                {
                    commonUtils.toastShort("Please enter valid amount",AgentHomePageActivity.this);
                    return;
                }
                if (et_TDS.getText().toString().equals(""))
                {
                    commonUtils.toastShort("Please enter TDS amount",AgentHomePageActivity.this);
                    return;
                }
                if (et_finalAmount.getText().toString().equals(""))
                {
                    commonUtils.toastShort("Please enter Final amount",AgentHomePageActivity.this);
                    return;
                }

                params.put("user_id",applicationPreference.getData(applicationPreference.userId));
                params.put("redeem_amount",et_reddem_amount.getText().toString());
                params.put("mode","net_amount");
                wsc.postRequest(apiConstants.REDEEM_TO_WALLET,params,4);
            }
        });
    }
    @OnClick(R.id.tv_settlement)
    void openSettlementDialog(){
        tv_settlement.setBackground(getResources().getDrawable(R.drawable.rectangel_orange));
        tv_settlement.setTextColor(getResources().getColor(R.color.white));
        tv_redeem_to_wallet.setBackground(getResources().getDrawable(R.drawable.curve_orange));
        tv_redeem_to_wallet.setTextColor(getResources().getColor(R.color.colorPrimary));
        dialog = new Dialog(AgentHomePageActivity.this);

        //   dialog = new Dialog(AgentHomePageActivity.this, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.settlement_dialog);
        dialog.setCanceledOnTouchOutside(true);
       dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView iv_cancel=dialog.findViewById(R.id.iv_cancel);
      final   EditText et_settlement_amount=dialog.findViewById(R.id.et_reddem_amount);
        final  EditText et_TDS=dialog.findViewById(R.id.et_TDS);
        final  EditText et_transactionfees=dialog.findViewById(R.id.et_transactionfees);

        final  EditText et_finalAmount=dialog.findViewById(R.id.et_finalAmount);
        TextView tv_submit=dialog.findViewById(R.id.tv_submit);
        et_settlement_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length()>0)
                {
                    Double rAmount=Double.parseDouble(et_settlement_amount.getText().toString());
                    Double tamount=10.0;

                    Double amount=rAmount*5/100;
                    Double tds=amount;
                    Double famount=rAmount-amount-tamount;

                    et_TDS.setText(""+tds);
                    et_finalAmount.setText(""+famount);
                    et_transactionfees.setText("10");
                }
                else
                {
                    et_TDS.setText("");
                    et_finalAmount.setText("");
                    et_transactionfees.setText("");
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_settlement_amount.getText().toString().equals(""))
                {
                    commonUtils.toastShort("Please enter redeem amount",AgentHomePageActivity.this);
                    return;
                }
                Double rAmount=Double.parseDouble(et_settlement_amount.getText().toString());

                if (rAmount<1000)
                {
                    commonUtils.toastShort("Please enter valid settlement amount",AgentHomePageActivity.this);
                    return;
                }
                if (et_TDS.getText().toString().equals(""))
                {
                    commonUtils.toastShort("Please enter TDS amount",AgentHomePageActivity.this);
                    return;
                }
                if (et_transactionfees.getText().toString().equals(""))
                {
                    commonUtils.toastShort("Please enter transaction fees amount",AgentHomePageActivity.this);
                    return;
                }
                if (et_finalAmount.getText().toString().equals(""))
                {
                    commonUtils.toastShort("Please enter Final amount",AgentHomePageActivity.this);
                    return;
                }

                params.put("user_id",applicationPreference.getData(applicationPreference.userId));
                params.put("redeem_amount",et_settlement_amount.getText().toString());
                params.put("mode","net_amount1");
                wsc.postRequest(apiConstants.SETTLEMENT,params,5);
            }
        });
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_agent_home_page;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_agent_home_page);
        wsc = new WebServiceController(this, AgentHomePageActivity.this);
        params=new RequestParams();
        params.put("user_id",applicationPreference.getData(applicationPreference.userId));

        wsc.postRequest(apiConstants.CHECK_REGISTER_AGENT,params,1);

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
                    if (jsonObject.getString("status").equals("Success"))
                    {

                        wsc.postRequest(apiConstants.AGENT_WALLET,params,2);

                        //commonUtils.toastShort(status,AddAgentActivity.this);


                    }
                    else
                    {
                        String status=jsonObject.getString("status");
                       // commonUtils.toastShort(status,AgentHomePageActivity.this);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("Success"))
                    {

                        walletAmount=jsonObject.getString("data");
                        if (walletAmount.equals(""))
                        {
                            tv_walletAmount.setText("INR 0");
                        }

                        else
                        {
                            tv_walletAmount.setText("INR "+walletAmount);
                        }
                        wsc.postRequest(apiConstants.CHECK_PURCHASE_KIT,params,3);
                        tv_register.setVisibility(View.GONE);
                        tv_buy.setVisibility(View.VISIBLE);
                        //commonUtils.toastShort(status,AddAgentActivity.this);


                    }
                    else
                    {
                        String status=jsonObject.getString("status");
                        tv_buy.setVisibility(View.GONE);

                        //    commonUtils.toastShort(status,AgentHomePageActivity.this);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("Success"))
                    {

                        linear_agent.setVisibility(View.GONE);




                        //commonUtils.toastShort(status,AddAgentActivity.this);


                    }
                    else
                    {
                        String status=jsonObject.getString("status");
                      //  commonUtils.toastShort(status,AgentHomePageActivity.this);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 4:

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("Success"))
                    {
                        dialog.dismiss();


                        String status=jsonObject.getString("msg");
                        commonUtils.toastShort(status,AgentHomePageActivity.this);
                        LandingActivityNew.getInstance().walletApiCall();


                    }
                    else
                    {
                        String status=jsonObject.getString("status");
                        commonUtils.toastShort(status,AgentHomePageActivity.this);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 5:

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("Success"))
                    {


                        dialog.dismiss();
                        String status=jsonObject.getString("msg");
                        commonUtils.toastShort(status,AgentHomePageActivity.this);
                        LandingActivityNew.getInstance().walletApiCall();


                    }
                    else
                    {
                        String status=jsonObject.getString("status");
                        commonUtils.toastShort(status,AgentHomePageActivity.this);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}