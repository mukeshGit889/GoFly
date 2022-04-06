package com.gofly.myaccount.supercashback;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gofly.LandingActivityNew;
import com.gofly.R;
import com.gofly.main.activity.BaseActivity;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class AddSupercashbackActivity extends BaseActivity implements WebInterface {

    WebServiceController wsc;
    RequestParams params=new RequestParams();
    AlertDialog alertDialog;
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;

    @BindView(R.id.tv_redeem)
    TextView tv_redeem;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.back_button)
    ImageView back_button;

    @BindView(R.id.tv_walletamount)
    TextView tv_walletamount;

    @BindView(R.id.et_cashbackamount)
    EditText et_cashbackamount;

    @BindView(R.id.tv_nameFirst)
    TextView tv_nameFirst;

    String cashbackvalue="";

    @OnClick(R.id.back_button)
    void goBackPage()
    {
        intentAndFragmentService.intentDisplay(AddSupercashbackActivity.this, SupercashbackTransactionActivity.class, null);
        finish();
    }

    @OnClick(R.id.tv_redeem)
    void OpenDialogSuccess()
    {
        cashbackvalue=et_cashbackamount.getText().toString();

        if (cashbackvalue.equals(""))
        {
            commonUtils.toastShort("Please enter Amount",AddSupercashbackActivity.this);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", applicationPreference.getData(applicationPreference.userId));
            jsonObject.put("redeem_amount", cashbackvalue);
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("redeem_points",jsonObject);
            params.put("redeem_points", jsonObject1.toString());
            wsc.postRequest(
                    apiConstants.ADD_SUPERCASHBACK,
                    params,
                    1);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
      /*  AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddSupercashbackActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.supercashbackdialog, null);
        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.show();*/
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_supercashback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text_toolbar.setText("Supercash Redeem");
        wsc = new WebServiceController(this, AddSupercashbackActivity.this);


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
            String supercashbackAmount=bundle.getBundle("bundleWithValues").getString("supercashbackamount");
            tv_walletamount.setText(supercashbackAmount);
        }

        // setContentView(R.layout.activity_add_supercashback);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void getResponse(String response, int flag) {
        Log.e("response",response);
        switch (flag)
        {
            case 1:

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status") == 1)
                    {

                        Toast.makeText(AddSupercashbackActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddSupercashbackActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.supercashbackdialog, null);
                        dialogBuilder.setView(dialogView);

                        alertDialog = dialogBuilder.create();
                        alertDialog.show();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        TextView amountMessage=dialogView.findViewById(R.id.tv_amountmsg);
                        TextView amountSuccessmsg=dialogView.findViewById(R.id.tv_successmsg);
                        TextView ref_Id=dialogView.findViewById(R.id.tv_refid);
                        TextView tv_ok=dialogView.findViewById(R.id.tv_ok);

                        amountMessage.setText("Amount "+cashbackvalue+" credited to wallet");


                        // amountMessage.setText("Amount "+jsonObject.getString("redeemed_amount")+" credited to wallet");
                        ref_Id.setText(jsonObject.getString("book_id"));
                        LandingActivityNew.getInstance().walletApiCall();

                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                intentAndFragmentService.intentDisplay(AddSupercashbackActivity.this, SupercashbackTransactionActivity.class, null);
                                finish();
                            }
                        });
                       // intentAndFragmentService.intentDisplay(RechargeWalletActivity.this, RechargeSuccessfulActivity.class, null);

                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            break;

        }

    }
}
