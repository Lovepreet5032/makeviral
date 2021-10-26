package com.prouman.videonewscreen;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aseemchoudhary on 14/02/18.
 */

public class Caption implements Parcelable {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("icon")
    @Expose
    private String icon;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    protected Caption(Parcel in) {
        label = in.readString();
        link = in.readString();
        icon = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeString(link);
        dest.writeString(icon);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Caption> CREATOR = new Parcelable.Creator<Caption>() {
        @Override
        public Caption createFromParcel(Parcel in) {
            return new Caption(in);
        }

        @Override
        public Caption[] newArray(int size) {
            return new Caption[size];
        }
    };
}



