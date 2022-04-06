package com.gofly.more;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.login.LoginManager;
import com.gofly.LandingActivityNew;
import com.gofly.R;
import com.gofly.main.activity.BaseActivity;
import com.gofly.main.activity.CommonViewActivity;
import com.gofly.main.activity.LoginActivity;
import com.gofly.myaccount.MyAccountPageActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.OnClick;

public class MoreActivity extends BaseActivity {
    Bundle bundle;
    Intent intent;
    @OnClick(R.id.iv_back)
    void backAction(){
        onBackPressed();
    }

    @OnClick(R.id.rl_myAccount)
    void openMyAccountPage() {
        if(applicationPreference.getData(applicationPreference.login_flag).equals("true")){
            intentAndFragmentService.intentDisplay(this, MyAccountPageActivity.class, bundle);
          /*  if(applicationPreference.getData(applicationPreference.login_flag).equals("true")){
                intentAndFragmentService.intentDisplay(this, MyAccountActivity.class, bundle);*/


        }
        else {
            commonUtils.toastShort(getString(R.string.please_login_first),getApplicationContext());
        }
    }
    @OnClick(R.id.rl_aboutus)
    void openAboutusPage(){
        intent.putExtra("page_type",1);
        startActivity(intent);
    }

    @OnClick(R.id.rl_contactus)
    void openContactUsPage(){
        intent.putExtra("page_type",4);
        startActivity(intent);
    }

    @OnClick(R.id.rl_termsCondition)
    void openTermsConditionPage(){
        intent.putExtra("page_type",3);
        startActivity(intent);

    }

    @OnClick(R.id.rl_privacyPolicy)
    void openPrivacyPolicyPage(){
        intent.putExtra("page_type",2);
        startActivity(intent);
    }

    @OnClick(R.id.rl_logout)
    void doLogout(){

        logOut();
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_more;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_more);
        intent=new Intent(getApplicationContext(), CommonViewActivity.class);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
   /* @Override
    public void onBackPressed() {
        finish();
    }*/
   public  void logOut()
   {
       if(applicationPreference.getData(applicationPreference.login_type).equals("google"))
       {
           GoogleSignInOptions options =
                   new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                           .requestEmail().build();
           GoogleApiClient mGoogleApiClient;
           mGoogleApiClient = new GoogleApiClient.Builder(MoreActivity.this)
                   .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                   .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                   .build();
           if (mGoogleApiClient.isConnected()) {
               Auth.GoogleSignInApi.signOut(mGoogleApiClient);
               mGoogleApiClient.disconnect();
               mGoogleApiClient.connect();
           }
       }else if(applicationPreference.getData(applicationPreference.login_type).equals("facebook"))
       {
           LoginManager.getInstance().logOut();
       }
       applicationPreference.setData(
               applicationPreference.login_flag,
               "false");
       applicationPreference.clearAll();

       intentAndFragmentService.intentDisplay(MoreActivity.this,
               LoginActivity.class, null);
       finish();
   }

}