package com.prouman.contactmanager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aseemchoudhary on 11/06/18.
 */

public class SearchTagLocalObject implements Parcelable {
    private  String key;
    private  String value;

    public SearchTagLocalObject() {

    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    protected SearchTagLocalObject(Parcel in) {
        key = in.readString();
        value = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SearchTagLocalObject> CREATOR = new Parcelable.Creator<SearchTagLocalObject>() {
        @Override
        public SearchTagLocalObject createFromParcel(Parcel in) {
            return new SearchTagLocalObject(in);
        }

        @Override
        public SearchTagLocalObject[] newArray(int size) {
            return new SearchTagLocalObject[size];
        }
    };
}
