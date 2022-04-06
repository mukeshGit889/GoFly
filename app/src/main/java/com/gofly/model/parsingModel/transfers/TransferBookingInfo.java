package com.gofly.model.parsingModel.transfers;

public class TransferBookingInfo
{
    private String bookingStatus,booking_id,currency,total_fare,product_name,travel_date;

    public TransferBookingInfo(String bookingStatus, String booking_id, String currency, String total_fare, String product_name,String travel_date) {
        this.bookingStatus = bookingStatus;
        this.booking_id = booking_id;
        this.currency = currency;
        this.total_fare = total_fare;
        this.product_name = product_name;
        this.travel_date=travel_date;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTotal_fare() {
        return total_fare;
    }

    public void setTotal_fare(String total_fare) {
        this.total_fare = total_fare;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getTravel_date() {
        return travel_date;
    }

    public void setTravel_date(String travel_date) {
        this.travel_date = travel_date;
    }
}
