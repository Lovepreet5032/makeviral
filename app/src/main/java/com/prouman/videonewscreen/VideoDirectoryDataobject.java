package com.prouman.videonewscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aseemchoudhary on 14/02/18.
 */

public class VideoDirectoryDataobject {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("directory")
    @Expose
    private List<VideoDirectoryData> directory = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<VideoDirectoryData> getDirectory() {
        return directory;
    }

    public void setDirectory(List<VideoDirectoryData> directory) {
        this.directory = directory;
    }

}


