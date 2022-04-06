package com.gofly.myaccount.agent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gofly.R;
import com.gofly.main.activity.BaseActivity;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.OnClick;

public class WelcomeKitActivity extends BaseActivity implements WebInterface {
    WebServiceController wsc;
    RequestParams params=new RequestParams();

    @OnClick(R.id.iv_back)
    void backAction(){
        onBackPressed();
    }
    @OnClick(R.id.tv_buy)
    void purchaseKit(){
        params=new RequestParams();
        params.put("user_id",applicationPreference.getData(applicationPreference.userId));
        wsc.postRequest(apiConstants.WELCOME_KIT,params,1);
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_welcome_kit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_welcome_kit);
        wsc = new WebServiceController(this, WelcomeKitActivity.this);

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
                    if (jsonObject.getString("status").equals("success"))
                    {

                        String status=jsonObject.getString("status");

                      //  commonUtils.toastShort(status,WelcomeKitActivity.this);

                        intentAndFragmentService.intentDisplay(WelcomeKitActivity.this, AgentHomePageActivity.class, null);
                        finish();


                    }
                    else
                    {
                        String status=jsonObject.getString("status");
                        commonUtils.toastShort(status,WelcomeKitActivity.this);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;



        }
    }
}