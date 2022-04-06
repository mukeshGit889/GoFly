package com.gofly.myaccount.kyc;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.main.activity.BaseActivity;
import com.gofly.main.adapter.landingAdapter.CountryAdapter;
import com.gofly.model.CountryInfo;
import com.gofly.utils.CommonUtils;
import com.gofly.utils.Global;
import com.gofly.utils.MyDatePicker;
import com.gofly.utils.retrofit.ApiClient;
import com.gofly.utils.retrofit.ApiInterface;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class KycActivity extends BaseActivity implements WebInterface {
    File imageFile;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    Bitmap thumbnail = null;
    String message, encodedImage;
   CountryAdapter countryAdapter;
    ArrayList<CountryInfo> countryList = new ArrayList<>();
    String walletAmount="";
    String name="",countycode="",mobileno="",emailid="",dob="",address="",idno="";
    WebServiceController wsc;
    RequestParams params=new RequestParams();
    AlertDialog alertDialog;
    @BindView(R.id.tv_name) TextView tv_name;
    @BindView(R.id.tv_email) TextView tv_email;
    @BindView(R.id.tv_walletamount) TextView tv_walletamount;
    @BindView(R.id.text_toolbar) TextView text_toolbar;
    @BindView(R.id.tv_upload) TextView tv_upload;
    @BindView(R.id.tv_uploadIdproof) TextView tv_uploadIdproof;
    @BindView(R.id.et_name) EditText et_name;
    @BindView(R.id.et_countycode) EditText et_countycode;
    @BindView(R.id.et_mobile) EditText et_mobile;
    @BindView(R.id.et_email_id) EditText et_email_id;
    @BindView(R.id.et_dob) EditText et_dob;
    @BindView(R.id.et_address) EditText et_address;
    @BindView(R.id.et_idno) EditText et_idno;
    @BindView(R.id.ivProfilePhoto)
    ImageView ivProfilePhoto;
    @BindView(R.id.iv_kycstatus)
    ImageView iv_kycstatus;
    @BindView(R.id.tv_nameFirst)
    TextView tv_nameFirst;
    int actionValue=1;

    @OnClick(R.id.et_countycode)
    void openCountryDialog()
    {
        alertDialog = new AlertDialog.Builder(KycActivity.this).create();
        //alert.setTitle("Package Durati");

        RecyclerView recyclerView = new RecyclerView(KycActivity.this);
        recyclerView.setPadding(10,10,10,10);
        commonUtils.linearLayout(recyclerView, KycActivity.this);

        countryAdapter =
                new CountryAdapter( Global.countryList,KycActivity.this);
        recyclerView.setAdapter(countryAdapter);


        alertDialog.setView(recyclerView);
        alertDialog.show();
    }

    @OnClick(R.id.back_button)
    void goBackPage()
    {
        //  intentAndFragmentService.intentDisplay(SupercashbackTransactionActivity.this, MyAccountPageActivity.class, null);
        finish();
    }
    @OnClick(R.id.et_dob)
    void setDob(){
        Global.dateset_flag=3;
        MyDatePicker newFragment = new MyDatePicker(KycActivity.this,actionValue);
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }
    @OnClick(R.id.tv_uploadIdproof)
    void selectImage()
    {
        final CharSequence[] items = {"Take Photo", "Choose from Gallary",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // boolean result = Utility.checkPermission(MainActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    //if (result)
                    cameraIntent();

                } else if (items[item].equals("Choose from Gallary")) {
                    userChoosenTask = "Choose from Gallary";
                    //if (result)
                    galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @OnClick(R.id.tv_upload)
    void OpenDialogSuccess()
    {
        name=et_name.getText().toString();
        if (name.equals(""))
        {
            commonUtils.toastShort("Please enter name",KycActivity.this);
            return;
        }

       // countycode=et_countycode.getText().toString();
        if (countycode.equals(""))
        {
            commonUtils.toastShort("Please select country code",KycActivity.this);
            return;
        }
        mobileno=et_mobile.getText().toString();
        if (mobileno.equals(""))
        {
            commonUtils.toastShort("Please enter mobile number",KycActivity.this);
            return;
        }
        emailid=et_email_id.getText().toString();
        if (emailid.equals(""))
        {
            commonUtils.toastShort("Please enter email id",KycActivity.this);
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailid).matches()) {
            commonUtils.toastShort("Enter a valid email", KycActivity.this);
            return;
        }
        dob=et_dob.getText().toString();
        if (dob.equals(""))
        {
            commonUtils.toastShort("Please select dob",KycActivity.this);
            return;
        }

        Date lAvlDate = CommonUtils.convertStrToDate(dob, "d-M-yyyy");
        dob=CommonUtils.conDateToStr(lAvlDate, "yyyy-MM-dd");


        address=et_address.getText().toString();
        if (address.equals(""))
        {
            commonUtils.toastShort("Please enter address",KycActivity.this);
            return;
        }
        idno=et_idno.getText().toString();
        if (idno.equals(""))
        {
            commonUtils.toastShort("Please enter proof id no",KycActivity.this);
            return;
        }

      /*  try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", applicationPreference.getData(applicationPreference.userId));
            jsonObject.put("name", name);
            jsonObject.put("country_code", countycode);
            jsonObject.put("phone", mobileno);
            jsonObject.put("email", emailid);
            jsonObject.put("dob", dob);
            jsonObject.put("address", address);
            jsonObject.put("aadhar_card_number", idno);

            params.put("kycDetails", jsonObject.toString());

            wsc.postRequest(apiConstants.KYC_UPLOAD, params, 2);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
*/


        RequestBody reqFile = null;
        MultipartBody.Part lFileBody = null;
        if (imageFile != null) {

            // imageFile = sessionManager.compressImage(imageFile, profile_image);
            String lStrMediaType = getMimeType(imageFile);
            reqFile = RequestBody.create(MediaType.parse(lStrMediaType), imageFile);
            lFileBody = MultipartBody.Part.createFormData("image", imageFile.getName(), reqFile);
        }


        RequestBody userIDData = RequestBody.create(okhttp3.MultipartBody.FORM, applicationPreference.getData(applicationPreference.userId));
        RequestBody nameData = RequestBody.create(okhttp3.MultipartBody.FORM, name);
        RequestBody countycodeData = RequestBody.create(okhttp3.MultipartBody.FORM, countycode);
        RequestBody mobilenoData = RequestBody.create(okhttp3.MultipartBody.FORM, mobileno);
        RequestBody emailidData = RequestBody.create(okhttp3.MultipartBody.FORM, emailid);
        RequestBody dobData = RequestBody.create(okhttp3.MultipartBody.FORM, dob);
        RequestBody addrData = RequestBody.create(okhttp3.MultipartBody.FORM, address);
        RequestBody idnoData = RequestBody.create(okhttp3.MultipartBody.FORM, idno);

        ApiInterface apiInterface= null;
        try {
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        Call<ResponseBody> signInCall = apiInterface.uploadKyc(userIDData, nameData, countycodeData,mobilenoData, emailidData,dobData,addrData,idnoData,lFileBody);
        ApiClient.getApiResponse(signInCall, new ApiClient.ApiCallBack() {
            @Override
            public void Success(Response<ResponseBody> response) throws IOException {

                String responseData= response.body().string();
                Log.e("Api url ",response.raw().request().url().toString()+"  blank ");
                Log.e("Api response",responseData+"  blank ");
                try {
                    JSONObject jsonObject=new JSONObject(responseData);

                    if(jsonObject.getInt("status")==1)
                    {

                        commonUtils.toastShort(jsonObject.getString("message"),getApplicationContext());
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(KycActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.kycdialog, null);
                        dialogBuilder.setView(dialogView);

                        alertDialog = dialogBuilder.create();
                        alertDialog.show();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                        TextView tv_ok=dialogView.findViewById(R.id.tv_ok);
                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });




                    }else {

                    }
                    commonUtils.toastShort(jsonObject.getString("message"),getApplicationContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                    commonUtils.toastShort(e.toString(),getApplicationContext());
                }

            }

            @Override
            public void Failure(Response<ResponseBody> response) throws IOException
            {



                commonUtils.toastShort(response.toString(),getApplicationContext());
            }

            @Override
            public void Error(Throwable t)
            {

                commonUtils.toastShort(t.toString(),getApplicationContext());

            }
        });

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_kyc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_kyc);
        text_toolbar.setText("KYC");
        if (applicationPreference.getData("login_flag").equals("true"))
        {
            String nameFirst=applicationPreference.getData(applicationPreference.userName);
            tv_nameFirst.setText(String.valueOf(nameFirst.charAt(0)));
            tv_name.setText(applicationPreference.getData(applicationPreference.userName)+" "+applicationPreference.getData(applicationPreference.userLastName));
            tv_email.setText(applicationPreference.getData(applicationPreference.userEmail));
        }
        wsc = new WebServiceController(this, KycActivity.this);
        params.put("user_id", applicationPreference.getData(applicationPreference.userId));

        wsc.postRequest(apiConstants.WALLET_BALANCE,params,1);
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
                    if (jsonObject.getInt("status") == 1) {
                        walletAmount = jsonObject.getString("wallet_ballence");
                        tv_walletamount.setText(Global.currencySymbol + " " + walletAmount);

                        RequestParams params = new RequestParams();
                        wsc.postRequest(apiConstants.URL_COUNTRY_LIST,params,2);

                        //  Toast.makeText(getContext(),walletAmount,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:

                try
                {
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
                    et_countycode.setText("("+Global.countryList.get(89).getCountry_code()+")"+Global.countryList.get(89).getName());
                    countycode=Global.countryList.get(89).getCountry_code();

                    params=new RequestParams();
                    params.put("user_id",applicationPreference.getData(applicationPreference.userId));

                    wsc.postRequest(apiConstants.KYC_DETAILS,params,3);

                }

                catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case 3:

                try
                {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject jsonObject1=jsonObject.getJSONObject("data");
                    if (jsonObject.getInt("status") == 1) {
                        et_name.setText(jsonObject1.getString("name"));
                        et_countycode.setText(jsonObject1.getString("country_code"));
                        et_mobile.setText(jsonObject1.getString("phone"));
                        et_email_id.setText(jsonObject1.getString("email"));

                        et_address.setText(jsonObject1.getString("address"));
                        et_idno.setText(jsonObject1.getString("aadhar_card_number"));
                        countycode=jsonObject1.getString("country_code");

                        Date lAvlDate = CommonUtils.convertStrToDate(jsonObject1.getString("dob"), "yyyy-MM-dd");
                        dob=jsonObject1.getString("dob");
                        et_dob.setText(CommonUtils.conDateToStr(lAvlDate, "dd-MM-yyyy"));

                       // String kycStatus="approved";
                        String kycStatus=jsonObject1.getString("status");
                        if (kycStatus.equals("declined"))
                        {
                            iv_kycstatus.setImageDrawable(getResources().getDrawable(R.drawable.kycdeclined));
                            tv_upload.setText("RE-APPLY");
                        }
                        if (kycStatus.equals("pending"))
                        {
                            iv_kycstatus.setImageDrawable(getResources().getDrawable(R.drawable.kycpending));
                            tv_upload.setVisibility(View.GONE);
                        }

                        if (kycStatus.equals("approved"))
                        {
                            iv_kycstatus.setImageDrawable(getResources().getDrawable(R.drawable.kycapproved));
                            tv_upload.setVisibility(View.GONE);
                        }
                    }



                }

                catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    public void selectedCountry(int position)
    {
        countycode=Global.countryList.get(position).getCountry_code();
        et_countycode.setText("("+Global.countryList.get(position).getCountry_code()+")"+Global.countryList.get(position).getName());
        alertDialog.dismiss();

    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageFile=persistImage(thumbnail);
        ivProfilePhoto.setImageBitmap(thumbnail);
    }

    private File persistImage(Bitmap bitmap) {

        File imageFile  = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {

        }
        return imageFile;
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(KycActivity.this.getContentResolver(), data.getData());
                imageFile=persistImage(thumbnail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivProfilePhoto.setImageBitmap(thumbnail);
    }

    public String getStringImage(Bitmap bmp){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG,60, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }catch (Exception e){

        }
        return encodedImage;
    }

    public String getMimeType(File lFile) {
        Uri selectedUri = Uri.fromFile(lFile);
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        return mimeType;
    }

}
