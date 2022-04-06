package com.gofly.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.LoginManager;
import com.gofly.R;
import com.gofly.main.activity.CommonViewActivity;
import com.gofly.main.activity.LoginActivity;
import com.gofly.main.activity.MainActivity;
import com.gofly.main.fragment.BaseFragment;
import com.gofly.myaccount.MyAccountPageActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.OnClick;


public class MoreFragment extends BaseFragment {

    Intent intent;
    Bundle bundle;
    public MoreFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.ll_flight)
    void openFlightSearch(){
        //   callActivity(2);
        ((MainActivity)getActivity()).fragmentCallingAction(1);
    }
    @OnClick(R.id.ll_hotels)
    void openHotelSearch(){
        //   callActivity(2);
        ((MainActivity)getActivity()).fragmentCallingAction(2);
    }

    @OnClick(R.id.ll_bus)
    void openbusSearch(){
        ((MainActivity)getActivity()).fragmentCallingAction(3);
    }

    @OnClick(R.id.ll_holidays)
    void openholidaySearch(){
        ((MainActivity)getActivity()).fragmentCallingAction(4);
    }

    @OnClick(R.id.rl_myAccount)
    void openMyAccountPage() {
        if(applicationPreference.getData(applicationPreference.login_flag).equals("true")){
            intentAndFragmentService.intentDisplay(getContext(), MyAccountPageActivity.class, bundle);
          /*  if(applicationPreference.getData(applicationPreference.login_flag).equals("true")){
                intentAndFragmentService.intentDisplay(this, MyAccountActivity.class, bundle);*/


        }
        else {
            commonUtils.toastShort(getString(R.string.please_login_first),getContext());
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
        return R.layout.fragment_more;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent=new Intent(getContext(), CommonViewActivity.class);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public  void logOut()
    {
        if(applicationPreference.getData(applicationPreference.login_type).equals("google"))
        {
            GoogleSignInOptions options =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail().build();
            GoogleApiClient mGoogleApiClient;
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .enableAutoManage(getActivity(), (GoogleApiClient.OnConnectionFailedListener) this)
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

        intentAndFragmentService.intentDisplay(getContext(),
                LoginActivity.class, null);
        getActivity().finish();
    }
}