package com.prouman.ninjaforms;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by om on 2/19/2017.
 */
public class Form implements Parcelable {
    private String id;

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    private String form_name;
    private String button_text;
    private String app_home_icon;
    private String app_home_title;
    public String getFormName() { return this.form_name; }

    public void setFormName(String form_name) { this.form_name = form_name; }

    private ArrayList<FormField> form_fields;

    public ArrayList<FormField> getFormFields() { return this.form_fields; }

    public void setFormFields(ArrayList<FormField> form_fields) { this.form_fields = form_fields; }

    protected Form(Parcel in) {
        id = in.readString();
        form_name = in.readString();
        button_text=in.readString();
        if (in.readByte() == 0x01) {
            form_fields = new ArrayList<FormField>();
            in.readList(form_fields, FormField.class.getClassLoader());
        } else {
            form_fields = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(form_name);
        dest.writeString(button_text);
        if (form_fields == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(form_fields);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Form> CREATOR = new Creator<Form>() {
        @Override
        public Form createFromParcel(Parcel in) {
            return new Form(in);
        }

        @Override
        public Form[] newArray(int size) {
            return new Form[size];
        }
    };

    public String getButton_text() {
        return button_text;
    }

    public void setButton_text(String button_text) {
        this.button_text = button_text;
    }

    public String getApp_home_icon() {
        return app_home_icon;
    }

    public void setApp_home_icon(String app_home_icon) {
        this.app_home_icon = app_home_icon;
    }

    public String getGetApp_home_title() {
        return app_home_title;
    }

    public void setGetApp_home_title(String getApp_home_title) {
        this.app_home_title = getApp_home_title;
    }
}
