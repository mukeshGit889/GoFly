package com.gofly.model.parsingModel;

import java.util.ArrayList;
import java.util.stream.Stream;

public class GiftCardListModel extends ArrayList<String> {
    private String gift_id,card_type,currency,card_price,final_price,discount_type,image,card_discription;

    public GiftCardListModel(String gift_id, String card_type, String currency, String card_price, String final_price, String discount_type, String image, String card_discription) {
        this.gift_id = gift_id;
        this.card_type = card_type;
        this.currency = currency;
        this.card_price = card_price;
        this.final_price = final_price;
        this.discount_type = discount_type;
        this.image = image;
        this.card_discription = card_discription;
    }

    public String getGift_id() {
        return gift_id;
    }

    public void setGift_id(String gift_id) {
        this.gift_id = gift_id;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCard_price() {
        return card_price;
    }

    public void setCard_price(String card_price) {
        this.card_price = card_price;
    }

    public String getFinal_price() {
        return final_price;
    }

    public void setFinal_price(String final_price) {
        this.final_price = final_price;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCard_discription() {
        return card_discription;
    }

    public void setCard_discription(String card_discription) {
        this.card_discription = card_discription;
    }

    @Override
    public Stream<String> stream() {
        return null;
    }
}
