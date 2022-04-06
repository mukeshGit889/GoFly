package com.gofly.myaccount.wallet;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.main.activity.BaseActivity;
import com.gofly.model.parsingModel.WalletTransctionModel;
import com.gofly.utils.Global;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletTransactionActivity extends BaseActivity implements WebInterface {
    ArrayList<WalletTransctionModel> walletTransctionModelArrayList=new ArrayList<WalletTransctionModel>();

    WalletAdapter walletAdapter;
    String walletAmount="";
    Bundle bundle;
    WebServiceController wsc;
    RequestParams params=new RequestParams();
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;
    @BindView(R.id.tv_walletamount)
    TextView tv_walletamount;
    @BindView(R.id.back_button)
    ImageView back_button;

    @BindView(R.id.cv_rechargewallet)
    CardView cv_rechargewallet;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.rv_walletList)
    RecyclerView rv_walletList;

    @BindView(R.id.tv_nameFirst)
    TextView tv_nameFirst;

    @OnClick(R.id.back_button)
    void goBackPage()
    {


        finish();
    }
    @OnClick(R.id.cv_rechargewallet)
    void OpenWalletRechrgePage()
    {
        bundle.putString("walletamount",walletAmount);
        intentAndFragmentService.intentDisplay(WalletTransactionActivity.this, RechargeWalletActivity.class, bundle);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_wallet_transaction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text_toolbar.setText("Wallet Transaction");
        wsc = new WebServiceController(this, WalletTransactionActivity.this);
        bundle=new Bundle();
     /*   if (applicationPreference.getData("login_flag").equals("true"))
        {
            tv_name.setText(applicationPreference.getData(applicationPreference.userName)+" "+applicationPreference.getData(applicationPreference.userLastName));
            tv_email.setText(applicationPreference.getData(applicationPreference.userEmail));
        }*/
        if (applicationPreference.getData("login_flag").equals("true"))
        {
            String nameFirst=applicationPreference.getData(applicationPreference.userName);
            tv_nameFirst.setText(String.valueOf(nameFirst.charAt(0)));
            params.put("user_id", applicationPreference.getData(applicationPreference.userId));
            tv_name.setText(applicationPreference.getData(applicationPreference.userName)+" "+applicationPreference.getData(applicationPreference.userLastName));
            tv_email.setText(applicationPreference.getData(applicationPreference.userEmail));

        }
        else
        {
            params.put("user_id", "") ;
        }

        wsc.postRequest(apiConstants.WALLET_BALANCE,params,1);

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
                        wsc.postRequest(apiConstants.WALLET_TRANSACTION,params,2);


                        //  Toast.makeText(getContext(),walletAmount,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("response");

                    if(jsonObject.getInt("status") == 1)
                    {

                        for(int i=0;i<jsonArray.length();i++)
                        {
                            walletTransctionModelArrayList.add(new WalletTransctionModel(
                                    jsonArray.getJSONObject(i).getString("user_id"),
                                    jsonArray.getJSONObject(i).getString("receiver_user_id"),
                                    jsonArray.getJSONObject(i).getString("reference_number"),
                                    jsonArray.getJSONObject(i).getString("description"),
                                    jsonArray.getJSONObject(i).getString("credit_amount"),
                                    jsonArray.getJSONObject(i).getString("debit_amount"),
                                    jsonArray.getJSONObject(i).getString("opening_balance"),
                                    jsonArray.getJSONObject(i).getString("closing_balance"),
                                    jsonArray.getJSONObject(i).getString("created_datetime")
                                    ));
                        }



                        //  Toast.makeText(getContext(),walletAmount,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(walletTransctionModelArrayList.size()==0)
                {
                    commonUtils.toastShort("No Transaction  found",WalletTransactionActivity.this);
                    return;
                }

                commonUtils.linearLayout(rv_walletList,this);
                walletAdapter = new WalletAdapter(walletTransctionModelArrayList,
                        WalletTransactionActivity.this);
                rv_walletList.setAdapter(walletAdapter);
                break;

        }
    }
    @Override
    public void onPermissionsGranted(int requestCode) {

    }
}
