package com.gofly.myaccount.supercashback;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.main.activity.BaseActivity;
import com.gofly.model.parsingModel.SupercashbackTransactionModel;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SupercashbackTransactionActivity extends BaseActivity implements WebInterface {
    Bundle bundle;
    SupercashbackAdapter supercashbackAdapter;
    ArrayList<SupercashbackTransactionModel>supercashbackTransactionModelArrayList=new ArrayList<>();
    @BindView(R.id.rv_supercashback)
    RecyclerView rv_supercashback;

    WebServiceController wsc;
    RequestParams params=new RequestParams();
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;

    @BindView(R.id.back_button)
    ImageView back_button;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.cv_supercashback)
    CardView cv_supercashback;

    @BindView(R.id.tv_walletamount)
    TextView tv_walletamount;

    @BindView(R.id.tv_nameFirst)
    TextView tv_nameFirst;

    String supercashbackAmount="";

    @OnClick(R.id.cv_supercashback)
    void OpenSuperCashbackage()
    {
        bundle=new Bundle();
        bundle.putString("supercashbackamount",supercashbackAmount);

        intentAndFragmentService.intentDisplay(SupercashbackTransactionActivity.this, AddSupercashbackActivity.class, bundle);

    }

    @OnClick(R.id.back_button)
    void goBackPage()
    {
      //  intentAndFragmentService.intentDisplay(SupercashbackTransactionActivity.this, MyAccountPageActivity.class, null);
        finish();
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_supercashback_transaction;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text_toolbar.setText("Supercash Redeem");
        wsc = new WebServiceController(this, SupercashbackTransactionActivity.this);
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

        wsc.postRequest(apiConstants.SUPERCASHBACK_AMOUNT,params,1);

        // setContentView(R.layout.activity_supercashback_transaction);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void getResponse(String response, int flag)
    {
        Log.e("response",response);
        switch (flag)
        {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status") == 1)
                    {
                        JSONObject jsonObject1=jsonObject.getJSONObject("data");
                        supercashbackAmount=jsonObject1.getString("super_cashback");
                        tv_walletamount.setText(supercashbackAmount);
                        wsc.postRequest(apiConstants.SUPERCASHBACK_TRANSACTION,params,2);

                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                break;

            case 2:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("redeem_details");
                    if(jsonObject.getInt("status") == 1)
                    {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            supercashbackTransactionModelArrayList.add(new SupercashbackTransactionModel(
                                    jsonArray.getJSONObject(i).getString("redeem_id"),
                                    jsonArray.getJSONObject(i).getString("user_id"),
                                    jsonArray.getJSONObject(i).getString("app_reference"),

                                    jsonArray.getJSONObject(i).getString("description"),

                                    jsonArray.getJSONObject(i).getString("open_super_cash"),

                                    jsonArray.getJSONObject(i).getString("close_super_cash"),

                                    jsonArray.getJSONObject(i).getString("open_wallet_balance"),

                                    jsonArray.getJSONObject(i).getString("close_wallet_balance"),

                                    jsonArray.getJSONObject(i).getString("redeemed_value"),
                                    jsonArray.getJSONObject(i).getString("redeemed_points"),
                                    jsonArray.getJSONObject(i).getString("per_val_to_pts"),
                                    jsonArray.getJSONObject(i).getString("created_datetime")




                                    ));
                        }


                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                if(supercashbackTransactionModelArrayList.size()==0)
                {
                    commonUtils.toastShort("No Transaction  found",SupercashbackTransactionActivity.this);
                    return;
                }

                commonUtils.linearLayout(rv_supercashback,this);
                supercashbackAdapter = new SupercashbackAdapter(supercashbackTransactionModelArrayList,
                        SupercashbackTransactionActivity.this);
                rv_supercashback.setAdapter(supercashbackAdapter);

                break;
        }
    }
}
