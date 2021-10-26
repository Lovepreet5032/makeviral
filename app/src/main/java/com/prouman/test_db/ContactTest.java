package com.prouman.test_db;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jcs on 11/22/2016.
 */

public class ContactTest implements Parcelable {
    private String id;
    private String name;
    private String  phone;
    private String mobile_defaut;
    private String email_defaut;
    private boolean isChecked;
    private byte[] contactImage = null;
    private int isPositionExpanded=-1;
    private String emailShared;
    private String mobileShared;
    private boolean isSelected;
    private String email_mOnline;
    private String mobile_mOnline;
    private String Email_Id;
    public ContactTest(String id, String name, String phone,String email_mOnline,String mobile_mOnline,
                       byte[] contactImage,String mobile_defaut,String email_defaut,String Email_Id) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email_mOnline= email_mOnline;
        this.mobile_mOnline=mobile_mOnline;
        this.contactImage = contactImage;
        this.mobile_defaut=mobile_defaut;
        this.email_defaut=email_defaut;
        this.Email_Id=Email_Id;
        this.contactImage=contactImage;

    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public ContactTest() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getContactImage() {
        return contactImage;
    }

    public void setContactImage(byte[] contactImage) {
        this.contactImage = contactImage;
    }


    public String getEmail_mOnline() {
        return email_mOnline;
    }

    public void setEmail_mOnline(String email_mOnline) {
        this.email_mOnline = email_mOnline;
    }

    public String getMobile_mOnline() {
        return mobile_mOnline;
    }

    public void setMobile_mOnline(String mobile_mOnline) {
        this.mobile_mOnline = mobile_mOnline;
    }

    protected ContactTest(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        isSelected = in.readByte() != 0x00;
        email_mOnline = in.readString();
        mobile_mOnline = in.readString();
        mobile_defaut=in.readString();
        email_defaut=in.readString();
        Email_Id=in.readString();
        contactImage = in.createByteArray();
     //   if(null != contactImage && contactImage.length>0) {
//            contactImage = new byte[in.readInt()];
//            in.readByteArray(contactImage);
//        }
//          else{
//            contactImage=in.readByteArray(contactImage);
//        }
        //}

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeByte((byte) (isSelected ? 0x01 : 0x00));
        dest.writeString(email_mOnline);
        dest.writeString(mobile_mOnline);
        dest.writeString(mobile_defaut);
        dest.writeString(email_defaut);
        dest.writeString(Email_Id);
//       if(null != contactImage && contactImage.length>0) {
//           dest.writeInt(contactImage.length);
//
//           dest.writeByteArray(contactImage);
//      }
//      else{
//           byte[] b=new byte[1];
//           b[0]=0x01;
//           dest.writeByteArray(b);
//       }
        dest.writeByteArray(contactImage);
    }

    @SuppressWarnings("unused")
    public static final Creator<ContactTest> CREATOR = new Creator<ContactTest>() {
        @Override
        public ContactTest createFromParcel(Parcel in) {
            return new ContactTest(in);
        }

        @Override
        public ContactTest[] newArray(int size) {
            return new ContactTest[size];
        }
    };

    public String getMobile_defaut() {
        return mobile_defaut;
    }

    public void setMobile_defaut(String mobile_defaut) {
        this.mobile_defaut = mobile_defaut;
    }

    public String getEmail_defaut() {
        return email_defaut;
    }

    public void setEmail_defaut(String email_defaut) {
        this.email_defaut = email_defaut;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getIsPositionExpanded() {
        return isPositionExpanded;
    }

    public void setIsPositionExpanded(int isPositionExpanded) {
        this.isPositionExpanded = isPositionExpanded;
    }

    public String getEmailShared() {
        return emailShared;
    }

    public void setEmailShared(String emailShared) {
        this.emailShared = emailShared;
    }

    public String getMobileShared() {
        return mobileShared;
    }

    public void setMobileShared(String mobileShared) {
        this.mobileShared = mobileShared;
    }

    public String getEmail_Id() {
        return Email_Id;
    }

    public void setEmail_Id(String email_Id) {
        Email_Id = email_Id;
    }
}