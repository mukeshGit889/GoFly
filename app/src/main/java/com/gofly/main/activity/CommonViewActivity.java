package com.gofly.main.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gofly.WebViewActivity;
import com.loopj.android.http.RequestParams;
import com.gofly.R;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class CommonViewActivity extends BaseActivity implements WebInterface
{
    int page_type=0;
    WebServiceController webServiceController;
    @BindView(R.id.text_toolbar) TextView toolbarText;
    @BindView(R.id.content_text) TextView content_text;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_common_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page_type=getIntent().getExtras().getInt("page_type");
        RequestParams requestParams = new RequestParams();
        webServiceController=new WebServiceController(this,CommonViewActivity.this);

        if (page_type==1)
        {
            toolbarText.setText("About Us");
            webServiceController.postRequest(apiConstants.ABOUNT_US,requestParams,1);


        }
        else  if (page_type==2)
        {
            toolbarText.setText("Privacy Policy");
            webServiceController.postRequest(apiConstants.PRIVACY_POLICY,requestParams,2);


        }
        else  if (page_type==3)
        {
            toolbarText.setText("Terms and Conditions");
            webServiceController.postRequest(apiConstants.TERMS_AND_CONDITION,requestParams,3);

        }
        else
        {
            toolbarText.setText("Contact Us");
            webServiceController.postRequest(apiConstants.CONTACT_US,requestParams,4);


        }

        //content_text.setMovementMethod(LinkMovementMethod.getInstance());
       // content_text.setLinksClickable(true);

        content_text.setMovementMethod(new TextViewLinkHandler() {
            @Override
            public void onLinkClick(String url) {
                Toast.makeText(content_text.getContext(), url, Toast.LENGTH_SHORT).show();
                Intent intent;
                intent = new Intent(CommonViewActivity.this, WebViewActivity.class);
                intent.putExtra("weburl", url);
               startActivity(intent);


            }
        });


    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
    public abstract class TextViewLinkHandler extends LinkMovementMethod {

        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            if (event.getAction() != MotionEvent.ACTION_UP)
                return super.onTouchEvent(widget, buffer, event);

            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            URLSpan[] link = buffer.getSpans(off, off, URLSpan.class);
            if (link.length != 0) {
                onLinkClick(link[0].getURL());
            }
            return true;
        }

        abstract public void onLinkClick(String url);
    }
    @Override
    public void getResponse(String response, int flag) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            String pageContent=jsonObject.getString("data");

        switch (flag){
            case 1:

                content_text.setText(Html.fromHtml(pageContent));
                break;
            case 2:
                content_text.setText(Html.fromHtml(pageContent));

                break;
            case 3:
                content_text.setText(Html.fromHtml(pageContent));

                break;
            case 4:
                content_text.setText(Html.fromHtml(pageContent));

                break;
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.back_button)
    void setGoBack(){
        finish();
    }
}
