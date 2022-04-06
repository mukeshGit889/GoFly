package com.gofly.myaccount;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.myaccount.Redeemgift.RedeemGiftActivity;
import com.gofly.myaccount.agent.AgentHomePageActivity;
import com.gofly.myaccount.giftcard.GiftCardActivity;
import com.gofly.interfaces.UpdateProfile;
import com.gofly.myaccount.kyc.KycActivity;
import com.gofly.main.activity.BaseActivity;
import com.gofly.main.activity.MyBookingsActivity;
import com.gofly.myaccount.referearn.ReferAndEarnActivity;
import com.gofly.myaccount.supercashback.SupercashbackTransactionActivity;
import com.gofly.myaccount.wallet.WalletTransactionActivity;
import com.gofly.myaccount.wallet_to_wallet.WalletToWalletActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MyAccountPageActivity extends BaseActivity implements UpdateProfile
{
    Bundle bundle;
    @BindView(R.id.tv_name) TextView tv_name;
    @BindView(R.id.tv_email) TextView tv_email;
    @BindView(R.id.linear_profile) LinearLayout linear_profile;
    @BindView(R.id.linear_mytrips) LinearLayout linear_mytrips;
    @BindView(R.id.linear_giftcard) LinearLayout linear_giftcard;
    @BindView(R.id.linear_wallet) LinearLayout linear_wallet;
    @BindView(R.id.linear_supercashback) LinearLayout linear_supercashback;
    @BindView(R.id.linear_wallettowallet) LinearLayout linear_wallettowallet;
    @BindView(R.id.linear_reddemgift) LinearLayout linear_reddemgift;
    @BindView(R.id.linear_referearn) LinearLayout linear_referearn;
    @BindView(R.id.linear_kyc) LinearLayout linear_kyc;
    @BindView(R.id.tv_nameFirst) TextView tv_nameFirst;
    @OnClick(R.id.iv_back)
    void goBackPage()
    {

        finish();
    }

    @OnClick(R.id.linear_profile)
    void openProfilePage()
    {
        if(applicationPreference.getData(applicationPreference.login_flag).equals("true")){
            intentAndFragmentService.intentDisplay(this, ProfilePageActivity.class, bundle);



        }else {
            commonUtils.toastShort(getString(R.string.please_login_first),getApplicationContext());
        }
    }

    @OnClick(R.id.linear_mytrips)
    void openMyTrips()
    {
        if(applicationPreference.getData(applicationPreference.login_flag).equals("true")){
            intentAndFragmentService.intentDisplay(this, MyBookingsActivity.class, bundle);

        }else {
            commonUtils.toastShort(getString(R.string.please_login_first),getApplicationContext());
        }

    }
    @OnClick(R.id.linear_giftcard)
    void openGiftCardPage()
    {
        intentAndFragmentService.intentDisplay(MyAccountPageActivity.this, GiftCardActivity.class, null);

    }

    @OnClick(R.id.linear_wallet)
    void openWalletPage()
    {
        intentAndFragmentService.intentDisplay(MyAccountPageActivity.this, WalletTransactionActivity.class, null);
        // finish();

    }

    @OnClick(R.id.linear_supercashback)
    void openSupercashbackPage()
    {
        intentAndFragmentService.intentDisplay(MyAccountPageActivity.this, SupercashbackTransactionActivity.class, null);


    }

    @OnClick(R.id.linear_wallettowallet)
    void openwallettowalletPage()
    {
        intentAndFragmentService.intentDisplay(MyAccountPageActivity.this, WalletToWalletActivity.class, null);


    }


    @OnClick(R.id.linear_reddemgift)
    void openReddemGiftPage()
    {
        intentAndFragmentService.intentDisplay(MyAccountPageActivity.this, RedeemGiftActivity.class, null);

    }

    @OnClick(R.id.linear_referearn)
    void openReferEarnPage()
    {
        intentAndFragmentService.intentDisplay(MyAccountPageActivity.this, ReferAndEarnActivity.class, null);

    }

    @OnClick(R.id.linear_kyc)
    void openKycPage()
    {
        intentAndFragmentService.intentDisplay(MyAccountPageActivity.this, KycActivity.class, null);

    }
    @OnClick(R.id.linear_adAgent)
    void openAgentPage()
    {

        if(applicationPreference.getData(applicationPreference.login_flag).equals("true")){
            intentAndFragmentService.intentDisplay(MyAccountPageActivity.this, AgentHomePageActivity.class, null);



        }else {
            commonUtils.toastShort(getString(R.string.please_login_first),getApplicationContext());
        }

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_my_account_page;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (applicationPreference.getData("login_flag").equals("true"))
        {
            String nameFirst=applicationPreference.getData(applicationPreference.userName);
           tv_nameFirst.setText(String.valueOf(nameFirst.charAt(0)));
            tv_name.setText(applicationPreference.getData(applicationPreference.userName)+" "+applicationPreference.getData(applicationPreference.userLastName));
            tv_email.setText(applicationPreference.getData(applicationPreference.userEmail));
        }


      //  setContentView(R.layout.activity_my_account_page);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void updateProfileData(String fname, String lname, String email) {
        String nameFirst=applicationPreference.getData(applicationPreference.userName);
        tv_nameFirst.setText(String.valueOf(nameFirst.charAt(0)));
        tv_name.setText(applicationPreference.getData(applicationPreference.userName)+" "+applicationPreference.getData(applicationPreference.userLastName));
        tv_email.setText(applicationPreference.getData(applicationPreference.userEmail));
    }
}
