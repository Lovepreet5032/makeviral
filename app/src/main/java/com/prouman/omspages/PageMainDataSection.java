package com.prouman.omspages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aseemchoudhary on 03/01/19.
 */

public class PageMainDataSection {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("section_name")
    @Expose
    private String sectionName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
