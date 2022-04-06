package com.gofly.model.requestModel.bus.confirmSeat;

import java.util.List;

/**
 * Created by ptblr-1195 on 5/6/17.
 */

public class ConfirmSeatMain {



    private String search_id;
    private List<String> gender = null;
    private List<String> pax_title = null;
    private List<String> contact_name = null;
    private List<String> age = null;
    private String token;
    private String token_key;
    private String op;
    private String booking_source;
    private String ResultToken;
    private String passenger_contact;
    private String alternate_contact;
    private String billing_email;
    private String tc;
    private String payment_method;
    String wallet_bal,phone_country_code,superCashDisc,charityVal;
    int convenience_fee;

    public ConfirmSeatMain(String searchId, List<String> gender, List<String> pax_title, List<String> contact_name,
                           List<String> age, String token,String token_key, String ResultToken, String op, String booking_source,
                           String passenger_contact, String alternate_contact, String billing_email, String tc,
                           String payment_method,String wallet_bal,String phone_country_code,int convenience_fee,String superCashDisc,String charityVal) {
        this.search_id = searchId;
        this.gender = gender;
        this.pax_title = pax_title;
        this.contact_name = contact_name;
        this.age = age;
        this.token = token;
        this.token_key = token_key;
        this.ResultToken=ResultToken;
        this.op = op;
        this.booking_source = booking_source;
        this.passenger_contact = passenger_contact;
        this.alternate_contact = alternate_contact;
        this.billing_email = billing_email;
        this.tc = tc;
        this.payment_method = payment_method;
        this.wallet_bal=wallet_bal;
        this.phone_country_code=phone_country_code;
        this.convenience_fee=convenience_fee;
        this.superCashDisc=superCashDisc;
        this.charityVal=charityVal;
    }

    public ConfirmSeatMain() {
        super();
    }
}
