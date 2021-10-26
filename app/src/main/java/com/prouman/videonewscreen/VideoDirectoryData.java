package com.prouman.videonewscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aseemchoudhary on 14/02/18.
 */

class VideoDirectoryData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("directory_name")
    @Expose
    private String directoryName;
    @SerializedName("app_icon")
    @Expose
    private String appIcon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

}

