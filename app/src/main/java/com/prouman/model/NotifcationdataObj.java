package com.prouman.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by om on 2/17/2017.
 */

public class NotifcationdataObj implements Parcelable {
    private int notification_id;
    private String str_tittle;
    private String str_description;
    private String str_userimage;
    private String str_fromuser;
    private String str_date;
    private String str_username;
    private String str_isread;
 //   private String seen_status;
    public String getStr_tittle() {
        return str_tittle;
    }

    public void setStr_tittle(String str_tittle) {
        this.str_tittle = str_tittle;
    }

    public String getStr_description() {
        return str_description;
    }

    public void setStr_description(String str_description) {
        this.str_description = str_description;
    }

    public String getStr_userimage() {
        return str_userimage;
    }

    public void setStr_userimage(String str_userimage) {
        this.str_userimage = str_userimage;
    }

    public String getStr_fromuser() {
        return str_fromuser;
    }

    public void setStr_fromuser(String str_fromuser) {
        this.str_fromuser = str_fromuser;
    }

    public String getStr_date() {
        return str_date;
    }

    public void setStr_date(String str_date) {
        this.str_date = str_date;
    }

    public int getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(int notification_id) {
        this.notification_id = notification_id;
    }

    public String getStr_username() {
        return str_username;
    }

    public void setStr_username(String str_username) {
        this.str_username = str_username;
    }
    public NotifcationdataObj(){}
    public NotifcationdataObj(Parcel in) {
        notification_id = in.readInt();
        str_tittle = in.readString();
        str_description = in.readString();
        str_userimage = in.readString();
        str_fromuser = in.readString();
        str_date = in.readString();
        str_username = in.readString();
        str_isread=in.readString();
        //seen_status=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(notification_id);
        dest.writeString(str_tittle);
        dest.writeString(str_description);
        dest.writeString(str_userimage);
        dest.writeString(str_fromuser);
        dest.writeString(str_date);
        dest.writeString(str_username);
        dest.writeString(str_isread);
       // dest.writeString(seen_status);
    }

    @SuppressWarnings("unused")
    public static final Creator<NotifcationdataObj> CREATOR = new Creator<NotifcationdataObj>() {
        @Override
        public NotifcationdataObj createFromParcel(Parcel in) {
            return new NotifcationdataObj(in);
        }

        @Override
        public NotifcationdataObj[] newArray(int size) {
            return new NotifcationdataObj[size];
        }
    };

    public String getStr_isread() {
        return str_isread;
    }

    public void setStr_isread(String str_isread) {
        this.str_isread = str_isread;
    }

//    public String getSeen_status() {
//        return seen_status;
//    }
//
//    public void setSeen_status(String seen_status) {
//        this.seen_status = seen_status;
//    }
}