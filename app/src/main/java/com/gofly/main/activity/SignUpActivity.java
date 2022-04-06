package com.gofly.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import com.gofly.main.adapter.SignUpCountryAdapter;
import com.gofly.main.adapter.landingAdapter.CountryAdapter;
import com.gofly.model.CountryInfo;
import com.gofly.myaccount.agent.AddAgentActivity;
import com.gofly.myaccount.kyc.KycActivity;
import com.gofly.utils.Global;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.gofly.R;
import com.gofly.model.requestModel.login.UserSignUp;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements WebInterface {
    ArrayList<CountryInfo> countryList = new ArrayList<>();
    String countycode="";
    SignUpCountryAdapter countryAdapter;
    WebServiceController webServiceController;
    Gson gson;
    AlertDialog alertDialog;
    @BindView(R.id.et_name) EditText et_name;
    @BindView(R.id.et_lastname) EditText et_lastname;
    @BindView(R.id.et_mobile) EditText et_mobile;
    @BindView(R.id.et_email) EditText et_email;
    @BindView(R.id.et_password) EditText et_password;
    @BindView(R.id.et_confpassword) EditText et_confpassword;
    @BindView(R.id.et_refercode) EditText et_refercode;
    @BindView(R.id.et_countrycode) EditText et_countrycode;
    @BindView(R.id.cb_whatsapp)
    CheckBox cb_whatsapp;
    @BindView(R.id.cb_terms)
    CheckBox cb_terms;

    @OnClick(R.id.bt_signup)
    void doSignUp(){

        final String fname = et_name.getText().toString().trim();
        final String lname = et_lastname.getText().toString().trim();
        final String mobile = et_mobile.getText().toString().trim();
        final String email = et_email.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        final String conf_password = et_confpassword.getText().toString().trim();
        final String terms ="on";
        final String refercode =et_refercode.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(fname)) {
            et_name.setError("Enter first name");
            et_name.requestFocus();
            return;
        }

       else if (TextUtils.isEmpty(lname)) {
            et_lastname.setError("Enter last name");
            et_lastname.requestFocus();
            return;
        }

       else if (TextUtils.isEmpty(mobile)) {
            et_mobile.setError("Enter mobile number");
            et_mobile.requestFocus();
            return;
        }

       else if (TextUtils.isEmpty(email)) {
            et_email.setError("Enter your email");
            et_email.requestFocus();
            return;
        }

      else  if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Enter a valid email");
            et_email.requestFocus();
            return;
        }

      else  if (TextUtils.isEmpty(password)) {
            et_password.setError("Enter a password");
            et_password.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(conf_password)) {
            et_confpassword.setError("Enter a confirm password");
            et_confpassword.requestFocus();
            return;
        }
       else if(!password.equalsIgnoreCase(conf_password))
        {
            et_confpassword.setError("Password and confirm password did not match");

        }
        else  if (!cb_terms.isChecked())
        {
            commonUtils.toastShort("Please select terms and conditions.", SignUpActivity.this);
            return;
        }
        else  if (!cb_whatsapp.isChecked())
        {
            commonUtils.toastShort("Please select whatsapp notification.", SignUpActivity.this);
            return;
        }
        else
        {

            UserSignUp usersignup = new UserSignUp(
                    fname,lname,mobile,email,password,conf_password,terms,refercode
            );
            RequestParams requestParams = new RequestParams();
            requestParams.put("user_register", gson.toJson(usersignup));
            webServiceController.postRequest(
                    apiConstants.USER_SIGN_UP,
                    requestParams,
                    1);
        }
    }
    @OnClick(R.id.et_countrycode)
    void openCountryDialog()
    {
        alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
        //alert.setTitle("Package Durati");

        RecyclerView recyclerView = new RecyclerView(SignUpActivity.this);
        recyclerView.setPadding(10,10,10,10);
        commonUtils.linearLayout(recyclerView, SignUpActivity.this);

        countryAdapter =
                new SignUpCountryAdapter( Global.countryList,SignUpActivity.this);
        recyclerView.setAdapter(countryAdapter);


        alertDialog.setView(recyclerView);
        alertDialog.show();
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_sign_up);
        cb_whatsapp.setChecked(true);
        gson = new Gson();
        webServiceController = new WebServiceController(this, SignUpActivity.this);
     //   progressLoader.ShowProgress(getActivity());
        RequestParams params = new RequestParams();
        webServiceController.postRequest(apiConstants.URL_COUNTRY_LIST,params,2);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void getResponse(String response, int flag) {

        switch (flag) {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 0) {
                        commonUtils.toastShort(jsonObject.getString("message"), getApplicationContext());
                        return;
                    }
                    commonUtils.toastShort(jsonObject.getString("message"), getApplicationContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

                break;
            case 2:

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("country_list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        countryList.add(new CountryInfo(jsonArray.getJSONObject(i).getString("origin"),
                                jsonArray.getJSONObject(i).getString("name"),
                                jsonArray.getJSONObject(i).getString("country_code"),
                                jsonArray.getJSONObject(i).getString("country_code"),
                                jsonArray.getJSONObject(i).getString("iso_country_code")));
                        Global.arrCountry.add(jsonArray.getJSONObject(i).getString("name") + " ("
                                + jsonArray.getJSONObject(i).getString("country_code") + ")");
                    }
                    Global.countryList.addAll(countryList);
                    et_countrycode.setText(Global.countryList.get(89).getCountry_code());

                    /*if(applicationPreference.getData("login_flag").equalsIgnoreCase("true"))
                    {
                        callWalletAPI();
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    public void selectedCountry(int position)
    {
        countycode=Global.countryList.get(position).getCountry_code();
        et_countrycode.setText("("+Global.countryList.get(position).getCountry_code()+")"+Global.countryList.get(position).getName());
        alertDialog.dismiss();

    }


}
