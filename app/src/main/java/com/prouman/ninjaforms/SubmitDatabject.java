package com.prouman.ninjaforms;

import android.view.View;

/**
 * Created by om on 2/22/2017.
 */
public class SubmitDatabject {
    private String strType;
    private String strId;
    private String strRequired;
    private String value;
    private View view;
    private String field_name;

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getStrRequired() {
        return strRequired;
    }

    public void setStrRequired(String strRequired) {
        this.strRequired = strRequired;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }
}
