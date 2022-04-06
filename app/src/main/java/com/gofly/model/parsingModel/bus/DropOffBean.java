package com.gofly.model.parsingModel.bus;

/**
 * Created by ptbr-1167 on 27/3/18.
 */

public class DropOffBean {

    private String DropoffTime = "";
    private String DropoffName = "";
    private String DropoffCode = "";
    private String citypointname="";
    private String citypointtime="";

    public String getDropoffTime() {
        return DropoffTime;
    }

    public void setDropoffTime(String dropoffTime) {
        DropoffTime = dropoffTime;
    }

    public String getDropoffName() {
        return DropoffName;
    }

    public void setDropoffName(String dropoffName) {
        DropoffName = dropoffName;
    }

    public String getDropoffCode() {
        return DropoffCode;
    }

    public void setDropoffCode(String dropoffCode) {
        DropoffCode = dropoffCode;
    }

    public void setCitypointtime(String citypointtime) {
        this.citypointtime = citypointtime;
    }

    public String getCitypointtime() {
        return citypointtime;
    }

    public void setCitypointname(String citypointname) {
        this.citypointname = citypointname;
    }

    public String getCitypointname() {
        return citypointname;
    }
}
