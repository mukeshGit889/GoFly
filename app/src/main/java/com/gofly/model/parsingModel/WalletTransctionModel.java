package com.gofly.model.parsingModel;

public class WalletTransctionModel
{
    private String user_id,receiver_user_id,reference_number,description,credit_amount,debit_amount,opening_balance,closing_balance,created_datetime;

    public WalletTransctionModel(String user_id, String receiver_user_id, String reference_number, String description, String credit_amount, String debit_amount, String opening_balance, String closing_balance, String created_datetime) {
        this.user_id = user_id;
        this.receiver_user_id = receiver_user_id;
        this.reference_number = reference_number;
        this.description = description;
        this.credit_amount = credit_amount;
        this.debit_amount = debit_amount;
        this.opening_balance = opening_balance;
        this.closing_balance = closing_balance;
        this.created_datetime = created_datetime;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReceiver_user_id() {
        return receiver_user_id;
    }

    public void setReceiver_user_id(String receiver_user_id) {
        this.receiver_user_id = receiver_user_id;
    }

    public String getReference_number() {
        return reference_number;
    }

    public void setReference_number(String reference_number) {
        this.reference_number = reference_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCredit_amount() {
        return credit_amount;
    }

    public void setCredit_amount(String credit_amount) {
        this.credit_amount = credit_amount;
    }

    public String getDebit_amount() {
        return debit_amount;
    }

    public void setDebit_amount(String debit_amount) {
        this.debit_amount = debit_amount;
    }

    public String getOpening_balance() {
        return opening_balance;
    }

    public void setOpening_balance(String opening_balance) {
        this.opening_balance = opening_balance;
    }

    public String getClosing_balance() {
        return closing_balance;
    }

    public void setClosing_balance(String closing_balance) {
        this.closing_balance = closing_balance;
    }

    public String getCreated_datetime() {
        return created_datetime;
    }

    public void setCreated_datetime(String created_datetime) {
        this.created_datetime = created_datetime;
    }
}
