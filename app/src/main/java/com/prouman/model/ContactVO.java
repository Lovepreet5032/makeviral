package com.prouman.model;

import android.net.Uri;

/**
 * Created by dsingh on 11/2/2016.
 */

public class ContactVO {
    //private String ContactImage;
    private String ContactName;
    private String ContactNumber;
    private Uri contactImage;
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }
    public Uri getContactImage() {
        return contactImage;
    }

    public void setContactImage(Uri contactImage) {
        this.contactImage = contactImage;
    }
}
