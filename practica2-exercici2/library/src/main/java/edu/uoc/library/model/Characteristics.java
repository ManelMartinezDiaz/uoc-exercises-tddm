package edu.uoc.library.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Manel on 23/11/2016.
 */

public class Characteristics {
    @SerializedName("year")
    private String year;

    @SerializedName("location")
    private Location location;

    @SerializedName("visitors")
    private int visitors;

    public String getYear() {
        return year ;
    }

    public Integer getvisitors() {
        return visitors ;
    }

    public Location getLocation(){return location;}

}