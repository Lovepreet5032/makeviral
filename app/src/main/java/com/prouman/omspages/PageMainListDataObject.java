package com.prouman.omspages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aseemchoudhary on 03/01/19.
 */

public class PageMainListDataObject {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("sections")
    @Expose
    private List<PageMainDataSection> sections = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PageMainDataSection> getSections() {
        return sections;
    }

    public void setSections(List<PageMainDataSection> sections) {
        this.sections = sections;
    }

}
