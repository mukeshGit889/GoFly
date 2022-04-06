package com.gofly.myaccount.giftcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.myaccount.giftcard.adapter.GiftCardListingAdapter;
import com.gofly.main.activity.BaseActivity;
import com.gofly.model.parsingModel.GiftCardListModel;
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

public class GiftCardActivity extends BaseActivity implements WebInterface {

    Bundle bundle;

    WebServiceController wsc;
    RequestParams params=new RequestParams();
    String walletAmount="";
    @BindView(R.id.text_toolbar)
    TextView text_toolbar;

    @BindView(R.id.tv_walletamount)
    TextView tv_walletamount;
    @BindView(R.id.back_button)
    ImageView back_button;

    @BindView(R.id.cv_purchasegiftcard)
    CardView cv_purchasegiftcard;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.tv_nameFirst)
    TextView tv_nameFirst;
    GiftCardListingAdapter giftCardListingAdapter;
    ArrayList<GiftCardListModel> giftCardListModelArrayList=new ArrayList<>();
    @BindView(R.id.rv_giftcardlisting)
    RecyclerView rv_giftcardlisting;




    @OnClick(R.id.back_button)
    void goBackPage()
    {


        finish();
    }

    @OnClick(R.id.cv_purchasegiftcard)
    void OpenWalletRechrgePage()
    {
        Intent intent = new Intent(getApplicationContext(), PurchasedGiftTransactionActivity.class);
        intent.putExtra("walletamount", walletAmount);
        startActivity(intent);

       /* intentAndFragmentService.fragmentDisplay(this,R.id.fragment_container,
                new PurchasedGiftCardTransactionFragment(),null,true);*/
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_gift_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text_toolbar.setText("Gift Card");
        wsc = new WebServiceController(this, GiftCardActivity.this);

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


       // setContentView(R.layout.activity_gift_card);
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

                        wsc.postRequest(apiConstants.GIFTCARD_LIST,params,2);


                       /* intentAndFragmentService.fragmentDisplay(this,R.id.fragment_container,
                                new PurchaseGiftCardListFragment(),null,false);
*/
                        //  Toast.makeText(getContext(),walletAmount,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case 2:

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("gift_card_details");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        giftCardListModelArrayList.add(new GiftCardListModel(
                                jsonArray.getJSONObject(i).getString("gift_id"),
                                jsonArray.getJSONObject(i).getString("card_type"),
                                jsonArray.getJSONObject(i).getString("currency"),
                                jsonArray.getJSONObject(i).getString("card_price"),
                                jsonArray.getJSONObject(i).getString("final_price"),
                                jsonArray.getJSONObject(i).getString("discount_type"),
                                jsonArray.getJSONObject(i).getString("image"),
                                jsonArray.getJSONObject(i).getString("card_discription")



                        ));
                    }
                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                if(giftCardListModelArrayList.size()==0)
                {
                    commonUtils.toastShort("No Record  found",GiftCardActivity.this);
                    return;
                }

                commonUtils.linearLayout(rv_giftcardlisting,GiftCardActivity.this);
                giftCardListingAdapter = new GiftCardListingAdapter(giftCardListModelArrayList,
                        GiftCardActivity.this);
                rv_giftcardlisting.setAdapter(giftCardListingAdapter);
                break;

        }
    }

    public void buyGiftCard(String id,String image,String desc,String cardType,String cardPrice,String finalPrice)
    {
       // bundle=new Bundle();
      /*  bundle.putString("id",id);
        bundle.putString("image",image);
        bundle.putString("desc",desc);
        bundle.putString("cardType",cardType);
        bundle.putString("cardPrice",cardPrice);
        bundle.putString("finalPrice",finalPrice);*/
        Intent intent = new Intent(getApplicationContext(), BuyGiftCardActivity.class);
        intent.putExtra("walletamount", walletAmount);
        intent.putExtra("id", id);
        intent.putExtra("image", image);
        intent.putExtra("desc", desc);
        intent.putExtra("cardType", cardType);
        intent.putExtra("cardPrice", cardPrice);
        intent.putExtra("finalPrice", finalPrice);
        startActivity(intent);



      //  intentAndFragmentService.intentDisplay(GiftCardActivity.this, BuyGiftCardActivity.class, bundle);

    }
}
