package com.gofly.myaccount.agent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.main.activity.BaseActivity;
import com.gofly.myaccount.kyc.KycActivity;
import com.gofly.myaccount.referearn.ReferAndEarnActivity;
import com.gofly.utils.Global;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class AddAgentActivity extends BaseActivity implements WebInterface {
    WebServiceController wsc;
    RequestParams params=new RequestParams();
    AlertDialog alertDialog;
    String name="",mobileno="",email="",accountHolderName="",accountNo="",bankName="",ifscCode="",accountType="",panno="",branch="",t_shirtType="",shippingAdd="",shippingincode="";
    @BindView(R.id.et_bank) EditText et_bank;
    @BindView(R.id.et_accountType) EditText et_accountType;
    @BindView(R.id.et_tshirt) EditText et_tshirt;
    @BindView(R.id.et_name) EditText et_name;
    @BindView(R.id.et_mobileno) EditText et_mobileno;
    @BindView(R.id.et_emailId) EditText et_emailId;
    @BindView(R.id.et_accountHolderName) EditText et_accountHolderName;
    @BindView(R.id.et_accountNo) EditText et_accountNo;
    @BindView(R.id.et_ifsccode) EditText et_ifsccode;
    @BindView(R.id.et_panno) EditText et_panno;
    @BindView(R.id.et_branch) EditText et_branch;
    @BindView(R.id.et_shippingAddress) EditText et_shippingAddress;
    @BindView(R.id.et_pincode) EditText et_pincode;

    @OnClick(R.id.iv_back)
    void backAction(){
        onBackPressed();
    }

    @OnClick(R.id.et_accountType)
    void openAccountTypeDialog(){
        openAccountDialog();
    }

    @OnClick(R.id.et_tshirt)
    void openTshirtTypeDialog(){
        openTshirtDialog();
    }

    @OnClick(R.id.et_bank)
    void openBankTypeDialog(){
        openBankListDialog();
    }

    @OnClick(R.id.tv_submit)
    void submitData()
    {
        accountHolderName=et_accountHolderName.getText().toString();
        if (accountHolderName.equals(""))
        {
            commonUtils.toastShort("Please enter account holder name",AddAgentActivity.this);
            return;
        }
        accountNo=et_accountNo.getText().toString();
        if (accountNo.equals(""))
        {
            commonUtils.toastShort("Please enter account number",AddAgentActivity.this);
            return;
        }
        bankName=et_bank.getText().toString();
        if (bankName.equals(""))
        {
            commonUtils.toastShort("Please select bank name",AddAgentActivity.this);
            return;
        }
        ifscCode=et_ifsccode.getText().toString();
        if (ifscCode.equals(""))
        {
            commonUtils.toastShort("Please enter ifsc code",AddAgentActivity.this);
            return;
        }
        accountType=et_accountType.getText().toString();
        if (accountType.equals(""))
        {
            commonUtils.toastShort("Please select account type",AddAgentActivity.this);
            return;
        }
        panno=et_panno.getText().toString();
        if (panno.equals(""))
        {
            commonUtils.toastShort("Please enter pan number",AddAgentActivity.this);
            return;
        }
        branch=et_branch.getText().toString();
        if (branch.equals(""))
        {
            commonUtils.toastShort("Please enter branch name",AddAgentActivity.this);
            return;
        }
        t_shirtType=et_tshirt.getText().toString();
        if (t_shirtType.equals(""))
        {
            commonUtils.toastShort("Please select t-shirt type",AddAgentActivity.this);
            return;
        }
        shippingAdd=et_shippingAddress.getText().toString();
        if (shippingAdd.equals(""))
        {
            commonUtils.toastShort("Please enter address",AddAgentActivity.this);
            return;
        }
        shippingincode=et_pincode.getText().toString();
        if (shippingincode.equals(""))
        {
            commonUtils.toastShort("Please enter pincode",AddAgentActivity.this);
            return;
        }

        params=new RequestParams();
        params.put("first_name",name);
        params.put("phone",mobileno);
        params.put("email",email);
        params.put("account_name",accountHolderName);
        params.put("account_no",accountNo);
        params.put("ifsc_code",ifscCode);
        params.put("panNumber",panno);
        params.put("bank",bankName);
        params.put("branch",branch);
        params.put("tShirtSize",t_shirtType);
        params.put("shippingAddress",shippingAdd);
        params.put("shippingPinCode",shippingincode);
        params.put("tc","1");
        params.put("account_type",accountType);
        params.put("user_id",applicationPreference.getData(applicationPreference.userId));
        wsc.postRequest(apiConstants.ADD_AGENT,params,1);

    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_agent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wsc = new WebServiceController(this, AddAgentActivity.this);
        name=applicationPreference.getData("user_fname")+" "+applicationPreference.getData("user_lname");
        mobileno=applicationPreference.getData("user_mobile");
        email=applicationPreference.getData("user_email");

        et_name.setText(applicationPreference.getData("user_fname")+" "+applicationPreference.getData("user_lname"));
        et_mobileno.setText(applicationPreference.getData("user_mobile"));
        et_emailId.setText(applicationPreference.getData("user_email"));
        //setContentView(R.layout.activity_add_agent);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    public void openAccountDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddAgentActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.account_type_dialog, null);
        dialogBuilder.setView(dialogView);
        // dialogView.getParent().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog = dialogBuilder.create();

        alertDialog.show();

        TextView tv_savings=dialogView.findViewById(R.id.tv_savings);
        TextView tv_current=dialogView.findViewById(R.id.tv_current);
        TextView tv_salary=dialogView.findViewById(R.id.tv_salary);


        tv_savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_accountType.setText("Savings");

                alertDialog.dismiss();
            }
        });
        tv_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_accountType.setText("Current");

                alertDialog.dismiss();
            }
        });
        tv_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_accountType.setText("Salary");

                alertDialog.dismiss();
            }
        });



    }
    public void openTshirtDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddAgentActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.tshirt_size_dialog, null);
        dialogBuilder.setView(dialogView);
        // dialogView.getParent().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog = dialogBuilder.create();

        alertDialog.show();

        TextView tv_s=dialogView.findViewById(R.id.tv_s);
        TextView tv_m=dialogView.findViewById(R.id.tv_m);
        TextView tv_l=dialogView.findViewById(R.id.tv_l);
        TextView tv_xl=dialogView.findViewById(R.id.tv_xl);
        TextView tv_xxl=dialogView.findViewById(R.id.tv_xxl);

        tv_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tshirt.setText("S");

                alertDialog.dismiss();
            }
        });
        tv_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tshirt.setText("M");

                alertDialog.dismiss();
            }
        });
        tv_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tshirt.setText("L");

                alertDialog.dismiss();
            }
        });

        tv_xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tshirt.setText("XL");

                alertDialog.dismiss();
            }
        });

        tv_xxl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tshirt.setText("XXL");

                alertDialog.dismiss();
            }
        });

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

                        commonUtils.toastShort(status,AddAgentActivity.this);

                        intentAndFragmentService.intentDisplay(AddAgentActivity.this, WelcomeKitActivity.class, null);

                    }
                    else
                    {
                        String status=jsonObject.getString("status");
                        commonUtils.toastShort(status,AddAgentActivity.this);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;



        }
    }

    public void openBankListDialog()
    {
        ArrayList<String>bankList=new ArrayList<>();
        bankList.add("Allahabad Bank");
        bankList.add("Andhra Bank");
        bankList.add("Axis Bank");
        bankList.add("Bank of Bahrain and Kuwait");
        bankList.add("Bank of Baroda - Corporate Banking");
        bankList.add("Bank of Baroda - Retail Banking'");
        bankList.add("Bank of India");
        bankList.add("Bank of Maharashtra");
        bankList.add("Canara Bank");
        bankList.add("Catholic Syrian Bank");
        bankList.add("Central Bank of India");
        bankList.add("City Union Bank");
        bankList.add("Citi Bank");
        bankList.add("Corporation Bank");
        bankList.add("Cosmos Bank");
        bankList.add("DBS Bank");
        bankList.add("DCB Bank - Corporate Netbanking");
        bankList.add("DCB Bank Business");
        bankList.add("DCB Bank Limited");
        bankList.add("Dena Bank");
        bankList.add("Deutsche Bank");
        bankList.add("Development Credit Bank");
        bankList.add("Dhanlaxmi Bank");
        bankList.add("Federal Bank");
        bankList.add("HDFC Bank");
        bankList.add("ICICI Bank");
        bankList.add("IDBI Bank");
        bankList.add("Indian Bank");
        bankList.add("Indian Overseas Bank");
        bankList.add("IndusInd Bank");
        bankList.add("ING Vysya Bank");
        bankList.add("Janata Sahakari Bank Pune");
        bankList.add("Jammu and Kashmir Bank");
        bankList.add("Karnataka Bank Ltd");
        bankList.add("Karur Vysya - Corporate Netbanking");
        bankList.add("Karur Vysya - Retail Netbanking");
        bankList.add("Karur Vysya Net Bank");
        bankList.add("Kotak Mahindra Bank");
        bankList.add("Laxmi Vilas Bank");
        bankList.add("Laxmi Vilas Bank Retail");
        bankList.add("Laxmi Vilas Bank (Corporate)");
        bankList.add("Oriental Bank of Commerce");
        bankList.add("Punjab And Maharashtra Co-operative Bank Limited");
        bankList.add("Punjab National Bank - Corporate Banking");
        bankList.add("Punjab National Bank - Retail Banking");
        bankList.add("Punjab & Sind Bank");
        bankList.add("Saraswat Bank");
        bankList.add("Shamrao Vitthal Co-operative Bank");
        bankList.add("South Indian Bank");
        bankList.add("State Bank of Bikaner & Jaipur");
        bankList.add("State Bank of Hyderabad");
        bankList.add("State Bank of India");
        bankList.add("State Bank of Mysore");
        bankList.add("State Bank of Patiala");
        bankList.add("State Bank of Travancore");
        bankList.add("Standard Chartered Bank");
        bankList.add("Syndicate Bank");
        bankList.add("Tamilnad Mercantile Bank Ltd");
        bankList.add("Test Bank");
        bankList.add("The Nainital Bank");
        bankList.add("UCO Bank");
        bankList.add("Union Bank of India");
        bankList.add("Union Bank - Retail Netbanking");
        bankList.add("Union Bank - Corporate Netbanking");
        bankList.add("United Bank of India");
        bankList.add("Vijaya Bank");
        bankList.add("Yes Bank Ltd");
        bankList.add("Other Banks");



        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddAgentActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.banklist_dialog, null);
        dialogBuilder.setView(dialogView);
        // dialogView.getParent().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog = dialogBuilder.create();

        alertDialog.show();

        RecyclerView rv_bankList=dialogView.findViewById(R.id.rv_bankList);
        BankListingAdapter bankListingAdapter=new BankListingAdapter(bankList,AddAgentActivity.this);
        rv_bankList.setAdapter(bankListingAdapter);

    }
    public void setBankName(String bankName)
    {
        alertDialog.dismiss();
        et_bank.setText(bankName);
    }
}