package com.prouman.model;

import com.prouman.ninjaforms.Form;

/**
 * Created by om on 2/25/2017.
 */
public class HomeItemObjectModel {
    private int name;
    private int photo;
    private String img_url;
    private String strFromname;
    private Form form;


    public HomeItemObjectModel(int name, int photo, String img_url, String strFromname, Form form) {
        this.name = name;
        this.photo = photo;
        this.img_url = img_url;
        this.strFromname = strFromname;
        this.form=form;
    }


    public int getPhoto() {
        return this.photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getStrFromname() {
        return strFromname;
    }

    public void setStrFromname(String strFromname) {
        this.strFromname = strFromname;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}