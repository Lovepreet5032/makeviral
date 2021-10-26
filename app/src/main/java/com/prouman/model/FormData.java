package com.prouman.model;

/**
 * Created by jcs on 12/15/2016.
 */

    public class FormData {
    int form_id;
    String form_name;

 /*   public FormData(int form_id, String form_name) {
        this.form_id = form_id;
        this.form_name = form_name;
    }*/

    public int getForm_id() {
        return form_id;
    }

    public void setForm_id(int form_id) {
        this.form_id = form_id;
    }

    public String getForm_name() {
        return form_name;
    }

    public void setForm_name(String form_name) {
        this.form_name = form_name;
    }
}
