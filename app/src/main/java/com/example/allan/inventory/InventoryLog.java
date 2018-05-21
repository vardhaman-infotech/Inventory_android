package com.example.allan.inventory;

/**
 * Created by allan on 2/03/2018.
 */

public class InventoryLog {

    private String name;
    private String time;
    private String referenceNum;
    private String itemQuality;

    private String latitude;
    private String longitude;


    public InventoryLog(String name, String time, String referenceNum, String itemQuality,
                        String latitude, String longitude) {
        this.name = name;
        this.time = time;
        this.referenceNum = referenceNum;
        this.itemQuality = itemQuality;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReferenceNum() {
        return this.referenceNum;
    }

    public void setReferenceNum(String referenceNum) {
        this.referenceNum = referenceNum;
    }

    public String getQuality() {
        return this.itemQuality;
    }

    public void setQuality(String itemQuality) {
        this.itemQuality = itemQuality;
    }


}



