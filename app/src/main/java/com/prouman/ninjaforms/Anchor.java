package com.prouman.ninjaforms;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by om on 2/19/2017.
 */
public class Anchor implements Parcelable {
    private String text;

    public String getText() { return this.text; }

    public void setText(String text) { this.text = text; }

    private String href;

    public String getHref() { return this.href; }

    public void setHref(String href) { this.href = href; }

  /*  private Style3 style;

    public Style3 getStyle() { return this.style; }

    public void setStyle(Style3 style) { this.style = style; }*/

    protected Anchor(Parcel in) {
        text = in.readString();
        href = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(href);
    }

    @SuppressWarnings("unused")
    public static final Creator<Anchor> CREATOR = new Creator<Anchor>() {
        @Override
        public Anchor createFromParcel(Parcel in) {
            return new Anchor(in);
        }

        @Override
        public Anchor[] newArray(int size) {
            return new Anchor[size];
        }
    };
}