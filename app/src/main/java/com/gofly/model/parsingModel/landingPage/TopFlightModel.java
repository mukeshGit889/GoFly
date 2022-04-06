package com.gofly.model.parsingModel.landingPage;

public class TopFlightModel
{
    String origin;
    String from_airport_code;
    String from_airport_name;
    String to_airport_code;
    String to_airport_name;
    String image;

    public TopFlightModel(String origin, String from_airport_code, String from_airport_name, String to_airport_code, String to_airport_name, String image) {
        this.origin = origin;
        this.from_airport_code = from_airport_code;
        this.from_airport_name = from_airport_name;
        this.to_airport_code = to_airport_code;
        this.to_airport_name = to_airport_name;
        this.image = image;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getFrom_airport_code() {
        return from_airport_code;
    }

    public void setFrom_airport_code(String from_airport_code) {
        this.from_airport_code = from_airport_code;
    }

    public String getFrom_airport_name() {
        return from_airport_name;
    }

    public void setFrom_airport_name(String from_airport_name) {
        this.from_airport_name = from_airport_name;
    }

    public String getTo_airport_code() {
        return to_airport_code;
    }

    public void setTo_airport_code(String to_airport_code) {
        this.to_airport_code = to_airport_code;
    }

    public String getTo_airport_name() {
        return to_airport_name;
    }

    public void setTo_airport_name(String to_airport_name) {
        this.to_airport_name = to_airport_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
