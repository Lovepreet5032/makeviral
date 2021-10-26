package com.prouman.ninjaforms;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by om on 2/19/2017.
 */
public class Label implements Parcelable {
    private String text;

    public String getText() { return this.text; }

    public void setText(String text) { this.text = text; }

    /*private Style style;

    public Style getStyle() { return this.style; }

    public void setStyle(Style style) { this.style = style; }*/

    protected Label(Parcel in) {
        text = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
    }

    @SuppressWarnings("unused")
    public static final Creator<Label> CREATOR = new Creator<Label>() {
        @Override
        public Label createFromParcel(Parcel in) {
            return new Label(in);
        }

        @Override
        public Label[] newArray(int size) {
            return new Label[size];
        }
    };
}
