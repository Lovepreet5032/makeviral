package com.prouman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by aseemchoudhary on 09/04/18.
 */

public class CountryListDataObject {
    @SerializedName("CountryData")
    @Expose
    private ArrayList<CountryData> listCountryData;

    public ArrayList<CountryData> getListCountryData() {
        return listCountryData;
    }

    public void setListCountryData(ArrayList<CountryData> listCountryData) {
        this.listCountryData = listCountryData;
    }

    public class CountryData
{
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("iso")
    @Expose
    private String iso;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("dialcode")
    @Expose
    private String dialcode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDialcode() {
        return dialcode;
    }

    public void setDialcode(String dialcode) {
        this.dialcode = dialcode;
    }
}
}
