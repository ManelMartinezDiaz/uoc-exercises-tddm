package edu.uoc.library.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Manel on 23/11/2016.
 */

public class Location {
    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    public double getLatitude() {
        return latitude ;
    }
    public double getLongitude() { return longitude ;}

    public String getLocation(){
        String  result = Double.toString(latitude) + ", " + Double.toString(longitude);
        return result;}
}
