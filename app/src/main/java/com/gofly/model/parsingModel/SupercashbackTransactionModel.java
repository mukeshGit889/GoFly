package com.gofly.model.parsingModel;

public class SupercashbackTransactionModel
{
    private String redeem_id,user_id,app_reference,description,open_super_cash,close_super_cash,open_wallet_balance,close_wallet_balance,redeemed_value,redeemed_points,per_val_to_pts,created_datetime;

    public SupercashbackTransactionModel(String redeem_id, String user_id, String app_reference, String description, String open_super_cash, String close_super_cash, String open_wallet_balance, String close_wallet_balance, String redeemed_value, String redeemed_points, String per_val_to_pts, String created_datetime)
    {
        this.redeem_id = redeem_id;
        this.user_id = user_id;
        this.app_reference = app_reference;
        this.description = description;
        this.open_super_cash = open_super_cash;
        this.close_super_cash = close_super_cash;
        this.open_wallet_balance = open_wallet_balance;
        this.close_wallet_balance = close_wallet_balance;
        this.redeemed_value = redeemed_value;
        this.redeemed_points = redeemed_points;
        this.per_val_to_pts = per_val_to_pts;
        this.created_datetime = created_datetime;
    }

    public String getRedeem_id() {
        return redeem_id;
    }

    public void setRedeem_id(String redeem_id) {
        this.redeem_id = redeem_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getApp_reference() {
        return app_reference;
    }

    public void setApp_reference(String app_reference) {
        this.app_reference = app_reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpen_super_cash() {
        return open_super_cash;
    }

    public void setOpen_super_cash(String open_super_cash) {
        this.open_super_cash = open_super_cash;
    }

    public String getClose_super_cash() {
        return close_super_cash;
    }

    public void setClose_super_cash(String close_super_cash) {
        this.close_super_cash = close_super_cash;
    }

    public String getOpen_wallet_balance() {
        return open_wallet_balance;
    }

    public void setOpen_wallet_balance(String open_wallet_balance) {
        this.open_wallet_balance = open_wallet_balance;
    }

    public String getClose_wallet_balance() {
        return close_wallet_balance;
    }

    public void setClose_wallet_balance(String close_wallet_balance) {
        this.close_wallet_balance = close_wallet_balance;
    }

    public String getRedeemed_value() {
        return redeemed_value;
    }

    public void setRedeemed_value(String redeemed_value) {
        this.redeemed_value = redeemed_value;
    }

    public String getRedeemed_points() {
        return redeemed_points;
    }

    public void setRedeemed_points(String redeemed_points) {
        this.redeemed_points = redeemed_points;
    }

    public String getPer_val_to_pts() {
        return per_val_to_pts;
    }

    public void setPer_val_to_pts(String per_val_to_pts) {
        this.per_val_to_pts = per_val_to_pts;
    }

    public String getCreated_datetime() {
        return created_datetime;
    }

    public void setCreated_datetime(String created_datetime) {
        this.created_datetime = created_datetime;
    }
}
