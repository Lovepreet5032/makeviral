package com.prouman.contact_act;

/**
 * Created by jcs on 11/22/2016.
 */

public class ContactTest {
    private String id, name, phone;
    private byte[] contactImage = null;

    private String mOnline;
    public ContactTest(String id, String name, String phone,String mOnline, byte[] contactImage) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.mOnline= mOnline;
        this.contactImage = contactImage;
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


    public String getmOnline() {
        return mOnline;
    }

    public void setmOnline(String mOnline) {
        this.mOnline = mOnline;
    }
}
