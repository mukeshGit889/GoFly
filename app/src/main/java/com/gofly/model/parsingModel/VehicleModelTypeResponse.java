package com.gofly.model.parsingModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleModelTypeResponse {


    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("vehicle_name")
    private String vehicle_name;
    @Expose
    @SerializedName("vehicle_model")
    private String vehicle_model;
    @Expose
    @SerializedName("vehicle_type_id")
    private String vehicle_type_id;
    @Expose
    @SerializedName("vehicle_id")
    private String vehicle_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getVehicle_type_id() {
        return vehicle_type_id;
    }

    public void setVehicle_type_id(String vehicle_type_id) {
        this.vehicle_type_id = vehicle_type_id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public VehicleModelTypeResponse(String status, String vehicle_name, String vehicle_model, String vehicle_type_id, String vehicle_id) {
        this.status = status;
        this.vehicle_name = vehicle_name;
        this.vehicle_model = vehicle_model;
        this.vehicle_type_id = vehicle_type_id;
        this.vehicle_id = vehicle_id;
    }
}
