package com.gofly.model.parsingModel;

public class GiftCardTransactionModel
{
    private String book_id,card_type,gift_card_code,created_datetime,giftcardAmount,paidAmount;

    public GiftCardTransactionModel(String book_id,String gift_card_code,String giftcardAmount, String paidAmount, String created_datetime,String card_type)
    {
        this.book_id = book_id;
        this.gift_card_code = gift_card_code;
        this.giftcardAmount = giftcardAmount;
        this.paidAmount = paidAmount;
        this.created_datetime = created_datetime;
        this.card_type = card_type;



    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getGift_card_code() {
        return gift_card_code;
    }

    public void setGift_card_code(String gift_card_code) {
        this.gift_card_code = gift_card_code;
    }

    public String getCreated_datetime() {
        return created_datetime;
    }

    public void setCreated_datetime(String created_datetime) {
        this.created_datetime = created_datetime;
    }

    public String getGiftcardAmount() {
        return giftcardAmount;
    }

    public void setGiftcardAmount(String giftcardAmount) {
        this.giftcardAmount = giftcardAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }
}


